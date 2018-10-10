package sushigame.view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver {

	private SushiGameModel game_model;
	private JLabel display;
	private int setScoreBoard;
	
	public ScoreboardWidget(SushiGameModel gm) {
		setScoreBoard = 0;
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML(setScoreBoard));
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JButton spoiled = new JButton("Spoiled Food");
		spoiled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setScoreBoard = 1;
				refresh();
			}
		});
		JButton food = new JButton("Food Consumed");
		food.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setScoreBoard = 2;
				refresh();
			}
		});
		JButton balance = new JButton("Chef's Balance");
		balance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setScoreBoard = 0;
				refresh();
			}
		});
		panel.setLayout(new FlowLayout());
		panel.add(spoiled);
		panel.add(food);
		panel.add(balance);
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		add(panel, BorderLayout.SOUTH);
	
	}

	private String makeScoreboardHTML(int type) {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";
		if(type == 0) { 		// Create an array of all chefs and sort by balance.
			Chef[] opponent_chefs= game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length+1];
			chefs[0] = game_model.getPlayerChef();
			for (int i=1; i<chefs.length; i++) {
				chefs[i] = opponent_chefs[i-1];
			}
			Arrays.sort(chefs, new HighToLowBalanceComparator());
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
			}
		} else if(type == 1) {
			Chef[] opponent_chefs= game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length+1];
			chefs[0] = game_model.getPlayerChef();
			for (int i=1; i<chefs.length; i++) {
				chefs[i] = opponent_chefs[i-1];
			}
			Arrays.sort(chefs, new HighToLowSpoiledAmountComparator());
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getSpoiled()*100.0)/100.0 + " Oz.) <br>";
			}
		} else if(type == 2) {
			Chef[] opponent_chefs= game_model.getOpponentChefs();
			Chef[] chefs = new Chef[opponent_chefs.length+1];
			chefs[0] = game_model.getPlayerChef();
			for (int i=1; i<chefs.length; i++) {
				chefs[i] = opponent_chefs[i-1];
			}
			Arrays.sort(chefs, new HighToLowFoodConsumedComparator());
			
			for (Chef c : chefs) {
				sb_html += c.getName() + " (" + Math.round(c.getFoodConsumed()*100.0)/100.0 + " Oz.) <br>";
			}
		}
		return sb_html;	
	}


	public void refresh() {
		display.setText(makeScoreboardHTML(setScoreBoard));		
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

}
