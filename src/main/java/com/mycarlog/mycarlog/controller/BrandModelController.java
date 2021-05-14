package com.mycarlog.mycarlog.controller;

import com.mycarlog.mycarlog.model.Brand;
import com.mycarlog.mycarlog.model.Model;
import com.mycarlog.mycarlog.service.BrandModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/brands/id={brandId}")
    public Brand getBrand(@PathVariable Long brandId){
        return brandModelService.getBrand(brandId);
    }

    // PUBLIC ENDPOINT, everybody has access (NAME)
    @GetMapping("/brands/name={brandName}")
    public Brand getBrand(@PathVariable String brandName){
        return brandModelService.getBrand(brandName);
    }

    // PUBLIC ENDPOINT, everybody has access
    @GetMapping("/brands/id={brandId}/models")
    public List<?> getBrandModels(@PathVariable Long brandId){
        return brandModelService.getModels(brandId);
    }

    @GetMapping("/brands/id={brandId}/models/id={modelId}")
    public Model getBrandModel(@PathVariable Long brandId, @PathVariable Long modelId){
        return brandModelService.getModelInBrand(brandId,modelId);
    }

    @GetMapping("/brands/name={brandName}/models/name={modelName}")
    public Model getBrandModel(@PathVariable String brandName, @PathVariable String modelName){
        return brandModelService.getModelInBrand(brandName,modelName);
    }

    // PUBLIC ENDPOINT, everybody has access
    @GetMapping("/models")
    public List<?> getModels(){
        return brandModelService.getModels();
    }

    @GetMapping("/models/id={modelId}")
    public Model getModel(@PathVariable Long modelId){
        return brandModelService.getModel(modelId);
    }

    @GetMapping("/models/name={modelName}")
    public Model getModel(@PathVariable String modelName){
        return brandModelService.getModel(modelName);
    }


}
