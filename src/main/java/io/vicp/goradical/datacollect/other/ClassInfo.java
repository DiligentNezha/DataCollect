package io.vicp.goradical.datacollect.other;

import java.lang.reflect.Method;

public class ClassInfo {

	public static void main(String[] args) {
		showClassInfo(Simple.class);
		try {
			Simple simple = new Simple(ClassInfo.class);
			Method method = Simple.class.getMethod("print", String.class);
			method.invoke(simple, "hello world");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> void showClassInfo(Class<T> clz) {
		try {
			Package pack = clz.getPackage();
			System.out.println(pack);
			Method[] methods = clz.getDeclaredMethods();
			for (Method method : methods) {
				System.out.println(method);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
