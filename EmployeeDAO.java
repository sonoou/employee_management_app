import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

  private Connection conn = DBConnection.getConnection();
  public boolean addEmployee(Employee employee) {
    String sql =
      "INSERT INTO employees (name, email, department, position, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?)";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, employee.getName());
      pstmt.setString(2, employee.getEmail());
      pstmt.setString(3, employee.getDepartment());
      pstmt.setString(4, employee.getPosition());
      pstmt.setDouble(5, employee.getSalary());
      pstmt.setDate(6, Date.valueOf(employee.getHireDate()));

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(" Error adding employee: " + e.getMessage());
      return false;
    }
  }


  public List<Employee> getAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    String sql = "SELECT * FROM employees ORDER BY id";

    try (
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql)
    ) {
      while (rs.next()) {
        employees.add(extractEmployeeFromResultSet(rs));
      }
    } catch (SQLException e) {
      System.out.println(" Error retrieving employees: " + e.getMessage());
    }

    return employees;
  }


  public Employee getEmployeeById(int id) {
    String sql = "SELECT * FROM employees WHERE id = ?";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return extractEmployeeFromResultSet(rs);
      }

      rs.close();
    } catch (SQLException e) {
      System.out.println(" Error retrieving employee: " + e.getMessage());
    }

    return null;
  }


  public boolean updateEmployee(Employee employee) {
    String sql =
      "UPDATE employees SET name = ?, email = ?, department = ?, position = ?, salary = ?, hire_date = ? WHERE id = ?";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, employee.getName());
      pstmt.setString(2, employee.getEmail());
      pstmt.setString(3, employee.getDepartment());
      pstmt.setString(4, employee.getPosition());
      pstmt.setDouble(5, employee.getSalary());
      pstmt.setDate(6, Date.valueOf(employee.getHireDate()));
      pstmt.setInt(7, employee.getId());

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(" Error updating employee: " + e.getMessage());
      return false;
    }
  }


  public boolean deleteEmployee(int id) {
    String sql = "DELETE FROM employees WHERE id = ?";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setInt(1, id);
      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println(" Error deleting employee: " + e.getMessage());
      return false;
    }
  }


  public List<Employee> getEmployeesByDepartment(String department) {
    List<Employee> employees = new ArrayList<>();
    String sql =
      "SELECT * FROM employees WHERE department LIKE ? ORDER BY name";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, "%" + department + "%");
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        employees.add(extractEmployeeFromResultSet(rs));
      }

      rs.close();
    } catch (SQLException e) {
      System.out.println(
        " Error searching employees by department: " + e.getMessage()
      );
    }

    return employees;
  }


  public List<Employee> getEmployeesWithSalaryGreaterThan(double minSalary) {
    List<Employee> employees = new ArrayList<>();
    String sql =
      "SELECT * FROM employees WHERE salary >= ? ORDER BY salary DESC";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setDouble(1, minSalary);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        employees.add(extractEmployeeFromResultSet(rs));
      }

      rs.close();
    } catch (SQLException e) {
      System.out.println(
        " Error retrieving employees by salary: " + e.getMessage()
      );
    }

    return employees;
  }


  public boolean emailExists(String email) {
    String sql = "SELECT COUNT(*) FROM employees WHERE email = ?";

    try (
      PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, email);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return rs.getInt(1) > 0;
      }

      rs.close();
    } catch (SQLException e) {
      System.out.println(" Error checking email: " + e.getMessage());
    }

    return false;
  }


  public int getTotalEmployees() {
    String sql = "SELECT COUNT(*) FROM employees";

    try (
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql)
    ) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      System.out.println(" Error getting total employees: " + e.getMessage());
    }

    return 0;
  }


  private Employee extractEmployeeFromResultSet(ResultSet rs)
    throws SQLException {
    Employee employee = new Employee();
    employee.setId(rs.getInt("id"));
    employee.setName(rs.getString("name"));
    employee.setEmail(rs.getString("email"));
    employee.setDepartment(rs.getString("department"));
    employee.setPosition(rs.getString("position"));
    employee.setSalary(rs.getDouble("salary"));
    employee.setHireDate(rs.getDate("hire_date").toLocalDate());
    employee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    return employee;
  }
}
