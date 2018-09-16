package hc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MeterReading extends MonthProfile {

	@Id
	@GeneratedValue
	private Long id;

	private Long meterId;

	private int meterReading;

	public MeterReading() {
	}

	public MeterReading(Long id, Long meterId, String profile, String month, int meterReading) {
		super(profile, month);
		this.id = id;
		this.meterId = meterId;
		this.meterReading = meterReading;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public int getMeterReading() {
		return meterReading;
	}

	public void setMeterReading(int meterReading) {
		this.meterReading = meterReading;
	}

}
