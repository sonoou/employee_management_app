import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeApp {

  private static EmployeeDAO employeeDAO;
  private static Scanner scanner;

  public static void main(String[] args) {
    employeeDAO = new EmployeeDAO();
    scanner = new Scanner(System.in);

    System.out.println("====================================");
    System.out.println("     EMPLOYEE MANAGEMENT SYSTEM");
    System.out.println("        DATABASE APPLICATION");
    System.out.println("====================================");

    boolean running = true;
    while (running) {
      displayMenu();
      int choice = getMenuChoice();
      running = processChoice(choice);
    }

    scanner.close();
    DBConnection.closeConnection();
    System.out.println("Thank you for using Employee Management System!");
  }

  private static void displayMenu() {
    System.out.println("\n=== MAIN MENU ===");
    System.out.println("1. Add New Employee");
    System.out.println("2. View All Employees");
    System.out.println("3. View Employee by ID");
    System.out.println("4. Update Employee");
    System.out.println("5. Delete Employee");
    System.out.println("6. Search by Department");
    System.out.println("7. View High Salary Employees");
    System.out.println("8. View Statistics");
    System.out.println("9. Exit");
    System.out.print("Choose an option (1-9): ");
  }

  private static int getMenuChoice() {
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private static boolean processChoice(int choice) {
    switch (choice) {
      case 1:
        addEmployee();
        break;
      case 2:
        viewAllEmployees();
        break;
      case 3:
        viewEmployeeById();
        break;
      case 4:
        updateEmployee();
        break;
      case 5:
        deleteEmployee();
        break;
      case 6:
        searchByDepartment();
        break;
      case 7:
        viewHighSalaryEmployees();
        break;
      case 8:
        viewStatistics();
        break;
      case 9:
        return false;
      default:
        System.out.println(" Invalid option! Please choose 1-9.");
    }
    return true;
  }

  private static void addEmployee() {
    System.out.println("\n=== ADD NEW EMPLOYEE ===");

    System.out.print("Enter Name: ");
    String name = scanner.nextLine();

    System.out.print("Enter Email: ");
    String email = scanner.nextLine();

    if (employeeDAO.emailExists(email)) {
      System.out.println(" Error: Employee with this email already exists!");
      return;
    }

    System.out.print("Enter Department: ");
    String department = scanner.nextLine();

    System.out.print("Enter Position: ");
    String position = scanner.nextLine();

    System.out.print("Enter Salary: ");
    double salary = Double.parseDouble(scanner.nextLine());

    System.out.print("Enter Hire Date (YYYY-MM-DD): ");
    LocalDate hireDate = LocalDate.parse(scanner.nextLine());

    Employee employee = new Employee(
      name,
      email,
      department,
      position,
      salary,
      hireDate
    );

    if (employeeDAO.addEmployee(employee)) {
      System.out.println(" Employee added successfully!");
    } else {
      System.out.println(" Failed to add employee!");
    }
  }

  private static void viewAllEmployees() {
    System.out.println("\n=== ALL EMPLOYEES ===");

    List<Employee> employees = employeeDAO.getAllEmployees();

    if (employees.isEmpty()) {
      System.out.println("No employees found in database.");
      return;
    }

    System.out.println("Total Employees: " + employees.size());
    System.out.println(
      "=================================================================================="
    );
    for (Employee employee : employees) {
      System.out.println(employee);
    }
  }

  private static void viewEmployeeById() {
    System.out.println("\n=== VIEW EMPLOYEE BY ID ===");
    System.out.print("Enter Employee ID: ");
    int id = Integer.parseInt(scanner.nextLine());

    Employee employee = employeeDAO.getEmployeeById(id);

    if (employee != null) {
      System.out.println(employee.getDetailedInfo());
    } else {
      System.out.println(" Employee with ID " + id + " not found!");
    }
  }

  private static void updateEmployee() {
    System.out.println("\n=== UPDATE EMPLOYEE ===");
    System.out.print("Enter Employee ID to update: ");
    int id = Integer.parseInt(scanner.nextLine());

    Employee employee = employeeDAO.getEmployeeById(id);
    if (employee == null) {
      System.out.println(" Employee with ID " + id + " not found!");
      return;
    }

    System.out.println("Current Details:");
    System.out.println(employee.getDetailedInfo());

    System.out.print("Enter new Name (current: " + employee.getName() + "): ");
    String name = scanner.nextLine();
    if (!name.isEmpty()) employee.setName(name);

    System.out.print(
      "Enter new Email (current: " + employee.getEmail() + "): "
    );
    String email = scanner.nextLine();
    if (!email.isEmpty() && !email.equals(employee.getEmail())) {
      if (employeeDAO.emailExists(email)) {
        System.out.println(
          " Error: Another employee with this email already exists!"
        );
        return;
      }
      employee.setEmail(email);
    }

    System.out.print(
      "Enter new Department (current: " + employee.getDepartment() + "): "
    );
    String department = scanner.nextLine();
    if (!department.isEmpty()) employee.setDepartment(department);

    System.out.print(
      "Enter new Position (current: " + employee.getPosition() + "): "
    );
    String position = scanner.nextLine();
    if (!position.isEmpty()) employee.setPosition(position);

    System.out.print(
      "Enter new Salary (current: " + employee.getSalary() + "): "
    );
    String salaryInput = scanner.nextLine();
    if (!salaryInput.isEmpty()) employee.setSalary(
      Double.parseDouble(salaryInput)
    );

    System.out.print(
      "Enter new Hire Date (current: " + employee.getHireDate() + "): "
    );
    String hireDateInput = scanner.nextLine();
    if (!hireDateInput.isEmpty()) employee.setHireDate(
      LocalDate.parse(hireDateInput)
    );

    if (employeeDAO.updateEmployee(employee)) {
      System.out.println(" Employee updated successfully!");
    } else {
      System.out.println(" Failed to update employee!");
    }
  }

  private static void deleteEmployee() {
    System.out.println("\n=== DELETE EMPLOYEE ===");
    System.out.print("Enter Employee ID to delete: ");
    int id = Integer.parseInt(scanner.nextLine());

    Employee employee = employeeDAO.getEmployeeById(id);
    if (employee == null) {
      System.out.println(" Employee with ID " + id + " not found!");
      return;
    }

    System.out.println("Employee to delete:");
    System.out.println(employee.getDetailedInfo());
    System.out.print("Are you sure you want to delete this employee? (y/n): ");
    String confirmation = scanner.nextLine();

    if (confirmation.equalsIgnoreCase("y")) {
      if (employeeDAO.deleteEmployee(id)) {
        System.out.println(" Employee deleted successfully!");
      } else {
        System.out.println(" Failed to delete employee!");
      }
    } else {
      System.out.println("Deletion cancelled.");
    }
  }

  private static void searchByDepartment() {
    System.out.println("\n=== SEARCH BY DEPARTMENT ===");
    System.out.print("Enter department name: ");
    String department = scanner.nextLine();

    List<Employee> employees = employeeDAO.getEmployeesByDepartment(department);

    if (employees.isEmpty()) {
      System.out.println(
        "No employees found in department: '" + department + "'"
      );
      return;
    }

    System.out.println(
      "Found " +
      employees.size() +
      " employee(s) in " +
      department +
      " department:"
    );
    System.out.println(
      "=================================================================================="
    );
    for (Employee employee : employees) {
      System.out.println(employee);
    }
  }

  private static void viewHighSalaryEmployees() {
    System.out.println("\n=== HIGH SALARY EMPLOYEES ===");
    System.out.print("Enter minimum salary: ");
    double minSalary = Double.parseDouble(scanner.nextLine());

    List<Employee> employees = employeeDAO.getEmployeesWithSalaryGreaterThan(
      minSalary
    );

    if (employees.isEmpty()) {
      System.out.println(
        "No employees found with salary greater than $" + minSalary
      );
      return;
    }

    System.out.println(
      "Found " + employees.size() + " employee(s) with salary >= $" + minSalary
    );
    System.out.println(
      "=================================================================================="
    );
    for (Employee employee : employees) {
      System.out.println(employee);
    }
  }

  private static void viewStatistics() {
    System.out.println("\n=== EMPLOYEE STATISTICS ===");
    int totalEmployees = employeeDAO.getTotalEmployees();
    System.out.println("Total Employees: " + totalEmployees);

    List<Employee> allEmployees = employeeDAO.getAllEmployees();
    if (!allEmployees.isEmpty()) {
      double totalSalary = allEmployees
        .stream()
        .mapToDouble(Employee::getSalary)
        .sum();
      double avgSalary = totalSalary / totalEmployees;
      double maxSalary = allEmployees
        .stream()
        .mapToDouble(Employee::getSalary)
        .max()
        .orElse(0);

      System.out.printf("Total Salary Expense: $%,.2f\n", totalSalary);
      System.out.printf("Average Salary: $%,.2f\n", avgSalary);
      System.out.printf("Highest Salary: $%,.2f\n", maxSalary);
    }
  }
}
