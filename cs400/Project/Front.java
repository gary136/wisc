// --== CS400 File Header Information ==--
// Name: Lee Hung Ting
// Email: hlee864@@wisc.edu
// Notes to Grader: None

import java.util.ArrayList;
import java.util.Scanner;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Front {

  private static boolean isNumeric(String strNum) {
    if (strNum == null) return false;
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
  }
  
  public static HashTableMap LoadMetaData(String dirpath, int innerDataCapacity) throws IOException {
    String path = dirpath+"/"+"metadata.csv";
    HashTableMap metadata = new HashTableMap(30);
    String line;
    ArrayList idList = new ArrayList<String>();
    try {
      // read the data
      InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
      BufferedReader reader = new BufferedReader(isr);
      
      while((line=reader.readLine())!=null) {
        ArrayList item = new ArrayList();
        for (String i:line.split(",")) item.add(i);
        /** read csv into separate text **/
        String id= ((String)item.get(0)).trim();
        String name = ((String)item.get(1)).trim();
        String shortDescription = ((String)item.get(2)).trim();
        String innerpath = dirpath+"/"+name+".csv";
        File f=new File(innerpath);
        
        if(f.isFile()) {
          idList.add(id);
          item.remove(0);      
          item.add(new HashTableMap(innerDataCapacity));
          metadata.put(id, item);
        }
      }
      
      for (int i=0; i<idList.size(); i++) {
        ArrayList data = (ArrayList) metadata.get(idList.get(i));
        String innerPath = dirpath+"/"+ data.get(0)+".csv";
        try {
          InputStreamReader innerIsr = new InputStreamReader(new FileInputStream(innerPath));//檔案讀取路徑
          String total = "";
          BufferedReader innerReader = new BufferedReader(innerIsr);
          String innerLine;
          total+=("date" + "\t" + "price" + "\n"); 
          
          while((innerLine=innerReader.readLine())!=null){
             String innerItem[] = innerLine.split(",");
             /** read csv into separate text **/
             String date= innerItem[0].trim();
             String price= innerItem[1].trim();
             if (isNumeric(date)) {
               ((HashTableMap) data.get(2)).put(date, price);
               total+=(date + "\t" + price + "\n"); 
             }
           }
          data.add(total);
       } catch (FileNotFoundException e) {
        e.printStackTrace();
       }
      }      
     } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.out.println("Directory not found");
     }
    return metadata;
  }
  
  public static void main(String[] args) throws IOException {  

    // prepare the data
    String dirpath="./data";
    HashTableMap metadata = LoadMetaData(dirpath, 100);
    Scanner myObj;
    String inst;    
    String answer;
    
    // show welcome and instruction messages
    System.out.println("Welcome to commodities info searching app!");
    System.out.println("You can get commodity related information by entering corresponding instruction.");
    
    outerloop:
    while (true){    
      // ask user to type the searching criteria
      myObj = new Scanner(System.in);  // Create a Scanner object;
      System.out.print("Enter s to start searching, q to leave the app : ");
      inst = myObj.nextLine();  // Read user input      
      if (inst.equals("s")) {
        // ask for id
        myObj = new Scanner(System.in);  
        System.out.print("Enter the commodity id : ");
        String id = myObj.nextLine();  // Read user input
        if (!metadata.containsKey(id)) System.out.println("Commodity does not exist.");
        
        else {
          ArrayList data = (ArrayList) metadata.get(id);
          System.out.println("You are searching : " + data.get(0));           
          
          innerloop:
          while (true) {
            // ask for next instruction
            myObj = new Scanner(System.in);
            System.out.print("Enter d to get short description, "
                + "p to get price of whole time range, "
                + "x to get price of specific date, "
                + "l to restore the last stage, "
                + "q to leave the app : ");
            answer = myObj.nextLine();
            if (answer.equals("d")) System.out.println(data.get(1)+"\n");
            
            if (answer.equals("p")) System.out.println(data.get(3));
            
            if (answer.equals("x")) {
              myObj = new Scanner(System.in);
              System.out.print("Enter the date(yymmdd) : ");
              String date = myObj.nextLine();
              HashTableMap innerData = (HashTableMap) data.get(2);
              if (!innerData.containsKey(date)) System.out.println("Date of price does not exist.");
              else {
                String price = (String) innerData.get(date);
                System.out.println("price = " + price+"\n");
              }
            }
            if (answer.equals("l")) {
              System.out.println("Back to last stage.\n");
              break innerloop;
            }
            else if (answer.equals("q")) {
              System.out.println("Thank you for using this silly app.");
              break outerloop;
            }
          }
        }
      }
      else if (inst.equals("q")) {
        System.out.println("Thank you for using this silly app.");
        break;
      }    
    }       
  }
  
}