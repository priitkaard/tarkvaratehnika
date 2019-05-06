package com.qaengine;

import com.qaengine.controllers.CategoryController;
import com.qaengine.services.CategoryService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTests {
    @Autowired
    CategoryService categoryService;
    @Test
    public void categoryControllerTest() {
        CategoryController categoryController = new CategoryController(categoryService);
        assert(categoryController.listCategories().size()>0);

    }
}
