package com.cegz.api.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoConfig {
	@Value("${mongo.read.url}")
	public static String readUrl;
	@Value("${mongo.read.user}")
	public static String readUser;
	
	@Value("${mongo.read.db")
	public static String readDb;
	
	@Value("${mongo.read.port}")
	public static Integer readPort;
	
	@Value("${mongo.read.password}")
	public static String readPassword;
	
}
