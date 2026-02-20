package com.evently.users.categoryFollow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryFollowRepository extends JpaRepository<CategoryFollow, Long> {
    @Query(value = """
            SELECT DISTINCT up.user.id
            FROM CategoryFollow up
            WHERE up.categoryId IN (:ids)
        """)
    List<Long> findFollowersByCategories(@Param("ids") List<Long> ids);
}