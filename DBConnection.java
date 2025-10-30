import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private static final String URL = "jdbc:mysql://localhost:3306/company_db";
  private static final String USERNAME = "sonu";
  private static final String PASSWORD = "15061999"; 

  private static Connection connection = null;

  public static Connection getConnection() {
    if (connection == null) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      } catch (ClassNotFoundException e) {
        System.out.println("MySQL JDBC Driver not found!");
        e.printStackTrace();
      } catch (SQLException e) {
        System.out.println("Database connection failed!");
        e.printStackTrace();
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Database connection closed.");
        connection = null;
      } catch (SQLException e) {
        System.out.println("Error closing database connection!");
        e.printStackTrace();
      }
    }
  }
}
