package com.evently.events.event;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

@Repository
public interface EventRepository extends BaseRepository<Event> {
    boolean existsByName(String name);

    default String getEntityName() {
        return "Event";
    }
}