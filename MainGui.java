package orderman_lidolana_v1;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2982884721030803196L;

	private List<FoodButton> foodButtons;
	private List<JButton> buttons = new ArrayList<>();
	private TextArea tfDisplay;

	public MainGui(List<FoodButton> foodButtons) {
		// set Elements
		this.foodButtons = foodButtons;

		// setup main windows:
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();

		GridBagLayout gbl = new GridBagLayout();
		c.setLayout(gbl);

		// Create new Order:
		OrderCreator tempOrderCreator = new OrderCreator();

		// setup food buttons
		Panel panelButtons = new Panel(new GridLayout(3, 3));
		for (int i = 0; i < numberOfButtons(); i++) {
			buttons.add(new JButton(foodButtons.get(i).getName()));
			buttons.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("[MainGui]: Button clicked: " + e.getActionCommand());
					FoodButton tempFoodButton = getFoodButtonByText(e.getActionCommand());
					MenuItem tempMenuItem = new MenuItem(1, tempFoodButton.getName(), null);
					if (tempFoodButton.hasOptions()){
						//create options array:
						Object[] options1 = new Object[tempFoodButton.numberOfOptions()];
						for(int i = 0; i < tempFoodButton.numberOfOptions(); i++) 
							options1[i] = tempFoodButton.getOptions().get(i);
						int n = JOptionPane.showOptionDialog(f,//parent container of JOptionPane
							    "Bitte auswählen", 
							    "Optionen",
							    JOptionPane.YES_NO_CANCEL_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null,//do not use a custom Icon
							    options1,//the titles of buttons
							    "null");
						
						//options list:
						ArrayList<String> selectedOption = new ArrayList<>();
						selectedOption.add((String) options1[n]);
						tempMenuItem.setOptions(selectedOption);
					} 
					tempOrderCreator.addMenuItem(tempMenuItem);
				}
			});
			panelButtons.add(buttons.get(i));

		}

		// Setup right panel
		Button sendButton = new Button("send");
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tempOrderCreator.setNow(new Date(System.currentTimeMillis()));
				TicketCreator ticketCreator = new TicketCreator(tempOrderCreator);
				ticketCreator.sendOrder();
				tempOrderCreator.reset();
			}
		});

		tfDisplay = new TextArea();

		// Set right gridlayout
		Panel sendAndShowGrid = new Panel(new GridLayout(2, 1));
		sendAndShowGrid.add(tfDisplay);
		sendAndShowGrid.add(sendButton);

		// Set Main GridbagLayout
		addComponent(c, gbl, panelButtons, 0, 0, 2, 2, 1.0, 1.0);
		addComponent(c, gbl, sendAndShowGrid, 2, 0, 1, 1, 0, 1.0);

		// make Fullscreen:
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());

		// show:
		f.setSize(xSize, ySize);
		f.setVisible(true);

	}

	private Integer numberOfButtons() {
		return foodButtons.size();
	}

	private FoodButton getFoodButtonByText(String text) {
		for (int i = 0; i < foodButtons.size(); i++)
			if (foodButtons.get(i).getName().equals(text))
				return foodButtons.get(i);
		return null;
	}

	// Hilfsklasse für GridbadLayout
	static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width, int height,
			double weightx, double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		cont.add(c);
	}

}
