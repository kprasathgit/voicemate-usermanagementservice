package com.voicemate.usermanagementservice.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.server.ResponseStatusException;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db2entity.Product;
import com.voicemate.usermanagementservice.service.ProductService;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin("*")
@SessionScope
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/findbyproductname")
	public ResponseEntity<?> findByProductName(@RequestParam String productname) throws Exception {

		try {
			Result result = productService.findByProductName(productname);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}

	}

	@PostMapping("/saveproduct")
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {

		try {
			product.setCreatedAt(LocalDateTime.now());
			Product savedProduct = productService.saveProduct(product);
			return new ResponseEntity<Product>(savedProduct, HttpStatus.OK);

		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}
}
