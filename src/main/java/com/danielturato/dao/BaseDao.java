package com.danielturato.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

public abstract class BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    Class<T> clazz;

    public List<T> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        criteria.from(clazz);
        List<T> list = session.createQuery(criteria).getResultList();
        session.close();

        return list;
    }

    public T findById(Long id) {
        Session session = sessionFactory.openSession();
        T type = session.get(clazz, id);
        session.close();

        return type;
    }

    public void save(T t) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(T t) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }
}
