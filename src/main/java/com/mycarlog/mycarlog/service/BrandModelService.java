package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.Brand;
import com.mycarlog.mycarlog.model.Model;
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

    // Return a list of models if exists
    public List<?> getModels() {
        if (modelRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No models were found!");
        } else
            return modelRepository.findAll();
    }

    // Return a list of models for brand
    public List<?> getModels(Long brandId) {
        if (modelRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No models were found!");
        } else
            return modelRepository.findAll();
    }

    // Return a model if exists by ID
    public Model getModel(Long id) {
        if (modelRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No models were found!");
        }else{
            utility.errorIfRepositoryElementNotExistById(modelRepository, id, "Model");
            return modelRepository.findById(id).get();
        }
    }

    // Return a model if exists by NAME
    public Model getModel(String name) {
        if (modelRepository.findAll().isEmpty()) {
            throw new InformationNotFoundException("No models were found!");
        }else{
            if(modelRepository.findByName(name) == null) {
                throw new InformationNotFoundException("Model with name " + name + " doesn't exist");
            }else
                return modelRepository.findByName(name);
        }
    }

    // Return a model if exists by ID for brand
    public Model getModelInBrand(Long id, Long brandId) {
        utility.errorIfRepositoryElementNotExistById(brandRepository, brandId, "Brand");
        utility.errorIfRepositoryElementNotExistById(modelRepository, id, "Model");
        Model returnModel = modelRepository.findByIdAndBrandId(id,brandId);
        if (returnModel != null)
            return returnModel;
        else
            throw new InformationNotFoundException("Model is in a different brand");
    }

    // Return a model if exists by name for brand name
    public Model getModelInBrand(String name, String brandName) {
        utility.errorIfRepositoryElementsNotExist(brandRepository, "Brand");
        utility.errorIfRepositoryElementsNotExist(modelRepository, "Model");
        Model returnModel = modelRepository.findByNameAndBrandName(name, brandName);
        if (returnModel != null)
            return returnModel;
        else if (modelRepository.findByName(name) != null)
            throw new InformationNotFoundException("Model is in a different brand");
            else
            throw new InformationNotFoundException("Model doesn't exist");
    }


}
