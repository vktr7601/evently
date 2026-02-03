package com.evently.events.event;

import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.List;

@Repository
public interface EventRepository extends BaseRepository<Event> {
    boolean existsByName(String name);

    default String getEntityName() {
        return "Event";
    }
}