package org.example;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DirectMemoryAlloc {
  public static void main(String[] args) throws InterruptedException {
    List<ByteBuffer> list = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      list.add(ByteBuffer.allocateDirect(10 * 1024 * 1024)); // 500MB total
    }
    System.out.println("Allocated 500MB direct buffer. Sleeping...");
    Thread.sleep(600000);
  }
}
