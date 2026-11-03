package RMI_main;

import java.rmi.registry.*;

import RMI.DataService;
import RMI.Object;

import java.rmi.registry.*;
import java.util.*;
public class Data1 {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242",1099);
            DataService service = (DataService)registry.lookup(
                "RMIDataService"
            );

            Object data = service.requestData("B23DCCN463", "yjodPiCE");
            List<Integer> numbers = new ArrayList<>();

            if(data instanceof int[]){
                int []arr = (int[])data;
                for(int n : arr){
                    numbers.add(n);
                }
            }else if(data instanceof List){
                numbers = (List<Integer>)data;
            }else if(data instanceof Integer[]){
                Integer []arr = (Integer[])data;
                for(Integer n : arr){
                    numbers.add(n);
                }
            }
            System.out.println("Dữ liệu nhận từ server: " + numbers);
            List<Integer> localPeaks = new ArrayList<>();

            int size = numbers.size();
            for(int i = 0;i<size;i++){
                int check = 1;
                if(i > 0 && numbers.get(i - 1) >= numbers.get(i)){
                    check = 0;
                }
                if(i < size - 1 && numbers.get(i+1) >= numbers.get(i)){
                    check = 0;
                }
                if(check == 1){
                    localPeaks.add(i + 1);
                }
            }
            System.out.println("Các vị trí đỉnh cục bộ (1-based): " + localPeaks);

            // d. Triệu gọi submitData để gửi kết quả lên server
            service.submitData("B23DCCN463", "yjodPiCE", localPeaks);
            System.out.println("Đã gửi kết quả thành công lên server!");
        }catch(Exception e){

        }
       
    }
}
