package com.mycarlog.mycarlog.utilities;


import com.mycarlog.mycarlog.model.User;

// Class Object used for setting ADMIN user
public class UserAdminHint extends User {

    private String whoShotFirst;

    public UserAdminHint() {
    }

    public User toUser(Boolean admin){
        User returnUser = new User(this.getId(), this.getUserName(), this.getEmailAddress(), this.getPassword(),getPasswordChangedTime());
        returnUser.setAdmin(admin);
        return returnUser;
    }

    public String getWhoShotFirst() {
        return this.whoShotFirst;
    }
}
