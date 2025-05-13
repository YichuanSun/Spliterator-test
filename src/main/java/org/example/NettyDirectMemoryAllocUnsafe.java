package org.example;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class NettyDirectMemoryAllocUnsafe {
  public static void main(String[] args) throws Exception {
    // 获取 Unsafe 实例
    Field field = Unsafe.class.getDeclaredField("theUnsafe");
    field.setAccessible(true);
    Unsafe unsafe = (Unsafe) field.get(null);

    System.in.read();

    // 分配 100 MB 的 native memory
    long size = 100 * 1024 * 1024;
    long address = unsafe.allocateMemory(size);

    System.out.println("Allocated memory at address: " + address);
    System.in.read();

    // 写入数据
    unsafe.putByte(address, (byte) 42);
    byte b = unsafe.getByte(address);
    System.out.println("Read from memory: " + b);
    System.in.read();

    // 释放内存
    unsafe.freeMemory(address);
    System.out.println("Memory freed.");
    System.in.read();
  }
}
