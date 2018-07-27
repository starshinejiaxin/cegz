package com.cegz.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cegz.api.mongo.MongoUtil;

@RestController
@RequestMapping("test")
public class TestController {
	@GetMapping("mongo")
	public String get() {
		MongoUtil.getMongoReadClient();
		return "1";
	}
}
