package com.disney.repository.specifications;

import com.disney.dto.request.MovieFiltersRequest;
import com.disney.entity.CharacterEntity;
import com.disney.entity.MovieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieSpecification {

    public Specification<MovieEntity> getByFilters(MovieFiltersRequest filtersRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filtersRequest.getTitle())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + filtersRequest.getTitle().toLowerCase() + "%"));
            }

//            if (StringUtils.hasLength(filtersRequest.getDate())) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate date = LocalDate.parse(filtersRequest.getDate(), formatter);
//                predicates.add(criteriaBuilder.equal(root.<LocalDate>get("creationDate"), date));
//            }

            if (!CollectionUtils.isEmpty(filtersRequest.getCharacters())) {
                Join<CharacterEntity, MovieEntity> join = root.join("movieCharacters", JoinType.INNER);
                Expression<String> charactersId = join.get("id");
                predicates.add(charactersId.in(filtersRequest.getCharacters()));
            }

            //remove duplicates
            query.distinct(true);

            //Order resolver
            String orderByField = "title"; //es el parametro por el que se va a ordenar
            query.orderBy(
                    filtersRequest.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
