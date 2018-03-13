package orderman_lidolana_v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;

public class Printer implements Runnable {

	public String hostname;
	public Integer port;
	public Boolean shutdown;
	public File folder;
	public String folderName = "done";
	public String unprintableFile = "not_printed";
	//make this lower if printing is delayed!
	public Long timeToSleep = (long) 500;

	public Printer(String hostname, Integer port, String pathToFiles) throws FileNotFoundException {
		this.hostname = hostname;
		this.port = port;
		this.folder = new File(pathToFiles);
		this.shutdown = false;

		// check Environment
		if (checkForFolder())
			throw new FileNotFoundException();
	}

	public void shutdownNow() {
		this.shutdown = true;
	}

	@Override
	public void run() {

		// Initialize:

		List<CupsPrinter> printers = null;
		CupsPrinter epson = null;

		try {

			CupsClient cupsClient = new CupsClient(hostname, port);
			printers = cupsClient.getPrinters();
			epson = printers.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * Proceder: -Get List of all Files in directory -new Thread if found one and
		 * print! -take one, get size out of filename and print -if error open popup(new
		 * Thread: Printhandler!)
		 */

		while (!shutdown) {
			
			try {
				Thread.sleep(timeToSleep);
			} catch (InterruptedException e1) {
				System.err.println("[Printer]: Unable to sleep ... continue anyway!");
			}
			// get files:
			File[] listOfFiles = folder.listFiles();

			String[] splittedFilename;

			for (int i = 0; i < listOfFiles.length; i++) {

				// Directories simply ignored
				if (listOfFiles[i].isFile()) {
					// get paper length
					splittedFilename = listOfFiles[i].getName().split("_");
					// move to new folder
					File newFilePath = new File(folder, folderName);
					try {
						Integer.parseInt(splittedFilename[0]);
					} catch (Exception e) {
						newFilePath = new File(folder, unprintableFile);
						listOfFiles[i].renameTo(new File(newFilePath, listOfFiles[i].getName()));
						continue;
					}
					listOfFiles[i].renameTo(new File(newFilePath, listOfFiles[i].getName()));

					try {
						InputStream is = new FileInputStream(listOfFiles[i].getAbsolutePath());
						PrintHandler ph = new PrintHandler(is, epson, Integer.parseInt(splittedFilename[0]));
						ph.run();

					} catch (Exception e) {
						// TODO: catch errors properly
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean checkForFolder() {
		File folderToSaveTo = new File(folder, folderName);
		if (!folderToSaveTo.exists()) {
			if (!folderToSaveTo.mkdirs())
				return false;
		} else if (!folderToSaveTo.isDirectory()) {
			return false;
		}
		return true;
	}

}
