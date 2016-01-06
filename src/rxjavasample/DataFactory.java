package rxjavasample;

import java.util.ArrayList;
import java.util.List;

import rxjavasample.model.Student;

public class DataFactory {
	
	public static List<Integer> getItems() {
		List<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			items.add(i);
		}
		return items;
	}
	
	public static List<Student> getStudents() {
		List<Student> list = new ArrayList<Student>();
		Student stu = new Student();
		stu.setName("Micky Liu");
		stu.setAge(28);
		list.add(stu);
//		
//		stu = new Student();
//		stu.setName("Micky Liu");
//		stu.setAge(28);
//		list.add(stu);
		
		stu = new Student();
		stu.setName("Kate Zhou");
		stu.setAge(23);
		list.add(stu);

		stu = new Student();
		stu.setName("Lucy Zhen");
		stu.setAge(19);
		list.add(stu);
		
		stu = new Student();
		stu.setName("Jack Wang");
		stu.setAge(25);
		list.add(stu);
		
		
		
		return list;
	}
}
