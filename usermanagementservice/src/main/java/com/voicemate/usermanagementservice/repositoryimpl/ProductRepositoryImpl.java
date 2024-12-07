package com.voicemate.usermanagementservice.repositoryimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db2entity.Product;
import com.voicemate.usermanagementservice.query.ProductQuery;
import com.voicemate.usermanagementservice.repository.db2repo.ProductRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	/*
	 * @PersistenceContext private EntityManager db2EntityManagerFactory; // By
	 * default, it wires the EntityManager of the primary persistence // unit
	 * configured in your application.
	 */

//	@Autowired
//	@Qualifier("db2EntityManagerFactory")
//	private EntityManager db2EntityManagerFactory; //This works too.

	@PersistenceContext(unitName = "db2")
	private EntityManager db2EntityManagerFactory; // When you specify .persistenceUnit("db2") in the
													// LocalContainerEntityManagerFactoryBean, it associates the
													// EntityManagerFactory with the specific persistence unit named
													// "db2". This is particularly important when you have multiple data
													// sources or persistence units in your application.

	@Autowired
	private ProductQuery productQuery;

	public Result findByProductName(String productname) {

		try {

			Query nativeQuery = db2EntityManagerFactory.createNativeQuery(productQuery.findByProductName(),
					Product.class);
			nativeQuery.setParameter("productname", productname);
			Product product = (Product) nativeQuery.getSingleResult();
			return new Result(true, product);
		} catch (Exception e) {
			return new Result(false, e.getMessage());
		}

	}
}
