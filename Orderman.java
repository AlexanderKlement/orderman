package orderman_lidolana_v1;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class Orderman {

	public static void main(String[] args) {
		List<FoodButton> foodButtons = new ArrayList<FoodButton> ();
		
		//Add data:
		FoodButtonCreator foodButtonCreator = new FoodButtonCreator();
		//Options:
		ArrayList<String> ketchupMayoOptions = new ArrayList<>();
		ketchupMayoOptions.add("Ketchup");
		ketchupMayoOptions.add("Mayo");
		ketchupMayoOptions.add("Ketchup + Mayo");
		ketchupMayoOptions.add("Nix");
		//Food:
		foodButtons.add(foodButtonCreator.newFoodButton("Pommes", ketchupMayoOptions, (float) 3.00));
		foodButtons.add(foodButtonCreator.newFoodButton("Nuggets", ketchupMayoOptions, (float) 4.00));
		foodButtons.add(foodButtonCreator.newFoodButton("Baguette", ketchupMayoOptions, (float) 5.00));
		foodButtons.add(foodButtonCreator.newFoodButton("Piadina", ketchupMayoOptions, (float) 5.00));
		foodButtons.add(foodButtonCreator.newFoodButton("Toast", ketchupMayoOptions, (float) 3.50));
		foodButtons.add(foodButtonCreator.newFoodButton("Bauernt", ketchupMayoOptions, (float) 5.00));
		foodButtons.add(foodButtonCreator.newFoodButton("Pizza", null, (float) 3.50));
		
		
		SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new MainGui(foodButtons); // Let the constructor do the job
	         }
	      });
	}

}
