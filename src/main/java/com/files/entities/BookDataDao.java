package com.files.entities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookDataDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public BookDataDao(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public List<BookData> getRecords(int start, int total) {
        String hql = "FROM BookData ORDER BY booking_Time DESC";
        return hibernateTemplate.execute(session -> {
            return session.createQuery(hql, BookData.class)
                          .setFirstResult(start)
                          .setMaxResults(total)
                          .list();
        });
    }

    public int countRecords() {
        String hql = "SELECT COUNT(*) FROM BookData";
        return hibernateTemplate.execute(session -> {
            return session.createQuery(hql, Long.class)
                          .uniqueResult()
                          .intValue();
        });
    }
}
