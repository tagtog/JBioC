package bioc;

import java.io.File;
import java.io.FileReader;

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

public class BioCTestCommon {

  public static void apply() {
    BioCTestCommon.prepareXMLUnit();
    BioCTestCommon.dontValidateDTD();
  }

  public static void prepareXMLUnit() {
    XMLUnit.setIgnoreWhitespace(true);
    XMLUnit.setIgnoreComments(true);
    XMLUnit.setIgnoreAttributeOrder(true);
  }

  public static void dontValidateDTD() {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setValidating(false);
    try {
      dbf.setFeature("http://xml.org/sax/features/namespaces", false);
      dbf.setFeature("http://xml.org/sax/features/validation", false);
      dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
      dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }

    XMLUnit.setTestDocumentBuilderFactory(dbf);
    XMLUnit.setControlDocumentBuilderFactory(dbf);
  }

  public static void areXMLSimilar(File expected, File actual) throws Exception {
    FileReader expectedReader = new FileReader(expected);
    FileReader actualReader = new FileReader(actual);
    Diff diff = new Diff(expectedReader, actualReader);

    XMLAssert.assertTrue("XML similar", diff.similar());
  }

}
