package com.company.ticket_service.location;

import com.company.ticket_service.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface LocationRepository extends BaseRepository<Location> {
    Set<Location> findAllByNameIn(Collection<String> names);
}
