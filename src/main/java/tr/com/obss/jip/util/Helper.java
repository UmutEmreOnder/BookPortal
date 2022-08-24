package tr.com.obss.jip.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import tr.com.obss.jip.exception.AuthorNotFoundException;
import tr.com.obss.jip.exception.GenreNotFoundException;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;
import tr.com.obss.jip.model.RespondType;
import tr.com.obss.jip.model.RespondedBookRequest;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public static <T> List<T> getAll(EntityManager entityManager, String searchField, String keyword, Integer page, Integer pageSize, String sortField, String order, Class<T> tClass) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        if (order.equals("ascend")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortField)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortField)));
        }

        criteriaQuery.where(criteriaBuilder.like(root.get(searchField), "%" + keyword + "%"));
        CriteriaQuery<T> select = criteriaQuery.select(root);
        TypedQuery<T> typedQuery = entityManager.createQuery(select);


        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    public static <T> List<T> getBooksOrRequests(EntityManager entityManager, String searchField, String keyword, Integer page, Integer pageSize, String sortField, String order, Class<T> tClass, List<String> authors, AuthorRepository authorRepository, List<String> genres, GenreRepository genreRepository) {
        List<Author> authorList = Helper.getAuthors(authors, authorRepository);
        List<Genre> genreList = Helper.getGenres(genres, genreRepository);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);

        if (order.equals("ascend")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortField)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortField)));
        }

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(criteriaBuilder.like(root.get(searchField), "%" + keyword + "%"));

        if (authorList != null) {
            predicates.add(root.get("author").in(authorList));
        }

        if (genreList != null) {
            try {
                predicates.add(root.get("genre").in(genreList));
            } catch (Exception e) {
                predicates.add(root.get("genreName").in(genres));
            }
        }

        CriteriaQuery<T> select = criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
        TypedQuery<T> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    public static List<RespondedBookRequest> getResponses(EntityManager entityManager, Integer page, Integer pageSize, String sortField, String order, List<String> responses, Author authenticatedAuthor) {
        List<RespondType> respondTypes = getRespondTypes(responses);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RespondedBookRequest> criteriaQuery = criteriaBuilder.createQuery(RespondedBookRequest.class);
        Root<RespondedBookRequest> root = criteriaQuery.from(RespondedBookRequest.class);

        if (order.equals("ascend")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortField)));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortField)));
        }

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(root.get("author").in(authenticatedAuthor));

        if (respondTypes != null) {
            predicates.add(root.get("respond").in(respondTypes));
        }

        CriteriaQuery<RespondedBookRequest> select = criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
        TypedQuery<RespondedBookRequest> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult((page - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    private static List<Genre> getGenres(List<String> genres, GenreRepository genreRepository) {
        if (genres == null) return null;
        return genres.stream().map(genreName -> genreRepository.findGenreByName(GenreType.valueOf(genreName)).orElseThrow(() -> new GenreNotFoundException(genreName))).toList();
    }

    private static List<Author> getAuthors(List<String> names, AuthorRepository authorRepository) {
        if (names == null) return null;
        return names.stream().map(username -> authorRepository.findAuthorByUsername(username).orElseThrow(() -> new AuthorNotFoundException(username))).toList();
    }

    private static List<RespondType> getRespondTypes(List<String> responses) {
        if (responses == null) return null;

        return responses.stream().map(RespondType::valueOf).toList();
    }

}
