package com.mycarlog.mycarlog.controller;

import com.mycarlog.mycarlog.model.Brand;
import com.mycarlog.mycarlog.model.Vehicle;
import com.mycarlog.mycarlog.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    //ALL OF CRUD (Post, Get, Delete, Update) needed to work with vehicle model
    // PUBLIC ENDPOINT, everybody has access
    @GetMapping("/vehicles")
    public List<?> getVehicles(){
        return vehicleService.getVehicles();
    }

    // PUBLIC ENDPOINT, everybody has access (ID)
    @GetMapping("/vehicles/{id}")
    public Vehicle getVehicle(@PathVariable Long id){
        return vehicleService.getVehicle(id);
    }

    @GetMapping("/brands/{brandId}/models/{modelId}/vehicles")
    public List<?> getVehicles(@PathVariable Long brandId, @PathVariable Long modelId){
        return vehicleService.getVehicles(brandId, modelId);
    }

    // PUBLIC ENDPOINT, everybody has access (ID)
    @GetMapping("/brands/{brandId}/models/{modelId}/vehicles/{vehicleId}")
    public Vehicle getVehicle(@PathVariable Long brandId, @PathVariable Long modelId, @PathVariable Long vehicleId){
        return vehicleService.getVehicle(brandId, modelId, vehicleId);
    }

    //PRIVATE endpoint, registered users can have access
    @PostMapping("/brands/{brandId}/models/{modelId}/vehicles")
    public Vehicle createVehicle(@PathVariable Long brandId, @PathVariable Long modelId, @RequestBody Vehicle vehicle){
        return vehicleService.addVehicle(brandId, modelId, vehicle);
    }

    //PRIVATE endpoint, registered users can have access to their own topics only
    @PutMapping("/brands/{brandId}/models/{modelId}/vehicles/{vehicleId}")
    public Vehicle updateVehicle(@PathVariable Long brandId, @PathVariable Long modelId, @RequestBody Vehicle vehicle){
        return vehicleService.updateVehicle(brandId, modelId, vehicle);
    }

    //PRIVATE endpoint, registered users can have access to their own topics only
    @DeleteMapping("/brands/{brandId}/models/{modelId}/vehicles/{vehicleId}")
    public ResponseEntity<HashMap> deleteVehicle(@PathVariable Long brandId, @PathVariable Long modelId,
                                                 @PathVariable Long vehicleId){
        vehicleService.deleteVehicle(brandId, modelId, vehicleId);
        HashMap response = new HashMap();
        response.put("Response", "Vehicle with id " + vehicleId + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
