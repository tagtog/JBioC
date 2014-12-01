package bioc;

import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import bioc.tool.FindAbbr;
import bioc.io.BioCFactory;

public class FindAbbrTest {

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

    FindAbbr copy_xml = new FindAbbr();
    copy_xml.copy(input.getAbsolutePath(), output.getAbsolutePath(), BioCFactory.WOODSTOX);

    File expected = new File(testResourcesDir, "abbr.xml");
    BioCTestCommon.areXMLSimilar(expected, output);
  }

}
