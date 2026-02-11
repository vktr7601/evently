package com.evently.events.artists;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

@Repository
public interface ArtistsRepository extends BaseRepository<Artist> {
    Artist findByName(String name);
}