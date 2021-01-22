package com.marcinwinny.CodeSharingPlatform.repository;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CodeRepository extends CrudRepository<Code, Long> {

    Optional<Code> findById(String uuid);

}
