package com.mycarlog.mycarlog.repository;

import com.mycarlog.mycarlog.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByVehicleId(Long vehicleId);
    Log findById0AndVehicleId(Long id, Long vehicleId);
    List<Log> findByTitleAndVehicleId(String title, Long vehicleId);
}
