import static java.lang.String.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class Report {

	static int currentYear = 0;
	static int currentMonth = 0;
	static double currentTotal = 0;
	static double grandTotal = 0;
	static double regiokarte = 33.4;
	static String seperator = "----";
    public static SimpleDateFormat mois = new SimpleDateFormat("MMMM", Locale.FRENCH);

	public static void main(String[] args) throws Exception {
		SortedMap<Date, Double> date2price = new TreeMap<>();
		Files.newDirectoryStream(Paths.get(WatchDir.path)).forEach(path->{
			try {
				Entry<Date, Double> parsed = WatchDir.parseFile(path);
				date2price.put(parsed.getKey(), parsed.getValue());
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		});
		
		
		date2price.forEach((date,price)-> {
			int year = date.getYear()+1900;
			if(currentYear!=year) {
				currentYear = year;
				//System.out.println(format("%s %s %s\n", seperator, currentYear, seperator));
			}
			int month = date.getMonth()+1;
			if(currentMonth!=month) {
				if(currentMonth>0) {
					System.out.println(String.format("Total      : %.2f€", currentTotal));
					System.out.println();
				}
				currentMonth = month;
				currentTotal = 0;
				System.out.println(format("%s %s %s\n", seperator, mois.format(date), currentYear));				
				if(hasRegio(currentYear, currentMonth)) {
					currentTotal+= regiokarte;
					System.out.println(String.format("Regiokarte : %.2f€", regiokarte));
				}
				
			}
			System.out.println(String.format("%s : %.2f€", WatchDir.yyyyMMdd.format(date), price));
			currentTotal += price;
			grandTotal += price;
		});
		System.out.println(String.format("Total      : %.2f€", currentTotal));
		System.out.println();
		System.out.println(String.format("Total année scolaire: %.2f€", grandTotal));
	}

	private static boolean hasRegio(int year, int month) {
		return year != 2023 || month != 5;
	}

}