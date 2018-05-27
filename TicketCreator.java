package orderman_lidolana_v1;


public class TicketCreator {
	private OrderCreator order;
	public String filePath = "/home/kalle/lidolana/";
	private Integer ticketNumber;

	public TicketCreator(OrderCreator order, Integer ticketNumber) {
		super();
		this.order = order;
		this.ticketNumber = ticketNumber;
	}

	public void sendOrder() {
		if(order.getMenuItems().size() == 0)
			return;
		System.out.println("Date: " + order.getNow());
		for (int i = 0; i < order.getMenuItems().size(); i++) {
			System.out.println(order.getMenuItems().get(i).toString());
			for(int j=0; j < order.getMenuItems().get(i).numberOfOptions(); j++) {
				System.out.println("       " + order.getMenuItems().get(i).optionsToString(j));
			}
			
		}
		System.out.println("\n" + order.getOrderPrice() + " €");
		//TODO: implement this after testing!
		new Order2PostScript(this.order, filePath, ticketNumber);
		
	}
	
}
