package api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class FileUtils {

	public static JSONObject readFile(String FilePath) throws FileNotFoundException {
		File f= new File(FilePath);
//		read data from file
		FileReader fr=new FileReader(f);
		JSONTokener jt=new JSONTokener(fr);
		JSONObject Payload=new JSONObject(jt);
		return Payload;
				
		
	}
}

