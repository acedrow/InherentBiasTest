package griffinProject;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Grabs images, resizes, formats images, packs them with descriptions, and adds them to an array
 */

public class ImagePacker {
	String imagePath;
	String descriptionsPath;
	int numImages;

	Vector<ImagePack> imageVector = new Vector<ImagePack>();
	Vector<String> description = new Vector<String>();
	
	int imageSize;

	public ImagePacker(int inum, String ipath, String dpath) {
		imagePath = ipath;
		numImages = inum;
		descriptionsPath = dpath;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight() - 50;
		imageSize = screenWidth/2 - (screenWidth/10);
	}

	public Vector<ImagePack> pack() {
		String line = null;
		FileReader fileReader = null;

		try {
			fileReader = new FileReader(descriptionsPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader bufferedReader = new BufferedReader(fileReader);

		try {
			while ((line = bufferedReader.readLine()) != null) {
				description.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 1; i <= numImages; i++) {
			String specificImagePath = (imagePath + i + ".jpg");
			BufferedImage img = null;

			try {
				img = ImageIO.read(new File(specificImagePath));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			Image scaledImg = img.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(scaledImg);

			JLabel lbl = new JLabel();
			lbl.setIcon(icon);
			lbl.setPreferredSize(new Dimension(imageSize, imageSize));

			ImagePack imagePack = new ImagePack(lbl, description.get(i-1));

			imageVector.add(imagePack);
		}
		return imageVector;
	}
	
	public ImagePack packOne(String imagePath, String description) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File(imagePath));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Image scaledImg = img.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(scaledImg);

		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		lbl.setPreferredSize(new Dimension(imageSize, imageSize));

		ImagePack imagePack = new ImagePack(lbl, description);
		return imagePack;
	}

	/*
	 * Executes a modified Fisher-Yates shuffle to shuffle every element of the array except [0]
	 */
	public Vector<ImagePack> randomize(Vector<ImagePack> randoVector) {
		
		//by subtracting 1, i will directly reference array indicies
		int length = randoVector.size() - 1;
		Random random = new Random();
		for (int i = length; i > 1; i--) {

			int toSwap = random.nextInt(i) + 1;

			ImagePack swap = randoVector.get(toSwap);
			randoVector.set(toSwap, randoVector.get(i));
			randoVector.set(i, swap);

		}
		return randoVector;

	}
}
