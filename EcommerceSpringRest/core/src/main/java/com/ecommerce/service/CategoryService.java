package com.ecommerce.service;

import org.springframework.stereotype.Service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService extends BaseService<Category, Long, CategoryRepository>{

}
