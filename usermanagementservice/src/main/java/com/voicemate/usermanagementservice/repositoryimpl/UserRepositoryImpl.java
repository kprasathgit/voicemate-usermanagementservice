package com.voicemate.usermanagementservice.repositoryimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.voicemate.usermanagementservice.common.Result;
import com.voicemate.usermanagementservice.entities.User;
import com.voicemate.usermanagementservice.query.UserQuery;
import com.voicemate.usermanagementservice.repositorycustom.UserRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public  class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserQuery userQuery;

	@Override
	public Result findByEmail(String email) throws Exception {

		try {
			Query jpqlQuery = entityManager.createNativeQuery(userQuery.findByEmail(), User.class);
			jpqlQuery.setParameter("email", jpqlQuery);
			// Execute query and get the result as a UserPojo object
			User user = (User) jpqlQuery.getSingleResult();
			return new Result(true, user);
		} catch (Exception e) {
			return new Result(false, e.getMessage());
		}
		
	}

	
}
