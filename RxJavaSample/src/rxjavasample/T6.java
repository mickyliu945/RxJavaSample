package rxjavasample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func2;

public class T6 {
	public static void main(String[] args) {
		T6 t6 = new T6();
		t6.testMerge();
		t6.testConcat();
		t6.testZip();
		t6.testCombineLatest();
	}

	private void testMerge() {
		/**
		 * 使用Merge操作符你可以将多个Observables的输出合并，就好像它们是一个单个的Observable一样。
		 * Merge可能会让合并的Observables发射的数据交错
		 * （有一个类似的操作符Concat不会让数据交错，它会按顺序一个接着一个发射多个Observables的发射物）。
		 * 注意错误时的toast消息，你可以认为每个Observable抛出的错误将会打断合并。
		 */
		Observable<Integer> listObservable = Observable.from(DataFactory
				.getItems());
		List<Integer> reverseList = new ArrayList<Integer>();
		reverseList.addAll(DataFactory.getItems());
		Collections.reverse(reverseList);
		Observable<Integer> reverseListObservable = Observable
				.from(reverseList);
		Observable.merge(listObservable, reverseListObservable).subscribe(
				new Subscriber<Integer>() {
					@Override
					public void onNext(Integer i) {
						System.out.print(i + "\t");
					}

					@Override
					public void onError(Throwable e) {
						System.err.println(e.getMessage());
					}

					@Override
					public void onCompleted() {
						System.out.println("onCompleted");
					}
				});
	}

	private void testConcat() {
		Observable<Integer> listObservable = Observable.from(DataFactory
				.getItems());
		List<Integer> reverseList = new ArrayList<Integer>();
		reverseList.addAll(DataFactory.getItems());
		Collections.reverse(reverseList);
		Observable<Integer> reverseListObservable = Observable
				.from(reverseList);
		Observable.concat(listObservable, reverseListObservable).subscribe(
				new Subscriber<Integer>() {
					@Override
					public void onNext(Integer i) {
						System.out.print(i + "\t");
					}

					@Override
					public void onError(Throwable e) {
						System.err.println(e.getMessage());
					}

					@Override
					public void onCompleted() {
						System.out.println("onCompleted");
					}
				});
	}

	private void testZip() {
		/**
		 * 通过一个函数将多个Observables的发射物结合到一起，基于这个函数的结果为每个结合体发射单个数据项。
		 * Zip操作符返回一个Obversable ，
		 * 它使用这个函数按顺序结合两个或多个Observables发射的数据项，然后它发射这个函数返回的结果
		 * 。它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。 
		 * Javadoc: zip(Iterable,FuncN)) 
		 * Javadoc: zip(Observable,FuncN)) 
		 * Javadoc: zip(Observable,Observable,Func2)) (最多可以有九个Observables参数)
		 */
		Observable<String> observable1 = Observable.just("A", "B", "F", "G","N");
		Observable<String> observable2 = Observable.just("C", "F", "R");
		Observable.zip(observable1, observable2,
				new Func2<String, String, String>() {

					@Override
					public String call(String t1, String t2) {
						return t1 + t2;
					}

				}).subscribe(new Observer<String>() {

			@Override
			public void onNext(String str) {
				System.out.print(str + "\t");
			}

			@Override
			public void onError(Throwable e) {
				System.err.println(e.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

		});

	}
	
	private void testCombineLatest() {
		/**zip() 作用于最近未打包的两个Observables。相反， combineLatest() 作用于最近发射的数据项：
		 * 如果 Observable1 发射了A并且 Observable2 发射了B和C， combineLatest() 将会分组处理AB和AC
		 */
		Observable<String> observable1 = Observable.just("A", "B", "F", "G","N");
		Observable<String> observable2 = Observable.just("C", "F", "R");
		Observable.combineLatest(observable1, observable2,
				new Func2<String, String, String>() {

					@Override
					public String call(String t1, String t2) {
						return t1 + t2;
					}

				}).subscribe(new Observer<String>() {

			@Override
			public void onNext(String str) {
				System.out.print(str + "\t");
			}

			@Override
			public void onError(Throwable e) {
				System.err.println(e.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

		});
	}
}
