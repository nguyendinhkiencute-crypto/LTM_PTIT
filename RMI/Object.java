package RMI;

import java.rmi.*;
import java.rmi.registry.*;

public class Object {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242",1099);
            ObjectService service = (ObjectService)registry.lookup(
                "RMIObjectService"
            );

            java.io.Serializable responseObj = service.requestObject("B23DCCN463", "WGJer1SB");
            if(responseObj == null){
                System.out.println("Loi: server tra ve doi tuong null");
                return ;
            }
            if(! (responseObj instanceof TicketSla)){
                System.out.println("Loi: doi tuong nhan duoc khong phai TicketSla");
                return ;
            }

            TicketSla ticket = (TicketSla)responseObj;  

            System.out.println("Nhận từ server -> TicketId: " +ticket.getClass()     + 
                               ", Priority: " + ticket.getPriority() + 
                               ", OpenedHoursAgo: " + ticket.getOpenedHoursAgo());
            String priority = ticket.getPriority();
            int hours = ticket.getOpenedHoursAgo();
            boolean breached = false;

            if (priority != null) {
                switch (priority.toUpperCase()) {
                    case "CRITICAL":
                        breached = (hours > 2);
                        break;
                    case "HIGH":
                        breached = (hours > 8);
                        break;
                    case "MEDIUM":  
                        breached = (hours > 24);
                        break;
                    case "LOW":
                        breached = (hours > 72);
                        break;
                    default:
                        breached = false;
                        break;
                }
            }
                ticket.setBreached(breached);

                String action;
                if(!breached){
                    action = "MONITOR";
                }else{
                    if("CRITICAL".equalsIgnoreCase(priority) || hours > 96 ){
                        action ="ESCALATE_L2";
                    }else{
                        action = "ESCALATE_L1";
                    }
                }
                
                ticket.setAction(action);
                System.out.println("Cập nhật -> Breached: " + ticket.isBreached() + ", Action: " + ticket.getAction());
                service.submitObject("B23DCCN463", "WGJer1SB", ticket);
                System.out.println("Đã gửi đối tượng cập nhật lên server thành công!");
        
        }catch(Exception e){

        }

    }
}
