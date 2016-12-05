package io.vicp.goradical.datacollect.multithread;

public class Main {
	public static void main(String[] args) {
		Work work = new Work();
		Thread t1 = new Thread(work);
		Thread t2 = new Thread(work);
		Thread t3 = new Thread(work);
		Thread t4 = new Thread(work);
		Thread t5 = new Thread(work);
		t1.setName("t1");
		t2.setName("t2");
		t3.setName("t3");
		t4.setName("t4");
		t5.setName("t5");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
	}
}
