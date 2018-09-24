package com.jeanboy.web.demo.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public abstract class BaseRepository<PK extends Serializable, T> implements BaseContract<PK, T> {

    @Resource
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    protected abstract Class<T> getClazz();

    protected T findByWhere(String name, Object value) {
//        try {
            Session session = getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClazz());
            Root<T> from = query.from(getClazz());
            query.select(from);
            query.where(builder.equal(from.get(name), value));
//            return session.createQuery(query).getSingleResult();
            Optional<T> optional = session.createQuery(query).uniqueResultOptional();
            return optional.orElse(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    protected T findByWhere(Map<String, Object> whereParams) {
//        try {
            Session session = getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClazz());
            Root<T> from = query.from(getClazz());
            query.select(from);
            List<Predicate> predicateList = new ArrayList<>();
            for (String key : whereParams.keySet()) {
                predicateList.add(builder.equal(from.get(key), whereParams.get(key)));
            }
            Predicate[] predicateArray = new Predicate[predicateList.size()];
            for (int i = 0; i < predicateList.size(); i++) {
                predicateArray[i] = predicateList.get(i);
            }
            query.where(builder.and(predicateArray));
//            return session.createQuery(query).getSingleResult();
            Optional<T> optional = session.createQuery(query).uniqueResultOptional();
            return optional.orElse(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    @Override
    public T load(PK id) {
        return getCurrentSession().load(getClazz(), id);
    }

    @Override
    public T get(PK id) {
        return getCurrentSession().get(getClazz(), id);
    }

    @Override
    public List<T> findAll() {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getClazz());
        Root<T> from = query.from(getClazz());
        query.select(from);
//        query.orderBy(builder.asc(from.get("age")));
        return session.createQuery(query).getResultList();
    }

    @Override
    public PK save(T entity) {
        return (PK) getCurrentSession().save(entity);
    }

    @Override
    public void persist(T entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public T merge(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void flush() {
        getCurrentSession().flush();
    }

    @Override
    public void delete(PK id) {
        T model = load(id);
        getCurrentSession().delete(model);
    }
}
