package orderman_lidolana_v1;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;



public class OrderCreator {

	private Date now;
	private List<MenuItem> menuItems = new ArrayList<>();
	public Boolean listFlag; //ActionListener fires multiple times

	public OrderCreator() {
		this.now = new Date(System.currentTimeMillis());
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void addMenuItem(MenuItem menuItem) {
		this.menuItems.add(menuItem);
	}

	public boolean removeMenuItem(MenuItem menuItem) {
		for (int i = 0; i < this.menuItems.size(); i++) {
			if (this.menuItems.get(i).equals(menuItem)) {
				this.menuItems.remove(i);
				return true;
			}
		}
		return false;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public void reset() {
		menuItems.clear();
	}

	public Float getOrderPrice() {
		Float returnPrice = (float) 0.0;
		if (menuItems == null)
			return (float) 0.0;
		for (MenuItem menuItem : menuItems) {
			returnPrice += menuItem.getPrice();
		}
		return returnPrice;
	}

	public void updateTextPanel(JList currentOrderList) {
		DefaultListModel<String> model = new DefaultListModel<>();
		String tempString;
		for (int i = 0; i < getMenuItems().size(); i++) {
			tempString = "";
			tempString += (getMenuItems().get(i).toString() + "\n");
			for (int j = 0; j < getMenuItems().get(i).numberOfOptions(); j++) {
				tempString += ("       " + getMenuItems().get(i).optionsToString(j) + "\n");
			}
			model.addElement(tempString);
		}
		currentOrderList.setModel(model);
		this.listFlag = true;
	}

	public void updatePrice(JTextArea priceTag) {
		priceTag.setText(getOrderPrice() + " €");

	}
	
	public void removeItemById(int idx) {
		System.out.println("Id to remove: " + idx);
		System.out.println("Length of menuList: " + menuItems.size());
		this.menuItems.remove(idx);
	}

}
