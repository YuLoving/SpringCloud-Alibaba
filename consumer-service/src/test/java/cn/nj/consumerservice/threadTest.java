package cn.nj.consumerservice;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zty
 * @date ：Created in 2021/3/25 9:58
 * @description ：
 */
public class threadTest {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 7; i++) {
            threadPoolExecutor.submit(() -> sayHi("submit"));
        }
        threadPoolExecutor.shutdown();



    }



    private static void sayHi(String name) {

        String str = LocalDateTime.now() +"[Thread-name :" + Thread.currentThread().getName() + " ,执行方式:" + name + "]";

        System.out.println(str);
        try {

            TimeUnit.SECONDS.sleep(2);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }
// throw new RuntimeException(str+",我异常了");

    }




}
