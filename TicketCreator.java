package orderman_lidolana_v1;


public class TicketCreator {
	private OrderCreator order;

	public TicketCreator(OrderCreator order) {
		super();
		this.order = order;
	}

	public void sendOrder() {
		System.out.println("Date: " + order.getNow());
		for (int i = 0; i < order.getMenuItems().size(); i++) {
			System.out.println(order.getMenuItems().get(i).toString());
			for(int j=0; j < order.getMenuItems().get(i).numberOfOptions(); j++) {
				System.out.println("       " + order.getMenuItems().get(i).optionsToString(j));
			}
			
		}
	}
}
