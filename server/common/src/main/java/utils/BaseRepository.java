package utils;

import exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {
    default T findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("%s with id : %d not found".formatted(getEntityName(), id)));
    }

    default String getEntityName() {
        return "Resource";
    }
}