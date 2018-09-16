package hc.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MonthProfile {

	protected String profile;

	protected String month;

	public MonthProfile() {
	}

	public MonthProfile(String profile, String month) {
		this.profile = profile;
		this.month = month;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
