package com.tpe.repository;

import com.tpe.domain.Owner;
import com.tpe.dto.OwnerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {


    //1-c
    boolean existsByEmail(String email);

    //3-c
    @Query("SELECT new com.tpe.dto.OwnerDTO(o) FROM Owner o WHERE o.id=?1")
    Optional<OwnerDTO> findDtoById(Long id);
}