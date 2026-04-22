package com.example.Q.datastore;

import com.example.Q.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

import org.springframework.stereotype.Repository;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
}