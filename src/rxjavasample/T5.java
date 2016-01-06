package rxjavasample;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rxjavasample.model.Student;

public class T5 {
	
	public static void main(String[] args) {
		T5 t5 = new T5();
		t5.testMap();
		t5.testFlatMap();
	}
	
	private void testMap() {
		/**对Observable发射的每一项数据应用一个函数，执行变换操作*/
		Observable.from(DataFactory.getStudents()).map(new Func1<Student, Student>() {
			@Override
			public Student call(Student t) {
				t.setName(t.getName().toLowerCase());
				return t;
			}
		}).subscribe(new Action1<Student>() {
			@Override
			public void call(Student t) {
				System.out.println(t.getName());
			}
		});
	}
	
	private void testFlatMap() {
		System.out.println("=========testFlatMap 版本1===============");
		Observable<List<Student>> listObservable = Observable.from(DataFactory.getStudents()).toSortedList(); //模拟数据,假如我们取到的数据就是如此的
		
		//版本1
		listObservable.subscribe(new Action1<List<Student>>() {
			@Override
			public void call(List<Student> list) {
				for (Student stu : list) {
					System.out.println(stu);
				}
			}
		});
		
		System.out.println("=========testFlatMap 版本2===============");
		listObservable.subscribe(new Action1<List<Student>>() {
			@Override
			public void call(List<Student> list) {
				Observable.from(list).subscribe(new Action1<Student>() {
					@Override
					public void call(Student t) {
						System.out.println(t);
					}
				});
			}
		});
		
		System.out.println("=========testFlatMap 版本3===============");
		listObservable.flatMap(new Func1<List<Student>, Observable<Student>>() {
			@Override
			public Observable<Student> call(List<Student> list) {
				return Observable.from(list);
			}
		}).subscribe(new Action1<Student>() {
			@Override
			public void call(Student t) {
				System.out.println(t);
			}
		});
	}
}
