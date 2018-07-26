package com.cegz.api.mongo;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * Mongo数据库连接，单例模式（读写分离）
 * @author 		GaoWang
 * @date		2017/02/24
 */
@Configuration
public class MongoUtil {
	
	
	@Value("${mongo.read.url}")
	private static String readUrl;
	@Value("${mongo.read.user}")
	private static String readUser;
	
	@Value("${mongo.read.db")
	private static String readDb;
	
	@Value("${mongo.read.port}")
	private static Integer readPort;
	
	@Value("${mongo.read.password}")
	private static String readPassword;
	
	private static MongoClient mongoReadClient;
    //private static MongoClient mongoWriteClient;
	
    static {
    	mongoReadClient = new MongoClient(readUrl, readPort);
		// 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
		//boolean auth = db.authenticate(readUser, readPassword);
		Builder options = new MongoClientOptions.Builder();
		options.cursorFinalizerEnabled(true);
		// options.autoConnectRetry(true);// 自动重连true
		// options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
		options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
		options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
		options.maxWaitTime(5000); //
		options.socketTimeout(0);// 套接字超时时间，0无限制
		options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
		options.writeConcern(WriteConcern.SAFE);//
		options.build();
		
	}

    //获取读mongo连接
	public static MongoClient getMongoReadClient() {
		return mongoReadClient;
	}
//	//获取写mongo连接
//	public static MongoClient getWriteMongoClient() {
//		return mongoWriteClient;
//	}
	
}
