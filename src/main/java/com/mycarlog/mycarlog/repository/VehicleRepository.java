package com.mycarlog.mycarlog.repository;

import com.mycarlog.mycarlog.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByModelName(String name);
    List<Vehicle> findByModelId(Long modelId);

}
