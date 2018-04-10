package com.jacastellano.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.jacastellano.quickpoll.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
