package TestPackage;

import org.junit.Before;
import org.junit.Test;

public class C extends AbstractC {
  @Before
  public void before() {
    System.out.println("c");
  }

  @Test
  public void test() {
    System.out.println("testing...");
  }
}
