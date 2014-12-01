package bioc;

import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import bioc.manual.CopyCollectionXML;

public class CopyCollectionXMLTest {

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
    File input = new File(testResourcesDir, "everything-sentence.xml");
    File output = testFolder.newFile("out.xml");

    CopyCollectionXML copyXML = new CopyCollectionXML();
    copyXML.copy(input.getAbsolutePath(), output.getAbsolutePath());

    BioCTestCommon.areXMLSimilar(input, output);
  }

}
