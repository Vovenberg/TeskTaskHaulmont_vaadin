package TestHaulmont.jpa.dao;

import TestHaulmont.jpa.entity.Student;

import java.util.List;

/**
 * Created by Vladimir on 22.08.2016.
 */
public class StudentDAO extends AbstractDAO<Student> {
    private final String query="Select s from Student s";

    @Override
    public Student getById(Long i) {
        return null;
    }

    @Override
    public List<Student> getAll() {
        em.getTransaction().begin();
        List<Student> list=em.createQuery(query).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
