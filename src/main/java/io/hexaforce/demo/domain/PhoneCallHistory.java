package io.hexaforce.demo.domain;

import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PhoneCallHistory extends BaseEntity {
	@NonNull
	private String systemTel, customerTel;
	private String contractSecret, initParam, code, status, contactId;
	private long executionInterval;
}
