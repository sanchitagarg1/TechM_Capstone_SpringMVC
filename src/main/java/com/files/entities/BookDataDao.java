package com.files.entities;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookDataDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<BookData> getRecords(int start, int total) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM BookData ORDER BY booking_Time DESC";
        Query<BookData> query = session.createQuery(hql, BookData.class);
        query.setFirstResult(start - 1);
        query.setMaxResults(total);
        return query.list();
    }

    @Transactional(readOnly = true)
    public int countRecords() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM BookData";
        Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult().intValue();
    }
}