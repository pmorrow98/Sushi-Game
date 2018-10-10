package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import comp401.sushi.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401.sushi.Avocado;
import comp401.sushi.AvocadoPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.IngredientPortionImpl;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.Roll;
import comp401.sushi.Sashimi;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.Sushi;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private int belt_size;
	private 	Sashimi.SashimiType sashimiType;
	private Plate.Color sashimiColor;
	private int sashimiPosition;
	private double goldSashimiPrice;
	private Nigiri.NigiriType nigiriType;
	private Plate.Color nigiriColor;
	private int nigiriPosition;
	private double goldNigiriPrice;
	private double goldRollPrice;
	private Plate.Color rollColor;
	private double rollAvocado = 0;
	private double rollCrab = 0;
	private double rollEel = 0;
	private double rollRice = 0;
	private double rollSalmon = 0;
	private double rollSeaweed = 0;
	private double rollShrimp = 0;
	private double rollTuna = 0;
	private int rollPosition;
	private IngredientPortion[] rollIngs = new IngredientPortion[8];
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton sashimi_button = new JButton("Make new Sashimi");
		sashimi_button.setActionCommand("red_crab_sashimi_at_3");
		sashimi_button.addActionListener(this);
		add(sashimi_button);

		JButton nigiri_button = new JButton("Make new Nigiri");
		nigiri_button.setActionCommand("blue_eel_nigiri_at_8");
		nigiri_button.addActionListener(this);
		add(nigiri_button);

		JButton roll_button = new JButton("Make new Roll");
		roll_button.setActionCommand("gold_kmp_roll_at_5");
		roll_button.addActionListener(this);
		add(roll_button);

	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "red_crab_sashimi_at_3":
			makeSashimi();
			break;
		case "blue_eel_nigiri_at_8":
			makeNigiri();
			break;
		case "gold_kmp_roll_at_5":
			makeRoll();
		}
	}
	
	
	public void makeSashimi() {
		
		JFrame frame = new JFrame("Make your Sashimi");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		
		JLabel goldLabel = new JLabel("Enter Plate's Price: ");
		SpinnerModel goldModel = new SpinnerNumberModel(5.0, 5.0, 10.0, .01);
		JSpinner goldPlatePrice = new JSpinner(goldModel);
		goldPlatePrice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				goldSashimiPrice = (double)(spinner.getValue());
			}
		});
		
		String[] sushiTypes = {"Select sushi type", "eel", "crab", "salmon", "shrimp", "tuna"};
		JComboBox<String> selectType = new JComboBox<String>(sushiTypes);
		selectType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String temp = (String) source.getSelectedItem();
				switch(temp) {
				case("eel"):
					sashimiType = Sashimi.SashimiType.EEL;
					break;
				case("crab"):
					sashimiType = Sashimi.SashimiType.CRAB;
					break;
				case("salmon"):
					sashimiType = Sashimi.SashimiType.SALMON;
					break;
				case("shrimp"):
					sashimiType = Sashimi.SashimiType.SHRIMP;
					break;
				case("tuna"):
					sashimiType = Sashimi.SashimiType.TUNA;
					break;
				}
			}	
		});
		
		String[] plateColors = {"Select plate type", "red", "green", "blue", "gold"};
		JComboBox<String> selectPlate = new JComboBox<String>(plateColors);
		selectPlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String temp = (String) source.getSelectedItem();
				switch(temp) {
				case("red"):
					sashimiColor = Plate.Color.RED;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("green"):
					sashimiColor = Plate.Color.GREEN;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("blue"):
					sashimiColor = Plate.Color.BLUE;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("gold"): 
					sashimiColor = Plate.Color.GOLD;
					goldLabel.setVisible(true);
					goldPlatePrice.setVisible(true);
					break;
				}
			}
		});
		
		JLabel label = new JLabel("Enter plate's position: ");
		SpinnerModel model = new SpinnerNumberModel(0, 0, belt_size, 1);
		JSpinner selectPosition = new JSpinner(model);
		selectPosition.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				sashimiPosition = (int)(spinner.getValue());
			}
		});
		
		JButton makePlate = new JButton("Press to make Plate!");
		makePlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sashimiColor == Plate.Color.GOLD) {
					placeSashimi(sashimiColor, sashimiPosition, sashimiType, goldSashimiPrice);
				} else {
					placeSashimi(sashimiColor, sashimiPosition, sashimiType);
				}
				
				sashimiColor = null;
				sashimiPosition = 0;
				sashimiType = null;
				goldSashimiPrice = 5.0;
				
				frame.dispose();
			}
		});
		
		panel.add(selectType);
		panel.add(label);
		panel.add(selectPosition);
		panel.add(selectPlate);
		goldLabel.setVisible(false);
		panel.add(goldLabel);
		goldPlatePrice.setVisible(false);
		panel.add(goldPlatePrice);
		panel.add(makePlate);
		frame.add(panel);
		frame.setSize(400, 300);
		frame.setVisible(true);
		
	}
	
	public void placeSashimi(Plate.Color color, int pos, Sashimi.SashimiType type) {
		if(color == Plate.Color.RED) {
			makeRedPlateRequest(new Sashimi(type), pos);
		}
		if(color == Plate.Color.BLUE) {
			makeBluePlateRequest(new Sashimi(type), pos);
		}
		if(color == Plate.Color.GREEN) {
			makeGreenPlateRequest(new Sashimi(type), pos);
		}
	}
	
	public void placeSashimi(Plate.Color color, int pos, Sashimi.SashimiType type, double price) {
		makeGoldPlateRequest(new Sashimi(type), pos, price);
	}
	
	public void makeNigiri() {
		JFrame frame = new JFrame("Make your Nigiri");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		
		JLabel goldLabel = new JLabel("Enter Plate's Price: ");
		SpinnerModel goldModel = new SpinnerNumberModel(5.0, 5.0, 10.0, .01);
		JSpinner goldPlatePrice = new JSpinner(goldModel);
		goldPlatePrice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				goldNigiriPrice = (double)(spinner.getValue());
			}
		});
		
		String[] sushiTypes = {"Select sushi type", "eel", "crab", "salmon", "shrimp", "tuna"};
		JComboBox<String> selectType = new JComboBox<String>(sushiTypes);
		selectType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String temp = (String) source.getSelectedItem();
				switch(temp) {
				case("eel"):
					nigiriType = Nigiri.NigiriType.EEL;
					break;
				case("crab"):
					nigiriType = Nigiri.NigiriType.CRAB;
					break;
				case("salmon"):
					nigiriType = Nigiri.NigiriType.SALMON;
					break;
				case("shrimp"):
					nigiriType = Nigiri.NigiriType.SHRIMP;
					break;
				case("tuna"):
					nigiriType = Nigiri.NigiriType.TUNA;
					break;
				}
			}	
		});
		
		String[] plateColors = {"Select plate type", "red", "green", "blue", "gold"};
		JComboBox<String> selectPlate = new JComboBox<String>(plateColors);
		selectPlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String temp = (String) source.getSelectedItem();
				switch(temp) {
				case("red"):
					nigiriColor = Plate.Color.RED;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("green"):
					nigiriColor = Plate.Color.GREEN;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("blue"):
					nigiriColor = Plate.Color.BLUE;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("gold"): 
					nigiriColor = Plate.Color.GOLD;
					goldLabel.setVisible(true);
					goldPlatePrice.setVisible(true);
					break;
				}
			}
		});
		
		JLabel label = new JLabel("Enter plate's position: ");
		SpinnerModel model = new SpinnerNumberModel(0, 0, belt_size, 1);
		JSpinner selectPosition = new JSpinner(model);
		selectPosition.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				nigiriPosition = (int)(spinner.getValue());
			}
		});
		
		JButton makePlate = new JButton("Press to make Plate!");
		makePlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nigiriColor == Plate.Color.GOLD) {
					placeNigiri(nigiriColor, nigiriPosition, nigiriType, goldNigiriPrice);
				} else {
					placeNigiri(nigiriColor, nigiriPosition, nigiriType);
				}
				
				nigiriColor = null;
				nigiriPosition = 0;
				nigiriType = null;
				goldNigiriPrice = 5.0;
				
				frame.dispose();
			}
		});
		
		panel.add(selectType);
		panel.add(label);
		panel.add(selectPosition);
		panel.add(selectPlate);
		goldLabel.setVisible(false);
		panel.add(goldLabel);
		goldPlatePrice.setVisible(false);
		panel.add(goldPlatePrice);
		panel.add(makePlate);
		frame.add(panel);
		frame.setSize(400, 300);
		frame.setVisible(true);
	}
	
	public void placeNigiri(Plate.Color color, int pos, Nigiri.NigiriType type) {
		if(color == Plate.Color.RED) {
			makeRedPlateRequest(new Nigiri(type), pos);
		}
		if(color == Plate.Color.BLUE) {
			makeBluePlateRequest(new Nigiri(type), pos);
		}
		if(color == Plate.Color.GREEN) {
			makeGreenPlateRequest(new Nigiri(type), pos);
		}
	}
	
	public void placeNigiri(Plate.Color color, int pos, Nigiri.NigiriType type, double price) {
		makeGoldPlateRequest(new Nigiri(type), pos, price);
	}
	
	public void makeRoll() {
		
		String rollName = "Player Roll";
		
		JFrame frame = new JFrame("Make your Roll");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		
		JLabel goldLabel = new JLabel("Enter Plate's Price: ");
		SpinnerModel goldModel = new SpinnerNumberModel(5.0, 5.0, 10.0, .01);
		JSpinner goldPlatePrice = new JSpinner(goldModel);
		goldPlatePrice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				goldRollPrice = (double)(spinner.getValue());
			}
		});
		
		JLabel labelAvocado = new JLabel("Enter avocado amount: ");
		SpinnerModel modelAv = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner avocadoAmount = new JSpinner(modelAv);
		avocadoAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollAvocado = (double)(spinner.getValue());
			}
		});
		
		JLabel labelSeaweed = new JLabel("Enter seaweed amount");
		SpinnerModel modelSea = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner seaweedAmount = new JSpinner(modelSea);
		seaweedAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollSeaweed = (double)(spinner.getValue());
			}
		});
		
		JLabel labelRice = new JLabel("Enter rice amount");
		SpinnerModel modelRice = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner riceAmount = new JSpinner(modelRice);
		riceAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollRice = (double)(spinner.getValue());
			}
		});
		
		JLabel labelEel = new JLabel("Enter eel amount");
		SpinnerModel modelEel = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner eelAmount = new JSpinner(modelEel);
		eelAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollEel = (double)(spinner.getValue());
			}
		});
		
		JLabel labelCrab = new JLabel("Enter crab amount");
		SpinnerModel modelCrab = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner crabAmount = new JSpinner(modelCrab);
		crabAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollCrab = (double)(spinner.getValue());
			}
		});
		
		JLabel labelSalmon = new JLabel("Enter salmon amount");
		SpinnerModel modelSal = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner salmonAmount = new JSpinner(modelSal);
		salmonAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollSalmon = (double)(spinner.getValue());
			}
		});
		
		JLabel labelShrimp = new JLabel("Enter shrimp amount");
		SpinnerModel modelShrimp = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner shrimpAmount = new JSpinner(modelShrimp);
		shrimpAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollShrimp = (double)(spinner.getValue());
			}
		});
		
		JLabel labelTuna = new JLabel("Enter tuna amount");
		SpinnerModel modelTuna = new SpinnerNumberModel(0, 0, 1.5, .1);
		JSpinner tunaAmount = new JSpinner(modelTuna);
		tunaAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollTuna = (double)(spinner.getValue());
			}
		});
		
		String[] plateColors = {"Select plate type", "red", "green", "blue", "gold"};
		JComboBox<String> selectPlate = new JComboBox<String>(plateColors);
		selectPlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String temp = (String) source.getSelectedItem();
				switch(temp) {
				case("red"):
					rollColor = Plate.Color.RED;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("green"):
					rollColor = Plate.Color.GREEN;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("blue"):
					rollColor = Plate.Color.BLUE;
					goldLabel.setVisible(false);
					goldPlatePrice.setVisible(false);
					break;
				case("gold"): 
					rollColor = Plate.Color.GOLD;
					goldLabel.setVisible(true);
					goldPlatePrice.setVisible(true);
					break;
				}
			}
		});
		
		JLabel label = new JLabel("Enter plate's position: ");
		SpinnerModel model = new SpinnerNumberModel(0, 0, belt_size, 1);
		JSpinner selectPosition = new JSpinner(model);
		selectPosition.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spinner = (JSpinner) e.getSource();
				rollPosition = (int)(spinner.getValue());
			}
		});
		
		JButton makePlate = new JButton("Press to make Plate!");
		makePlate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rollAvocado > 0) {
					rollIngs[0] = new AvocadoPortion(rollAvocado);
				}
				// rollIngs[0] = new AvocadoPortion(rollAvocado);
				if(rollCrab > 0) {
					rollIngs[1] = new CrabPortion(rollCrab);
				}
				// rollIngs[1] = new CrabPortion(rollCrab);
				if(rollEel > 0) {
					rollIngs[2] = new EelPortion(rollEel);
				}
				// rollIngs[2] = new EelPortion(rollEel);
				if(rollRice > 0) {
					rollIngs[3] = new RicePortion(rollRice);
				}
				// rollIngs[3] = new RicePortion(rollRice);
				if(rollSalmon > 0) {
					rollIngs[4] = new SalmonPortion(rollSalmon);
				}
				// rollIngs[4] = new SalmonPortion(rollSalmon);
				if(rollShrimp > 0) {
					rollIngs[5] = new ShrimpPortion(rollShrimp);
				}
				// rollIngs[5] = new ShrimpPortion(rollShrimp);
				if(rollSeaweed > 0) {
					rollIngs[6] = new SeaweedPortion(rollSeaweed);
				}
				// rollIngs[6] = new SeaweedPortion(rollSeaweed);
				if(rollTuna > 0) {
					rollIngs[7] = new TunaPortion(rollTuna);
				}
				// rollIngs[7] = new TunaPortion(rollTuna);
				
				ArrayList<IngredientPortion> ingsList = new ArrayList<IngredientPortion>();
				for(int i = 0; i < rollIngs.length; i++) {
					if(rollIngs[i] != null && rollIngs[i].getAmount() > 0) {
						ingsList.add(rollIngs[i]);
					}
				}
				IngredientPortion[] newIngs = ingsList.toArray(new IngredientPortion[ingsList.size()]);
				
				if(rollColor == Plate.Color.GOLD) {
					placeRoll(newIngs, rollName, rollColor, rollPosition, goldRollPrice);
				} else {
					placeRoll(newIngs, rollName, rollColor, rollPosition);
				}
				
				rollColor = null;
				rollPosition = 0;
				Arrays.fill(rollIngs, null);
				rollAvocado = 0;
				rollCrab = 0;
				rollEel = 0;
				rollRice = 0;
				rollSalmon = 0;
				rollSeaweed = 0;
				rollShrimp = 0;
				rollTuna = 0;
				goldRollPrice = 5.0;
				
				frame.dispose();
			}
		});
		
		
		panel.add(labelAvocado);
		panel.add(avocadoAmount);
		panel.add(labelSeaweed);
		panel.add(seaweedAmount);
		panel.add(labelRice);
		panel.add(riceAmount);
		panel.add(labelEel);
		panel.add(eelAmount);
		panel.add(labelCrab);
		panel.add(crabAmount);
		panel.add(labelSalmon);
		panel.add(salmonAmount);
		panel.add(labelShrimp);
		panel.add(shrimpAmount);
		panel.add(labelTuna);
		panel.add(tunaAmount);
		panel.add(label);
		panel.add(selectPosition);
		panel.add(selectPlate);
		goldLabel.setVisible(false);
		panel.add(goldLabel);
		goldPlatePrice.setVisible(false);
		panel.add(goldPlatePrice);
		panel.add(makePlate);
		frame.add(panel);
		frame.setSize(500,600);
		frame.setVisible(true);
			
	}
	
	public void placeRoll(IngredientPortion[] ings, String name, Plate.Color color, int pos) {

		if(color == Plate.Color.RED) {
			makeRedPlateRequest(new Roll(name, ings), pos);
		}
		if(color == Plate.Color.BLUE) {
			makeBluePlateRequest(new Roll(name, ings), pos);
		}
		if(color == Plate.Color.GREEN) {
			makeGreenPlateRequest(new Roll(name, ings), pos);
		}	
	}
	
	public void placeRoll(IngredientPortion[] ings, String name, Plate.Color color, int pos, double price) {
		
		makeGoldPlateRequest(new Roll(name, ings), pos, price);
		
	}
	
}
