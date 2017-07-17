package com.nicolasguo.express.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nicolasguo.express.condition.impl.CustomerCondition;
import com.nicolasguo.express.condition.impl.ExpressCondition;
import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.service.AreaService;
import com.nicolasguo.express.service.CustomerService;
import com.nicolasguo.express.service.ExpressService;
import com.nicolasguo.express.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerAction {

	@Resource(name = "customerService")
	private CustomerService<Customer, String> customerService;

	@Resource(name = "areaService")
	private AreaService<Area, String> areaService;

	@Resource(name = "expressService")
	private ExpressService<Express, String> expressService;

	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo) {
		Page<Customer> pageEntity = new Page<Customer>();
		pageEntity.setPageNo(pageNo);
		CustomerCondition condition = new CustomerCondition();
		pageEntity = customerService.findCustomerByCondition(condition, pageEntity);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer");
		mav.addObject("customerPage", pageEntity);
		return mav;
	}

	@RequestMapping(value = "/create.action", produces = "text/html;charset=UTF-8")
	public @ResponseBody String createCustomer(@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String areacode) {
		String message = "success";
		List<Customer> customerList = customerService.findCustomerByProperty("phoneNumber", phoneNumber);
		if (customerList.size() == 0) {
			Area area = areaService.findAreaByProperty("code", areacode).get(0);
			Customer customer = new Customer();
			customer.setId(UUID.generateUUID());
			customer.setCreateTime(new Date());
			customer.setName(name);
			customer.setArea(area);
			customer.setPhoneNumber(phoneNumber);
			customerService.saveCustomer(customer);
		} else {
			message = "该手机号码已存在";
		}
		return message;
	}

	@RequestMapping(value = "/update.action", produces = "text/html;charset=UTF-8")
	public @ResponseBody String updateCustomer(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String areacode) {
		String message = "success";
		Customer customer = customerService.getCustomer(id);
		if (customer != null) {
			List<Customer> customerList = customerService.findCustomerByProperty("phoneNumber", phoneNumber);
			if (customerList.size() > 0) {
				Customer result = customerList.get(0);
				if (!result.getId().equals(id)) {
					message = "该手机号码已被占用";
					return message;
				}
			}
			Area area = areaService.findAreaByProperty("code", areacode).get(0);
			customer.setModifyTime(new Date());
			customer.setName(name);
			customer.setArea(area);
			customer.setPhoneNumber(phoneNumber);
			customerService.updateCustomer(customer);
		}
		return message;
	}

	@RequestMapping("/remove.action")
	public @ResponseBody String removeCustomer(@RequestParam("ids[]") List<String> ids) {
		CustomerCondition customerCondition = new CustomerCondition();
		ExpressCondition expressCondition = new ExpressCondition();
		customerCondition.setIds(ids);
		List<Customer> customerRemoveList = customerService.findCustomerByCondition(customerCondition);
		List<Express> expressRemoveList = new ArrayList<Express>();
		customerRemoveList.forEach(new Consumer<Customer>() {
			@Override
			public void accept(Customer c) {
				expressCondition.setRecipient(c);
				expressRemoveList.addAll(expressService.findExpressByCondition(expressCondition));
			}
		});
		expressService.deleteExpresss(expressRemoveList);
		customerService.deleteCustomers(customerRemoveList);
		return "success";
	}

	@RequestMapping("/retrieve.action")
	public @ResponseBody Customer retrieveCustomer(@RequestParam String id) {
		Customer customer = customerService.getCustomer(id);
		return customer;
	}

	@RequestMapping("/searchbykeynum.action")
	public @ResponseBody List<Customer> searchByKeynumber(@RequestParam String keynumber) {
		CustomerCondition condition = new CustomerCondition();
		condition.setKeynumber(keynumber);
		List<String> result = new ArrayList<String>();
		List<Customer> customerList = customerService.findCustomerByCondition(condition);
		customerList.forEach(customer -> result.add(customer.toString()));
		return customerList;
	}

	@RequestMapping("/search.action")
	public ModelAndView searchCustomer(@RequestParam(value="name", required=false) String name, @RequestParam String phoneNumber,
			@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo) {
		Page<Customer> pageEntity = new Page<Customer>();
		pageEntity.setPageNo(pageNo);
		CustomerCondition condition = new CustomerCondition();
		condition.setName(name);
		condition.setPhoneNumber(phoneNumber);
		pageEntity = customerService.findCustomerByCondition(condition, pageEntity);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer");
		mav.addObject("customerPage", pageEntity);
		return mav;
	}
}
