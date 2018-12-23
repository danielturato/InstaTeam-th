package com.danielturato.service;

import com.danielturato.dao.CollaboratorDaoImpl;
import com.danielturato.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    private CollaboratorDaoImpl collaboratorDao;

    @Override
    public List<Collaborator> findAll() {
        return collaboratorDao.findAll();
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorDao.findById(id);
    }

    @Override
    public void save(Collaborator c) {
        collaboratorDao.save(c);
    }

    @Override
    public void delete(Collaborator c) {
        collaboratorDao.delete(c);
    }
}
