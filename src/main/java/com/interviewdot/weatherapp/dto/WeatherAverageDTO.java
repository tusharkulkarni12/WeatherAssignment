package com.interviewdot.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WeatherAverageDTO implements Serializable {

	private static final long serialVersionUID = 5763148931413501367L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;

	private BigDecimal temperature;

	public WeatherAverageDTO(LocalDate date, BigDecimal temperature) {
		this.date = date;
		this.temperature = temperature;

	}

	public WeatherAverageDTO() {

	}

	public void plusMap(WeatherMapTimeDTO map) {
		this.temperature = map.getMain().getTemp();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getTemperature() {
		return temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

}
