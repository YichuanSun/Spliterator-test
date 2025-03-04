import TestPackage.WrapperForA;
import org.example.A;
import org.junit.Assert;
import org.junit.Test;

public class WrapperIntegrationTest {
  @Test
  public void test1() {
    A a = new A();
    WrapperForA w = new WrapperForA(a);
    Assert.assertEquals(2, w.invokeGetNum2());
  }
}
