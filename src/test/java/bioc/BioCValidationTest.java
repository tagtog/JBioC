package bioc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.MalformedURLException;

import org.custommonkey.xmlunit.Validator;
import org.custommonkey.xmlunit.exceptions.ConfigurationException;
import org.junit.Test;
import org.junit.Before;
import org.xml.sax.SAXException;

public class BioCValidationTest {

  File resourcesDir;
  String localDTD = "BioC.dtd";

  @Before
  public void setUp() throws Exception {
    resourcesDir = new File(getClass().getResource("/xml").toURI());
  }

  @Test
  public void test() throws ConfigurationException, FileNotFoundException,
                            SAXException, MalformedURLException {

    File[] files = resourcesDir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.endsWith(".xml");
        }
      });

    for (File f : files) {
      test(f);
    }
  }

  private void test(File file) throws FileNotFoundException,
                                      ConfigurationException, SAXException, MalformedURLException {
    try {
      System.out.println("testing " + file + " ... ");
      FileReader reader = new FileReader(file);
      File dtd = new File(localDTD);
      Validator v = new Validator(reader, dtd.toURI().toURL().toString());
      v.assertIsValid();
    } catch (AssertionError e) {
      System.err.println("test FAILED: " + file);
      System.err.println(e.getMessage());
      throw e;
    } catch (FileNotFoundException e) {
      System.err.println("test FAILED: " + file);
      System.err.println(e.getMessage());
      throw e;
    } catch (ConfigurationException e) {
      System.err.println("test FAILED: " + file);
      System.err.println(e.getMessage());
      throw e;
    } catch (SAXException e) {
      System.err.println("test FAILED: " + file);
      System.err.println(e.getMessage());
      throw e;
    } catch (MalformedURLException e) {
      System.err.println("test FAILED: " + file);
      System.err.println(e.getMessage());
      throw e;
    }
  }
}
