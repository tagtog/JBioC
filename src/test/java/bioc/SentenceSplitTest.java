package bioc;

import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import bioc.manual.SentenceSplit;

public class SentenceSplitTest {

  File testResourcesDir;

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @BeforeClass
  public static void onlyOnce() {
    BioCTestCommon.apply();
  }

  @Before
  public void setUp() throws Exception {
    testResourcesDir = new File(getClass().getResource("/xml").toURI());
  }

  @Test
  public void test() throws Exception {
    File input = new File(testResourcesDir, "ascii.xml");
    File output = testFolder.newFile("out.xml");

    SentenceSplit sentenceSplitter = new SentenceSplit();
    sentenceSplitter.split(input.getAbsolutePath(), output.getAbsolutePath());

    File expected = new File(testResourcesDir, "sentence.xml");
    BioCTestCommon.areXMLSimilar(expected, output);
  }

}
