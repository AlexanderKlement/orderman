package orderman_lidolana_v1;

import java.util.List;

public class MenuItem {
	private Integer amount;
	private String name;
	private List<String> options;
	private Float price;
	
	public Integer getCount() {
		return amount;
	}
	public void setCount(Integer count) {
		this.amount = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public MenuItem(Integer count, String name, List<String> options, Float price) {
		super();
		this.amount = count;
		this.name = name;
		this.options = options;
		this.price = price;
	}
	
	public boolean equals(MenuItem otherItem) {
		if((this.name.equals(otherItem.getName())) && (this.options.size() == otherItem.getOptions().size())){
			for (int i = 0; i < this.options.size(); i++) {
				if(!this.options.get(i).equals(otherItem.getOptions().get(i)))
					return false;
			}
			return true;
		}
		return false;
	}
	
	public String toString() {
		return amount + "x: " + name;
	}
	
	public String optionsToString(int nr) {
		return options.get(nr);
	}
	
	public Integer numberOfOptions() {
		if(options == null)
			return 0;
		return options.size();
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
}
