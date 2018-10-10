package sushigame.view;

import java.awt.ComponentOrientation;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.Plate;
import comp401.sushi.Roll;
import sushigame.model.Belt;

public class PlateMouseListener implements MouseListener {
	
	private Plate p;
	private Belt b;
	private int position;
	
	public PlateMouseListener(Plate p, Belt b, int position) {
		this.p = p;
		this.b = b;
		this.position = position;
	}
	
	public Plate getPlate() {
		return p;
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JFrame frame = new JFrame();
		JLabel plateColor = new JLabel ("Plate Color: " + p.getColor().toString() + " - ");
		JPanel infoDisplay = new JPanel();
		JLabel sushiType = new JLabel ("Type of Sushi: " + p.getContents().getName() + " - ");
		infoDisplay.add(plateColor);
		infoDisplay.add(sushiType);
		
		if(p.getContents().getName().contains("Roll")) {
			for(int i = 0; i < p.getContents().getIngredients().length; i++) {
				JLabel ingredient = 
						new JLabel("Roll has: " + p.getContents().getIngredients()[i].getAmount()   + "oz of " + p.getContents().getIngredients()[i].getName());
				infoDisplay.add(ingredient);
			}
		}
		
		JLabel chefName = new JLabel("Chef Name: " + p.getChef().getName() + " - ");
		JLabel plateAge = new JLabel("Plate Age: " + b.getAgeOfPlateAtPosition(position));
		infoDisplay.add(chefName);
		infoDisplay.add(plateAge);
		frame.add(infoDisplay);
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//
	}

}
