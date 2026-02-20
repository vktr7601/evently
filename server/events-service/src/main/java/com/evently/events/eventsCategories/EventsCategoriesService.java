package com.evently.events.eventsCategories;

import com.evently.events.category.Category;
import com.evently.events.category.CategoryService;
import com.evently.events.category.entities.CategoryDto;
import com.evently.events.event.Event;
import com.evently.events.event.entities.EventListItemDto;
import com.evently.events.eventsCategories.entities.EventCategoriesDto;
import com.evently.events.eventsCategories.entities.EventsCategoriesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsCategoriesService {
    private final EventsCategoriesRepository eventsCategoriesRepository;
    private final EventsCategoriesMapper eventsCategoriesMapper;
    private final CategoryService categoryService;

    @Transactional
    public List<CategoryDto> categorizeEvent(Event event,
                                             List<Long> categories) {
        log.info("Categorizing event ID: {} with {} categories",
                event.getId(), categories.size());

        List<Category> fetchedCategories =
                categoryService.findAllByIdIn(categories);
        log.debug("Fetched {} categories for event ID: {}",
                fetchedCategories.size(), event.getId());

        List<EventsCategories> mapped = fetchedCategories.stream()
                .map(cat -> eventsCategoriesMapper.toEventsCategories(event,
                        cat))
                .toList();

        eventsCategoriesRepository.saveAll(mapped);
        log.info("Successfully categorized event ID: {} with categories: {}",
                event.getId(),
                fetchedCategories.stream().map(Category::getName).toList());

        return fetchedCategories.stream()
                .map(eventsCategoriesMapper::toCategoryDto)
                .toList();
    }

    @Transactional
    public List<CategoryDto> updateEventCategories(Event event,
                                                   List<Long> categories) {
        eventsCategoriesRepository.deleteEventsCategoriesByEventId(event.getId());

        return categorizeEvent(event, categories);
    }

    @Transactional
    public void addCategoriesToEventListItems(List<EventListItemDto> events) {
        List<Long> eventIds =
                events.stream().map(EventListItemDto::getId).toList();
        Map<Long, List<CategoryDto>> eventCategoryMap =
                findAllCategoriesByEventIds(eventIds);

        events.forEach(e -> e.setCategoryDtoList(eventCategoryMap.getOrDefault(e.getId(), List.of())));
    }

    public List<EventCategoriesDto> getEventsByCategoryName(String categoryName) {
        return eventsCategoriesRepository.findAllEventsByCategoryName(categoryName);
    }

    public List<CategoryDto> getEventCategories(long eventId) {
        return eventsCategoriesRepository.findEventCategoriesByEventId(eventId);
    }

    private Map<Long, List<CategoryDto>> findAllCategoriesByEventIds(List<Long> eventIds) {
        List<EventCategoriesDto> eventCategoriesDtos =
                eventsCategoriesRepository.findAllCategoriesByEventIds(eventIds);

        Map<Long, List<CategoryDto>> categoriesByEvent =
                eventCategoriesDtos.stream().collect(Collectors.groupingBy(EventCategoriesDto::getEventId, Collectors.mapping(eventsCategoriesMapper::toCategoryDto, Collectors.toList())));

        return categoriesByEvent;
    }
}