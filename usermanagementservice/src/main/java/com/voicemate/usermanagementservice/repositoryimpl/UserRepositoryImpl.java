package com.voicemate.usermanagementservice.repositoryimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.db1entity.User;
import com.voicemate.usermanagementservice.query.UserQuery;
import com.voicemate.usermanagementservice.repository.db1repo.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {

	/*
	 * @PersistenceContext private EntityManager db1EntityManagerFactory; // By
	 * default, it wires the EntityManager of the primary persistence // unit
	 * configured in your application.
	 */

	@PersistenceContext(unitName = "db1")
	private EntityManager db1EntityManagerFactory; // When you specify .persistenceUnit("db1") in the
													// LocalContainerEntityManagerFactoryBean, it associates the
													// EntityManagerFactory with the specific persistence unit named
													// "db1". This is particularly important when you have multiple data
													// sources or persistence units in your application.
	@Autowired
	private UserQuery userQuery;

	public Result findByEmail(String email) throws Exception {

		try {
			Query jpqlQuery = db1EntityManagerFactory.createNativeQuery(userQuery.findByEmail(), User.class);
			jpqlQuery.setParameter("email", jpqlQuery);
			// Execute query and get the result as a UserPojo object
			User user = (User) jpqlQuery.getSingleResult();
			return new Result(true, user);
		} catch (Exception e) {
			return new Result(false, e.getMessage());
		}

	}

}
