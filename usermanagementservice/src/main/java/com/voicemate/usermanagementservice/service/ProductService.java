package com.voicemate.usermanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db2entity.Product;
import com.voicemate.usermanagementservice.repository.db2repo.ProductRepository;
import com.voicemate.usermanagementservice.repository.db2repo.ProductRepositoryCustom;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductRepositoryCustom productRepositoryCustom;

	public Result findByProductName(String productname) throws Exception {

		return productRepositoryCustom.findByProductName(productname);
	}

	public Product saveProduct(Product product) {

		return productRepository.save(product);
	}

}
