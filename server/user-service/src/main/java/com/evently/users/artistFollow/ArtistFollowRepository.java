package com.evently.users.artistFollow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistFollowRepository extends JpaRepository<ArtistFollow, Long> {
    @Query(value = """
            SELECT DISTINCT af.user.id
            FROM ArtistFollow af
            WHERE af.artistId = :id
        """)
    List<Long> findFollowersByArtist(@Param("ids") Long id);


    @Query("""
            SELECT af
            FROM ArtistFollow af
            WHERE af.user.id = :userId AND af.artistId = :artistId
        """)
    Optional<ArtistFollow> findByArtistIdAndUserId(@Param("userId") Long userId, @Param("artistId") Long artistId);
}