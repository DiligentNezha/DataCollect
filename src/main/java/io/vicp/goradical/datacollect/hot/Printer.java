package io.vicp.goradical.datacollect.hot;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Printer {
	private static FileOutputStream fosCollect;
	static {
		try {
			fosCollect = new FileOutputStream("sql/collect.sql");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static FileOutputStream getFosCollect(){
		return fosCollect;
	}

	public static void closeFos(){
		if (fosCollect != null) {
			try {
				fosCollect.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
