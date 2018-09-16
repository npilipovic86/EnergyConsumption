package hc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fraction extends MonthProfile {

	@Id
	@GeneratedValue
	private Long id;

	private double fraction;

	public Fraction() {
	}

	public Fraction(Long id, String profile, String month, double fraction) {
		super(profile, month);
		this.id = id;
		this.fraction = fraction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getFraction() {
		return fraction;
	}

	public void setFraction(double fraction) {
		this.fraction = fraction;
	}

}
