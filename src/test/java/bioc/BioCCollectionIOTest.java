package bioc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import bioc.BioCCollection;
import bioc.io.BioCCollectionReader;
import bioc.io.BioCCollectionWriter;
import bioc.io.BioCFactory;

public class BioCCollectionIOTest {

  File dir;

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder();

  @BeforeClass
  public static void onlyOnce() {
    BioCTestCommon.apply();
  }

  @Before
  public void setUp() throws Exception {
    dir = new File(getClass().getResource("/xml").toURI());
  }

  @Test
  public void testWoodstox()
      throws Exception {
    File tempFile = testFolder.newFile("test.woodstox.xml");

    File[] files = dir.listFiles(new FilenameFilter() {

      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".xml");
      }

    });
    for (File f : files) {
      System.out.println("testing WOODSTOX IO on " + f + " ...");
      testXMLIdentical(
          BioCFactory.newFactory(BioCFactory.WOODSTOX),
          f,
          tempFile);
    }
  }

  @Test
  public void testStandard()
      throws Exception {
    File tempFile = testFolder.newFile("test.standard.xml");

    File[] files = dir.listFiles(new FilenameFilter() {

      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".xml");
      }

    });
    for (File f : files) {
      System.out.println("testing STANDARD IO on " + f + " ...");
      testXMLIdentical(
          BioCFactory.newFactory(BioCFactory.STANDARD),
          f,
          tempFile);
    }
  }

  @Ignore("Test is ignored as this function is not decided yet")
  @Test(expected = IllegalStateException.class)
  public void testWriteCollectionTwice()
      throws XMLStreamException, IOException {
    BioCCollection collection = new BioCCollection();

    BioCCollectionWriter writer = BioCFactory.newFactory(BioCFactory.STANDARD)
        .createBioCCollectionWriter(
            new FileWriter(testFolder.newFile("test.standard.xml")));
    writer.writeCollection(collection);
    writer.writeCollection(collection);
    writer.close();
  }

  private void testXMLIdentical(BioCFactory factory, File inXML, File outXML)
      throws Exception {

    BioCCollectionReader reader = factory
        .createBioCCollectionReader(new FileReader(inXML));
    BioCCollectionWriter writer = factory
        .createBioCCollectionWriter(new FileWriter(outXML));

    BioCCollection collection = reader.readCollection();
    writer.writeCollection(collection);

    reader.close();
    writer.close();

    FileReader controlReader = new FileReader(inXML);
    FileReader testReader = new FileReader(outXML);

    Diff diff = new Diff(controlReader, testReader);

    try {
      XMLAssert.assertTrue("XML similar " + diff.toString(), diff.similar());
    } catch (AssertionError e) {
      DetailedDiff detDiff = new DetailedDiff(diff);
      @SuppressWarnings("rawtypes")
      List allDifferences = detDiff.getAllDifferences();
      for (Object object : allDifferences) {
        Difference difference = (Difference) object;
        System.out.println(difference);
      }
    }
  }
}
