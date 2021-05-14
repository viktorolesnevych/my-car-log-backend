package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.User;
import com.mycarlog.mycarlog.model.Vehicle;
import com.mycarlog.mycarlog.repository.BrandRepository;
import com.mycarlog.mycarlog.repository.ModelRepository;
import com.mycarlog.mycarlog.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;

    UtilityService utilityService = new UtilityService();

    public List<?> getVehicles(){
        if (vehicleRepository.findAll().isEmpty())
            throw new InformationNotFoundException("No vehicles found.");
        return vehicleRepository.findAll();
    }

    public List<?> getVehicles(Long brandId, Long modelId){
        utilityService.errorIfRepositoryElementNotExistById(brandRepository, brandId, "Brand" );
        utilityService.errorIfRepositoryElementNotExistById(modelRepository, modelId, "Model" );
        if (modelRepository.findById(modelId).get().getBrand().getId() != brandId)
            throw new InformationNotFoundException("Model belongs to a different brand.");
        return vehicleRepository.findByModelId(modelId);
    }

    public Vehicle getVehicle(Long vehicleId){
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle" );
        if (vehicleRepository.findById(vehicleId).get() == null)
            throw new InformationNotFoundException("No vehicle with " + vehicleId + " found.");
        return vehicleRepository.findById(vehicleId).get();
    }

    public Vehicle getVehicle(Long brandId, Long modelId, Long vehicleId){
        utilityService.errorIfRepositoryElementNotExistById(brandRepository, brandId, "Brand" );
        utilityService.errorIfRepositoryElementNotExistById(modelRepository, modelId, "Model" );
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle" );
        if (modelRepository.findById(modelId).get().getBrand().getId() != brandId)
            throw new InformationNotFoundException("Model belongs to a different brand.");
        if (vehicleRepository.findById(vehicleId).get().getModel().getId() != modelId)
            throw new InformationNotFoundException("Vehicle belongs to a different model.");
        return vehicleRepository.findById(vehicleId).get();
    }

    public Vehicle addVehicle(Long brandId, Long modelId, Vehicle vehicle){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(brandRepository, brandId, "Brand");
        utilityService.errorIfRepositoryElementNotExistById(modelRepository, modelId,"Model");
        if (modelRepository.findById(modelId).get().getBrand().getId() != brandId)
            throw new InformationNotFoundException("Model with ID " + modelId + " belongs to a different brand");
        vehicle.setModel(modelRepository.findById(modelId).get());
        vehicle.setUser(currentUser);
        return vehicleRepository.save(vehicle);
    }
}
