package io.vicp.goradical.datacollect.other;

import io.vicp.goradical.datacollect.entity.Nation;
import io.vicp.goradical.datacollect.entity.Type;

public class Simple<T> {
	private T t;

	public Simple(T t) {
		this.t = t;
	}

//	public <T> void print(Class<T> c){
//		try {
//			c.newInstance();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		System.out.println(t.getClass());
//	}

	public void print(String string) {
		System.out.println(string);
	}

	public static void main(String[] args) {
		Simple<Nation> simple = new Simple<>(new Nation());
		Simple simple1 = new Simple(new Type());
	}
}
