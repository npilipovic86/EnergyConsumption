package hc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hc.model.Fraction;
import hc.service.FractionService;

@RestController
public class FractionController {

	@Autowired
	private FractionService fractionService;

	@PostMapping(value = "api/fractions")
	public ResponseEntity<String> add(@RequestBody Fraction fraction) {
		List<Fraction> fractons = fractionService.findByProfile(fraction.getProfile());
		double sumOld = 0;
		double sum = 0;
		for (Fraction fr : fractons) {
			sumOld += fr.getFraction();
		}
		if (fraction.getFraction() < 0) {
			return new ResponseEntity<>("Error, no fraction", HttpStatus.BAD_REQUEST);
		}
		sum = sumOld + fraction.getFraction();
		if (sum > 1) {
			return new ResponseEntity<>("Error, fraction is bigger then 1", HttpStatus.BAD_REQUEST);
		}
		fractionService.save(fraction);
		return new ResponseEntity<>("Fraction saved", HttpStatus.CREATED);
	}
}
