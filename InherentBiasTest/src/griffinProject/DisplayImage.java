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

	char keyChar;

	boolean waitingForAction = false;
	boolean keyPressed = false;
	boolean timeBoo = false;
	boolean firstTime = true;
	long timePressed = 0;


	public DisplayImage() {


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

	
	public void populateFrame(ImagePack pack) {

		panel.removeAll();

		panel.add(pack.getLabel());

		panel.revalidate();
		panel.repaint();

	}
	
	public void clearFrame() {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}

	private void onKeyTyped(KeyEvent event) {

		char tempKey = event.getKeyChar();

		// Boolean used to make sure keyChar is treated as atomic for purposes
		// of
		// waitOnAction completing everything it needs to
		// i.e. only the first keypress of A or L in the 500ms window is logged
		if (!keyPressed) {
			// if a or l (the only inputs for this project)
			// is pressed:
			if (tempKey == 'a' || tempKey == 'l') {

				// if it's our first time running (i.e. we need to skip the wait
				// screen,
				// ONLY CHANGE THIS VARIABLE ONCE.
				if (firstTime) {
					firstTime = false;
					synchronized (this) {
						notifyAll();
					}
				}
				if (waitingForAction) {
					keyChar = tempKey;

					keyPressed = true;
					timePressed = System.currentTimeMillis();
					System.out.println("Key pressed: " + keyChar);
					

				}
			}
		}

	}

	/*
	 * Called from driver, waits for either time (500 ms) to run out, or for a
	 * key to be pressed If a timeout occurs, logs 9999 as the time, and null as
	 * the key, else if a key is pressed, log key and time, then notify Driver
	 * which will call back to display to log
	 * 
	 * cleans up afterwards: resets timeboo and keyboo resets timer (necessary?)
	 */

	public void waitForAction() {
		clearFrame();
		waitingForAction = true;
		
		timer.startSnapShot();
		while (timer.getCurrentTime() < 500) {

		}
		
		waitingForAction = false;
		
		if(keyPressed){
			System.out.println("logged key: " + keyChar);
			charVector.add(keyChar);
			timer.logTime(timePressed, true);
		}
		
		else{
			System.out.println("logged key: null");
			charVector.add(null);
			timer.logTime(9999, false);
		}
		
		timer.reset();
		resetBooleans();
	}

	public void waitTwoHundred(ImagePack pack) {
		clearFrame();
		populateFrame(pack);
		timer.startSnapShot();
		while (timer.getCurrentTime() < 200) {

		}
		timer.reset();
	}

	public Vector<Character> getCharVector() {
		return charVector;
	}

	void resetBooleans() {
		waitingForAction = false;
		keyPressed = false;
	}
	
	public Timer getTimer(){
		return timer;
	}
}
