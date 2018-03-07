package orderman_lidolana_v1;

import java.util.List;

public class FoodButton{
	
	private Integer id;
	private String name;
	private List<String> options;
	private Float price;
	
	
	public FoodButton(Integer id, String name, List<String> options, Float price) {
		this.id = id;
		this.name = name;
		this.options = options;
		this.price = price;
	}
	
	public boolean hasOptions() {
		return this.options == null ? false : true;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getOptions() {
		return options;
	}
	
	public int numberOfOptions() {
		if(hasOptions())
			return options.size();
		return 0;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	
	
	
}
