package com.nixmash.jangles.service.base;

import com.google.inject.ImplementedBy;
import com.nixmash.jangles.service.JanglesApiServiceImpl;

@ImplementedBy(JanglesApiServiceImpl.class)
public interface JanglesApiService {

    String showConfiguration();
    String sayHello();
}
