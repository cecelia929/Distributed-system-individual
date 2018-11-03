
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * yajing Huang
 * 896243
 * @author huangyajing
 *
 */


public class DictionaryFunction {
	
	static File file = new File(Server.path);
	public static List<String> dic = readTxt(Server.path);
	// public static String[][] dicWordList = new String[dic.size()][2];
	
	public static HashMap<String, String> hashMap = new HashMap<>();
	
	String meaning;

	public DictionaryFunction() {
		for (int i = 0; i < dic.size(); i++) {
			String[] mix = dic.get(i).split("-");
			hashMap.put(mix[0], mix[1]);
			//dicWordList[i][0] = mix[0];
			//dicWordList[i][1] = mix[1];
		}
	}

	public synchronized String Search(String word) {
		
		if (hashMap.containsKey(word)) {
			meaning = hashMap.get(word);
		} else {
			meaning = "The word you want search is NOT FOUND! You can try to add it!";
		}
		return meaning;
	}

	public synchronized String Delete(String word) {
		if (hashMap.containsKey(word)) {
			hashMap.remove(word);
			meaning = "Delete successful!";
			try {
				file.delete();
				if (!file.exists()) {
					//@SuppressWarnings("unused")
					File file = new File(Server.path);
					file.createNewFile(); 
				}
				PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

				for (HashMap.Entry<String,String> entry : hashMap.entrySet()) {
					out.println(entry.getKey() + "-"+ entry.getValue());
				}
				out.close();
				
			} catch (IOException e) {

				System.exit(0);
			}
		} else {
			meaning = "NOT FOUND! FAIL TO DELETE";
		}
		return meaning;
	}

	public synchronized String Add(String word) {
		
		String[] info = word.split("-");
		String wordOnly = null;
		if (info.length >= 1) {
			wordOnly = info[0];
		} else
			try {
				throw new Exception();
			} catch (Exception e) {
				meaning = "You must add word with meaning in such format: word-explanation1;explanation2;explanation3.......";
			}
		if (!hashMap.containsKey(word)) {
			meaning = "Add successful!";
			try {
				hashMap.put(wordOnly, info[1]);
			} catch (Exception e) {
				meaning = "You must add word with meaning in such format: word-explanation1;explanation2;explanation3.......";
			}
			
			try {
				file.delete();
				if (!file.exists()) {
					//@SuppressWarnings("unused")
					File file = new File(Server.path);
					file.createNewFile(); 
				}
				
				// use buffer writer to write the array to file
				PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

				for (HashMap.Entry<String,String> entry : hashMap.entrySet()) {
					out.println(entry.getKey() + "-"+ entry.getValue());
				}
				out.close();

			} catch (IOException e) {
				System.exit(0);
			}
		} else {
			meaning = "This Word is Already Exist! The meaning is " + hashMap.get(word);
		}
		return meaning;
	}



	public static List<String> readTxt(String url) {
		List<String> list = new ArrayList<String>();
		try {
			file = new File(url);
	        if(!file.exists()){
	        	 file.createNewFile();        
	        }
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {// use readLine method,read file line by line.

				list.add(s);
			}
			br.close();

		} catch (Exception e) {
			System.out.println("something wrong during reading the file");
			e.printStackTrace();
		}

		return list;
	}

}
