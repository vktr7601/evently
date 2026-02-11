package com.evently.events.venues;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface LocationRepository extends BaseRepository<Location> {

    Set<Location> findAllByNameIn(Collection<String> names);
}