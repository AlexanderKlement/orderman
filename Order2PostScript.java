package orderman_lidolana_v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

public class Order2PostScript {
	private OrderCreator ordercreator;
	private PrintWriter printwriter;
	public String filePath;
	public String creationDateAndTime;
	public Integer paperLengthMM;
	public Integer textLengthPoint;

	private Integer ticketNumber;

	public String font = "Courier";

	public Integer fontSizeTicketNumber = 23;
	public Integer fontSizeArticles = 13;
	//public Integer fontSizePriceTag = 23;
	
	public Integer xShiftTicketNumber = 20;
	public Integer xShiftOrderList = 30;
	//public Integer xShiftPriceTag = 20;
	
	public Integer yShiftTicketNumber = 15; //From Top!
	public Integer yShiftOrderListMinimum = 10; //Attention: only minimum from bottom
	//public Integer yShiftPriceTagMinimum = 10; //Attention: only minimum from bottom
	
	public Integer spacebetweenOrdersInOrderList = 2;
	public Integer spaceBetweenTicketNumberAndOrderList = 5;
	//public Integer spaceBetweenOrderListAndSeparator = 2;
	//public Integer spaceBetweenSeparatorAndPriceTag = 2;

	public Order2PostScript(OrderCreator ordercreator, String filepath, Integer ticketNumber) {
		this.ordercreator = ordercreator;
		this.filePath = filepath;
		this.ticketNumber = ticketNumber;
		
		this.textLengthPoint = 0;
		this.paperLengthMM = calcPaperLengthMM();

		// create Printwriter:
		if (!createPrintWriter())
			System.exit(1);

		writeHeader();

		// writeOrder();

		printTickerNumber();
		printOrders();

		// writeEOF();

		cleanUp();

		/*
		 * Steps: -Create orders with date and time as filenames -create intro function
		 * -write text function
		 * 
		 */
	}

	private Integer calcPaperLengthMM() {
		this.textLengthPoint += yShiftTicketNumber;
		this.textLengthPoint += fontSizeTicketNumber;
		this.textLengthPoint += spaceBetweenTicketNumberAndOrderList;
		this.textLengthPoint += this.ordercreator.getMenuItems().size() * (spacebetweenOrdersInOrderList + fontSizeArticles);
		this.textLengthPoint += yShiftOrderListMinimum;
		
		Integer textLengthMM = point2mm(textLengthPoint);
		return roundUp2ten(textLengthMM);
	}

	/**
	 * Creates a File with the current date and time as name
	 */

	private boolean createPrintWriter() {
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		String filename = String.valueOf(paperLengthMM) + "_" + String.valueOf(date.getYear()) + String.valueOf(date.getMonth())
				+ String.valueOf(date.getDay()) + "-" + String.valueOf(date.getHours()) + ":"
				+ String.valueOf(date.getMinutes() + ":" + String.valueOf(date.getSeconds()));
		this.creationDateAndTime = filename;
		String pathAndName = this.filePath + filename + ".ps";
		try {
			File file = new File(pathAndName);
			this.printwriter = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("[Order2PostScript]: Error creating file!");
			return false;
		}
		return true;
	}

	private void writeHeader() {
		printwriter.println("%!PS-Adobe-3.0");
		printwriter.println("%%Creator:Orderman LidoLana - Alexander Klement");
		// printwriter.println(creationDateAndTime);
		printwriter.println("%%DocumentData: Clean7Bit");
		printwriter.println("%%LanguageLevel: 2");
		printwriter.println("%%Pages: 1");
		// printwriter.println("%%BoundingBox: 14 14 215 215");
		// TODO: take a look what BoundingBox does!
		printwriter.println("%%BoundingBox: 14 14 215 120");
		printwriter.println("%%EndComments");
		// printwriter.println("%%DocumentMedia: Plain 72 72 0 white Plain");
		// TODO: maybe look what this does
		printwriter.println("%%BeginProlog");
		printwriter.println("% Use own dictionary to avoid conflicts");
		printwriter.println("10 dict begin");
		printwriter.println("%%EndProlog");
		printwriter.println("%%Page: 1 ");
	}

	/*
	 * 
	 * private void writeEOF() { // printwriter.println("%%EndData");
	 * printwriter.println("showpage"); printwriter.println("%%Trailer");
	 * printwriter.println("end"); printwriter.println("%%EOF");
	 * 
	 * }
	 * 
	 */

	private void setFontAndSize(String fontName, Integer fontsize) {
		printwriter.println("/" + fontName);
		printwriter.println(fontsize + " selectfont");
	}

	private void selectLocation(Integer x, Integer y) {
		printwriter.println(x + " " + y + " moveto");
	}

	private void printTickerNumber() {
		setFontAndSize(this.font, this.fontSizeTicketNumber);
		selectLocation(xShiftTicketNumber, startPlacePoints());
		printwriter.println("(Ticket No: " + this.ticketNumber + ") show");
	}

	private Integer startPlacePoints() {
		return mm2point(paperLengthMM) - yShiftTicketNumber;
	}

	private void printOrders() {
		setFontAndSize(font, fontSizeArticles);
		// selectLocation(xShiftOrderList, yShiftTicketNumber - fontSizeTicketNumber -
		// spaceBetweenTicketNumberAndOrderList);
		int i = 0;
		for (MenuItem mi : this.ordercreator.getMenuItems()) {
			selectLocation(xShiftOrderList, startPlacePoints() - fontSizeTicketNumber
					- spaceBetweenTicketNumberAndOrderList - i * (fontSizeArticles + spacebetweenOrdersInOrderList));
			i++;
			printwriter.println("(" + mi.getName() + "\t" + mi.getPrice() + ") show");
		}
	}

	private void cleanUp() {
		printwriter.println("showpage");
		printwriter.close();
	}
	
	public static Integer point2mm(Integer point) {
		return (int) (point/2.83);
	}
	
	public static Integer mm2point(Integer mm) {
		return (int) (mm/0.35);
	}
	
	public static Integer roundUp2ten(Integer number) {
		return (int) ((Math.ceil(number/10))*10);
	}

}
