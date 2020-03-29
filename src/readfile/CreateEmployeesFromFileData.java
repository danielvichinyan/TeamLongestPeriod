package readfile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import employees.Employee;
/**
 * The CreateEmployeesFromFileData class provides a method 
 * which creates Employee based on the information extracted from the text file.
 * 
 * @author Daniel Vichinyan
 */
public class CreateEmployeesFromFileData {

	/**
	 * Default Constructor.
	 */
	public CreateEmployeesFromFileData() {
	}
	
	/**
	 * Takes the list of words which is extracted from the readEmployeesFromFile method in ReadStringFromFile Class.
	 * Creates a new Employee with the current employeeId, projectId, dateFrom, dateTo from
	 * the list. Adds the new Employee to the list of Employees. If dateTo is equal
	 * to "NULL", uses today's date. Returns the list of employees.
	 * 
	 * @param listofEmployeeDetails
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static List<Employee> assignEmployees(List<String> listofEmployeeDetails, String filePath) throws IOException {
		List<Employee> employees = new ArrayList<>();
		// extract the splitted strings from that file and add them to a list of strings
		listofEmployeeDetails = ReadInputFromFile.readEmployeesFromFile(filePath);

		// go through that list of strings
		for (int i = 0; i < listofEmployeeDetails.size(); i += 4) {
			// if dateTo is "NULL", use today's date
			if (listofEmployeeDetails.get(i + 3).equals("NULL")) {
				// take today's date
				LocalDate todaysDate = LocalDate.now();

				// create a new employee with the current employeeId, projectId, dateFrom and dateTo variables
				employees.add(new Employee(Integer.parseInt(listofEmployeeDetails.get(i)),
						Integer.parseInt(listofEmployeeDetails.get(i + 1)),
						LocalDate.parse(listofEmployeeDetails.get(i + 2)), 
						todaysDate));
			} else { // if date is not "NULL" but has a value, use that value
				employees.add(new Employee(Integer.parseInt(listofEmployeeDetails.get(i)),
						Integer.parseInt(listofEmployeeDetails.get(i + 1)),
						LocalDate.parse(listofEmployeeDetails.get(i + 2)),
						LocalDate.parse(listofEmployeeDetails.get(i + 3))));
			}

		}

		return employees;
	}
}
