package com.mycarlog.mycarlog.repository;

import com.mycarlog.mycarlog.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findByName(String name);
    Model findByIdAndBrandId(Long id, Long brandId);
    Model findByNameAndBrandName(String name, String brandName);
    Model findByBrandId(Long brandId);
}
