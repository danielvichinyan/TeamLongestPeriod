package readfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The ReadStringFromFile class provides a method 
 * to read strings from a file, split them and add them to a list.
 * 
 * @author Daniel Vichinyan
 */
public class ReadInputFromFile {

	/**
	 * Default constructor.
	 */
	public ReadInputFromFile() {
	}

	/**
	 * Reads input from a file. Splits the different words with ", ". Adds the
	 * splitted words into a list. Returns the list of splitted words.
	 * 
	 * @param file
	 * @return List<String>
	 * @throws IOException
	 */
	public static List<String> readEmployeesFromFile(String filePath) throws IOException {

		ArrayList<String> tokens = new ArrayList<String>();
		InputStream inputStream = ReadInputFromFile.class.getResourceAsStream(filePath);
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			String[] str = scanner.nextLine().split(", ");
			for (int i = 0; i < str.length; i++) {
				tokens.add(str[i]);
			}
		}
		scanner.close();

		return tokens;
	}
}
