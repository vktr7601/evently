package com.evently.users.locationFollow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationToFollowRepository extends JpaRepository<LocationFollow,
        Long> {

    List<LocationFollow> findAllByUserId(Long userId);
}