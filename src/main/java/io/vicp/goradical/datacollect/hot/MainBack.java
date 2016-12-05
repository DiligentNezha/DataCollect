package io.vicp.goradical.datacollect.hot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainBack {
	public static void main(String[] args) {
		//2016-05-06
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse("2016-05-06");
			System.out.println(date.toLocaleString());
			System.out.println(date.getTime());
			//1477137110
			long temp = 1471532566L * 1000;
			System.out.println(temp);
			System.out.println(new Date(temp).toLocaleString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public static void main1(String[] args) {
		long start = System.currentTimeMillis();
		DataGenerateTest dataGenerate = new DataGenerateTest();
		Thread[] threads = new Thread[50];
		/**
		 * 50--3771
		 * 40--3719
		 * 30--3613
		 * 20--3569
		 * 10--3376
		 * 5--3480
		 * 1--4709
		 */
		
		for(int i = 0; i < 10; i++) {
			threads[i]= new Thread(dataGenerate);
			threads[i].setName("thread" + i);
			threads[i].start();
		}
		for(int i = 0; i < 10; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long stop = System.currentTimeMillis();
		System.out.println(stop - start);
	}
}
