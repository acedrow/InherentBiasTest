package griffinProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/*
 * Class to handle systematic rotating of images (manage upper-level file I/O), do user input, 
 * process results, and create instances of other classes
 * 
 */
public class Driver {

	// Test filepath:
	// C:/Users/Linden/Desktop/TestImages/

	static Vector<ImagePack> faceVector;
	static Vector<ImagePack> objectVector;
	static Vector<String> history;

	static String mainPath = "C:/Users/Linden/Desktop/griffinProgram/";
	static String facePath = "faces/";
	static String objectPath = "objects/";
	static String faceDescriptions = "faceDescriptions.txt";
	static String objectDescriptons = "objectDescriptons.txt";
	static String logPath = "logs/";

	static int numFaces;
	static int numObjects;
	static int timeToRun = 10000; // in ms: 300000 ms = 5 minutes

	static DisplayImage display;

	public static void main(String[] args) {
		// Concatenate description paths to absolute paths.
		facePath = mainPath + facePath;
		objectPath = mainPath + objectPath;
		logPath = mainPath + logPath;
		faceDescriptions = mainPath + faceDescriptions;
		objectDescriptons = mainPath + objectDescriptons;

		// sets correct numbers for how many face and object images we have.
		File filePath = (new File(facePath));
		numFaces = (filePath.listFiles().length);

		filePath = (new File(objectPath));
		numObjects = (filePath.listFiles().length);

		// pairs images with descriptions, puts them in correct vectors.
		ImagePacker packer = new ImagePacker(numFaces, facePath, faceDescriptions);
		faceVector = packer.pack();

		packer = new ImagePacker(numObjects, objectPath, objectDescriptons);
		objectVector = packer.pack();
		
		history = new Vector<String>();

		display = new DisplayImage();

		// shows our waitscreen, and waits for a keypress to start the test
		ImagePack waitScreen = packer.packOne("C:/Users/Linden/Desktop/griffinProgram/0.jpg", "waitScreen");
		display.populateFrame(waitScreen);

		synchronized (display) {
			try {
				System.out.println("waiting on keypress");
				display.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Random random = new Random();
		Timer timer = new Timer();
		timer.startSnapShot();

		// Our main loop for the test
		while (timer.getCurrentTime() < timeToRun) {
			int faceNum = random.nextInt(numFaces);
			int objectNum = random.nextInt(numObjects);

			display.waitTwoHundred(faceVector.get(faceNum));
			display.waitTwoHundred(objectVector.get(objectNum));
			display.waitForAction();
			
			history.add(faceVector.get(faceNum).getDescription());
			history.add(objectVector.get(objectNum).getDescription());

		}

		Logger logger = new Logger(history, display.getCharVector(), display.getTimer());
		logger.generateLog(logPath);

		System.exit(0);
	}
}
