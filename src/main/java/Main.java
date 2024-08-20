
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagement list = new StudentManagement();
        int choice;
        do {
            System.out.println("---------MENU---------");
            System.out.println("Enter the choice");
            System.out.println(
                    "1. Add new Student\n"
                            +"2. Show List Student\n"
                            +"3. Show Student by ID\n"
                            +"4. Delete Student by ID\n"
                            +"5. Update information of studen\n"
                            +"0. Close the program\n"
            );
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1){
                System.out.println("Enter student's id"); String idSv = sc.nextLine();
                System.out.println("Enter student's name"); String name = sc.nextLine();
                System.out.println("Enter student's address"); String address = sc.nextLine();
                System.out.println("Enter student's age"); int age = sc.nextInt();
                Student sv = new Student(idSv,name,address,age);
                //ds.addStudent(sv);
                list.add(sv);
            } else if (choice == 2) {
                //ds.showList();
                list.displayListStudent();
            }else if (choice == 3) {
                System.out.println("Enter student's id to find:");String idSv = sc.nextLine();
                //ds.searchSv(idSv);
                list.displayById(idSv);
            }else if (choice == 4) {
                System.out.println("Enter student's id to delete:");String idSv = sc.nextLine();
                //ds.deleteSv(idSv);
                list.deleteById(idSv);
            }else if (choice == 5) {
            System.out.println("Enter student's id to update:");String idSv = sc.nextLine();
            //ds.deleteSv(idSv);
                System.out.print("Enter the name you want to update, if not changed, re-enter the old name: ");
                String name = sc.nextLine();
                System.out.print("Enter the address you want to update, if not changed, re-enter the old address: ");
                String address = sc.nextLine();
                System.out.print("Enter the age you want to update, if not changed, re-enter the old age: ");
                int age = sc.nextInt();
                list.updateById(idSv,name,address,age);

        }

        }while (choice != 0);
    }
}
