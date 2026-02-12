package com.evently.events.services;

import com.evently.events.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriesServicesTests {
    @Autowired
    private CategoryService categoryService;
}