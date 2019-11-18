package it.solutionsexmachina.repositories.specs;

import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

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

    public static Specification<ContactDO> withThisAddress(String address) {
        return new Specification<ContactDO>() {
            public Predicate toPredicate(Root<ContactDO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                Subquery subquery = query.subquery(AddressDO.class);
                Root subRoot = subquery.from(AddressDO.class);
                Join join = subRoot.join("contact");
                subquery.select(join.get("id"));

                Predicate streetPredicate = builder.like(subRoot.get("street"), "%" + address + "%");
                Predicate postalCodePredicate = builder.like(subRoot.get("postalCode"), "%" + address + "%");
                Predicate townPredicate = builder.like(subRoot.get("town"), "%" + address + "%");
                Predicate countryPredicate = builder.like(subRoot.get("country"), "%" + address + "%");
                subquery.where(builder.or(streetPredicate, postalCodePredicate, townPredicate, countryPredicate));

                Predicate addressPredicate = builder.in(root.get("id")).value(subquery);

                return addressPredicate;

            }
        };
    }

}
