package com.danielturato.model;

import com.danielturato.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    RoleServiceImpl roleService;

    @Override
    public Role convert(String id) {
        return roleService.findById(Long.parseLong(id));
    }

}
