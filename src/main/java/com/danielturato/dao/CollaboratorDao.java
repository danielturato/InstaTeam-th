package com.danielturato.dao;

import com.danielturato.model.Collaborator;

import java.util.List;

public interface CollaboratorDao {
    List<Collaborator> findAll();
    Collaborator findById(Long id);
    void save(Collaborator c);
    void delete(Collaborator c);
}
