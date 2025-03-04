package TestPackage;

import org.example.A;

public class WrapperForA {
  private int ttt = 100;
  private A mA;

  public WrapperForA(A a) {
    mA = a;
  }
  public int invokeGetNum2() {
    return mA.getNum2();
  }
}
