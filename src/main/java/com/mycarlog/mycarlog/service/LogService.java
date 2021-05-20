package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationForbidden;
import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.Log;
import com.mycarlog.mycarlog.model.User;
import com.mycarlog.mycarlog.model.Vehicle;
import com.mycarlog.mycarlog.repository.BrandRepository;
import com.mycarlog.mycarlog.repository.LogRepository;
import com.mycarlog.mycarlog.repository.ModelRepository;
import com.mycarlog.mycarlog.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return logRepository.findByIdAndVehicleId(logId, vehicleId);
    }

    public Log addLog(Long vehicleId, Log log){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle");
        if (!logRepository.findByTitleAndVehicleId(log.getTitle(), vehicleId).isEmpty())
            throw new InformationNotFoundException("Log with title "+ log.getTitle() + " already exists");
        Vehicle currentVehicle = vehicleRepository.findById(vehicleId).get();
        if (currentUser.getId() != currentVehicle.getUser().getId())
            throw new InformationForbidden("You cannot create logs on other vehicle pages");
        log.setVehicle(currentVehicle);
        return logRepository.save(log);
    }

    public Log updateLog(Long vehicleId, Long logId, Log log){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId, "Log");
        Vehicle currentVehicle = vehicleRepository.findById(vehicleId).get();
        if (currentUser.getId() != currentVehicle.getUser().getId())
            throw new InformationForbidden("You cannot update logs on other vehicle pages");
        Log currentLog = logRepository.findById(logId).get();
        if (currentLog.getVehicle().getId() != vehicleId)
            throw new InformationNotFoundException("Log with id " + logId + " is outside of current vehicle page.");
        if (log.getTitle() != null)   currentLog.setTitle(log.getTitle());
        if (log.getContent() != null) currentLog.setContent(log.getContent());
        if (log.getImgLink() != null) currentLog.setImgLink(log.getImgLink());
        return logRepository.save(currentLog);
    }

    public void deleteLog(Long vehicleId, Long logId){
        User currentUser = utilityService.getAuthenticatedUser();
        utilityService.errorIfRepositoryElementNotExistById(vehicleRepository, vehicleId, "Vehicle");
        utilityService.errorIfRepositoryElementNotExistById(logRepository, logId, "Log");
        Vehicle currentVehicle = vehicleRepository.findById(vehicleId).get();
        if (currentUser.getId() != currentVehicle.getUser().getId())
            throw new InformationForbidden("You cannot delete logs on other vehicle pages");
        Log currentLog = logRepository.findById(logId).get();
        if (currentLog.getVehicle().getId() != vehicleId)
            throw new InformationNotFoundException("Log with id " + logId + " is outside of current vehicle page.");
        logRepository.deleteById(logId);
    }
}
