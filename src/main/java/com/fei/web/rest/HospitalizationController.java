package com.fei.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fei.domain.Hospitalization;
import com.fei.service.Dao;


@Controller
public class HospitalizationController {
	
	@Autowired
	Dao dao;


	@RequestMapping("/data") 
	@ResponseBody
	public List<Hospitalization> retrieveData() {
		return dao.retrieve();
	}
	


}