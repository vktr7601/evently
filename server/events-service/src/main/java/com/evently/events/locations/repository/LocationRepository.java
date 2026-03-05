package com.evently.events.locations.repository;

import com.evently.events.locations.dto.LocationDetails;
import com.evently.events.locations.dto.LocationListItem;
import com.evently.events.locations.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("""
            SELECT loc
            FROM Location loc
            WHERE loc.name IN :names
            """)
    List<Location> findAllByNameIn(@Param("names") List<String> names);

    @Query("""
                SELECT new com.evently.events.locations.dto.LocationListItem(
                    l.id,
                    l.name,
                    l.description,
                    l.imageUrl
                )
                FROM Location l
            """)
    List<LocationListItem> findAllLocationItems();

    @Query("""
                SELECT new com.evently.events.locations.dto.LocationDetails(
                    l.id,
                    l.name,
                    l.description,
                    l.imageUrl,
                   null
                )
                FROM Location l
                WHERE l.id = :id
            """)
    Optional<LocationDetails> findLocationDtoById(@Param("id") Long id);

    @Query("""
                SELECT l
                FROM Location l
                WHERE l.id  IN (:ids)
            """)
    List<Location> findAllByIdIn(Collection<Long> ids);

    boolean existsByName(String name);

}