package org.tutorial.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial.dao.DeptDAO;
import org.tutorial.model.DeptDO;

@Repository
public class DeptDAOImpl implements DeptDAO {

    private static final String WILD_CARD = "%";

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public void insert(DeptDO deptDO) {
        entityManager.persist(deptDO);
    }

    @Override
    @Transactional
    public void update(DeptDO deptDO) {
        DeptDO deptDOFromDB = entityManager.find(DeptDO.class, deptDO.getDeptno());
        if (deptDOFromDB != null) {
            deptDOFromDB.setDeptno(deptDO.getDeptno());
            deptDOFromDB.setDname(deptDO.getDname());
            deptDOFromDB.setLoc(deptDO.getLoc());
        }
    }

    @Override
    @Transactional
    public void delete(Integer deptno) {
        DeptDO deptDO = entityManager.find(DeptDO.class, deptno);
        entityManager.remove(deptDO);
    }

    @Override
    public DeptDO findByPrimaryKey(Integer deptno) {
        return entityManager.find(DeptDO.class, deptno);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DeptDO> getAll() {
        //Name Query
        Query query = entityManager.createNamedQuery("dept.all");
        //JPQL Query
//        Query query = entityManager.createQuery("select dept from DeptVO dept");
        //Native Query
//        Query query = entityManager.createNativeQuery("select * from dept2", DeptVO.class);
        return query.getResultList();
    }

    @Override
    public List<DeptDO> findByCriteria(DeptDO deptDO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeptDO> criteriaQuery = criteriaBuilder.createQuery(DeptDO.class);
        Root<DeptDO> column = criteriaQuery.from(DeptDO.class);

        List<Predicate> predicates = new ArrayList<>();
        if (deptDO.getDeptno() != null) {
            predicates.add(criteriaBuilder.equal(column.get("deptno"), deptDO.getDeptno()));
        }

        if (StringUtils.isNoneBlank(deptDO.getDname())) {
            predicates.add(criteriaBuilder.like(column.get("dname"), WILD_CARD + deptDO.getDname() + WILD_CARD));
        }

        if (StringUtils.isNoneBlank(deptDO.getLoc())) {
            predicates.add(criteriaBuilder.like(column.get("loc"), WILD_CARD + deptDO.getLoc() + WILD_CARD));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        TypedQuery<DeptDO> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
