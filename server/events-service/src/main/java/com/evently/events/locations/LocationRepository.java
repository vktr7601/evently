package com.evently.events.locations;

import com.evently.events.locations.entities.LocationDetailsDto;
import com.evently.events.locations.entities.LocationListItemDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utils.BaseRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends BaseRepository<Location> {

    @Query("""
        SELECT loc
        FROM Location loc
        WHERE loc.name IN :names
        """)
    List<Location> findAllByNameIn(@Param("names") List<String> names);

    @Query("""
            SELECT new com.evently.events.locations.entities.LocationListItemDto(
                l.id,
                l.name,
                l.description,
                l.imageUrl
            )
            FROM Location l
        """)
    List<LocationListItemDto> findAllLocationItems();

    @Query("""
            SELECT new com.evently.events.locations.entities.LocationDetailsDto(
                l.id,
                l.name,
                l.description,
                l.imageUrl
            )
            FROM Location l
            WHERE l.id = :id
        """)
    Optional<LocationDetailsDto> findLocationDtoById(@Param("id") Long id);

    @Query("""
            SELECT l
            FROM Location l
            WHERE l.id  IN (:ids)
        """)
    List<Location> findAllByIdIn(Collection<Long> ids);
}