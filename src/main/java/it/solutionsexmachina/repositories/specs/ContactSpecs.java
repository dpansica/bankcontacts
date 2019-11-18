package it.solutionsexmachina.repositories.specs;

import it.solutionsexmachina.entities.ContactDO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ContactSpecs {

    public static Specification<ContactDO> all() {
        return new Specification<ContactDO>() {
            public Predicate toPredicate(Root<ContactDO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate idNotNull = builder.isNotNull(root.get("id"));
                return idNotNull;

            }
        };
    }


    public static Specification<ContactDO> likeThisName(String name) {
        return new Specification<ContactDO>() {
            public Predicate toPredicate(Root<ContactDO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {


                Predicate firstNamePredicate = builder.like(root.get("firstName"), "%" + name + "%");
                Predicate secondNamePredicate = builder.like(root.get("secondName"), "%" + name + "%");

                return builder.or(firstNamePredicate, secondNamePredicate);

            }
        };
    }
}
