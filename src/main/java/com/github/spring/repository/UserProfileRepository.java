package com.github.spring.repository;

import com.github.spring.model.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserProfileRepository {

    private List<UserProfile> userProfiles = new ArrayList<>();

    public UserProfile add(UserProfile userProfile) {
        userProfile.setId((long) (userProfiles.size()+1));
        userProfiles.add(userProfile);
        return userProfile;
    }

    public UserProfile findById(Long id) {
        Optional<UserProfile> employee = userProfiles.stream().filter(a -> a.getId().equals(id)).findFirst();
        return employee.orElse(null);
    }

    public List<UserProfile> findAll() {
        return userProfiles;
    }

}
