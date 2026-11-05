package jpchs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import jpchs.dto.paging.Filter;
import jpchs.dto.paging.SortOrder;
import jpchs.entity.StudyGroup;
import jpchs.repo.spec.StudyGroupSpecification;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StudyGroupRepository {

    @PersistenceContext(unitName = "PostgreDS")
    private EntityManager em;

    @Inject
    private StudyGroupSpecification spec;

    public Optional<StudyGroup> findById(Integer id) {
        return Optional.ofNullable(
                em.find(StudyGroup.class, id)
        );
    }

    public StudyGroup save(StudyGroup entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        }

        return em.merge(entity);
    }

    public void delete(StudyGroup entity) {
        em.remove(
                em.contains(entity)
                        ? entity
                        : em.merge(entity)
        );
    }

    public Page<StudyGroup> findAll(
            List<Filter> filters,
            List<SortOrder> sorts,
            int page,
            int pageSize
    ) {
        var cb = em.getCriteriaBuilder();

        // Основной запрос
        var dataQuery = cb.createQuery(StudyGroup.class);
        var root = dataQuery.from(StudyGroup.class);

        // Загружаем groupAdmin вместе со StudyGroup.
        // Location является @Embedded и отдельного FETCH не требует.
        root.fetch("groupAdmin", JoinType.LEFT);

        var resolver =
                new StudyGroupSpecification.PathResolver(root);

        List<Predicate> predicates =
                spec.toPredicates(cb, resolver, filters);

        dataQuery.select(root)
                .where(predicates.toArray(Predicate[]::new))
                .orderBy(
                        sorts.stream()
                                .map(s -> s.direction().isAsc()
                                        ? cb.asc(resolver.resolve(s.field()))
                                        : cb.desc(resolver.resolve(s.field()))
                                )
                                .toList()
                );

        List<StudyGroup> content = em.createQuery(dataQuery)
                .setFirstResult(page * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        // Отдельный COUNT-запрос без FETCH JOIN
        var countQuery = cb.createQuery(Long.class);
        var countRoot = countQuery.from(StudyGroup.class);

        var countResolver =
                new StudyGroupSpecification.PathResolver(countRoot);

        List<Predicate> countPredicates =
                spec.toPredicates(cb, countResolver, filters);

        countQuery.select(cb.count(countRoot))
                .where(countPredicates.toArray(Predicate[]::new));

        long total = em.createQuery(countQuery)
                .getSingleResult();

        return new Page<>(content, total);
    }

    public int countExpelledStudents() {
        var result = em.createNativeQuery(
                "SELECT COALESCE(SUM(s.expelled_students), 0) " +
                        "FROM study_group s"
        ).getSingleResult();

        return ((Number) result).intValue();
    }

    public int countGroupsWithStudentsCount(int studentsCount) {
        var result = em.createNativeQuery(
                        "SELECT COUNT(*) FROM study_group s " +
                                "WHERE s.students_count = :c"
                )
                .setParameter("c", studentsCount)
                .getSingleResult();

        return ((Number) result).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<StudyGroup> findAllNameStartingWithPrefix(
            String prefix
    ) {
        return em.createNativeQuery(
                        "SELECT * FROM study_group s " +
                                "WHERE s.name LIKE CONCAT(:prefix, '%')",
                        StudyGroup.class
                )
                .setParameter("prefix", prefix)
                .getResultList();
    }

    public record Page<T>(List<T> content, long total) {

        public int totalPages(int pageSize) {
            return (int) Math.ceil(
                    (double) total / pageSize
            );
        }
    }
}
