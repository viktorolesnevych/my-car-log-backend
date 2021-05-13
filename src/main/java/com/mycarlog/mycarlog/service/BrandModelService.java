package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.Brand;
import com.mycarlog.mycarlog.repository.BrandRepository;
import com.mycarlog.mycarlog.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandModelService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    private UtilityService utility = new UtilityService();

    // Return a list of brands if exists
    public List<?> getBrands() {
        if (brandRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No brands were found!");
        } else
            return brandRepository.findAll();
    }

    // Return a brand if exists by ID
    public Brand getBrand(Long id) {
        if (brandRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No brands were found!");
        }else{
            utility.errorIfRepositoryElementNotExistById(brandRepository, id, "Brand");
            return brandRepository.findById(id).get();
        }
    }

    // Return a brand if exists by NAME
    public Brand getBrand(String name) {
        if (brandRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No brands were found!");
        }else{
            if(brandRepository.findByName(name) == null) {
                throw new InformationNotFoundException("Brand with name " + name + " doesn't exist");
            }else
                return brandRepository.findByName(name);
        }
    }
}
