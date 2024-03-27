/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package org.example;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * The code is mainly copied from ArraySpliterator in {@link Spliterators}.
 * @param <T> the type
 */
public class ArrayBackedSpliterator<T> implements Spliterator<T> {
  private final Object[] mArray;
  private int mIndex;        // current index, modified on advance/split
  private final int mFence;  // one past last index
  private final int mCharacteristics;
  private final int mEnd;    // the end position of current batch.
  public static AtomicInteger mInt = new AtomicInteger(1);

  /**
   * @param array the array, assumed to be unmodified during use
   * @param origin the least index (inclusive) to cover
   * @param fence one past the greatest index to cover
   * @param end the end position of current batch(exclusive)
   * @param additionalCharacteristics Additional spliterator characteristics
   * of this spliterator's source or elements beyond {@code SIZED} and
   * {@code SUBSIZED} which are always reported
   */
  public ArrayBackedSpliterator(Object[] array, int origin, int fence, int end,
                                int additionalCharacteristics) {
    System.out.println("ctor the object id: " + this.hashCode()
        + " tid: " + Thread.currentThread().getId() + " cur time: " + System.currentTimeMillis());
    System.out.println("$$$$$$$ begin: " + origin + " end: " + fence);
    mArray = array;
    mIndex = origin;
    mFence = fence;
    mCharacteristics = additionalCharacteristics | Spliterator.SIZED | Spliterator.SUBSIZED;
    mEnd = end;
    if (mFence == mEnd) {
      System.out.println("!!! " + mIndex + " " + mFence);
    }
  }

  @Override
  public Spliterator<T> trySplit() {
    int lo = mIndex, mid = (lo + mFence) >>> 1;
    System.out.println("element amount: " + (mid - lo));
    Spliterator<T> spliterator;
    if (lo >= mid) {
      spliterator = null;
    } else {
      System.out.println("current spliterator number: " + mInt.addAndGet(1));
      spliterator = new ArrayBackedSpliterator<>(mArray, lo, mIndex = mid, mEnd, mCharacteristics);
    }
    return spliterator;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void forEachRemaining(Consumer<? super T> action) {
    System.out.println("forEachRemaining: The object id: " + this.hashCode()
        + " tid: " + Thread.currentThread().getId() + " cur time:" + System.currentTimeMillis());
    Object[] a;
    int i;
    int hi; // hoist accesses and checks from loop
    if (action == null) {
      throw new NullPointerException();
    }
    if ((a = mArray).length >= (hi = mFence) && (i = mIndex) >= 0 && i < (mIndex = hi)) {
      do {
        action.accept((T) a[i]);
      } while (++i < hi);
    }
  }

  @Override
  public boolean tryAdvance(Consumer<? super T> action) {
    if (action == null) {
      throw new NullPointerException();
    }
    if (mIndex >= 0 && mIndex < mFence) {
      @SuppressWarnings("unchecked") T e = (T) mArray[mIndex++];
      action.accept(e);
      return true;
    }
    return false;
  }

  @Override
  public long estimateSize() { return mFence - mIndex; }

  @Override
  public int characteristics() {
    return mCharacteristics;
  }

  @Override
  public Comparator<? super T> getComparator() {
    if (hasCharacteristics(Spliterator.SORTED)) {
      return null;
    }
    throw new IllegalStateException();
  }
}
