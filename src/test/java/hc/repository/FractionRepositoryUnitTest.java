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

import hc.model.Fraction;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FractionRepositoryUnitTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	FractionRepository fractionRepository;

	double delta = 1e-15;

	@Test
	@Transactional
	public void testFindByProfile() {

		entityManager.persist(new Fraction(null, "a", "jan", 0.1));
		entityManager.persist(new Fraction(null, "a", "feb", 0.2));

		List<Fraction> fractions = fractionRepository.findByProfile("a");

		assertEquals(2, fractions.size());

		assertEquals("a", fractions.get(0).getProfile());

		assertEquals(0.1, fractions.get(0).getFraction(), delta);
		assertEquals("jan", fractions.get(0).getMonth());
		assertEquals("a", fractions.get(1).getProfile());
		assertEquals(0.2, fractions.get(1).getFraction(), delta);
		assertEquals("feb", fractions.get(1).getMonth());
	}

	@Test
	@Transactional
	public void testFindByMonth() {

		Fraction fraction = fractionRepository.findByMonth("jan");
		assertEquals(0.1, fraction.getFraction(), delta);
	}

}
