package com.files.entities;

import java.util.List;
import org.hibernate.query.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class Datadao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Data checklogin(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Data WHERE email = :email AND password = :password";
        Query<Data> query = session.createQuery(hql, Data.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Data searchUser(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Data WHERE email = :email";
        Query<Data> query = session.createQuery(hql, Data.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Transactional
    public Integer insertRecord(Data data) {
        Session session = sessionFactory.getCurrentSession();
        session.save(data);
        return 1;
    }

    @Transactional
    public int DeleteUser(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM Data WHERE LOWER(email) = LOWER(:email)";
        Query<?> query = session.createQuery(hql);
        query.setParameter("email", email.toLowerCase());
        return query.executeUpdate();
    }

    @Transactional
    public int updaterecord(Data data) {
        Session session = sessionFactory.getCurrentSession();
        session.update(data);
        return 1;
    }

    @Transactional(readOnly = true)
    public List<Data> getRecords(int start, int total) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Data";
        Query<Data> query = session.createQuery(hql, Data.class);
        query.setFirstResult(start - 1);
        query.setMaxResults(total);
        return query.list();
    }

    @Transactional(readOnly = true)
    public int countRecords() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM Data";
        Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult().intValue();
    }

    @Transactional
    public int UpdatePass(String password, String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Data SET password = :password WHERE email = :email";
        Query<?> query = session.createQuery(hql);
        query.setParameter("password", password);
        query.setParameter("email", email);
        return query.executeUpdate();
    }

    @Transactional
    public int UploadImage(String email, String imageFileName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Data SET imageFileName = :imageFileName WHERE email = :email";
        Query<?> query = session.createQuery(hql);
        query.setParameter("imageFileName", imageFileName);
        query.setParameter("email", email);
        return query.executeUpdate();
    }
}