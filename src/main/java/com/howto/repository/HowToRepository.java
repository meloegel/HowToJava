package com.howto.repository;

import com.howto.models.HowTo;
import org.springframework.data.repository.CrudRepository;

public interface HowToRepository extends CrudRepository<HowTo, Long> {
}
