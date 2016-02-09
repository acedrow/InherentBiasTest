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

	static Vector<ImagePack> imageVector;
	static String imagePath = "C:/Users/Linden/Desktop/griffinProgram/images/";
	static String descriptionsPath = "C:/Users/Linden/Desktop/griffinProgram/descriptions.txt";
	static String logPath = "C:/Users/Linden/Desktop/griffinProgram/logs/";
	static int numImages;
	static DisplayImage display;

	public static void main(String[] args) {
		
		File filePath = (new File(imagePath));
		numImages = (filePath.listFiles().length) - 1;

		ImagePacker packer = new ImagePacker(numImages, imagePath, descriptionsPath);
		imageVector = packer.pack();
		packer.randomize(imageVector);

		Timer timer = new Timer();
		
		display = new DisplayImage(numImages, imageVector);

		// for loop to iterate number of times = numImages/2
		// it loops once for each pair of images.

		synchronized (display) {
			try {
				System.out.println("waiting to start");
				display.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (int i = 1; i <= numImages; i += 2) {
			display.populateFrame(i);
			timer.startSnapShot();

			synchronized (display) {
				try {
					System.out.println("waiting on keypress");
					display.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			timer.endSnapShot();

			if (i == 1) {
				System.out.println(timer.getTime(0));
			} else {
				int timeToGet = (i - 1) / 2;
				System.out.println(timer.getTime(timeToGet));
			}
		}
		
		Logger logger = new Logger(imageVector, display.getCharVector(), timer);
		logger.generateLog(logPath);

		System.exit(0);
	}

	public String getDescriptionsPath() {
		return descriptionsPath;
	}
}
