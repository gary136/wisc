import java.util.ArrayList;

interface MathOperation {
    public int compute(int a, int b);
}

class Addition implements MathOperation {
    public int compute(int a, int b) {
        return a + b;
    }   
}

public class LambdaExpressionActivity {
        
    public void performAllOperations(int a, int b) {

        ArrayList<MathOperation> ops = new ArrayList<>();

        // TODO: use named class Addition for addition:
        // ops.add( ??? ); // addition 10+6=16
        ops.add(new Addition());
//        Addition p1 = new Addition();
//        ops.add(p1);

        // TODO: use anonymous class for multiplication:
        // ops.add( ??? ); // multiplication 10*6=60
        ops.add(new MathOperation() {
            public int compute(int a, int b) {
              return a * b;
          }   
        });
//        MathOperation multiplication = new MathOperation() {
//            public int compute(int a, int b) {
//              return a * b;
//          }   
//        };
      

        // TODO: use lambda expression for subtraction:
        // ops.add( ??? ); // subtraction 10-6=4
        ops.add(
      (int x, int y) -> x - y
        //    new MathOperation() {
        //    public int compute(int a, int b) {
        //      return a - b;
        //    }   
        //  }
      );
        // TODO: use lambda expressions for division:
        // ops.add( ??? ); // division 10/6 = 1 (integer arithmetic)
        ops.add((int x, int y) -> x / y);

        for(MathOperation op : ops) {
            System.out.println( op.compute(a,b) + " // computed by " + op.getClass().toString());
        }
    }
    
    public static void main(String args[]){
        LambdaExpressionActivity lambdaActivity = new LambdaExpressionActivity();
        lambdaActivity.performAllOperations(10, 6);
    }
}
