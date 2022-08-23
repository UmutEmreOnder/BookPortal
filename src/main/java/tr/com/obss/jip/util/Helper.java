package tr.com.obss.jip.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Helper {
    public static Pageable getPagable(Integer page, Integer pageSize, String field, String order) {
        Pageable pageable;
        if (order.equals("descend")) {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(field).descending());
        } else {
            pageable = PageRequest.of(page - 1, pageSize, Sort.by(field).ascending());
        }

        return pageable;
    }

    public static <T> List<T> getAll(EntityManager entityManager, Integer page, Integer pageSize, String field, String order, Class<T> tClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        if (order.equals("ascend")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(field)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(field)));
        }

        CriteriaQuery<T> select = criteriaQuery.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    public static <T> List<T> getAllContains(EntityManager entityManager, String searchField, String keyword, Integer page, Integer pageSize, String sortField, String order, Class<T> tClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        if (order.equals("ascend")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortField)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortField)));
        }
        criteriaQuery.where(criteriaBuilder.like(root.get(searchField), criteriaBuilder.parameter(String.class, "likeCondition")));


        CriteriaQuery<T> select = criteriaQuery.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);

        typedQuery.setParameter("likeCondition", "%" + keyword + "%");
        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }
}
