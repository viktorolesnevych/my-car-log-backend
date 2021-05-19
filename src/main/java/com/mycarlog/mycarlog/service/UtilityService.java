package com.mycarlog.mycarlog.service;

import com.mycarlog.mycarlog.exception.InformationForbidden;
import com.mycarlog.mycarlog.exception.InformationNotFoundException;
import com.mycarlog.mycarlog.model.User;
import com.mycarlog.mycarlog.security.MyUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;

//Here different helpful methods will be stored which can be used all around the services
public class UtilityService {

    // Returns AUTHENTICATED through JWT Token User
    public User getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
            throw new InformationForbidden("Forbidden");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails.getUser().isPasswordExpired())
            throw new InformationForbidden("Password is expired. Please, change your password");
        return userDetails.getUser();
    }

    // Checks if USER is ADMIN
    public boolean isUserAdmin(User user) {
        return user != null ? user.getIsAdmin() : false;
    }


    // Will throw an error if some data is set incorrectly
    public void errorIfRepositoryElementNotExistById(JpaRepository repository, Long id, String elementName) {
        if (repository.findAll().isEmpty())
            throw new InformationNotFoundException("No " + elementName + "s were found");
        if (!repository.findById(id).isPresent())
            throw new InformationNotFoundException(elementName + " with ID " + id + " doesn't exist!");
    }

    // Will throw an error if some data is set incorrectly
    public void errorIfRepositoryElementsNotExist(JpaRepository repository, String elementName) {
        if (repository.findAll() == null)
            throw new InformationNotFoundException("No any " + elementName + "s were found");
    }
}
