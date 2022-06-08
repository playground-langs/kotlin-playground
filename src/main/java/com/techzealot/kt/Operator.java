package com.techzealot.kt;

import java.util.Arrays;

public enum Operator {
    Add("+"), Sub("-"), Multi("*"), Div("/");

    private String value;

    Operator(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Operator.values()));
        //ok
        System.out.println(Operator.valueOf("Add"));
        //error
        //System.out.println(Operator.valueOf("+"));
    }
}
