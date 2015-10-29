package com.fei.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fei.domain.Hospitalization;


@Service
public class Dao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	public List<Hospitalization> retrieve() {
		return jdbcTemplate.query("select * from hospitalization", (rs, rowNum) -> new Hospitalization(
				rs.getString("numOfDays"),
				rs.getString("diagnosis"),
				rs.getString("program"),
				rs.getString("region"),
				rs.getString("numOfAdmissions"),
				rs.getString("costs")
				));
		
	}
	
	
}
