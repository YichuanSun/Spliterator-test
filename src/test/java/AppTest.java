import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.ArrayBackedSpliterator;
import org.junit.Assert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
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
    // make sure the init size is equal to the bat size?
    int mSize = 89;
    Integer[] mArray = new Integer[mSize];
    for (int i = 0; i < mSize; i++) {
      mArray[i] = i;
    }
    for (int i = 0; i < mSize; i++) {
      System.out.print(mArray[i] + " ");
    }
    System.out.println("\n");
    ArrayBackedSpliterator
        spliterator = new ArrayBackedSpliterator(mArray, 0, mSize, mSize, Spliterator.NONNULL, true);
    Stream<Integer> stream = StreamSupport.stream(spliterator, true);
    List<Integer> li = stream.sorted().collect(Collectors.toList());
    for (int i = 0; i < li.size(); i++) {
      System.out.print(li.get(i) + " ");
    }
    Set<Integer> se = new HashSet<>(li);
    Assert.assertEquals(se.size(), li.size());
    System.out.println("\n");
  }

  @org.junit.Test
  public void test1() throws Exception {
    String tpath = "/Users/sunbowen/code/java-maven-test/link";
    File file = new File(tpath);
    PosixFileAttributes attr =
        Files.readAttributes(Paths.get(file.getPath()), PosixFileAttributes.class);
    Assert.assertTrue(file.isFile());
  }
}
