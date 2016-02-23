package griffinProject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Logger {
	Vector<ImagePack> imageVector;
	Vector<String> stringVector;
	Vector<Character> charVector;
	Timer timer;

	public Logger(Vector<ImagePack> iVect, Vector<Character> cVect, Timer t) {
		imageVector = iVect;
		charVector = cVect;
		timer = t;
		stringVector = new Vector<String>();
		stringVector.add("First Image,Second Image:,Image Chosen:,Reaction Time(ms):");
	}

	/*
	 * Builds a Vector of Strings, with each element corresponding to a full row in our final
	 * .csv to export. [0] will always be the header row.
	 */
	public void generateLog(String logPath) {

		for (int i = 1; i < imageVector.size(); i += 2) {
			String firstDescription = imageVector.get(i).getDescription();
			String secondDescription = imageVector.get(i + 1).getDescription();
			
			firstDescription = firstDescription.substring(2, firstDescription.length());
			secondDescription = secondDescription.substring(2, secondDescription.length());
			
			String imageChosen;
			
			if (charVector.get(i / 2) == null){
				imageChosen = "neither";
			}
			else if (charVector.get(i / 2).equals('a')) {
				imageChosen = "first";
			} else {
				imageChosen = "second";
			}
			long reactionTime = timer.getFinalTime(i / 2);

			stringVector.add(firstDescription + "," + secondDescription + "," + imageChosen + "," + reactionTime);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH;mm;ss");
		Date date = new Date();
		
		//logPath + "log:" + dateFormat.format(date) + ".csv"
		String filename = (logPath + "LOG " + dateFormat.format(date) + ".csv");
		
		try {
			FileWriter fileWriter = new FileWriter(filename);
			for (int i = 0; i < stringVector.size(); i++){
				fileWriter.append(stringVector.get(i));
				fileWriter.append("\n");
			}
			
			fileWriter.flush();
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
