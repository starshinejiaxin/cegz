package com.cegz.api.mongo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
/**
 * mongo配置信息实体类
 *
 * @author lijiaxin
 * @date 2018年7月27日
 */
@Component
@Validated
public class MongoSettingsProperties {
	@NotBlank
	 private String database;
	 
	 @NotEmpty
	 private List<String> hosts;
	 
	 @NotEmpty
	 private List<Integer> ports;
	 private String replicaSet;
	 private String username;
	 private String password;
	 private String authenticationDatabase;
	 private Integer minConnectionsPerHost = 10;
	 private Integer connectionsPerHost = 2;
	 public MongoSettingsProperties() {
		 
	 }
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public List<String> getHosts() {
		return hosts;
	}
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}
	public List<Integer> getPorts() {
		return ports;
	}
	public void setPorts(List<Integer> ports) {
		this.ports = ports;
	}
	public String getReplicaSet() {
		return replicaSet;
	}
	public void setReplicaSet(String replicaSet) {
		this.replicaSet = replicaSet;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthenticationDatabase() {
		return authenticationDatabase;
	}
	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}
	public Integer getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}
	public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}
	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}
	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}
	 
	 
}
