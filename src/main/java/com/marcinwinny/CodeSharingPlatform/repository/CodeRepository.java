package com.marcinwinny.CodeSharingPlatform.repository;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//public interface CodeRepository extends CrudRepository<Code, Long> {
// JPA repo jest preferowane, ma więcej opcji, będziesz mógł przejść na streamy w serwisach z tego co masz
public interface CodeRepository extends JpaRepository<Code, Long> {

    Optional<Code> findById(Long uuid);

    long countAllByRestrictedFalse();

}
