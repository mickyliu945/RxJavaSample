package rxjavasample;

import rx.Observer;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class T2 {

	/**
	 * subject 是一个神奇的对象，它可以是一个Observable同时也可以是一个Observer：它作为连接这两个世界的一座桥梁。
	 * 一个主题可以订阅一个Observable，就像一个观察者，并且它可以发射新的数据，或者传递它接受到的数据，就像一个Observable。很明显，
	 * 作为一个Observable，观察者们或者其它主题都可以订阅它。
	 * 串行化如果你把 Subject 当作一个 Subscriber 使用，注意不要从多个线程中调用它的onNext方法（包括其它的on系列方法），这可能导致同时（非顺序）调用，这会违反Observable协议，给Subject的结果增加了不确定性。要避免此类问题，你可以将 Subject 转换为一个 SerializedSubject ，类似于这样：
	 * mySafeSubject = new SerializedSubject( myUnsafeSubject );
	 */
	public static void main(String[] args) {
		T2 t2 = new T2();
		System.out.println("===================testPublishSubject==========================");
		t2.testPublishSubject();
		System.out.println("===================testBehaviorSubject==========================");
		t2.testBehaviorSubject();
		System.out.println("===================testReplaySubject==========================");
		t2.testReplaySubject();
		System.out.println("===================testAsyncSubject==========================");
		t2.testAsyncSubject();
		
	}

	private void testPublishSubject() {
		/** PublishSubject的观察者接收到的是后续的消息*/
		Observer<String> observer1 = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

		};
		
		Observer<String> observer2 = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

		};
		PublishSubject<String> publishSubject = PublishSubject.create();
		publishSubject.subscribe(observer1);
		publishSubject.onNext("A");
		publishSubject.onNext("B");
		publishSubject.subscribe(observer2);
		publishSubject.onNext("C");
		publishSubject.onNext("D");
		publishSubject.onCompleted();
		System.out.println();
	}

	private void testBehaviorSubject() {
		/** BehaviorSubject的观察者接收到的永远是最近的消息 和后续的消息*/
		Observer<String> observer = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

		};
		
		//收到所有消息
		BehaviorSubject<String> subject1 = BehaviorSubject.create("default");
		subject1.subscribe(observer);
		subject1.onNext("A");
		subject1.onNext("B");
		subject1.onNext("C");
		System.out.println();

		//不能收到default、A
		BehaviorSubject<String> subject2 = BehaviorSubject.create("default");
		subject2.onNext("A");
		subject2.onNext("B");
		subject2.subscribe(observer);
		subject2.onNext("C");
		subject2.onNext("D");
		System.out.println();
		
		//只能收到onCompleted
		BehaviorSubject<String> subject3 = BehaviorSubject.create("default");
		subject3.onNext("A");
		subject3.onNext("B");
		subject3.onCompleted();
		subject3.subscribe(observer);
		System.out.println();

		// 只能收到error
		BehaviorSubject<String> subject4 = BehaviorSubject.create("default");
		subject4.onNext("A");
		subject3.onNext("B");
		subject4.onError(new RuntimeException("error"));
		subject4.subscribe(observer);

		System.out.println();
	}

	private void testReplaySubject() {
		/**ReplaySubject会缓存所有消息，所以观察者都会收到所有消息*/
		Observer<String> observer1 = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

		};
		
		Observer<String> observer2 = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

		};
		ReplaySubject<String> publishSubject = ReplaySubject.create();
		publishSubject.subscribe(observer1);
		publishSubject.onNext("A");
		publishSubject.onNext("B");
		publishSubject.subscribe(observer2);
		publishSubject.onNext("C");
		publishSubject.onNext("D");
		publishSubject.onCompleted();
		System.out.println();
	}
	
	private void testAsyncSubject() {
		/**当Observable完成时AsyncSubject只会发布最后一条消息给已经订阅的每一个观察者,如果没有调用onCompleted则被观察者不会发送任何消息给观察者*/
		Observer<String> observer = new Observer<String>() {

			@Override
			public void onNext(String t) {
				System.out.print(t + "\t");
			}

			@Override
			public void onCompleted() {
				System.out.println("onCompleted");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}
		};
		
		AsyncSubject<String> publishSubject1 = AsyncSubject.create();
		publishSubject1.subscribe(observer);
		publishSubject1.onNext("A");
		publishSubject1.onNext("B");
		publishSubject1.onNext("C");
		
		AsyncSubject<String> publishSubject2 = AsyncSubject.create();
		publishSubject2.subscribe(observer);
		publishSubject2.onNext("A");
		publishSubject2.onNext("B");
		publishSubject2.onNext("C");
		publishSubject2.onCompleted();
		System.out.println();
	}
}
