// --== CS400 File Header Information ==--
// Name: Lee Hung Ting
// Email: hlee864@@wisc.edu
// Notes to Grader: None

import java.io.IOException;
import java.util.ArrayList;
/*** JUnit imports ***/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/*** JUnit imports end  ***/

public class ApplicationTest {
  // add data in directory
  String dirpath="./data";
  HashTableMap metadata;
  //BeforeEach annotation makes a method invoked automatically before each test
  @BeforeEach
  public void Load() throws IOException {
    //create File object
    metadata = Front.LoadMetaData(dirpath, 100);
  }
    
  @Test
  public void testContainId() throws IOException {
    String commodityId = "01";
    ArrayList data = (ArrayList) metadata.get(commodityId);
    System.out.println(commodityId + " " +data.get(0) + " " + data.get(1));
    // test if input id is contained
    assertEquals(metadata.containsKey(commodityId), true);
    // test if input id maps to corresponding name
    assertEquals(data.get(0), "cement");
    String commodityFakeId = "08";
    // test if fake input id is not contained
    assertEquals(metadata.containsKey(commodityFakeId), false);
  }  

  @Test
  public void testPrintDescription() throws IOException {
    String commodityId = "04";
    ArrayList data = (ArrayList) metadata.get(commodityId);
    System.out.println(commodityId + " " +data.get(0) + " " + data.get(1));
    // test if input id maps to corresponding description
    assertEquals(data.get(1), "a common thermoplastic polymer typically used for injection molding applications");
  }

  @Test
  public void testGrowHash() throws IOException {
    String commodityId = "05";
    ArrayList data = (ArrayList) metadata.get(commodityId);
    HashTableMap h5 = (HashTableMap) data.get(2);
    System.out.println("size = " + h5.size() + ", capacity = " + h5.getCapacity());
    assertEquals(h5.getCapacity(), 100);
    
    // when loading data with smaller initial capacity, test if data can grow when needed
    metadata = Front.LoadMetaData(dirpath, 20);
    data = (ArrayList) metadata.get(commodityId);
    h5 = (HashTableMap) data.get(2);
    System.out.println("size = " + h5.size() + ", capacity = " + h5.getCapacity());
    assertEquals(h5.getCapacity(), 40);
  }
  
  @Test
    public void testRetrieveAllData() throws IOException {
      String commodityId = "06";
      ArrayList data = (ArrayList) metadata.get(commodityId);
      System.out.println(commodityId + " " +data.get(0) + " " + data.get(1));
      String allData = (String) data.get(3);
      // test if input id maps to corresponding file
      assertEquals(allData, "date" + "\t" + "price\n"
                        + "210109" + "\t" + "1160\n"
                        + "210116" + "\t" + "1160\n"
                        + "210123" + "\t" + "1160\n"
                        + "210130" + "\t" + "1160\n"
                        + "210206" + "\t" + "1210\n"
                        + "210213" + "\t" + "1210\n"
                        + "210220" + "\t" + "1210\n"
                        + "210227" + "\t" + "1260\n"
                        + "210306" + "\t" + "1290\n"
                        + "210313" + "\t" + "1290\n"
                        + "210320" + "\t" + "1390\n"
                        + "210327" + "\t" + "1440\n"
                        + "210403" + "\t" + "1440\n"
                        + "210410" + "\t" + "1440\n"
                        + "210417" + "\t" + "1440\n"
                        + "210424" + "\t" + "1440\n"
                        + "210501" + "\t" + "1440\n"
                        + "210508" + "\t" + "1420\n"
                        + "210515" + "\t" + "1420\n"
                        + "210522" + "\t" + "1420\n"
                        + "210529" + "\t" + "1350\n"
                        + "210605" + "\t" + "1325\n"
                        + "210612" + "\t" + "1325\n"
                        + "210619" + "\t" + "1325\n"
                        + "210626" + "\t" + "1240\n"
                        + "210703" + "\t" + "1240\n");
    }

    @Test
    public void testRetrievePartialData() throws IOException {
      String commodityId = "07";
      ArrayList data = (ArrayList) metadata.get(commodityId);
      System.out.println(commodityId + " " +data.get(0) + " " + data.get(1));
      String date = "210619";
      HashTableMap innerData = (HashTableMap) data.get(2);
      // test if input id and input date maps to corresponding price data
      assertEquals(innerData.get(date), "1460");
    }
    
}