package com.jacastellano.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.jacastellano.quickpoll.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {

}
