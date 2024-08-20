import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagement {
    private static final Logger logger = LoggerFactory.getLogger(StudentManagement.class);

    // Các câu lệnh SQL được khai báo dưới dạng biến constant
    private static final String SELECT_STUDENT_BY_ID = "SELECT 1 FROM student WHERE id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO student (id, name, address, age) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM student";
    private static final String SELECT_STUDENT_BY_ID_QUERY = "SELECT * FROM student WHERE id = ?";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM student WHERE id = ?";
    private static final String UPDATE_STUDENT_BY_ID = "UPDATE student SET name = ?, address = ?, age = ? WHERE id = ?";

    public static boolean existedId(String id) {
        try (Connection con = PostgreSQLJDBC.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_STUDENT_BY_ID)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            logger.error("error: ", e);
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
                ps = con.prepareStatement(INSERT_STUDENT);
                ps.setString(1, student.getId());
                ps.setString(2, student.getName());
                ps.setString(3, student.getAddress());
                ps.setInt(4, student.getAge());
                ps.executeUpdate();
                logger.info("add successfully.");

                PostgreSQLJDBC.commitTransaction(con); // Commit transaction
            } else {
                logger.warn("Student existed.");
                PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu sinh viên đã tồn tại
            }
        } catch (SQLException e) {
            logger.error("error: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("error rollback: ", rollbackEx);
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
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = PostgreSQLJDBC.getInstance().getConnection();
            PostgreSQLJDBC.beginTransaction(con); // Bắt đầu transaction

            ps = con.prepareStatement(SELECT_ALL_STUDENTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                logger.info(toString(rs));
            }

            PostgreSQLJDBC.commitTransaction(con); // Commit transaction
        } catch (SQLException e) {
            logger.error("error: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("error rollback: ", rollbackEx);
            }
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs); // Đóng tài nguyên
        }
    }

    public void displayById(String idSv) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = PostgreSQLJDBC.getInstance().getConnection();
            PostgreSQLJDBC.beginTransaction(con); // Bắt đầu transaction

            ps = con.prepareStatement(SELECT_STUDENT_BY_ID_QUERY);
            ps.setString(1, idSv);
            rs = ps.executeQuery();
            if (rs.next()) {
                logger.info(toString(rs));
            } else {
                logger.warn("Student don't exist.");
            }

            PostgreSQLJDBC.commitTransaction(con); // Commit transaction
        } catch (SQLException e) {
            logger.error("error: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("error rollback: ", rollbackEx);
            }
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, rs); // Đóng tài nguyên
        }
    }

    public void deleteById(String idSv) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = PostgreSQLJDBC.getInstance().getConnection();
            PostgreSQLJDBC.beginTransaction(con); // Bắt đầu transaction

            ps = con.prepareStatement(DELETE_STUDENT_BY_ID);
            ps.setString(1, idSv);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                logger.info("delete successfully.");
            } else {
                logger.warn("Don't find to ID: " + idSv);
            }

            PostgreSQLJDBC.commitTransaction(con); // Commit transaction
        } catch (SQLException e) {
            logger.error("error: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("erroe rollback: ", rollbackEx);
            }
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, null); // Đóng tài nguyên
        }
    }

    public void updateById(String idSv, String name, String address, int age) {


        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = PostgreSQLJDBC.getInstance().getConnection();
            PostgreSQLJDBC.beginTransaction(con); // Bắt đầu transaction

            ps = con.prepareStatement(UPDATE_STUDENT_BY_ID);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, age);
            ps.setString(4, idSv);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Update successfully.");
            } else {
                logger.warn("Student don't exist.");
            }

            PostgreSQLJDBC.commitTransaction(con); // Commit transaction
        } catch (SQLException e) {
            logger.error("error: ", e);
            try {
                if (con != null) {
                    PostgreSQLJDBC.rollbackTransaction(con); // Rollback transaction nếu có lỗi
                }
            } catch (SQLException rollbackEx) {
                logger.error("error rollback: ", rollbackEx);
            }
        } finally {
            PostgreSQLJDBC.closeResources(con, ps, null); // Đóng tài nguyên
        }
    }
}
