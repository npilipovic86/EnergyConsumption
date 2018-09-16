package hc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.model.MeterReading;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long> {

	MeterReading findByMonthAndMeterId(String month, Long meterId);

	List<MeterReading> findByMeterId(Long meterId);

}
