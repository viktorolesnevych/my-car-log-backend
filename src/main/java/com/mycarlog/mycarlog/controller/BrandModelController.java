package com.mycarlog.mycarlog.controller;

import com.mycarlog.mycarlog.model.Brand;
import com.mycarlog.mycarlog.service.BrandModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandModelController {

    @Autowired
    private BrandModelService brandModelService;

    // There is only need for GET method within brand and model controllers. User would not be able to do any changes
    // with these tables. Only DB admin can do it, as they are static and should be seeded in the beginning

    // PUBLIC ENDPOINT, everybody has access
    @GetMapping("/brands")
    public List<?> getBrands(){
        return brandModelService.getBrands();
    }

    // PUBLIC ENDPOINT, everybody has access (ID)
    @GetMapping("/brands/{brandId}")
    public Brand getBrand(@PathVariable Long brandId){
        return brandModelService.getBrand(brandId);
    }

    // PUBLIC ENDPOINT, everybody has access (NAME)
    @GetMapping("/brands/name={brandName}")
    public Brand getBrand(@PathVariable String brandName){
        return brandModelService.getBrand(brandName);
    }
}
