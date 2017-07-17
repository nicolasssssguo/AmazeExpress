package com.nicolasguo.express.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.service.AreaService;

@Controller
@RequestMapping("/area")
public class AreaAction {
	
	@Resource(name = "areaService")
	private AreaService<Area, String> areaService;
	
	@RequestMapping("/list.action")
	public @ResponseBody List<Area> areaList(){
		Area parent = areaService.getArea("350681110000");
		return areaService.findAreaByProperty("parent", parent);
	}

}
