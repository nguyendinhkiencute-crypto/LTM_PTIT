package RMI_main;

import java.rmi.*;
import java.net.*;
import RMI.CharacterService;

public class Character1 {
    public static void main(String[] args) {
        try{
            CharacterService service = (CharacterService)Naming.lookup(
                "rmi://36.50.135.242/RMICharacterService"
            );

            String receiveString = service.requestCharacter("B23DCCN463", "Sgq1PVrB");
            String urlEncode = new URI(null,null,receiveString,null).toASCIIString();
            System.out.println(urlEncode);
            service.submitCharacter("B23DCCN463", "Sgq1PVrB", urlEncode);
        }catch(Exception e){

        }
    }
}
