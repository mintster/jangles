package com.nixmash.jangles.service;

import com.nixmash.jangles.dto.JanglesUser;

import java.util.List;

/**
 * Created by daveburke on 6/18/17.
 */
public interface JanglesUserService {

    List<JanglesUser> getJanglesUsers();
    List<JanglesUser> getJanglesUsers(boolean useCached);
    JanglesUser getJanglesUser(Long userID);
    JanglesUser createJanglesUser(JanglesUser janglesUser);
}
