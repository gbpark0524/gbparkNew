package kr.pe.gbpark.util.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Converter(autoApply = true)
public class UTCLocalDateTimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {
    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.atZone(ZoneOffset.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();
    }
}
