public class Main {

    public static void main(String[] args) {
      // instantiate the class MyList 
//      ListADT<Integer> _test = null;
      ListADT<String> _test = new MyList<String>();
      // call ListADT methods add 
      _test.add("x");
      // call ListADT methods get
//      test.get(0);
      System.out.println(_test.get(0));
    }

}
