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

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicolasguo.express.condition.impl.ExpressCondition;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.entity.Result;
import com.nicolasguo.express.service.CustomerService;
import com.nicolasguo.express.service.ExpressService;
import com.nicolasguo.express.util.Constants;
import com.nicolasguo.express.util.DateEditor;
import com.nicolasguo.express.util.ExcelUtil;
import com.nicolasguo.express.util.UUID;

@Controller
@RequestMapping("/express")
public class ExpressAction {

	@Resource(name = "expressService")
	private ExpressService<Express, String> expressService;

	@Resource(name = "customerService")
	private CustomerService<Customer, String> customerService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	private Customer retrieveRecipient(String phoneNumber, String name, String area) {
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

	@RequestMapping(value = "/create.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public @ResponseBody Result<String> createExpress(@RequestParam("name") String name,
			@RequestParam("phone_number") String phoneNumber, @RequestParam("area") String area,
			@RequestParam("arrive_date") Date arriveDate) throws ParseException {
		Customer recipient = retrieveRecipient(phoneNumber, name, area);
		Express express = new Express();
		express.setId(UUID.generateUUID());
		express.setCreateTime(new Date());
		express.setArea(area);
		express.setRecipient(recipient);
		express.setStatus(Constants.NOT_SIGN);
		express.setArriveDate(arriveDate);
		expressService.saveExpress(express);
		return new Result<String>(HttpStatus.OK.value(), null);
	}

	@RequestMapping("/edit.do/{id}")
	public ModelAndView editExpress(@PathVariable String id, @RequestParam String url, RedirectAttributes attributes){
		ModelAndView mav = new ModelAndView();
		Express express = expressService.getExpress(id);
		if(express != null){
			attributes.addFlashAttribute("url", url);
			attributes.addFlashAttribute("express", express);
			mav.setViewName("redirect:/express/edit");
		}
		return mav;
	}
	
	@RequestMapping("/update.do")
	public @ResponseBody Result<String> updateExpress(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phone_number") String phoneNumber, @RequestParam("area") String area,
			@RequestParam("arrive_date") Date arriveDate, @RequestParam("status") int status) throws ParseException {
		Customer recipient = retrieveRecipient(phoneNumber, name, area);
		Express express = expressService.getExpress(id);
		if (express != null) {
			express.setArea(area);
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
		return new Result<String>(HttpStatus.OK.value(), null);
	}

	@RequestMapping("/remove.do")
	public @ResponseBody String removeExpress(@RequestParam("ids[]") List<String> ids) {
		ExpressCondition condition = new ExpressCondition();
		condition.setIds(ids);
		List<Express> removeList = expressService.findExpressByCondition(condition);
		expressService.deleteExpresss(removeList);
		return "success";
	}

	@RequestMapping("/search.do")
	public ModelAndView searchExpress(@RequestParam(value = "startDate", required = false) Date startDate,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "endDate", required = false) Date endDate,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "start", required = false, defaultValue = "0") int start, HttpSession session) throws ParseException {
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
		pageEntity.setStart(start);
		pageEntity = expressService.findExpressByCondition(condition, pageEntity);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("expressPage", pageEntity);
		return mav;
	}

	@RequestMapping("/list.do")
	public @ResponseBody Page<Express> expressList(@RequestParam(value = "iDisplayStart", required = false, defaultValue = "0") int start,
			@RequestParam(value = "sSearch", required = false) String queryParam, HttpSession session) {
		Page<Express> pageEntity = new Page<Express>();
		pageEntity.setStart(start);
		ExpressCondition condition = new ExpressCondition();
		if(StringUtils.isNotBlank(queryParam)){
			if(queryParam.matches("\\d+")){
				condition.setEndingNumber(queryParam);
			}
		}
		Boolean todayOnly = (Boolean) session.getAttribute("TODAY_ONLY");
		if(todayOnly == null || todayOnly){
			condition.setStartDate(new Date());
			condition.setEndDate(new Date());
		}
		session.setAttribute("EXPRESS_CONDITION", condition);
		pageEntity = expressService.findExpressByCondition(condition, pageEntity);
		return pageEntity;
	}

	@RequestMapping("/sign.do")
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
	
	@RequestMapping("/todayonly.do")
	public @ResponseBody Result<String> setDisplayMode(@RequestParam("todayonly") boolean todayonly, HttpSession session) {
		session.setAttribute("TODAY_ONLY", todayonly);
		return new Result<String>(HttpStatus.OK.value(), null);
	}

	@RequestMapping("/retrieve.do")
	public @ResponseBody Express retrieveExpress(@RequestParam String id) {
		Express express = expressService.getExpress(id);
		return express;
	}

	@RequestMapping("/export.do")
	public void exportExpress(HttpServletResponse response, HttpSession session) throws IOException {
		ExpressCondition condition = (ExpressCondition) session.getAttribute("EXPRESS_CONDITION");
		List<Express> expressList = expressService.findExpressByCondition(condition);
		List<Map<String, Object>> list = createExcelRecord(expressList);
		String[] columnNames = new String[] { "序号", "姓名", "地址", "手机号码", "状态" };
		String keys[] = new String[] { "rownum", "name", "area", "phoneNumber", "status" };
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
			mapValue.put("area", express.getArea());
			mapValue.put("phoneNumber", express.getRecipient().getPhoneNumber());
			mapValue.put("status", express.getStatus() == 1 ? "已签收" : "未签收");
			listmap.add(mapValue);
		}
		return listmap;
	}
}
