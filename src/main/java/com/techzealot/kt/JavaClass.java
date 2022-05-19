package com.techzealot.kt;

import java.util.List;
import java.util.stream.Collectors;

public class JavaClass {
    public int getZero() {
        return 0;
    }

    public List<String> convertToUpper(List<String> names) {
        return names.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    public void suspend() {
        System.out.println("suspending....");
    }

    public String when() {
        return "Now";
    }
}
