package orderman_lidolana_v1;

import java.util.List;

public class FoodButtonCreator {
	private Integer id;
	
	public FoodButtonCreator() {
		this.id = 0;
	}
	
	public FoodButton newFoodButton(String name, List<String> Options, Float price) {
		return new FoodButton(id, name, Options, price);
	}
	
}
