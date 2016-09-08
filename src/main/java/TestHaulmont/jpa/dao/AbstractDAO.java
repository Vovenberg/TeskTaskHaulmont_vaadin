package TestHaulmont.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Vladimir on 22.08.2016.
 */
public abstract class AbstractDAO<T>{

    public EntityManager em= Persistence.createEntityManagerFactory("main").createEntityManager();

    public abstract T getById(Long i);
    public abstract List<T> getAll();

    public void add(T o) {
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

    public void delete(T o){
        em.getTransaction().begin();
        em.remove(em.merge(o));
        em.getTransaction().commit();
    }

    public void update(T o){
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
    }

}
