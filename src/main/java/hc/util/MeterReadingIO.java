package hc.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hc.model.MeterReading;

public class MeterReadingIO {

	private static final String file = "src/main/java/data/meterReadings.svc";

	public static List<MeterReading> readFromFile() throws IOException {

		List<MeterReading> meterReadings = new ArrayList<>();

		BufferedReader inputStream = null;

		try {
			inputStream = new BufferedReader(new FileReader(file));
			String l;
			while ((l = inputStream.readLine()) != null) {
				meterReadings.add(string2MeterReading(l));
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		for (int i = 0; i < meterReadings.size() - 1; i++) {
			if (meterReadings.get(i).getMeterReading() > meterReadings.get(i + 1).getMeterReading()) {
				log("Error in line: " + meterReadings.get(i + 1).getMeterId() + ", "
						+ meterReadings.get(i + 1).getProfile() + ", " + meterReadings.get(i + 1).getMonth() + ", "
						+ meterReadings.get(i + 1).getMeterReading());
				return null;
			}
		}
		try {
			Files.deleteIfExists(Paths.get(file));
		} catch (IOException e) {
			System.out.println(e);
		}
		return meterReadings;

	}

	public static void log(String s) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileWriter("src/main/java/data/error.log"));
			outputStream.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static MeterReading string2MeterReading(String row) {
		String[] s = row.split(",");
		MeterReading retVal = new MeterReading(null, Long.parseLong(s[0]), s[1], s[2], Integer.parseInt(s[3]));
		return retVal;
	}

}
