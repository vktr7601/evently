package com.evently.events.artists;

import com.evently.events.artists.entities.ArtistDetailsdDto;
import com.evently.events.artists.entities.ArtistListItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistsRepository extends BaseRepository<Artist> {
    Artist findByName(String name);

    @Query(value = """
        SELECT  new com.evently.events.artists.entities.ArtistDetailsdDto(\
                a.id,
                a.name,
                a.bio,
                a.imageUrl)
        FROM Artist a
        WHERE a.id = :id
        """)
    Optional<ArtistDetailsdDto> findById(long id);

    @Query(value = """
        SELECT  new com.evently.events.artists.entities.ArtistListItem(\
                a.id,
                a.name,
                a.bio,
                a.imageUrl)
        FROM Artist a
        ORDER BY a.createdAt DESC
        """)
    List<ArtistListItem> findAllArtistsSortedByDateDesc();
}