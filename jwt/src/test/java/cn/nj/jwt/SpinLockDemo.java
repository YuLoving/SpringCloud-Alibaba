package cn.nj.jwt;

import org.assertj.core.util.DateUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.LocalTime.now;

/**
 * @author ：zty
 * @date ：Created in 2021/6/23 15:35
 * @description ：  简单手写自旋锁
 */
public class SpinLockDemo {

    // 原子引用线程
    private final AtomicReference<Object> atomicReference = new AtomicReference<>();


    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        // 我A线程进去占用这把锁，然后霸占5s
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnlock();
        },"线程A").start();

        // 主线程main暂停1s，保证A线程先启动
        TimeUnit.SECONDS.sleep(1);
        // 我B线程再进去循环等待这把锁
        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.myUnlock();
        },"线程B").start();

    }


    public void myLock() {
        // 返回对当前正在执行的线程对象的引用
        Thread thread = Thread.currentThread();
        System.out.println("当前线程:" + thread.getName() + "进来了");
        /* 首个线程进来，发现atomicReference是null，就变为这个线程。然后取反变为false，
        跳出循环等待。如果发现卫生间里面还有人，就一直循环一直等，直到卫生间里面没人。 */
        while (!atomicReference.compareAndSet(null, thread)) {
            //如果atomicReference不是null,当前存在其他线程  就一直循环
        }

    }


    public void myUnlock() {
        Thread thread = Thread.currentThread();
        //我出去要解锁，然后变为null给下一个人用
        atomicReference.compareAndSet(thread, null);
        System.out.println("当前线程:" + thread.getName() + "释放了锁");
    }


}
