package employees;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Employee class defines the properties and behavior of an employee.
 * 
 * @author Daniel Vichinyan
 */
public class Employee {

	/** The ID of the particular employee. */
	private int empId = 0;

	/** The ID of the particular project that the employee is working on. */
	private int projectId = 0;

	/** The date on which the employee finished working on the project. */
	private LocalDate dateFrom = null;

	/** The date from which the employee started working on the project. */
	private LocalDate dateTo = null;

	/**
	 * Constructor. Sets the field values.
	 * 
	 * @param empId
	 * @param projectId
	 * @param dateFrom
	 * @param dateTo
	 */
	public Employee(int empId, int projectId, LocalDate dateFrom, LocalDate dateTo) {
		this.empId = empId;
		this.projectId = projectId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	public int getEmpId() {
		return empId;
	}

	public int getProjectId() {
		return projectId;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	/**
	 * Uses the projectId as a Key in the Map.
	 * Groups a List of Employees by their project ID. Employees that work on
	 * project 10 will be put in the same group. Employees that work on project 11
	 * will be put in different group.
	 * 
	 * @param employees
	 * @return Map which takes the projectId as a key and uses it for grouping
	 *         different lists of employees.
	 */
	public static Map<Integer, List<Employee>> groupEmployeesByProjectId(List<Employee> listOfEmployees) {

		return listOfEmployees.stream()
				.collect(Collectors.groupingBy(Employee::getProjectId));
	}

	/**
	 * Displays the employees based on their project id. Appends the results to a
	 * string builder.
	 * 
	 * @param emplooyeesOnProject
	 * @return returns the string builder as a String.
	 */
	public static String displayEmployeesWorkingOnProject(Map<Integer, List<Employee>> emplooyeesOnProject) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Integer projectId : emplooyeesOnProject.keySet()) {
			stringBuilder.append("Employees working on Project [" + projectId + "]:\n");
			for (Employee employee : emplooyeesOnProject.get(projectId)) {
				stringBuilder.append("Employee ID: " + employee.getEmpId() + ", Start Date: " + employee.getDateFrom()
						+ ", Finish Date: " + employee.getDateTo() + "\n");
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * Displays the MAXIMUM time a pair of employees have spend together working on
	 * the same project.
	 * 
	 * @param emplooyeesOnProject
	 * @return returns the stringBuilder as a String
	 */
	public static String longestWorkingEmployeePair(Map<Integer, List<Employee>> emplooyeesOnProject) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<Integer, List<Employee>> me : emplooyeesOnProject.entrySet()) {
			Integer key = me.getKey();
			List<Employee> valueList = me.getValue();
			stringBuilder.append("\nProject ID: [" + key + "]\n" + largestSpan(valueList));
		}

		return stringBuilder.toString();
	}

	/**
	 * Finds the largest number of days that a PAIR of Employees have worked
	 * together. Counts only employees that work on the same project.
	 * 
	 * @param listOfEmployees
	 * @return maximum number of days that employees have worked together
	 */
	public static String largestSpan(List<Employee> listOfEmployees) {
		StringBuilder stringBuilder = new StringBuilder();
		// value of maximum days spent
		int maxDiffDays = 0;
		// days difference between two dates
		Duration duration = null;
		// current employees in the for-loop
		int firstEmployeeId = 0;
		int secondEmployeeId = 0;
		// which two employees have spent MAX time together
		int mostTimeSpendFirstEmployee = 0;
		int mostTimeSpendSecondEmployee = 0;
		for (int i = 0, j = 1; i < listOfEmployees.size() && j < listOfEmployees.size(); i = j++) {
			// current difference between dates
			int diffDays = 0;
			// DateFrom, DateTo and empId of first employee
			LocalDate firstDateFrom = listOfEmployees.get(i).getDateFrom();
			LocalDate firstDateTo = listOfEmployees.get(i).getDateTo();
			firstEmployeeId = listOfEmployees.get(i).getEmpId();

			// DateFrom, DateTo and empId of second employee
			LocalDate secondDateFrom = listOfEmployees.get(i + 1).getDateFrom();
			LocalDate secondDateTo = listOfEmployees.get(i + 1).getDateTo();
			secondEmployeeId = listOfEmployees.get(i + 1).getEmpId();

			// first empId is the same as second empId
			// that means it is the same person, so the difference will be 0
			if (firstEmployeeId == secondEmployeeId) {
				diffDays = 0;
				continue;
			}

			// first [finished working] -(before)- second [started working]
			// second [finished working] -(before)- first [started working]
			if (firstDateTo.isBefore(secondDateFrom) || secondDateTo.isBefore(firstDateFrom)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}

			// first [finished working] -(at the same time)- second [started working]
			// second [finished working] -(at the same time)- first [started working]
			if (firstDateTo.isEqual(secondDateFrom) || secondDateTo.isEqual(firstDateFrom)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}

			// first [started working] -(before)- second [started working]
			// first [finished working] -(before)- second [finished working]
			if (firstDateFrom.isBefore(secondDateFrom) && firstDateTo.isBefore(secondDateTo)) {
				duration = Duration.between(secondDateFrom.atStartOfDay(), firstDateTo.atStartOfDay());
			}

			// first [started working] -(after)- second [started working]
			// first [finished working] -(after)- second [finished working]
			if (firstDateFrom.isAfter(secondDateFrom) && firstDateTo.isAfter(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}

			// first [started working] -(before)- second [started working]
			// first [finished working] -(after)- second [finished working]
			if (firstDateFrom.isBefore(secondDateFrom) && firstDateTo.isAfter(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}

			// first [started working] -(at the same time)- second [started working]
			// first [finished working] -(at the same time)- second [finished working]
			if (firstDateFrom.isEqual(secondDateFrom) && firstDateTo.isEqual(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), firstDateTo.atStartOfDay());
			}

			// first [started working] -(at the same time)- second [started working]
			// first [finished working] -(before)- second [finished working]
			if (firstDateFrom.equals(secondDateFrom) && firstDateTo.isBefore(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), firstDateTo.atStartOfDay());
			}

			// first [started working] -(before)- second [started working]
			// first [finished working] -(at the same time)- second [finished working]
			if (firstDateFrom.isBefore(secondDateFrom) && firstDateTo.isEqual(secondDateTo)) {
				duration = Duration.between(secondDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}

			// first [started working] -(after)- second [started working]
			// first [finished working] -(before)- second [finished working]
			if (firstDateFrom.isAfter(secondDateFrom) && firstDateTo.isBefore(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), firstDateTo.atStartOfDay());
			}

			// first [started working] -(after)- second [started working]
			// first [finished working] -(at the same time)- second [finished working]
			if (firstDateFrom.isAfter(secondDateFrom) && firstDateTo.isEqual(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), firstDateTo.atStartOfDay());
			}

			// first [started working] -(at the same time)- second [started working]
			// first [finished working] -(after)- second [finished working]
			if (firstDateFrom.isEqual(secondDateFrom) && firstDateTo.isAfter(secondDateTo)) {
				duration = Duration.between(firstDateFrom.atStartOfDay(), secondDateTo.atStartOfDay());
			}
			
			// convert the difference between the two dates to days
			diffDays = (int) duration.toDays();

			// it will only get into this if-clause *** IF THERE IS A GREATER VALUE THAN THE CURRENT MAX ***
			// IT WILL ALWAYS TAKE THE MAX VALUE
			// it takes both employees that participate in that max value
			if (diffDays > maxDiffDays) {
				maxDiffDays = diffDays; // sets the difference as new maximum difference if it is larger than the previous value
				
				// take the emplId of the first employee that makes the maximum time spend
				mostTimeSpendFirstEmployee = listOfEmployees.get(i).getEmpId();
				
				// take the emplId of the second employee that makes the maximum time spend
				mostTimeSpendSecondEmployee = listOfEmployees.get(i + 1).getEmpId();
			}

		}
		return stringBuilder.append("The employees that have worked together for the longest period on this project are:\n"
				+ "Employees [" + mostTimeSpendFirstEmployee + "] and [" + mostTimeSpendSecondEmployee
				+ "] have spent [" + maxDiffDays + "] days working on this project together!\n" + "That is exactly [" + maxDiffDays / 365
				+ " years, " + (maxDiffDays % 365) / 7 + " weeks, " + "and " + (maxDiffDays % 365) % 7 + " days]!\n")
				.toString(); // returns the longest period of days between two employees
	}
}
