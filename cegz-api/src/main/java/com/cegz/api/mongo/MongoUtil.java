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

/**
 * Mongo数据库连接，单例模式（读写分离）
 * @author 		GaoWang
 * @date		2017/02/24
 */
public class MongoUtil {
	
	
	private static MongoClient mongoReadClient;
    //private static MongoClient mongoWriteClient;
	
    static {
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
		//options.writeConcern(WriteConcern.FSYNC_SAFE);//
		MongoClientOptions myOptions = options.build();
		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
	
		//读mongo配置
		ServerAddress address = new ServerAddress(MongoConfig.readUrl, MongoConfig.readPort);
		seeds.add(address);
		MongoCredential credential = MongoCredential.createScramSha1Credential(MongoConfig.readUser,
				MongoConfig.readDb, MongoConfig.readPassword.toCharArray());
		
		mongoReadClient = new MongoClient(seeds, credential, myOptions);
		
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
