package com.evently.users.userPreferences;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface UserPreferencesRepository extends BaseRepository<UserPreferences> {
    @Query(value = """
            SELECT DISTINCT up.user.id
            FROM UserPreferences up
            WHERE up.eventCategoryId IN :ids
        """)
    List<Long> findUserIdsByEventCategory(@Param("ids") List<Long> ids);
}