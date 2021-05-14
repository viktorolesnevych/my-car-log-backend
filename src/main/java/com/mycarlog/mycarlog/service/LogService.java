package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.Log;
import com.mycarlog.mycarlog.repository.BrandRepository;
import com.mycarlog.mycarlog.repository.LogRepository;
import com.mycarlog.mycarlog.repository.ModelRepository;
import com.mycarlog.mycarlog.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelRepository modelRepository;

    UtilityService utilityService = new UtilityService();

    public List<?> getLogs(){
        return logRepository.findAll();
    }

    public List<?> getLogs(Long brandId, Long modelId){
        utilityService.errorIfRepositoryElementNotExistById(brandRepository, brandId, "Brand");
        utilityService.errorIfRepositoryElementNotExistById(modelRepository, modelId, "Model");
        if (modelRepository.findById(modelId).get().getBrand().getId() != brandId)
            throw new InformationNotFoundException("Model belongs to a different brand.");
        List<Log> returnList = logRepository.findAll().stream().filter(log ->
                log.getVehicle().getModel().getId() == modelId).collect(Collectors.toList());
        if (returnList.isEmpty())
            throw new InformationNotFoundException("No logs found for current model.");
        return returnList;
    }

    public List<?> getLogs(Long vehicleId){
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle");
        List<Log> returnList = logRepository.findAllByVehicleId(vehicleId);
        if (returnList.isEmpty())
            throw new InformationNotFoundException("No logs found for current vehicle.");
        return returnList;
    }

    public Log getLog(Long logId){
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        return logRepository.findById(logId).get();
    }

    public Log getLog(Long vehicleId, Long logId){
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId,"Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId,"Log");
        return logRepository.findById0AndVehicleId(logId, vehicleId);
    }
}
