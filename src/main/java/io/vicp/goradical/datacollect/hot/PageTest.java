package io.vicp.goradical.datacollect.hot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class PageTest {
	private static void test() {
		String url = "http://www.soku.com/search_video/q_人皮拼图";
		try {
			Document doc = Jsoup.connect(url)
					.ignoreContentType(true)
					.ignoreHttpErrors(true)
					.get();
			Element element = doc.select("div.sk_container").first();
			System.out.println(element);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
