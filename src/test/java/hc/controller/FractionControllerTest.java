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

import hc.model.Fraction;
import hc.service.FractionService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FractionControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private FractionService fractionService;

	double delta = 1e-15;

	@Test
	@Transactional
	public void testAdd() {
		int size = fractionService.findAll().size();

		ResponseEntity<Fraction> responseEntity = restTemplate.postForEntity("/api/fractions",
				new Fraction(null, "a", "jan", 0.1), Fraction.class);

		Fraction fraction = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(fraction);
		assertEquals("jan", fraction.getMonth());
		assertEquals("a", fraction.getProfile());
		assertEquals(0.1, fraction.getFraction(), delta);

		List<Fraction> fractions = fractionService.findAll();

		assertEquals(size + 1, fractions.size());
		assertEquals("jan", fractions.get(fractions.size() - 1).getMonth());
		assertEquals("a", fractions.get(fractions.size() - 1).getProfile());
		assertEquals(0.1, fractions.get(fractions.size() - 1).getFraction(), delta);

		fractionService.remove(fraction.getId());
	}
}
