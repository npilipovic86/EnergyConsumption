package hc.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import hc.model.MeterReading;
import hc.service.MeterReadingService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MeterReadingControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MeterReadingService meterReadingService;

	@Test
	@Transactional
	public void testConsumption() {
		restTemplate.postForEntity("/api/meters", new MeterReading(null, 1L, "a", "feb", 12), MeterReading.class);

		ResponseEntity<MeterReading> responseEntity = restTemplate.getForEntity("/api/meters?month=feb&meterId=1",
				MeterReading.class);

		MeterReading meterReading = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(Long.valueOf(1), meterReading.getMeterId());
		assertEquals("a", meterReading.getProfile());
		assertEquals("feb", meterReading.getMonth());
		assertEquals(12, meterReading.getMeterReading());

	}

	@Test
	@Transactional
	public void testAdd() {
		int size = meterReadingService.findAll().size();

		ResponseEntity<MeterReading> responseEntity = restTemplate.postForEntity("/api/meters",
				new MeterReading(null, 1L, "a", "jan", 10), MeterReading.class);

		MeterReading meter = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(meter);
		assertEquals(Long.valueOf(1), meter.getMeterId());
		assertEquals("a", meter.getProfile());
		assertEquals("jan", meter.getMonth());
		assertEquals(10, meter.getMeterReading());

		List<MeterReading> meters = meterReadingService.findAll();

		assertEquals(size + 1, meters.size());
		assertEquals("a", meters.get(meters.size() - 1).getProfile());
		assertEquals("jan", meters.get(meters.size() - 1).getMonth());
		assertEquals(10, meters.get(meters.size() - 1).getMeterReading());

		meterReadingService.remove(meter.getId());
	}
}
