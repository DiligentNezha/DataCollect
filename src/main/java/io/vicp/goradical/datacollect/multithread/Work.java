package io.vicp.goradical.datacollect.multithread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Work implements Runnable{

	private static List<String> list;
	private static Integer index = 0;
	static {
		int size = 20;
		list = new ArrayList<>(size);
		Random random = new Random();
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < size; i++) {
			list.add(String.valueOf(i) + "-->" + (char)random.nextInt(128));
		}
	}
	
	@Override
	public void run() {
		while (index < list.size()) {
			synchronized (index) {
				if (index < list.size()) {
					int temp = index++;
					Thread.yield();
					System.out.println(Thread.currentThread() + ":" + list.get(temp));
				}
			}
		}
	}

}
