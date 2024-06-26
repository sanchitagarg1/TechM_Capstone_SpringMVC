package com.files.entities;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class Datadao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(readOnly = true)
    public Data checklogin(String email, String password) {
        String hql = "FROM Data WHERE email = :email AND password = :password";
        Query<Data> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Data.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Data searchUser(String email) {
        String hql = "FROM Data WHERE email = :email";
        Query<Data> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Data.class);
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Transactional
    public Integer insertRecord(Data data) {
        hibernateTemplate.save(data);
        return 1;
    }

    @Transactional
    public int DeleteUser(String email) {
        String hql = "DELETE FROM Data WHERE LOWER(email) = LOWER(:email)";
        Query<?> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("email", email.toLowerCase());
        return query.executeUpdate();
    }

    @Transactional
    public int updaterecord(Data data) {
        hibernateTemplate.update(data);
        return 1;
    }

    @Transactional(readOnly = true)
    public List<Data> getRecords(int start, int total) {
        String hql = "FROM Data";
        Query<Data> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Data.class);
        query.setFirstResult(start - 1);
        query.setMaxResults(total);
        return query.list();
    }

    @Transactional(readOnly = true)
    public int countRecords() {
        String hql = "SELECT COUNT(*) FROM Data";
        Query<Long> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql, Long.class);
        return query.uniqueResult().intValue();
    }

    @Transactional
    public int UpdatePass(String password, String email) {
        String hql = "UPDATE Data SET password = :password WHERE email = :email";
        Query<?> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("password", password);
        query.setParameter("email", email);
        return query.executeUpdate();
    }

    @Transactional
    public int UploadImage(String email, String imageFileName) {
        String hql = "UPDATE Data SET imageFileName = :imageFileName WHERE email = :email";
        Query<?> query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("imageFileName", imageFileName);
        query.setParameter("email", email);
        return query.executeUpdate();
    }
}
