package bioc;

import java.io.File;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import bioc.io.BioCFactory;
import bioc.manual.CopyXML;

public class CopyXMLTest {

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

  /**
   * Run BioC copy program with only standard libraries.
   */
  @Test
  public void testWithParserStandard() throws Exception {
    File input = new File(testResourcesDir, "everything.xml");
    File output = testFolder.newFile("out.xml");

    CopyXML copyXML = new CopyXML();
    copyXML.copy(BioCFactory.STANDARD, input.getAbsolutePath(), output.getAbsolutePath());

    File expected = new File(testResourcesDir, "everything-nolib.xml");

    BioCTestCommon.areXMLSimilar(expected, output);
  }

  @Test
  public void testWithParserWoodstox() throws Exception {
    File input = new File(testResourcesDir, "everything.xml");
    File output = testFolder.newFile("out.xml");

    CopyXML copyXML = new CopyXML();
    copyXML.copy(BioCFactory.WOODSTOX, input.getAbsolutePath(), output.getAbsolutePath());

    BioCTestCommon.areXMLSimilar(input, output);
  }

}
