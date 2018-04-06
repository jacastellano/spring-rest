package com.ardila.quick.poll.repository;

import org.springframework.data.repository.CrudRepository;

import com.ardila.quick.poll.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long>{

}
