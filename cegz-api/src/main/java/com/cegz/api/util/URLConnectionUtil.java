package com.cegz.api.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class URLConnectionUtil {
	private static final String SERVLET_POST = "POST";
	private static final String SERVLET_GET = "GET";
	private static final String SERVLET_DELETE = "DELETE";
	private static final String SERVLET_PUT = "PUT";

	private static String prepareParam(Map<String, Object> paramMap) {
		StringBuffer sb = new StringBuffer();
		if (paramMap.isEmpty()) {
			return "";
		} else {
			for (String key : paramMap.keySet()) {
				String value = String.valueOf(paramMap.get(key));
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				} else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}

	public static String doPost(String urlStr, Map<String, Object> paramMap)
			throws Exception {
	    	String result = "";
		
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_POST);
			String paramStr = prepareParam(paramMap);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(paramStr.toString().getBytes("utf-8"));
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			String line;

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
	 
		return result;
	}

	public static String doGet(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String result = "";
		try {
			String paramStr = prepareParam(paramMap);
			// logger.info("doGet的参数：" + paramStr);
			if (paramStr == null || paramStr.trim().length() < 1) {

			} else {
				urlStr += "?" + paramStr;
			}
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(SERVLET_GET);
			conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");

			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));// 设置编码,否则中文乱码

			String line;

			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void doPut(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(SERVLET_PUT);
		String paramStr = prepareParam(paramMap);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(paramStr.toString().getBytes("utf-8"));
		os.close();

		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream(),"utf-8"));//设置编码,否则中文乱码
		 * String line; String result = ""; while ((line = br.readLine()) !=
		 * null) { result += "/n" + line; } System.out.println(result);
		 * br.close();
		 */

	}

	public static void doDelete(String urlStr, Map<String, Object> paramMap)
			throws Exception {
		String paramStr = prepareParam(paramMap);
		if (paramStr == null || paramStr.trim().length() < 1) {

		} else {
			urlStr += "?" + paramStr;
		}
		// System.out.println(urlStr);
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod(SERVLET_DELETE);
		if (conn.getResponseCode() == 200) {
			System.out.println("成功");
		} else {
			System.out.println(conn.getResponseCode());
		}
	}

	public static void main(String[] args) throws Exception {
		String urlStr = "http://localhost:8080/SwTest/ReceiveDataServlet";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", "张三");
		map.put("password", "88888");
		// URLConnectionUtil.doGet(urlStr, map);
		// URLConnectionUtil.doPost(urlStr, map);
		// URLConnectionUtil.doPut(urlStr, map);
		URLConnectionUtil.doDelete(urlStr, map);

	}

}
