package app;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import employees.Employee;
import readfile.CreateEmployeesFromFileData;
import readfile.ReadInputFromFile;
/**
 * Class LongestPeriod combines the functionality of all the classes.
 * Provides a main method that demonstrates how the program works.
 * Output is shown *ON THE CONSOLE*.
 * There is *NO NEED* for code changes.
 * 
 * @author Daniel Vichinyan
 */
public class LongestPeriod {

	public static void main(String[] args) throws IOException {

		/** A list of Strings which contains all Strings from the input file, separated by ", ". */
		List<String> stringOfEmployeeDetails = ReadInputFromFile.readEmployeesFromFile("/resource/employees.txt");

		/** A list of Employees that is made up from the input Strings from the file. */
		List<Employee> employees = CreateEmployeesFromFileData.assignEmployees(stringOfEmployeeDetails,
				"/resource/employees.txt");

		/**
		 * Groups the employees by projectId. Integer is the projectId. 
		 * The projectId is used as a Key in the Map.
		 * The projectId is connected to a particular team of employees. 
		 * That way we know which employees work on which project.
		 */
		Map<Integer, List<Employee>> employeesByProjectId = Employee.groupEmployeesByProjectId(employees);

		/** Displays which employees work on which project. */
		System.out.println(Employee.displayEmployeesWorkingOnProject(employeesByProjectId));

		System.out.println("----------------------------------------------------------------");

		/** Displays which PAIR of employees has work THE MOST TIME on a project. */
		System.out.println(Employee.longestWorkingEmployeePair(employeesByProjectId));
	}
}
