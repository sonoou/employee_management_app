import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {

  private int id;
  private String name;
  private String email;
  private String department;
  private String position;
  private double salary;
  private LocalDate hireDate;
  private LocalDateTime createdAt;

  public Employee() {}

  public Employee(
    String name,
    String email,
    String department,
    String position,
    double salary,
    LocalDate hireDate
  ) {
    this.name = name;
    this.email = email;
    this.department = department;
    this.position = position;
    this.salary = salary;
    this.hireDate = hireDate;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public LocalDate getHireDate() {
    return hireDate;
  }

  public void setHireDate(LocalDate hireDate) {
    this.hireDate = hireDate;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return String.format(
      "ID: %-3d | Name: %-15s | Dept: %-12s | Position: %-15s | Salary: $%,.2f",
      id,
      name,
      department,
      position,
      salary
    );
  }

  public String getDetailedInfo() {
    return String.format(
      "Employee Details:\n" +
      "ID: %d\n" +
      "Name: %s\n" +
      "Email: %s\n" +
      "Department: %s\n" +
      "Position: %s\n" +
      "Salary: $%,.2f\n" +
      "Hire Date: %s",
      id,
      name,
      email,
      department,
      position,
      salary,
      hireDate
    );
  }
}
