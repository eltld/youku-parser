package com.bebeeru.youkuparser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Space;


public class Parser {

	public static String parse() {
		String videoPageUrl = "http://v.youku.com/v_show/id_XNjc5MTAyMDky.html";

		String normalUrl = String.format("http://www.flvcd.com/parse.php?kw=%s&format=", videoPageUrl);

		// 硕鼠搜索结果
		String normalResult = HttpUtils.get(normalUrl);

		if (normalResult == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		System.out.println();
		sb.append("标清版\n");
		print(normalResult, sb);

		if (normalResult.contains("高清版")) {
			String highUrl = normalUrl + "high";
			String highResult = HttpUtils.get(highUrl);
			
			sb.append("高清版\n");
			print(highResult, sb);
		}

		if (normalResult.contains("超清版")) {
			String superUrl = normalUrl + "super";
			String superResult = HttpUtils.get(superUrl);

			sb.append("超清版\n");
			print(superResult, sb);
		}
		
		return sb.toString();
	}
	
	private static void print(String html, StringBuffer sb) {
		sb.append(filterVideoName(html + "\n"));
		ArrayList<String> uris = filterVideoUri(html);
		for (String uri : uris) {
			sb.append(uri + "\n");
		}
	}
	
	private static String filterVideoName(String html) {
		String videoName = null;
		// html=new String(html.getBytes("GBK"),"gb2312");
		Pattern pattern = Pattern.compile("\\s+<strong>\\w+.+?<strong>");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			videoName = matcher.group().replaceAll("<strong>|</strong>", "");
		}
		return videoName;
	}

	private static ArrayList<String> filterVideoUri(String html) {
		ArrayList<String> ls = new ArrayList<String>();
		// html=new String(html.getBytes("GBK"),"gb2312");
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile("\\s+<br>.+\\s+?</tr>");
		matcher = pattern.matcher(html);
		if (matcher.find()) {
			String parseResult = matcher.group();
			if (parseResult.contains("自动切割")) {
				pattern = Pattern.compile("<BR><a.href=\"(.+?)\".target");
				Matcher matcher1 = pattern.matcher(parseResult);
				while (matcher1.find()) {
					ls.add(matcher1.group(1));
				}
			} else {
				pattern = Pattern.compile("<br>下载.+?ef=\"(.+?)\".target");
				Matcher matcher2 = pattern.matcher(parseResult);
				while (matcher2.find()) {
					ls.add(matcher2.group(1));
				}
			}
		}
		return ls;
	}
}
