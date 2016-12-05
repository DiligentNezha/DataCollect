package io.vicp.goradical.datacollect.other;

import io.vicp.goradical.datacollect.dao.FileActorDao;
import io.vicp.goradical.datacollect.dao.FileDirectorDao;
import io.vicp.goradical.datacollect.main.Main;
import io.vicp.goradical.datacollect.model.*;
import io.vicp.goradical.datacollect.tools.Test;

import java.lang.reflect.Method;
import java.util.List;

public class TestClass {

	public <T> T test(Class clz){
		T t = null;
		try {
			t = (T) clz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  t;
	}

	public void test5(){
		Object obj = test(Test.class);
		System.out.println(obj);
	}

	public void test4(){
		System.out.println(Main.class.getPackage());
		System.out.println(Main.class.getName());
		System.out.println(Main.class.getCanonicalName());
		System.out.println(Main.class.getSimpleName());
	}

	public void test3(){
		Method[] methods = FileDirectorDao.class.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i]);
		}
	}

	public void test2(){
		try {
			FileDirectorDao fileDirectorDao = new FileDirectorDao();
			Method method;
			method = FileDirectorDao.class.getMethod("getFileInfoDirectorListWithFileId", int.class);
			List<Director> directorList = (List<Director>) method.invoke(fileDirectorDao, 1);
			for (int i = 0; i < directorList.size(); i++) {
				System.out.println(directorList.get(i));
			}
//			method = FileDirectorDao.class.getMethod("print");
//			method.invoke(fileDirectorDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test1() {
		FileActorDao fileActorDao = new FileActorDao();
		System.out.println(fileActorDao.getClass().getSimpleName());
		System.out.println(FileActorDao.class);
		System.out.println(fileActorDao.getClass() == FileActorDao.class);
		System.out.println(fileActorDao.getClass().equals(FileActorDao.class));
	}

	public void main1() {
		Actor actor = new Actor();
		Nation nation = new Nation();
		Type type = new Type();
		Category category = new Category();
		Director director = new Director();
//		test(actor, Actor.class);
//		test(nation, Nation.class);
		try {
			Method method = Actor.class.getMethod("getActorName");
			actor.setActorName("jack chen");
			method.invoke(actor);
			System.out.println(actor);
			method = Actor.class.getMethod("setActorName", String.class);
			method.invoke(actor, "ABC");
			System.out.println(actor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test(Object o, Class c) {
		if (c.isInstance(o)) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
