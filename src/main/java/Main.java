
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagement list = new StudentManagement();
        int choice;
        do {
            System.out.println("---------MENU---------");
            System.out.println("Nhập từ bàn phím số bạn muốn chọn");
            System.out.println(
                    "1. Thêm sinh viên vào danh sách\n"
                            +"2. Hiển thị danh sách sinh viên\n"
                            +"3. Hiển thị sinh viên theo mã sinh viên\n"
                            +"4. Xóa sinh viên theo mã sinh viên\n"
                            +"0. Thoát khỏi trương trình\n"
            );
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1){
                System.out.println("Nhập mã sinh viên"); String idSv = sc.nextLine();
                System.out.println("Nhập tên của sinh viên"); String name = sc.nextLine();
                System.out.println("Nhập địa chỉ của sinh viên"); String address = sc.nextLine();
                System.out.println("Nhập tuổi của sinh viên"); int age = sc.nextInt();
                Student sv = new Student(idSv,name,address,age);
                //ds.addStudent(sv);
                list.add(sv);
            } else if (choice == 2) {
                //ds.showList();
                list.displayListStudent();
            }else if (choice == 3) {
                System.out.println("Nhập mã sinh viên muốn tìm:");String idSv = sc.nextLine();
                //ds.searchSv(idSv);
                list.displayById(idSv);
            }else if (choice == 4) {
                System.out.println("Nhập mã sinh viên muốn xóa:");String idSv = sc.nextLine();
                //ds.deleteSv(idSv);
                //1list.deleteStudent(idSv);
            }

        }while (choice != 0);
    }
}
