package hc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hc.model.Fraction;
import hc.model.MeterReading;
import hc.service.FractionService;
import hc.service.MeterReadingService;
import hc.util.Constant;
import hc.util.MeterReadingIO;

@RestController
public class MeterReadingController extends Constant {

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private FractionService fractionService;

	@GetMapping(value = "api/meters")
	public MeterReading consumption(@RequestParam String month, @RequestParam Long meterId) {
		MeterReading meterReading = meterReadingService.findByMonthAndMeterId(month, meterId);
		return meterReading;
	}

	@GetMapping(value = "api/files")
	public ResponseEntity<String> readFromFile() throws IOException {
		List<MeterReading> mr = MeterReadingIO.readFromFile();
		if (mr != null) {
			meterReadingService.saveAll(mr);
			return new ResponseEntity<>("Successfully load file", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error in file", HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "api/meters")
	public ResponseEntity<String> add(@RequestBody MeterReading mr) {
		MeterReading lastRecord = null;
		List<Fraction> fractons = fractionService.findByProfile(mr.getProfile());
		Fraction fractonMonth = fractionService.findByMont(mr.getMonth());
		List<MeterReading> meterReadings = meterReadingService.findByMeterId(mr.getMeterId());
		if (!meterReadings.isEmpty()) {
			lastRecord = meterReadings.get(meterReadings.size() - 1);
		}

		double differenceMeterReading = 0;

		if (lastRecord == null) {
			differenceMeterReading = mr.getMeterReading();
		} else {
			differenceMeterReading = mr.getMeterReading() - lastRecord.getMeterReading();
		}

		double lowerLimit = CONSUMPTION_PER_YEAR * (fractonMonth.getFraction() - FRACTION_DIFERENCE);
		double upperLimit = CONSUMPTION_PER_YEAR * (fractonMonth.getFraction() + FRACTION_DIFERENCE);

		if (differenceMeterReading <= lowerLimit || differenceMeterReading > upperLimit
				|| differenceMeterReading >= FRACTION_TOLERANCE * CONSUMPTION_PER_YEAR) {
			return new ResponseEntity<>("Error, over limit", HttpStatus.BAD_REQUEST);
		}

		if (fractons.isEmpty()) {
			return new ResponseEntity<>("Error, no fraction", HttpStatus.BAD_REQUEST);
		}
		if (lastRecord != null && mr.getMeterReading() < lastRecord.getMeterReading()) {
			return new ResponseEntity<>("Error, wrong input", HttpStatus.BAD_REQUEST);
		}

		meterReadingService.save(mr);
		return new ResponseEntity<>("Saved", HttpStatus.CREATED);
	}
}
