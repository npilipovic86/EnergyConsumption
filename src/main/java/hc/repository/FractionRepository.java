package hc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hc.model.Fraction;

@Repository
public interface FractionRepository extends JpaRepository<Fraction, Long> {

	List<Fraction> findByProfile(String profile);

	Fraction findByMonth(String month);

}
