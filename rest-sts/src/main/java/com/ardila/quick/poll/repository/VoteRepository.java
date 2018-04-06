package com.ardila.quick.poll.repository;

import org.springframework.data.repository.CrudRepository;

import com.ardila.quick.poll.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long>{

}
