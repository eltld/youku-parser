package com.bebeeru.youkuparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	public static String get(String urlString) {
		HttpURLConnection 	conn = null;
		BufferedReader		br 	 = null;
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6 * 1000);
			
			 br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
}
