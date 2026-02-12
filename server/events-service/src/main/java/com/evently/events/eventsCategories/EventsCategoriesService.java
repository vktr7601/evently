package com.evently.events.eventsCategories;

import com.evently.events.category.Category;
import com.evently.events.category.CategoryRepository;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.event.entities.EventListItemDto;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import com.evently.events.eventsCategories.entities.EventsCategoriesMapper;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsCategoriesService {
    private final EventsCategoriesRepository eventsCategoriesRepository;
    private final CategoryRepository categoryRepository;
    private final EventsCategoriesMapper eventsCategoriesMapper;

    public List<Category> categorize(Event event, List<String> categories) {
        List<Category> fetchedCategories = categoryRepository.findAllByNameIn(categories);
        if (fetchedCategories.size() != categories.size())
            throw new ResourceNotFoundException("Category not found");

        List<EventsCategories> mapping = fetchedCategories.stream().map(x -> eventsCategoriesMapper.toEventsCategories(event, x)).toList();

        eventsCategoriesRepository.saveAll(mapping);

        return fetchedCategories;
    }

    @Transactional
    public void addCategoriesToEventListItems(List<EventListItemDto> events) {
        List<Long> eventIds = events.stream().map(EventListItemDto::getId).toList();
        Map<Long, List<CategoryDto>> eventCategoryMap = findAllCategoriesByEventIds(eventIds);

        events.forEach(e -> e.setCategoryDtoList(
            eventCategoryMap.getOrDefault(e.getId(), List.of())
        ));
    }

    public List<EventCategoriesDto> getEventsByCategoryName(String categoryName) {
        return eventsCategoriesRepository.findAllEventsByCategoryName(categoryName);
    }

    public List<CategoryDto> getEventCategories(long eventId) {
        return eventsCategoriesRepository.findEventCategoriesByEventId(eventId);
    }

    private Map<Long, List<CategoryDto>> findAllCategoriesByEventIds(List<Long> eventIds) {
        List<EventCategoriesDto> eventCategoriesDtos = eventsCategoriesRepository.findAllCategoriesByEventIds(eventIds);

        Map<Long, List<CategoryDto>> categoriesByEvent = eventCategoriesDtos.stream().collect(Collectors.groupingBy(EventCategoriesDto::getEventId, Collectors.mapping(eventsCategoriesMapper::toCategoryDto, Collectors.toList())));

        return categoriesByEvent;
    }
}