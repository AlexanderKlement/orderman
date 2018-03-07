package orderman_lidolana_v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderCreator {

	private Date now;
	private List<MenuItem> menuItems = new ArrayList<>();

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
		if(menuItems == null)
			return (float) 0.0;
		for (MenuItem menuItem : menuItems) {
			menuItem.getPrice();
		}
	}
	
	

}
