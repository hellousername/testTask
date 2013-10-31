package taskTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/*
 * For each of the "M" lines displayed the most frequently used words that start with "Ui",
 * in descending order of frequency. In the case of identical frequency words are sorted alphabetically.
 * When there is more than ten options are displayed first ten of them.
 */
public class TaskTest {

	private static String inputFile = "input.txt";
	private static String outputFile = "output.txt";
	/**
	 * @param args
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException{
		// TODO Auto-generated method stub
		int i = 0;
		ArrayList<String[]> fullWord = fullWord(inputFile);
		ArrayList<String> beginningWord = beginningWord(searchLocationM(inputFile), inputFile);
		while(i<beginningWord.size()){
			ArrayList<String[]> resultSearch = searchWord(fullWord, beginningWord, i);
			ArrayList<String[]> resultSort = sortResult(resultSearch);
			resultWrite(outputFile, resultSort);
			i++;
		}
		
	}
	
	/*
	 * Method finds the location of M in a document
	 */
	
	public static long searchLocationM(String file) throws NumberFormatException, IOException{
		RandomAccessFile readLocation = new RandomAccessFile(file, "rw");
		int i = 0;
		int N = Integer.parseInt(readLocation.readLine());
		readLocation.seek(0);
		long[] countAll = new long[N+1];
		while(i<N+1){
			readLocation.readLine();
			countAll[i] = readLocation.getFilePointer();
			i++;
		}
		long locationM = countAll[N];
		readLocation.close();
		return locationM;
	}
	
	/*
	 * Method searches for whole words
	 */
	
	public static ArrayList<String[]> fullWord(String file) throws NumberFormatException, IOException{
		BufferedReader readFullWord = new BufferedReader(new FileReader(file));
		ArrayList<String[]> fullWord = new ArrayList<String[]>();
		int i = 0;
		int N = Integer.parseInt(readFullWord.readLine());
		while(i<N){
			String[] q = readFullWord.readLine().split(" ", 2);
			fullWord.add(q);
			i++;
		}
		readFullWord.close();
		return fullWord;
	}
	
	/*
	 * Method finds the beginning of the word entered by the user
	 */
	
	public static ArrayList<String> beginningWord(long firstCount, String file) throws IOException{
		BufferedReader readBeginningWord = new BufferedReader(new FileReader(file));
		readBeginningWord.skip(firstCount);
		int i =0;
		int N = Integer.parseInt(readBeginningWord.readLine());
		ArrayList<String> beginningWord = new ArrayList<String>();
		while(i<N){
			beginningWord.add(readBeginningWord.readLine());
			i++;
		}
		readBeginningWord.close();
		return beginningWord;
	}
	
	/*
	 * Method checks the occurrence of early words entered by the user in the full word
	 */
	
	public static ArrayList<String[]> searchWord(ArrayList<String[]> fullWord, ArrayList<String> beginningWord, int numberBeginningWord) throws IOException{
		int i = 0;
		ArrayList<String[]> result = new ArrayList<String[]>();
			while(i<fullWord.size()){
				String[] q = fullWord.get(i);
				q[0] = q[0].toLowerCase();
					if(q[0].startsWith(beginningWord.get(numberBeginningWord).toLowerCase())){
						result.add(q);
					}
				i++;
			}
			return result;

	}
	
	/*
	 * Sort method result searchWord
	 */
	
	public static ArrayList<String[]> sortResult(ArrayList<String[]> result){
		int i = 1;
		int j =1;
		String[] q;
		String[] w;
		while(i<result.size()){
		 for(j=result.size()-1;1<j; j--){
				w = result.get(j-1);
				q = result.get(j);
				if(Integer.parseInt(q[1])>Integer.parseInt(w[1])){
					result.set(j, w);
					result.set(j-1, q);						
				}
			}
		 i++;
		}
		i = 1;
		j = 1;
		while(i<result.size()){
			 for(j=result.size()-1;1<=j; j--){
					w = result.get(j-1);
					q = result.get(j);
						if(Integer.parseInt(q[1])==Integer.parseInt(w[1])){
							if(q[0].compareTo(w[0])<0){
								result.set(j, w);
								result.set(j-1, q);
						}
					}
				}
			 i++;
			}
		return result;
	}
	
	 /*
	  * Append the result to a file
	  */
	
	public static void resultWrite(String file, ArrayList<String[]> fullWord) throws IOException{
		int i=0;
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		while((i<fullWord.size())&&(i<10)){
			String[] q = fullWord.get(i);
			out.write(q[0] + "\n");
			i++;
		}
		out.write("\n");
		out.close();
	} 
	
}