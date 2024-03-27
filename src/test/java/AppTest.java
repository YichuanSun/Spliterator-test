import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.ArrayBackedSpliterator;

import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName )
  {
    super( testName );
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite()
  {
    return new TestSuite( AppTest.class );
  }

  /**
   * Rigourous Test :-)
   */
  public void testApp()
  {
    assertTrue( true );
  }

  public void testArrayBackedSpliterator() {
    int mSize = 32;
    Integer[] mArray = new Integer[mSize];
    for (int i = 0; i < mSize; i++) {
      mArray[i] = i;
    }
    for (int i = 0; i < mSize; i++) {
      System.out.print(mArray[i] + " ");
    }
    System.out.println("\n");
    ArrayBackedSpliterator
        spliterator = new ArrayBackedSpliterator(mArray, 0, mSize, mSize, Spliterator.SIZED);
    Stream<Integer> stream = StreamSupport.stream(spliterator, true);
    List<Integer> li = stream.map(x -> x + 1).collect(Collectors.toList());
    for (int i = 0; i < mSize; i++) {
      System.out.print(li.get(i) + " ");
    }
    System.out.println("\n");
  }
}
