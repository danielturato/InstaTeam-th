package com.danielturato.dao;

import com.danielturato.model.Collaborator;
import org.springframework.stereotype.Repository;


@Repository
public class CollaboratorDaoImpl extends BaseDao<Collaborator> implements CollaboratorDao {

    public CollaboratorDaoImpl() {
        clazz = Collaborator.class;
    }
}
