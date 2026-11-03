package RMI_main;

import java.rmi.Naming;
import java.util.LinkedHashMap;
import java.util.Map;

import RMI.ByteService;

public class Byte1 {
    public static void main(String[] args) {
        try{
            String studentCode = "B23DCCN463";
            String qCode = "SgKD0WzJ";
            ByteService service = (ByteService)Naming.lookup(
                "rmi://36.50.135.242/RMIByteService"
            );
            byte[] data = service.requestData(studentCode, qCode);
            LinkedHashMap <Byte,Integer> lhm = new LinkedHashMap<>();

            for(byte b : data){
                lhm.put(b,lhm.getOrDefault(b,0) + 1);
            }
            
            byte firstElm = data[0];
            int minFre = (int)1e9;

            for(Map.Entry<Byte,Integer> entry : lhm.entrySet()){
                if(entry.getValue() < minFre){
                    minFre = entry.getValue();
                    firstElm = entry.getKey();
                }
            }
            byte []res = new byte[2];
            res[0] = firstElm;
            res[1] = (byte) minFre;

            service.submitData(studentCode, qCode, res);
        }catch(Exception e){
            
        }
       
    }
}
