package com.learning.springwebfluxvideostreaming;

public class VarArgsTest {

    void m1(int a, String... b) {
        System.out.println("values: " + a);
        for (String i : b) {
            System.out.println("String array value: " + i);
        }
    }

    public static void main(String[] args) {
            VarArgsTest v = new VarArgsTest();
            v.m1(5,"fdfd","ffdfdf","refdfd");


        }

}
