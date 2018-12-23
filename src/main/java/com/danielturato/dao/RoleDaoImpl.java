package com.danielturato.dao;

import com.danielturato.model.Role;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDao<Role> implements RoleDao  {

    public RoleDaoImpl() {
        clazz = Role.class;
    }

}
