package com.danielturato.dao;

import com.danielturato.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();
    Role findById(Long id);
    void save(Role role);
    void delete(Role role);
}
