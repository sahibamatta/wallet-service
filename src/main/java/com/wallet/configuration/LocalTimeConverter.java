package com.wallet.configuration;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;

public class LocalTimeConverter implements AttributeConverter<LocalTime,Time> {
	public Time convertToDatabaseColumn(LocalTime attribute) {
		return attribute != null ? Time.valueOf(attribute) : null;
	}

	public LocalTime convertToEntityAttribute(Time dbData) {
		return dbData != null ? dbData.toLocalTime() : null;
	}
}
