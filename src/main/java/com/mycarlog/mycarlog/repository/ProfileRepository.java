package com.mycarlog.mycarlog.repository;


import com.mycarlog.mycarlog.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
