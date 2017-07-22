package com.nicolasguo.express.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicolasguo.express.condition.impl.CustomerCondition;
import com.nicolasguo.express.condition.impl.ExpressCondition;
import com.nicolasguo.express.entity.Customer;
import com.nicolasguo.express.entity.Express;
import com.nicolasguo.express.entity.Page;
import com.nicolasguo.express.service.CustomerService;
import com.nicolasguo.express.service.ExpressService;
import com.nicolasguo.express.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerAction {

	@Resource(name = "customerService")
	private CustomerService<Customer, String> customerService;

	@Resource(name = "expressService")
	private ExpressService<Express, String> expressService;

	@RequestMapping("/list.do")
	public @ResponseBody Page<Customer> index(@RequestParam(value = "iDisplayStart", required = false, defaultValue = "0") int start,
			@RequestParam(value = "sSearch", required = false) String queryParam) {
		Page<Customer> pageEntity = new Page<Customer>();
		pageEntity.setStart(start);
		CustomerCondition condition = new CustomerCondition();
		if(StringUtils.isNotBlank(queryParam)){
			if(queryParam.matches("\\d+")){
				condition.setEndingNumber(queryParam);
			}
		}
		pageEntity = customerService.findCustomerByCondition(condition, pageEntity);
		return pageEntity;
	}

	@RequestMapping(value = "/create.do", produces = "text/html;charset=UTF-8")
	public @ResponseBody String createCustomer(@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String area) {
		String message = "success";
		List<Customer> customerList = customerService.findCustomerByProperty("phoneNumber", phoneNumber);
		if (customerList.size() == 0) {
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
	
	@RequestMapping("/edit.do/{id}")
	public ModelAndView editCustomer(@PathVariable String id, @RequestParam String url, RedirectAttributes attributes){
		ModelAndView mav = new ModelAndView();
		Customer customer = customerService.getCustomer(id);
		if(customer != null){
			attributes.addFlashAttribute("url", url);
			attributes.addFlashAttribute("customer", customer);
			mav.setViewName("redirect:/customer/edit");
		}
		return mav;
	}

	@RequestMapping(value = "/update.do", produces = "text/html;charset=UTF-8")
	public @ResponseBody String updateCustomer(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("area") String area) {
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
			customer.setModifyTime(new Date());
			customer.setName(name);
			customer.setArea(area);
			customer.setPhoneNumber(phoneNumber);
			customerService.updateCustomer(customer);
		}
		return message;
	}

	@RequestMapping("/remove.do")
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

	@RequestMapping("/retrieve.do")
	public @ResponseBody Customer retrieveCustomer(@RequestParam String id) {
		Customer customer = customerService.getCustomer(id);
		return customer;
	}

	@RequestMapping("/search.do")
	public @ResponseBody List<Customer> searchByKeynumber(@RequestParam String endingNumber) {
		CustomerCondition condition = new CustomerCondition();
		condition.setEndingNumber(endingNumber);
		List<String> result = new ArrayList<String>();
		List<Customer> customerList = customerService.findCustomerByCondition(condition);
		customerList.forEach(customer -> result.add(customer.toString()));
		return customerList;
	}
}
