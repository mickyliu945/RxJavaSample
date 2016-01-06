package rxjavasample.model;

public class Student implements Comparable<Student>{
	
	private int id;
	private String name;
	private int age;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return name + "-" + age;
	}
	
	@Override
	public int compareTo(Student stu) {
		if (age > stu.getAge()) {
			return 1;
		} else if (age < stu.getAge()) {
			return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			return name.equals(((Student)obj).getName());
		}
		return false;
	}
}
