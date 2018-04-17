import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class LangGen {
	public static HashMap<String, Integer> gramHash = new HashMap<String, Integer>();
/**
 * This method generates n grams from a string, and adds them to a hash map,
 * incrementing the count if the ngram already exists
 * @param s - the string which you are processing
 * @param len - the number of n grams
 */
	public static void makeNgrams(String s, int len) {
		String[] parts = s.split(" ");
		if (parts.length < len) {
			System.out
					.println("ERROR: ngrams received invalid String or length: "
							+ len + " : " + s + " :");
			
		}
		String[] result = new String[parts.length - len + 1];
		for (int i = 0; i < parts.length - len + 1; i++) {
			StringBuilder sb = new StringBuilder();
			for (int k = 0; k < len; k++) {
				if (k > 0) {
					sb.append(' ');
				}
				sb.append(parts[i + k]);
			}
			result[i] = sb.toString();
			String key = result[i];

			if (gramHash.get(key) == null) {
				gramHash.put(key, 1);
			} else {
				int oldVal = gramHash.get(key);
				gramHash.put(key, oldVal + 1);
			}
		}
	}
/**
 * This is used to generaate a random int between the min and max
 * @param min
 * @param max
 * @return
 */
	public static int randInt(int min, int max) {
		Random r = new Random();
		int rand = r.nextInt(max - min) + min;
		return rand;
	}
/**
 * This serves as the weighted dice roll to see which ngram is chosen. 
 * Serves as a helper to the chooseNext method
 * @param gramList
 * @return returns the chosen ngram 
 */
	public static String chance(ArrayList<nGram> gramList) {
		ArrayList<String> maybeGrams = new ArrayList<String>();
		for (int j = 0; j < gramList.size(); j++) {
			for (int i = 0; i < gramList.get(j).prob; i++) {
				maybeGrams.add(gramList.get(j).s);
			}
		}
		int rand = randInt(0, maybeGrams.size());
		return maybeGrams.get(rand);
	}
	/**
	 * This generates a list of ngrams that begin with a specified string,
	 * and then selects one using the chance method
	 * @param s this is the string which serves as the prefix to the ngram you are searching for
	 * @return the string to be probabilistically written
	 */
	public static String chooseNext(String s) {
		ArrayList<nGram> gramList = new ArrayList<nGram>();
		for (Entry<String, Integer> e : gramHash.entrySet()) {
			if (e.getKey().startsWith(s)) {
				String key = e.getKey();
				int val = e.getValue();
				nGram g = new nGram(key, val);
				gramList.add(g);
			} else {
			}
		}
		return chance(gramList);
	}
/**
 * This main method prompts the user for the required input 
 * (filepath, gram size, output length, and seed word)
 * and then uses the above methods to generate text.Cr
 * @param args
 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);  
		System.out.print("Enter file path: "); 
		String file = input.nextLine(); 
		System.out.print("Enter gram size: "); 
		Integer gramSize = Integer.parseInt(input.nextLine()); 
		System.out.print("Enter minimum output size: "); 
		Integer outputLength = Integer.parseInt(input.nextLine()); 
		System.out.print("Enter starting word: "); 
		String prevString = input.nextLine(); 
		makeNgrams(DocReader.readFile(file), gramSize);
		for (int j = 0; j < outputLength; j++) {
			String resString = chooseNext(prevString);
			String[] splitRes = resString.split(" ");
			if (j == 0) {
				String bStr = "";
				String[] tStr = resString.split(" ");
				for (int k = 0; k < tStr.length - 1; k++) {
					bStr += tStr[k] + " ";
				}
				System.out.print(bStr);
			}

			System.out.print(splitRes[gramSize - 1] + " ");
			String[] tempArr = resString.split(" ");
			String tempStr = "";
			for (int i = 1; i < gramSize; i++) {
				tempStr += (tempArr[i] + " ");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 1);
			prevString = tempStr;
		}
		while (true) {
			String resString = chooseNext(prevString);
			String[] splitRes = resString.split(" ");
			System.out.print(splitRes[gramSize - 1] + " ");
			String[] tempArr = resString.split(" ");
			String tempStr = "";
			for (int i = 1; i < gramSize; i++) {
				tempStr += (tempArr[i] + " ");
			}
			tempStr = tempStr.substring(0, tempStr.length() - 1);
			prevString = tempStr;
			if (splitRes[gramSize - 1].contains("."))
				break;
		}

	}

}
