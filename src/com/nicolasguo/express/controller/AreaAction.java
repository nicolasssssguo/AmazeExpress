package com.nicolasguo.express.controller;

import java.util.List;
import com.nicolasguo.express.entity.Area;
import com.nicolasguo.express.service.AreaService;

public class AreaAction {
	
	private AreaService<Area, String> areaService;
	
	public List<Area> areaList(){
		Area parent = areaService.getArea("350681110000");
		return areaService.findAreaByProperty("parent", parent);
	}

}
