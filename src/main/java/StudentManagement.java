import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagement {
    private static final Logger logger = LoggerFactory.getLogger(StudentManagement.class);

    public static boolean existedId(String id) {
        String query = "SELECT 1 FROM student WHERE id = ?";
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            logger.error("Lỗi kiểm tra tồn tại: ", e);
        }
        return false;
    }

    public void add(Student student) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = PostgreSQLJDBC.getInstance().getConnection();
            PostgreSQLJDBC.beginTransaction(con); // Bắt đầu transaction

            if (!existedId(student.getId())) {
                String query = "INSERT INTO student (id, name, address, age) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, student.getId());
                ps.setString(2, student.getName());
                ps.setString(3, student.getAddress());
                ps.setInt(4, student.getAge());
                ps.executeUpdate();
                logger.info("Thêm sinh viên thành công.");

                PostgreSQLJDBC.commitTransaction(con); // Commit transaction
            } else {
                logger.warn("Sinh viên đã tồn tại.");
                PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu sinh viên đã tồn tại
            }
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("Lỗi khi rollback: ", rollbackEx);
            }
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, null); // Đóng tài nguyên
        }
    }

    public String toString(ResultSet rs) {
        try {
            String studentId = rs.getString("id");
            String name = rs.getString("name");
            String address = rs.getString("address");
            int age = rs.getInt("age");
            return studentId + "\t" + name + "\t" + address + "\t" + age;
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
        }
        return "";
    }

    public void displayListStudent() {
        String query = "SELECT * FROM student";
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                logger.info(toString(rs));
            }
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
        }
    }

    public void displayById(String idSv) {
        String query = "SELECT * FROM student WHERE id = ?";
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, idSv);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logger.info(toString(rs));
                } else {
                    logger.warn("Sinh viên không tồn tại.");
                }
            }
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
        }
    }

    public void deleteById(String idSv) {
        String query = "DELETE FROM student WHERE id = ?";
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, idSv);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("Xóa sinh viên thành công.");
            } else {
                logger.warn("Không tìm thấy sinh viên với ID: " + idSv);
            }
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
        }
    }

    public void updateById(String idSv) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên muốn update nếu không thay đổi thì nhập lại tên cũ: ");
        String name = sc.nextLine();
        System.out.print("Nhập địa chỉ muốn update nếu không thay đổi thì nhập lại địa chỉ cũ: ");
        String address = sc.nextLine();
        System.out.print("Nhập tuổi muốn update nếu không thay đổi thì nhập lại tuổi cũ: ");
        int age = sc.nextInt();

        String query = "UPDATE student SET name = ?, address = ?, age = ? WHERE id = ?";
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, age);
            ps.setString(4, idSv);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Đã cập nhật thông tin sinh viên.");
            } else {
                logger.warn("Sinh viên không tồn tại.");
            }
        } catch (SQLException e) {
            logger.error("Lỗi: ", e);
        }
    }
}
