package com.danielturato.service;

import com.danielturato.model.Collaborator;

import java.util.List;

public interface CollaboratorService {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator c);
    void delete(Collaborator c);
}
