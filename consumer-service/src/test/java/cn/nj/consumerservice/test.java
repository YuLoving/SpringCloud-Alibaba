package cn.nj.consumerservice;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zty
 * @date ：Created in 2021/3/23 17:21
 * @description ：
 */
public class test {

    public static void main(String[] args) {
        Map<Object, Object> map = Maps.newHashMap();
        if(map.containsKey(null)){
            System.out.println("11");
        }
        System.out.println("22");
    }

}
