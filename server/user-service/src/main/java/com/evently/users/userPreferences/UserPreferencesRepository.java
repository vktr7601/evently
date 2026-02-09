package com.evently.users.userPreferences;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface UserPreferencesRepository extends BaseRepository<UserPreferences> {
    @Query(value = """
        SELECT up.eventCategoryId FROM UserPreferences  as up WHERE  up.eventCategoryId=:eventCategoryId
        """)
    List<Long> getUserPreferencesByEventCategoryId(@Param("eventCategoryId") Long eventCategoryId);
}