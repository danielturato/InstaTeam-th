package com.danielturato.model;

import com.danielturato.service.CollaboratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CollaboratorConverter implements Converter<String, Collaborator> {

    @Autowired
    CollaboratorServiceImpl collaboratorService;

    @Override
    public Collaborator convert(String id) {
        return collaboratorService.findById(Long.parseLong(id));
    }
}
