package ss15_exception_debug.exercise.exercise2.esercise1.controllor;

import ss15_exception_debug.exercise.exercise2.esercise1.service.ITeacherService;
import ss15_exception_debug.exercise.exercise2.esercise1.service.impl.TeacherService;

import java.util.Scanner;

public class TeacherController {
    private static final ITeacherService iTeacherService = new TeacherService<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void menuTeacher() {
        while(true) {
            System.out.println("--------------------------");
            System.out.println("Chào mừng bạn đến với chương trình quản lý giảng viên ");
            System.out.println("Chọn chức năng theo số (để tiếp tục)");
            System.out.println("1. Thêm mới giảng viên");
            System.out.println("2. Hiển thị danh sách giảng viên");
            System.out.println("3. Xóa giảng viên");
            System.out.println("4. Tìm kiếm giảng viên");
            System.out.println("5. Sắp xếp giảng viên");
            System.out.println("6. Thoát");
            System.out.print("Vui lòng nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    iTeacherService.addTeacher();
                    break;
                case 2:
                    iTeacherService.displayTeacher();
                    break;
                case 3:
                    iTeacherService.removeTeacher();
                    break;
                case 4:
                    iTeacherService.searchTeacher();
                    break;
                case 5:
                    iTeacherService.sortTeacher();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Nhập sai rồi!");

            }
        }

    }
}
