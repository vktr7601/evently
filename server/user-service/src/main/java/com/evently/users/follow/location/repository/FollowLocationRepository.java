package com.evently.users.follow.location.repository;

import com.evently.users.follow.location.model.FollowLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowLocationRepository extends JpaRepository<FollowLocation, Long> {
    List<FollowLocation> findAllByUserId(Long userId);
}