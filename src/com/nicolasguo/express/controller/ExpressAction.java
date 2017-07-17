package com.nicolasguo.express.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nicolasguo.express.condition.impl.ExpressCondition;
import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.service.AreaService;
import com.nicolasguo.express.service.CustomerService;
import com.nicolasguo.express.service.ExpressService;
import com.nicolasguo.express.util.DateEditor;
import com.nicolasguo.express.util.ExcelUtil;
import com.nicolasguo.express.util.UUID;

@Controller
@RequestMapping("/express")
public class ExpressAction {

	@Resource(name = "expressService")
	private ExpressService<Express, String> expressService;

	@Resource(name = "areaService")
	private AreaService<Area, String> areaService;

	@Resource(name = "customerService")
	private CustomerService<Customer, String> customerService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	private Customer retrieveRecipient(String phoneNumber, String name, String areacode) {
		Area area = areaService.findAreaByProperty("code", areacode).get(0);
		List<Customer> customerList = customerService.findCustomerByProperty("phoneNumber", phoneNumber);
		Customer recipient = null;
		if (customerList.size() == 0) {
			recipient = new Customer();
			recipient.setId(UUID.generateUUID());
			recipient.setName(name);
			recipient.setPhoneNumber(phoneNumber);
			recipient.setArea(area);
			customerService.saveCustomer(recipient);
			recipient = customerService.loadCustomer(recipient.getId());
		} else {
			recipient = customerList.get(0);
		}
		return recipient;
	}

	@RequestMapping("/create.action")
	public @ResponseBody String createExpress(@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String areacode,
			@RequestParam("arriveDate") Date arriveDate, @RequestParam("status") int status) throws ParseException {
		Area area = areaService.findAreaByProperty("code", areacode).get(0);
		Customer recipient = retrieveRecipient(phoneNumber, name, areacode);
		Express express = new Express();
		express.setId(UUID.generateUUID());
		express.setCreateTime(new Date());
		express.setDest(area);
		express.setRecipient(recipient);
		if (status == 0) {
			express.setSignTime(null);
		} else if (status != express.getStatus()) {
			express.setSignTime(new Date());
		}
		express.setStatus(status);
		express.setArriveDate(arriveDate);
		expressService.saveExpress(express);
		return "create";
	}

	@RequestMapping("/update.action")
	public @ResponseBody String updateExpress(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String areacode,
			@RequestParam("arriveDate") Date arriveDate, @RequestParam("status") int status) throws ParseException {
		Area area = areaService.findAreaByProperty("code", areacode).get(0);
		Customer recipient = retrieveRecipient(phoneNumber, name, areacode);
		Express express = expressService.getExpress(id);
		if (express != null) {
			express.setDest(area);
			express.setRecipient(recipient);
			if (status == 0) {
				express.setSignTime(null);
			} else if (status != express.getStatus()) {
				express.setSignTime(new Date());
			}
			express.setStatus(status);
			express.setArriveDate(arriveDate);
			express.setModifyTime(new Date());
			expressService.updateExpress(express);

		}
		return "update";
	}

	@RequestMapping("/remove.action")
	public @ResponseBody String removeExpress(@RequestParam("ids[]") List<String> ids) {
		ExpressCondition condition = new ExpressCondition();
		condition.setIds(ids);
		List<Express> removeList = expressService.findExpressByCondition(condition);
		expressService.deleteExpresss(removeList);
		return "success";
	}

	@RequestMapping("/search.action")
	public ModelAndView searchExpress(@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo, HttpSession session) throws ParseException {
		ExpressCondition condition = new ExpressCondition();
		if (startDate != null) {
			condition.setStartDate(startDate);
		}
		if (endDate != null) {
			condition.setEndDate(endDate);
		}
		condition.setEndingNumber(phoneNumber);
		condition.setStatus(status);
		condition.setArea(area);
		session.setAttribute("expressCondition", condition);
		Page<Express> pageEntity = new Page<Express>();
		pageEntity.setPageNo(pageNo);
		pageEntity = expressService.findExpressByCondition(condition, pageEntity);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("expressPage", pageEntity);
		return mav;
	}

	@RequestMapping("/list.action")
	public @ResponseBody List<Express> expressList() {
		return expressService.findAll();
	}

	@RequestMapping("/sign.action")
	public @ResponseBody String signExpress(@RequestParam("ids[]") List<String> ids) {
		ExpressCondition condition = new ExpressCondition();
		condition.setIds(ids);
		List<Express> signList = expressService.findExpressByCondition(condition);
		signList.forEach(new Consumer<Express>() {
			@Override
			public void accept(Express express) {
				if (express.getStatus() == 0) {
					express.setStatus(1);
					express.setSignTime(new Date());
					expressService.updateExpress(express);
				}
			}
		});
		return "success";
	}

	@RequestMapping("/retrieve.action")
	public @ResponseBody Express retrieveExpress(@RequestParam String id) {
		Express express = expressService.getExpress(id);
		return express;
	}

	@RequestMapping("/export.action")
	public void exportExpress(HttpServletResponse response, HttpSession session) throws IOException {
		ExpressCondition condition = (ExpressCondition) session.getAttribute("expressCondition");
		List<Express> expressList = expressService.findExpressByCondition(condition);
		List<Map<String, Object>> list = createExcelRecord(expressList);
		String[] columnNames = new String[] { "序号", "姓名", "地址", "手机号码", "状态" };
		String keys[] = new String[] { "rownum", "name", "dest", "phoneNumber", "status" };
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((format.format(new Date()) + "_导出.xls").getBytes(), "iso-8859-1"));
		ExcelUtil.createWorkBook(list, columnNames, keys).write(response.getOutputStream());
	}

	private List<Map<String, Object>> createExcelRecord(List<Express> expressList) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", format.format(new Date()));
		listmap.add(map);
		Express express = null;
		for (int cnt = 0; cnt < expressList.size(); cnt++) {
			express = expressList.get(cnt);
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.put("rownum", cnt + 1);
			mapValue.put("name", express.getRecipient().getName());
			mapValue.put("dest", express.getDest().getName());
			mapValue.put("phoneNumber", express.getRecipient().getPhoneNumber());
			mapValue.put("status", express.getStatus() == 1 ? "已签收" : "未签收");
			listmap.add(mapValue);
		}
		return listmap;
	}
}
