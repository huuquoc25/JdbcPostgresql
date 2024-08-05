import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagement {
    public static boolean existedId(String id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            con = PostgreSQLJDBC.con();
            if (con != null) {
                String query = "SELECT * FROM student WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, id);
                rs = ps.executeQuery();
                exists = rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra tồn tại: " + e.getMessage());
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs);
        }

        return exists;
    }
    public void add(Student student) {
        if (!existedId(student.getIdSv())) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = PostgreSQLJDBC.con();
                String query = "INSERT INTO student (id, name, address, age) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(query);
                ps.setString(1, student.getIdSv());
                ps.setString(2, student.getName());
                ps.setString(3, student.getAddress());
                ps.setInt(4, student.getAge());
                ps.executeUpdate();
                System.out.println("Thêm sinh viên thành công.");
            } catch (SQLException e) {
                System.out.println("lỗi: " + e.getMessage());
            } finally {
                PostgreSQLJDBC.closeResources(con, ps, null);

            }
        }
        else {
            System.out.println("Sinh viên đã tồn tại");
        }

    }
    public String toString(ResultSet rs) {
        try {
            String studentId = rs.getString("id");
            String name = rs.getString("name");
            String address = rs.getString("address");
            int age = rs.getInt("age");
            ;
            return studentId + "\t" + name + "\t" + address + "\t" + age;
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return "";
    }

    public void displayListStudent() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = PostgreSQLJDBC.con();
            if (con != null) {
                String query = "SELECT * FROM student";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(toString(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs);
        }
    }

    public void displayById(String idSv) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = PostgreSQLJDBC.con();
            if (con != null) {
                String query = "SELECT * FROM student WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, idSv);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println(toString(rs));
                } else {
                    System.out.println("Sinh viên không tồn tại");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs);
        }
    }
    public void deleteById(String idSv) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = PostgreSQLJDBC.con();
            if (con != null) {
                String query = "DELETE FROM student WHERE id = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, idSv);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("Đã xóa sinh viên");
                } else {
                    System.out.println("Sinh viên không tồn tại");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs);
        }
    }
}
