import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DocReader {

	public static void main(String[] args) {
	}

	public static String readFile(String fileName) {
		String totalText = "";
		//String[] bigArr;
		try {
			BufferedReader lineReader = new BufferedReader(new FileReader(fileName));
			String lineText = null;
			while ((lineText = lineReader.readLine()) != null) {
				if (lineText.length() == 0) {
				}
				else {
					totalText += lineText + " ";
				}
			}
			//bigArr = totalText.split(" ");
			lineReader.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
		//bigArr = totalText.split(" ");
		return totalText;
		//return bigArr;
	}
}
