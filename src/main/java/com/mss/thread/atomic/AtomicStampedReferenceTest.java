package com.mss.thread.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {
    public static void main(String[] args) {
        String oldValue = "old value";
        int oldStamp = 0;

        // 知识点1：使用AtomicStampedReference来避免ABA问题
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>(oldValue, oldStamp);

        String newValue = "new value";
        boolean flag1 = atomicStampedReference.compareAndSet(oldValue, newValue, oldStamp, oldStamp + 1);
        if (flag1) {
            System.out.println("成功从" + oldValue + "改成了" + newValue);
        }

        String newValue2 = "new value2";
        boolean flag2 = atomicStampedReference.compareAndSet(oldValue, newValue2, oldStamp, oldStamp + 1);
        if (!flag2) {
            System.out.println("修改失败.");
        }
    }
}
