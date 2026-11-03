package RMI_main;

import java.rmi.Naming;

import RMI.CharacterService;

public class Character2 {
    public static void main(String[] args) {
        String studentCode = "B21DCCN000"; // Thay bằng Mã sinh viên của bạn
        String qCode = "abcXYZ";           // Thay bằng Mã câu hỏi được cấp

        try {
            // 1. Kết nối tới RMI Registry tại localhost (port 1099)
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // 2. Tra cứu đối tượng từ xa với tên "RMICharacterService"
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

            // a. Triệu gọi phương thức requestCharacter để nhận chuỗi số thập phân
            String response = service.requestCharacter(studentCode, qCode);
            System.out.println("Chuỗi nhận từ Server: " + response);

            // b. Chuyển đổi số thập phân nhận được thành chuỗi số La Mã
            int number = Integer.parseInt(response.trim());
            String romanResult = convertToRoman(number);
            System.out.println("Số La Mã sau khi chuyển đổi: " + romanResult);

            // c. Triệu gọi phương thức submitCharacter để gửi kết quả trở lại server
            service.submitCharacter(studentCode, qCode, romanResult);
            System.out.println("Đã gửi kết quả thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // d. Kết thúc chương trình
    }

    /**
     * Thuật toán chuyển đổi số thập phân sang số La Mã (Roman)
     */
    private static String convertToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                roman.append(symbols[i]);
            }
        }
        return roman.toString();
    }
}
