package com.voicemate.usermanagementservice.entities.db2entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String productname;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = true)
	private LocalDateTime updatedAt;
}
