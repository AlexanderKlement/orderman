package orderman_lidolana_v1;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.cups4j.PrintRequestResult;

public class PrintHandler implements Runnable {

	private InputStream file;
	private CupsPrinter printer;
	private Integer lengthOfMedia;
	public Integer sleepBeforeErrorMessage = 5000;

	public PrintHandler(InputStream file, CupsPrinter printer, Integer lengthOfMedia) {
		super();
		this.file = file;
		this.printer = printer;
		this.lengthOfMedia = lengthOfMedia;
		System.out.println("[Printhandler started]: " + file + " " + printer + " " + lengthOfMedia);
	}

	@Override
	public void run() {
		// TODO check for empty paper tray error and display warning message!

		PrintJob.Builder pjb = new PrintJob.Builder(file);
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("job-attributes", "media:keyword:X80MMY" + lengthOfMedia + "MM");
		pjb = pjb.attributes(attributes);
		PrintJob pj = pjb.build();

		PrintRequestResult prr = null;
		try {
			prr = printer.print(pj);
		} catch (Exception e) {
			// TODO catch error messages properly
			//maybe it's done later..
			e.printStackTrace();
		}

		try {
			Thread.sleep(sleepBeforeErrorMessage);
		} catch (InterruptedException e) {
			System.err.println("[PrintHandler]: Could not sleep long enough for whatever reason...!");
		}
		
		//TODO check if printjob has finished
		// --> if not, open Pop-up and ask for repeat or delete
		//Wait until it can be tested -> note all return codes
		
		System.out.println(prr.getResultCode());
		System.out.println(prr.getResultDescription());
		

	}

}
