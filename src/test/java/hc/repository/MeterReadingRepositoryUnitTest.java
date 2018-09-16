package hc.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import hc.model.MeterReading;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MeterReadingRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	MeterReadingRepository meterReadingRepository;

	@Test
	@Transactional
	public void testFindByMonthAndMeterId() {
		entityManager.persist(new MeterReading(null, 1L, "a", "jan", 10));
		entityManager.persist(new MeterReading(null, 1L, "a", "feb", 12));
		entityManager.persist(new MeterReading(null, 2L, "b", "feb", 13));

		MeterReading meterReadings = meterReadingRepository.findByMonthAndMeterId("jan", 1L);
		assertEquals(Long.valueOf(1), meterReadings.getMeterId());
		assertEquals("a", meterReadings.getProfile());
		assertEquals(10, meterReadings.getMeterReading());
	}

	@Test
	@Transactional
	public void testFindByMeterId() {

		List<MeterReading> meterReadings = meterReadingRepository.findByMeterId(2L);
		assertEquals(1, meterReadings.size());
		assertEquals(Long.valueOf(2), meterReadings.get(0).getMeterId());
		assertEquals("b", meterReadings.get(0).getProfile());
		assertEquals(13, meterReadings.get(0).getMeterReading());
		assertEquals("feb", meterReadings.get(0).getMonth());
	}

}
