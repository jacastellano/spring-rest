package com.jacastellano.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.jacastellano.quickpoll.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByUsername(String username);
}
