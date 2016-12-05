package io.vicp.goradical.datacollect.main;

import io.vicp.goradical.datacollect.hot.DataGenerate;
import io.vicp.goradical.datacollect.hot.Printer;

import java.util.Collections;
import java.util.Date;

/**
 * 16041292554
 * 17769968051
 * 15298192437
 * 16334296891
 * 16524206348
 */
public class Main {
	public void time(){
		Integer time = null;
		try {
			Object obj = "1474444823103l";
			Integer a = Integer.parseInt(obj.toString());
			System.out.println(a);
			time = ((Integer) obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(new Date(time).toLocaleString());
	}

	public void main() {
		long start = System.nanoTime();
		int num = 15;
		Thread[] threads = new Thread[num];
		DataGenerate recommendDataGenerate = new DataGenerate();
		for(int i = 0; i < num; i++) {
			threads[i] = new Thread(recommendDataGenerate);
			threads[i].setName("thread" + i);
			threads[i].start();
		}
		for(int i = 0; i < num; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Printer.closeFos();
		Collections.sort(recommendDataGenerate.getTempResultList());
		recommendDataGenerate.showTempResultList();
		recommendDataGenerate.showMissFileInfoIdList();
		recommendDataGenerate.showDuplicateFileInfoIdList();
		long stop = System.nanoTime();
		System.out.println(stop - start);
	}
}
