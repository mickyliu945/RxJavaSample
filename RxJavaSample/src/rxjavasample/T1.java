package rxjavasample;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class T1 {

	public static void main(String[] args) {
		T1 t1 = new T1();
		t1.testCreate();
		t1.testFrom();
		t1.testJust();
	}
	
	private void testCreate() {
		System.out.println("====================Observable.create========================");
		Observable.create(new OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> subscriber) {
				for (int i = 0; i < 10; i++) {
					subscriber.onNext("value=" + i);
				}
				subscriber.onCompleted();
			}
		}).subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.println(t);
			}
			
		});
		
		Observable.create(new OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> t) {
				for (int i = 0; i < 10; i++) {
					t.onNext("value=" + i);
				}
				t.onCompleted();
			}
		}).subscribe(new Observer<String>() {
			@Override
			public void onNext(String t) {
				System.out.println(t);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}
			
		});
	}
	
	private void testFrom() {
		System.out.println("====================Observable.from========================");
		/**from()创建符可以从一个列表/数组来创建Observable,并一个接一个的从列表数组中发射出来每一个对象*/
		
		
		
		Observable.from(DataFactory.getItems()).subscribe(new Observer<Integer>() {

			@Override
			public void onNext(Integer t) {
				System.out.println(t);
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}
			
		});
	}
	
	private void testJust() {
		System.out.println("====================Observable.just========================");
		/**我们创建了Observable，和 just()执行函数helloMicky()，一旦我们订阅Observable时，它就会发射出helloMicky()返回的值。*/
		
		Observable.just(helloMicky()).subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.print(t);
			}
		});
		System.out.println();
		//从上面代码可以引出思考，类似Android网络访问，rxjava能不能直接把helloMicky()放在非主线程访问，而把subscribe放在主线程访问呢？这样就解决了android繁琐的callback问题了,请继续往下下下下...看
		
		Observable.just("A", "B", "C", "D").subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				System.out.print(t);
			}
		});
		System.out.println();
		/**just() 方法也可以接受列表或数组，就像 from() 方法，但是它不会迭代列表发射每个值,它将会发射整个列表*/
		Observable.just(DataFactory.getItems()).subscribe(new Action1<List<Integer>>() {
			@Override
			public void call(List<Integer> t) {
				for(int i = 0; i < t.size(); i++) {
					System.out.print(t.get(i));
				}
			}
		});
		System.out.println();
	}
	
	private String helloMicky() {
		return "Hello Micky";
	}

	
	
	/**当我们需要让一个Observable马上不再发射数据正常结束时，我们可以使用 empty() 。我们可以使用 never() 创建一个不发射数据并且也永远不会结束的Observable。我们也可以使用 throw() 创建一个不发射数据并且以错误结束的Observable。*/
}
