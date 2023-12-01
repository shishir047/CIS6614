package org.example;

import java.lang.reflect.Array;

public class BufferOverFlow {
    public static void main(String[] args) {
        String[] arr = new String[3];
        arr[0] = "This";
        arr[1] = "is";
        arr[2] = "example of";
        arr[3] = "Buffer OverFlow";
    }
}
