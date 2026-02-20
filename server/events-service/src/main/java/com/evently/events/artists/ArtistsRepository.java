package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetails;
import com.evently.events.artists.entities.ArtistListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistsRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(String name);

    @Query(value = """
            SELECT  new com.evently.events.artists.entities.ArtistDetails(
                    a.id,
                    a.name,
                    a.bio,
                    a.imageUrl)
            FROM Artist a
            WHERE a.id = :id
            """)
    Optional<ArtistDetails> findArtistDetails(long id);

    @Query(value = """
            SELECT  new com.evently.events.artists.entities.ArtistListItem(
                    a.id,
                    a.name,
                    a.bio,
                    a.imageUrl)
            FROM Artist a
            ORDER BY a.createdAt DESC
            """)
    List<ArtistListItem> findAllArtistsSortedByDateDesc();
}