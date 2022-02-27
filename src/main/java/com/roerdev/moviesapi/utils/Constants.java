package com.roerdev.moviesapi.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    private Constants(){}

    public static final String MESSAGE = "Successful Process.";

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;

    public static List<GrantedAuthority> roles(Integer typeUser){

        String[] array = {"USER","MANAGER", "ADMIN"};
        List<GrantedAuthority> auth = new ArrayList<>();
        for (int i = 0; i < typeUser; i++){
            auth.add(new SimpleGrantedAuthority(array[i]));
        }
        return auth;
    }
}
