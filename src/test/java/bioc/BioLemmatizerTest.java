package bioc;

import java.io.File;
import java.io.FileReader;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import bioc.io.BioCFactory;
import bioc.tool.AddLemma;

public class BioLemmatizerTest {

  File testResourcesDir;

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @BeforeClass
  public static void onlyOnce() {
    BioCTestCommon.apply();
  }

  @Before
  public void setUp() throws Exception {
    testResourcesDir = new File(getClass().getResource("/xml").toURI().getPath(), "BioLemmatizer");
  }

  /**
   * Currently not working yet being an exact copy of the old script test.
   * Furthermore, it's unclear what's being tested
   */
  @Ignore
  public void testBioLemmatizer() throws Exception {
    File input = new File(testResourcesDir, "pos.xml");
    File output = testFolder.newFile("out.xml");

    AddLemma copy_xml = new AddLemma();
    copy_xml.copy(input.getAbsolutePath(), output.getAbsolutePath(), BioCFactory.WOODSTOX);

    File expected = new File(testResourcesDir, "lemma.xml");
    BioCTestCommon.areXMLSimilar(expected, output);
  }

}
