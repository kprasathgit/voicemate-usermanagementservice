package com.voicemate.usermanagementservice.repositoryimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.voicemate.usermanagementservice.model.UserPojo;
import com.voicemate.usermanagementservice.query.UserQuery;
import com.voicemate.usermanagementservice.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public abstract class UserRepositoryImpl implements UserRepository {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private UserQuery userQuery;

	@Override
	public Optional<UserPojo> findByEmail(String email) throws Exception {

		try {
			Query nativeQuery = entityManager.createNativeQuery(userQuery.findByEmail(), UserPojo.class);
			nativeQuery.setParameter("email", nativeQuery);
			// Execute query and get the result as a UserPojo object
			UserPojo user = (UserPojo) nativeQuery.getSingleResult();
			return Optional.of(user);
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception(e.getMessage());
//			return Optional.empty();
		}

	}

}
