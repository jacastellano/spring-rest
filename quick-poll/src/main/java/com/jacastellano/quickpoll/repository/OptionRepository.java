package com.jacastellano.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;

import com.jacastellano.quickpoll.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long> {

}
