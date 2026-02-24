package com.evently.users.follow.category.repository;

import com.evently.users.follow.category.model.FollowCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowCategoryRepository extends JpaRepository<FollowCategory, Long> {
    @Query(value = """
                SELECT DISTINCT up.user.id
                FROM FollowCategory up
                WHERE up.categoryId IN (:ids)
            """)
    List<Long> findFollowersByCategories(@Param("ids") List<Long> ids);
}