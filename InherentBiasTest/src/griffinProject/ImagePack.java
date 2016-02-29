package griffinProject;

/*
 * pairs an image and its description in a single class
 */

import javax.swing.JLabel;

public class ImagePack {
	JLabel label;
	String description;
	int imageNumber;

	
	public ImagePack(JLabel lbl, String desc){
		label = lbl;
		description = desc;
	
	}
	
	public JLabel getLabel(){
		return label;
	}
	
	public String getDescription(){
		return description;
	}
	
	public int getImageNumber(){
		return imageNumber;
	}
	
}
