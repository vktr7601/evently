package com.evently.users.follow.location.repository;

import com.evently.users.follow.location.model.FollowLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowLocationRepository extends JpaRepository<FollowLocation, Long> {
    @Query("SELECT fl.locationId FROM FollowLocation fl WHERE fl.user.id = " +
            ":userId")
    List<Long> findAllByUserId(@Param("userId") Long userId);

    Optional<FollowLocation> findByUserIdAndLocationId(Long userId,
                                                       Long locationId);
}