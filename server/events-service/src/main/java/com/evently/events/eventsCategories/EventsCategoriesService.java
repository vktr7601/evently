package com.evently.events.eventsCategories;

import com.evently.events.category.Category;
import com.evently.events.category.CategoryRepository;
import com.evently.events.event.Event;
import com.evently.events.eventsCategories.entities.EventsCategoriesMapper;
import exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsCategoriesService {
    private final EventsCategoriesRepository eventsCategoriesRepository;
    private final CategoryRepository categoryRepository;
    private final EventsCategoriesMapper eventsCategoriesMapper;

    public void categorize(Event event, List<String> categories) {
        List<Category> fetchedCategories = categoryRepository.findAllByNameIn(categories);
        if (fetchedCategories.size() != categories.size())
            throw new ResourceNotFoundException("Category not found");

        List<EventsCategories> mapping = fetchedCategories.stream().map(x -> eventsCategoriesMapper.toEventsCategories(event, x)).toList();

        eventsCategoriesRepository.saveAll(mapping);
    }
}