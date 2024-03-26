package com.mss.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceTest {
    public static void main(String[] args) {
        String oldValue = "old value";
        String newValue = "new value";

        // 知识点1：使用AtomicMarkableReference来避免ABA问题
        AtomicMarkableReference<String> stringAtomicMarkableReference = new AtomicMarkableReference<>(oldValue, false);
        boolean flag = stringAtomicMarkableReference.compareAndSet(oldValue, newValue, false, true);
        if (flag) {
            System.out.println("成功从" + oldValue + "改成了" + newValue);
        }
    }
}
