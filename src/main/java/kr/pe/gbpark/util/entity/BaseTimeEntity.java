package kr.pe.gbpark.util.entity;

import jakarta.persistence.Convert;
import kr.pe.gbpark.util.converter.UTCLocalDateTimeConverter;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    @Convert(converter = UTCLocalDateTimeConverter.class)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Convert(converter = UTCLocalDateTimeConverter.class)
    private LocalDateTime lastModifiedDate;
}
