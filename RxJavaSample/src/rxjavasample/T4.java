package rxjavasample;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rxjavasample.model.Student;

public class T4 {

	public static void main(String[] args) {
		T4 t4 = new T4();
		t4.testFilter();
		t4.testTake();
		t4.testDistinct();
		t4.testFirstAndLast();
		t4.testElementAt();
		t4.testSampleAndThrottleFirst();
	}

	private void testFilter() {
		/**
		 * filter() 方法来过滤我们观查列表对象中不想要的值 ，最常用的用法之一是用于过滤列表中的 null对象.
		 * toSortedList() 将被观察者发出的消息组合成一个有序列表。每个消息对象必须实现Comparable接口。
		 * 
		 */
		Observable.from(DataFactory.getStudents()).filter(new Func1<Student, Boolean>() {
			@Override
			public Boolean call(Student t) {
				return t.getAge() > 20;
			}
		}).toSortedList().subscribe(new Observer<List<Student>>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(List<Student> t) {
				System.out.println(t);
			}
		});
	}

	private void testTake() {
		System.out.println();
		/** take()方法用于取列表中前n个对象 */
		Observable.from(DataFactory.getStudents()).take(2).subscribe(new Observer<Student>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Student t) {
				System.out.println(t);
			}
		});

		/** 如下代码，将take()放在toSortedList()之后不会有任何效果 */
		Observable.from(DataFactory.getStudents()).toSortedList().take(2).subscribe(new Observer<List<Student>>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(List<Student> t) {
				System.out.println(t);
			}
		});

		/** takeLast()方法用于取列表中后n个对象 */
		Observable.from(DataFactory.getStudents()).takeLast(2).subscribe(new Observer<Student>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Student t) {
				System.out.println(t);
			}
		});
	}

	private void testDistinct() {
		System.out.println();
		/**
		 * distinct()方法用于去重复, 可以同时重写对象的equals和 hashcode方法,判断是否同一对象，注：必须两个方法都重写
		 */

		/** 取list中前3个对象重复三次 */
		Observable<Student> observable = Observable.from(DataFactory.getStudents()).take(3).repeat(3);
		observable.distinct().subscribe(new Observer<Student>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Student t) {
				System.out.println(t);
			}
		});
	}

	private void testFirstAndLast() {
		System.out.println();
		/**
		 * 在某些实现中，First没有实现为一个返回Observable的过滤操作符，
		 * 而是实现为一个在当时就发射原始Observable指定数据项的阻塞函数。在这些实现中，如果你想要的是一个过滤操作符，最好使用Take(1)
		 * 或者ElementAt
		 */
		Observable.just(1, 2, 3).first().subscribe(new Observer<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("first onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}

		});

		Observable.just(1, 2, 3).last().subscribe(new Observer<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("last onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}

		});
	}

	private void testElementAt() {
		System.out.println();
		/** ElementAt操作符获取原始Observable发射的数据序列指定索引位置的数据项，然后当做自己的唯一数据发射 */
		Observable.just(1, 2, 3).elementAt(2).subscribe(new Observer<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("elementAt onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}

		});

		Student defStu = new Student();
		defStu.setName("default-name");

		Observable.from(DataFactory.getStudents()).elementAtOrDefault(5, defStu).subscribe(new Observer<Student>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Student t) {
				System.out.println(t);
			}
		});
	}

	private void testSampleAndThrottleFirst() {
		/**
		 * Sample操作符定时查看一个Observable，然后发射自上次采样以来它最近发射的数据。
		 * 如果我们想让它定时发射第一个元素而不是最近的一个元素，我们可以使用 throttleFirst(),是否能用于android 用户快速点击按钮等的处理，以防止打开多个页面?哈哈
		 * throttleFirst与throttleLast/sample不同，在每个采样周期内，它总是发射原始Observable的第一项数据，而不是最近的一项。
		 */
		Observable.interval(1, TimeUnit.SECONDS).sample(5, TimeUnit.SECONDS).subscribe(new Observer<Long>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Long t) {
				System.out.println("sample" + t);
			}
			
		});
		
		Observable.interval(1, TimeUnit.SECONDS).throttleFirst(5, TimeUnit.SECONDS).subscribe(new Observer<Long>() {

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Long t) {
				System.out.println("throttleFirst" + t);
			}
			
		});
		
		
		try {
			Thread.sleep(1000 * 60);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
