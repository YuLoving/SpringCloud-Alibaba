package cn.nj.elasticsearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：zty
 * @date ：Created in 2021/5/31 11:05
 * @description ：
 */
public class test {

    public static void main(String[] args) {
        User user1=new User("1111",20);
        User user2=new User("2222",19);
        User user3=new User("3333",21);

        List<User> list=new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        list = list.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
        System.out.println("根据年龄顺序：==="+list);

        list = list.stream().sorted(Comparator.comparing(User::getAge,Comparator.reverseOrder())).collect(Collectors.toList());
        System.out.println("根据年龄倒序：==="+list);
    }

}



