package com.example;

import java.util.LinkedList;

/**
 * Created by lixian on 2016/6/27.
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        LinkedList    items = new LinkedList<>();
        items.add("1");
        items.add("2");

        items.addFirst(3);
        items.add(0,"5");
        items.add(1,"4");

        System.out.print("");

    }
}
