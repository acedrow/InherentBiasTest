package griffinProject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * Class to handle the micro operations of reading images, sizing, converting, and displaying them on the JFrame.
 */

public class DisplayImage {
	JFrame frame;
	Vector<ImagePack> imageVector;
	Vector<Character> charVector;
	KeyListener listener;
	JPanel panel;
	Timer timer;

	boolean keyPressed = false;
	boolean timeBoo = false;

	/*
	 * 2-ARG CONSTRUCTOR
	 * 
	 * Instantiates our JFrame, labelVector, and keyListener fields Performs
	 * image I/O and stores JLabels in the labelVector, ready to be
	 * added/removed from the frame
	 * 
	 */
	public DisplayImage(int numImages, Vector<ImagePack> iVect) {

		imageVector = iVect;
		charVector = new Vector<Character>();
		timer = new Timer();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight() - 50;

		// Instatiates our JFrame
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(screenWidth, screenHeight);

		panel = new JPanel();

		frame.add(panel);

		panel.add(imageVector.get(0).getLabel());

		listener = new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
				onKeyTyped(e);
			}
		};

		frame.addKeyListener(listener);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/*
	 * Gets Jlabels from labelVector and adds them to the JFrame field. Removes
	 * old labels if necessary
	 * 
	 * ARGUMENTS: imageNum - specifies the current image to add This should
	 * usually be an odd number, e.g. for the first add, imageNum == 1, 1.jpg
	 * and 2.jpg will be added.
	 * 
	 * imageNum = 0 is a special case that will put up the
	 * "Press enter to begin image" stored in labelVector[0]
	 * 
	 */
	public void populateFrame(int imageNum) {

		panel.removeAll();

		panel.add(imageVector.get(imageNum).getLabel());

		panel.revalidate();
		panel.repaint();

	}

	public void clearFrame() {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	/*
	 * Method to be called whenever a keyTyped event is triggered Will notify
	 * driver...
	 */
	private void onKeyTyped(KeyEvent event) {

		char keyChar = event.getKeyChar();

		if (!keyPressed) {
			if (keyChar == 'a' || keyChar == 'l') {
				keyPressed = true;
				System.out.println("Key pressed: " + keyChar);
				timer.logTime();
				charVector.add(keyChar);

			}

		}
	}

	public void waitForAction() {
		timer.startSnapShot();
		while (timer.getCurrentTime() < 500) {
			timeBoo = true;
		}

		if (!keyPressed) {
			charVector.add(null);
			timer.logTime(9999);
		}
		resetBooleans();
		synchronized (this) {
			notifyAll();
		}
	}

	public Vector<Character> getCharVector() {
		return charVector;
	}

	void resetBooleans() {
		keyPressed = false;
		timeBoo = false;
	}
}