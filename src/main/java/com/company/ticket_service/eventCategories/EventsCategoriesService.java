package com.company.ticket_service.eventCategories;

import java.util.List;

import com.company.ticket_service.category.Category;
import com.company.ticket_service.category.CategoryMapper;
import com.company.ticket_service.category.CategoryRepository;
import com.company.ticket_service.category.entities.CategoryDto;
import com.company.ticket_service.core.exceptions.ResourceNotFoundException;
import com.company.ticket_service.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventsCategoriesService {
  private final EventsCategoriesRepository eventsCategoriesRepository;
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional
  @CacheEvict(value = "events", key = "#event.id")
  public void categorize(Event event, List<String> categoryNames) {
    List<Category> fetchedCategories = categoryRepository.findAllByNameIn(categoryNames);
    if (fetchedCategories.size() != categoryNames.size())
      throw new ResourceNotFoundException("Category not found");

    List<EventsCategories> mapping =
        fetchedCategories.stream()
            .map(
                category -> {
                  EventsCategories eventsCategories = new EventsCategories();
                  eventsCategories.setEvent(event);
                  eventsCategories.setCategory(category);
                  return eventsCategories;
                })
            .toList();

    eventsCategoriesRepository.saveAll(mapping);
  }

  public List<CategoryDto> getEventCategories(long eventId) {
    return eventsCategoriesRepository.findAllByEventId(eventId).stream()
        .map(eventCategory -> categoryMapper.toDto(eventCategory.getCategory()))
        .toList();
  }
}