package com.evently.events.venues;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface VenueRepository extends BaseRepository<Venue> {

    Set<Venue> findAllByNameIn(Collection<String> names);
}