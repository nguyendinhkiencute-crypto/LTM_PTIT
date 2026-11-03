package RMI_main;

import java.rmi.Naming;

import RMI.CharacterService;

public class Character2 {
    static String solve(long n){
        String res = "";
        int [] values = {1000,900,500,400,};
        return res;
    }
    public static void main(String[] args) {
        try{
            CharacterService service = (CharacterService)Naming.lookup(
                "rmi://36.50.135.242/RMICharacterService"
            );
            String receiveString = service.requestCharacter("B23DCCN463", "By45C3wF");
            // Các ký tự La Mã chính bao gồm: I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
            String handleString = solve(Long.parseLong(receiveString));
            System.out.println(handleString);
            service.submitCharacter("B23DCCN463", "By45C3wF", handleString);
        }catch(Exception e){

        }
        
    }
}
