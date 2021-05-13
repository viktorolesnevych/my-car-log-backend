package com.mycarlog.mycarlog.repository;

import com.mycarlog.mycarlog.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByModelName(String name);
}
