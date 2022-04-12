
// This is the interface that each of your Box classes will implement.
// As you can see, this interface allows different kinds of boxes to 
// store and return their volume and weight using any class types.
interface Parcel <VolumeType, WeightType> {
    public VolumeType getVolume();
    public WeightType getWeight();
}

// TODO: implement the MetricBox class below to:
//  1) correctly implement the Parcel interface above
//  2) force the volume and weight returned from any MetricBox to be type Double
//  3) enable the checks in the main method below to compile and run
class MetricBox {}

// TODO 2/3: implement the EnglishBox class below to:
//  1) correctly implement the Parcel interface above
//  2) force the volume returned from any EnglishBox to be type String, but
//     allow the weight to be different for different EnglishBoxes
//  3) enable the checks in the main method below to compile and run
class EnglishBox {}

// TODO 3/3: implement the UniformBox class below to:
//  1) correctly implement the Parcel interface above
//  2) force the weight and volume retrieved from any UniformBox to be of a single
//  consistent generic type: so that the volume and weight cannot be different types
//  3) enable the checks in the main method below to compile and run
class UniformBox {}

public class GenericsActivity {
    public static void main(String[] args) {
        String typesChecked = "";

        // Checks for the MetricBox type:
        // Parcel<Double,Double> m = new MetricBox(3.0,4.0);
        // if( !(m.getVolume().toString()+m.getWeight().toString()).equals("3.04.0") )
        //     System.out.println("m does not include expected contents.");
        // typesChecked += "MetricBox ";

        // Checks for the EnglishBox type:
        // Parcel<String,Integer> e1 = new EnglishBox<>("large",5);
        // if( !(e1.getVolume().toString()+e1.getWeight().toString()).equals("large5") )
        //     System.out.println("e1 does not include expected contents.");
        // Parcel<String,String> e2 = new EnglishBox<>("medium","heavy");
        // if( !(e2.getVolume().toString()+e2.getWeight().toString()).equals("mediumheavy") )
        //     System.out.println("e2 does not include expected contents.");
        // typesChecked += "EnglishBox ";

        // Checks for the UniformBox type:
        // Parcel<Float,Float> u1 = new UniformBox<>(6.0f, 2.0f);
        // if( !(u1.getVolume().toString()+u1.getWeight().toString()).equals("6.02.0") )
        //     System.out.println("u1 does not include expected contents.");
        // Parcel<String,String> u2 = new UniformBox<>("small","light");
        // if( !(u2.getVolume().toString()+u2.getWeight().toString()).equals("smalllight") )
        //     System.out.println("u2 does not include expected contents.");
        // typesChecked += "UniformBox ";

        System.out.println("Checks complete for: "+typesChecked);
    }    
}
