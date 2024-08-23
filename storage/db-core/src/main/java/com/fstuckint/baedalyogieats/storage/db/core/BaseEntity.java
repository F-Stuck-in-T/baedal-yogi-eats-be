package com.fstuckint.baedalyogieats.storage.db.core;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import org.hibernate.type.SqlTypes;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.BINARY)
	@Column(columnDefinition = "BINARY(16)")
	private UUID uuid;

	@CreationTimestamp
	@Column
	private LocalDateTime createdAt;

	@Column(length = 100)
	private String createdBy;

	@UpdateTimestamp
	@Column
	private LocalDateTime updatedAt;

	@Column(length = 100)
	private String updatedBy;

	private LocalDateTime deletedAt;

	@Column(length = 100)
	private String deletedBy;

	public UUID getUuid() {
		return uuid;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

}
