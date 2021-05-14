package com.mycarlog.mycarlog.controller;

import com.mycarlog.mycarlog.model.Log;
import com.mycarlog.mycarlog.model.Vehicle;
import com.mycarlog.mycarlog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LogController {

    @Autowired
    private LogService logService;

    // FULL CRUD OPERATIONS HERE FOR LOGS AS WELL

    @GetMapping("/logs")
    public List<?> getLogs(){
        return logService.getLogs();
    }

    @GetMapping("/brands/{brandId}/models/{modelId}/logs")
    public List<?> getLogs(@PathVariable Long brandId, @PathVariable Long modelId){
        return logService.getLogs(brandId, modelId);
    }

    // PUBLIC ENDPOINT, everybody has access (
    @GetMapping("/vehicle/{id}/logs")
    public List<?> getLogs(@PathVariable Long id){
        return logService.getLogs(id);
    }

    @GetMapping("/logs/{logId}")
    public Log getLog(@PathVariable Long logId){
        return logService.getLog(logId);
    }

    @GetMapping("/vehicle/{id}/logs/{logId}")
    public Log getLog(@PathVariable Long id, @PathVariable Long logId){
        return logService.getLog(id,logId);
    }

    //PRIVATE endpoint, registered users can have access
    @PostMapping("vehicle/{id}/logs")
    public Log createLog(@PathVariable Long id, @RequestBody Log log){
        return logService.addLog(id, log);
    }

    //PRIVATE endpoint, registered users can have access to their own topics only
    @PutMapping("vehicle/{id}/logs/{logId}")
    public Log updateLog(@PathVariable Long id, @PathVariable Long logId,
                                 @RequestBody Log log){
        return logService.updateLog(id, logId, log);
    }

    //PRIVATE endpoint, registered users can have access to their own topics only
    @DeleteMapping("vehicle/{id}/logs/{logId}")
    public ResponseEntity<HashMap> deleteVehicle(@PathVariable Long id, @PathVariable Long logId){
        logService.deleteLog(id, logId);
        HashMap response = new HashMap();
        response.put("Response", "Log with id " + logId + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
