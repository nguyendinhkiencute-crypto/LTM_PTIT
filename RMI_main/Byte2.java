package RMI_main;

import java.rmi.Naming;
import java.util.LinkedHashMap;
import java.util.Map;

import RMI.ByteService;

public class Byte2 {
    public static void main(String[] args) {
        try{
            ByteService service = (ByteService)Naming.lookup(
                "rmi://36.50.135.242/RMIByteService"
            );

            byte[]data = service.requestData("B23DCCN463", "M8VUDbBX");
            LinkedHashMap<Byte,Integer> lhm = new LinkedHashMap<>();

            for(byte b : data){
                lhm.put(b,lhm.getOrDefault(b, 0) + 1);
            }
            byte firstElm = data[0];
            int maxF = 0;
            for(Map.Entry<Byte,Integer> entry : lhm.entrySet()){
                if(entry.getValue() > maxF){
                    maxF = entry.getValue();
                    firstElm = entry.getKey();
                }
            }

            byte []res = new byte[2];
            res[0] = firstElm;
            res[1] = (byte) maxF;

            service.submitData("B23DCCN463", "M8VUDbBX", res);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
