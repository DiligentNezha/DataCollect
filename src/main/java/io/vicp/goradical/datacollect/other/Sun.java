package io.vicp.goradical.datacollect.other;

public class Sun extends Father {
	public static void main(String[] args) {
		Sun sun = new Sun();
		sun.setName("abc");
		sun.setAge(15);
		System.out.println(sun.getName());
	}

	public Sun(){
	}

	public Sun(String name, int age) {
		super(name, age);
	}
}
