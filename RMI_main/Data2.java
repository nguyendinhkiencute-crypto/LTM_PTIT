package RMI_main;
import java.rmi.registry.*;

import RMI.DataService;

import java.rmi.*;
import java.util.*;

public class Data2 {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242",1099);
            DataService service = (DataService)registry.lookup(
                "RMIDataService"
            );

            Object data = service.requestData("B23DCCN463", "l7fOtNpg");

            String csvString = data.toString();
            List<Double> numbers = new ArrayList<>();
            String []parts = csvString.split(",");
            for(String s : parts){
                numbers.add(Double.parseDouble(s));
            }
            
            int n = parts.length;
            double avg = 0;
            double stddev = 0;
            int p95 = (int)(Math.ceil(0.95 * n) - 1);

            for(Double x : numbers){
                avg+= (x/n);
            }
            for(Double x : numbers){
                stddev += (x -avg) * (x - avg);
            }
            stddev = Math.sqrt(stddev) / Math.sqrt(n);

            Collections.sort(numbers,new Comparator<Double>() {
                @Override 
                public int compare(Double a,Double b){
                    return Double.compare(a, b);
                }
            });
            String submissionData = String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f",avg,stddev,numbers.get(p95));
            System.out.println(submissionData);
            service.submitData("B23DCCN463", "l7fOtNpg", submissionData);
        }catch(Exception e){

        }
    }
}
