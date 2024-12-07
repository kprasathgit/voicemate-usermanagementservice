package com.voicemate.usermanagementservice.repository.db2repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voicemate.usermanagementservice.entities.db2entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
