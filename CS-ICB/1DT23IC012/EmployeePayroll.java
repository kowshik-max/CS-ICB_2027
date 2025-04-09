import java.io.*;
import java.util.*;

public class EmployeePayroll {
    static final String FILE_NAME = "employees.ser";
    static ArrayList<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Payroll Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search by Employee ID");
            System.out.println("4. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Basic Salary: ");
                    double basic = sc.nextDouble();
                    System.out.print("Enter HRA: ");
                    double hra = sc.nextDouble();
                    System.out.print("Enter DA: ");
                    double da = sc.nextDouble();

                    Employee emp = new Employee(id, name, basic, hra, da);
                    employeeList.add(emp);
                    System.out.println("Employee added successfully.");
                    break;

                case 2:
                    if (employeeList.isEmpty()) {
                        System.out.println("No employee records found.");
                    } else {
                        for (Employee e : employeeList) {
                            System.out.println("\n---------------------------");
                            System.out.println(e);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = sc.nextInt();
                    Employee found = null;
                    for (Employee e : employeeList) {
                        if (e.getEmpId() == searchId) {
                            found = e;
                            break;
                        }
                    }
                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 4:
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        sc.close();
    }

    static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employeeList);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                employeeList = (ArrayList<Employee>) ois.readObject();
                System.out.println("Loaded " + employeeList.size() + " employee(s) from file.");
            } catch (Exception e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        } else {
            System.out.println("No saved data found. Starting fresh.");
        }
    }
}

class Employee implements Serializable {
    private int empId;
    private String name;
    private double basicSalary;
    private double hra;
    private double da;

    public Employee(int empId, String name, double basicSalary, double hra, double da) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
    }

    public int getEmpId() {
        return empId;
    }

    public double getTotalSalary() {
        return basicSalary + hra + da;
    }

    public String toString() {
        return "Employee ID: " + empId +
        "\nName: " + name +
        "\nBasic Salary: " + basicSalary +
        "\nHRA: " + hra +
        "\nDA: " + da +
        "\nTotal Salary: " + getTotalSalary();
    }
}
