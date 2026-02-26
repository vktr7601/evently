package com.evently.users.follow.artist.repository;

import com.evently.users.follow.artist.model.FollowArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowArtistRepository extends JpaRepository<FollowArtist,
        Long> {
    @Query(value = """
                SELECT DISTINCT af.user.id
                FROM FollowArtist af
                WHERE af.artistId = :id
            """)
    List<Long> findFollowersByArtist(@Param("ids") Long id);

    @Query("""
                SELECT fa.artistId
                FROM FollowArtist fa
                WHERE fa.user.id = :userId
            """)
    List<Long> findAllByUserId(@Param("userId") long userId);

    @Query("""
                SELECT af
                FROM FollowArtist af
                WHERE af.user.id = :userId AND af.artistId = :artistId
            """)
    Optional<FollowArtist> findByArtistIdAndUserId(@Param("artistId") Long artistId, @Param("userId") Long userId);
}