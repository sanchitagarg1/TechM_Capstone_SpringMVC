package com.files.entities;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class Datadao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Transactional(readOnly = true)
    public Data checklogin(final String email, final String password) {
        return hibernateTemplate.execute(new HibernateCallback<Data>() {
            @Override
            public Data doInHibernate(Session session) {
                String hql = "FROM Data WHERE email = :email AND password = :password";
                Query<Data> query = session.createQuery(hql, Data.class);
                query.setParameter("email", email);
                query.setParameter("password", password);
                return query.uniqueResult();
            }
        });
    }

    @Transactional(readOnly = true)
    public Data searchUser(final String email) {
        return hibernateTemplate.execute(new HibernateCallback<Data>() {
            @Override
            public Data doInHibernate(Session session) {
                String hql = "FROM Data WHERE email = :email";
                Query<Data> query = session.createQuery(hql, Data.class);
                query.setParameter("email", email);
                return query.uniqueResult();
            }
        });
    }

    @Transactional
    public Integer insertRecord(Data data) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                session.save(data);
                return 1;
            }
        });
    }

    @Transactional
    public int deleteUser(final String email) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                String hql = "DELETE FROM Data WHERE LOWER(email) = LOWER(:email)";
                Query<?> query = session.createQuery(hql);
                query.setParameter("email", email.toLowerCase());
                return query.executeUpdate();
            }
        });
    }

    @Transactional
    public int updateRecord(final Data data) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                session.update(data);
                return 1;
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Data> getRecords(final int start, final int total) {
        return hibernateTemplate.execute(new HibernateCallback<List<Data>>() {
            @Override
            public List<Data> doInHibernate(Session session) {
                String hql = "FROM Data";
                Query<Data> query = session.createQuery(hql, Data.class);
                query.setFirstResult(start);
                query.setMaxResults(total);
                return query.list();
            }
        });
    }

    @Transactional(readOnly = true)
    public int countRecords() {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                String hql = "SELECT COUNT(*) FROM Data";
                Query<Long> query = session.createQuery(hql, Long.class);
                return query.uniqueResult().intValue();
            }
        });
    }

    @Transactional
    public int updatePass(final String password, final String email) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                String hql = "UPDATE Data SET password = :password WHERE email = :email";
                Query<?> query = session.createQuery(hql);
                query.setParameter("password", password);
                query.setParameter("email", email);
                return query.executeUpdate();
            }
        });
    }

    @Transactional
    public int UploadImage(final String email, final String imageFileName) {
        return hibernateTemplate.execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) {
                String hql = "UPDATE Data SET imageFileName = :imageFileName WHERE email = :email";
                Query<?> query = session.createQuery(hql);
                query.setParameter("imageFileName", imageFileName);
                query.setParameter("email", email);
                return query.executeUpdate();
            }
        });
    }
}
