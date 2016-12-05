package io.vicp.goradical.datacollect.other;

import java.net.URLDecoder;

public class UrlDecoderTest {
	public static void main(String[] args) {
		String url = "http://comments.youku.com/comments/~ajax/vpcommentContent.html?__callback=vpcommentContent_html&__ap=%7B%22videoid%22%3A%22412394405%22%2C%22showid%22%3A%220%22%2C%22isAjax%22%3A1%2C%22sid%22%3A%22%22%2C%22page%22%3A1%2C%22chkpgc%22%3A0%2C%22last_modify%22%3A%22%22%7D";
		try {
			String temp = URLDecoder.decode(url, "UTF-8");
			System.out.println(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
