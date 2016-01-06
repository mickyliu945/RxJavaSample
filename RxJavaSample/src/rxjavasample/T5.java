package rxjavasample;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rxjavasample.model.Student;

public class T5 {
	
	public static void main(String[] args) {
		T5 t5 = new T5();
		t5.testMap();
		t5.testFlatMap();
		t5.testScan();
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
	
	private void testScan() {
		/**Scan操作符对原始Observable发射的第一项数据应用一个函数，然后将那个函数的结果作为自己的第一项数据发射。它将函数的结果同第二项数据一起填充给这个函数来产生它自己的第二项数据。它持续进行这个过程来产生剩余的数据序列。这个操作符在某些情况下被叫做accumulator。*/
		
		Observable.from(DataFactory.getItems()).scan(new Func2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer t1, Integer t2) {
				return t1 + t2;
			}
		}).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer t) {
				System.out.print(t + "\t");
			}
		});
		System.out.println();
		
		Observable.from(DataFactory.getItems()).scan(1, new Func2<Integer, Integer, Integer>() {
			
			@Override
			public Integer call(Integer t1, Integer t2) {
				return t1 + t2;
			}
		}).subscribe(new Action1<Integer>() {
			@Override
			public void call(Integer t) {
				System.out.print(t + "\t");
			}
		});
	}
}
