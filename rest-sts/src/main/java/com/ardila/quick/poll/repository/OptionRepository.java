package com.ardila.quick.poll.repository;

import org.springframework.data.repository.CrudRepository;

import com.ardila.quick.poll.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long>{

}
