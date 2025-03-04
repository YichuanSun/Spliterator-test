import org.example.A;
import org.junit.Assert;
import org.junit.Test;

public class ATest {
  @Test
  public void test() {
    A a = new A();
    Assert.assertEquals(1, a.getNum());
  }
}
