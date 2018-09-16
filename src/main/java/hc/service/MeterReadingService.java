package hc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.model.MeterReading;
import hc.repository.MeterReadingRepository;

@Service
public class MeterReadingService {

	@Autowired
	MeterReadingRepository meterReadingRepository;

	public List<MeterReading> findByMeterId(Long meterId) {
		return meterReadingRepository.findByMeterId(meterId);
	}

	public MeterReading save(MeterReading mr) {
		return meterReadingRepository.save(mr);
	}

	public MeterReading findByMonthAndMeterId(String month, Long meterId) {
		return meterReadingRepository.findByMonthAndMeterId(month, meterId);
	}

	public void saveAll(List<MeterReading> mr) {
		meterReadingRepository.saveAll(mr);

	}

	public List<MeterReading> findAll() {
		return meterReadingRepository.findAll();
	}

	public void remove(Long id) {
		meterReadingRepository.deleteById(id);
	}

}
