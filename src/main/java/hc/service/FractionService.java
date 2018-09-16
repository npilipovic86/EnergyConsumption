package hc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.model.Fraction;
import hc.repository.FractionRepository;

@Service
public class FractionService {

	@Autowired
	FractionRepository fractionRepository;

	public Fraction save(Fraction fraction) {
		return fractionRepository.save(fraction);
	}

	public List<Fraction> findByProfile(String profile) {
		return fractionRepository.findByProfile(profile);
	}

	public Fraction findByMont(String month) {
		return fractionRepository.findByMonth(month);
	}

	public List<Fraction> findAll() {
		return fractionRepository.findAll();
	}

	public void remove(Long id) {
		fractionRepository.deleteById(id);
	}

}
