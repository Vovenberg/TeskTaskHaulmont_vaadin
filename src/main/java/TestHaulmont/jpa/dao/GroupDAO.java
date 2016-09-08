package TestHaulmont.jpa.dao;

import TestHaulmont.jpa.entity.Group;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Vladimir on 22.08.2016.
 */
public class GroupDAO extends AbstractDAO<Group> {
    private final String query="Select g from Group g";
    private final String queryForId="Select g from Group g Where g.id=?1 ";
    private final String queryForGroup="Select g from Group g Where g.number=?1 ";

    public Group getByNumber(Integer i){
        em.getTransaction().begin();
        Query q=em.createQuery(queryForGroup);
        q.setParameter(1,i);
        Group g= (Group) q.getSingleResult();
        em.getTransaction().commit();
        return g;

    }
    @Override
    public Group getById(Long i) {
        em.getTransaction().begin();
        Query q=em.createQuery(queryForId);
        q.setParameter(1,i);
        Group g= (Group) q.getSingleResult();
        em.getTransaction().commit();
        return g;

    }

    @Override
    public List<Group> getAll() {
        em.getTransaction().begin();
        List<Group> list=em.createQuery(query).getResultList();
        em.getTransaction().commit();
        return list;
    }
}
