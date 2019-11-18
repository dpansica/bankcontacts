package it.solutionsexmachina.repositories.specs;

import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.*;

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


                Predicate firstNamePredicate = builder.like(builder.upper(root.get("firstName")), "%" + name.toUpperCase() + "%");
                Predicate secondNamePredicate = builder.like(builder.upper(root.get("secondName")), "%" + name.toUpperCase() + "%");

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

                Predicate streetPredicate = builder.like(builder.upper(subRoot.get("street")), "%" + address.toUpperCase() + "%");
                Predicate postalCodePredicate = builder.like(builder.upper(subRoot.get("postalCode")), "%" + address.toUpperCase() + "%");
                Predicate townPredicate = builder.like(builder.upper(subRoot.get("town")), "%" + address.toUpperCase() + "%");
                Predicate countryPredicate = builder.like(builder.upper(subRoot.get("country")), "%" + address.toUpperCase() + "%");
                subquery.where(builder.or(streetPredicate, postalCodePredicate, townPredicate, countryPredicate));

                Predicate addressPredicate = builder.in(root.get("id")).value(subquery);

                return addressPredicate;

            }
        };
    }

    public static Specification<ContactDO> betweenTheseAges(Integer from, Integer to) {
        return new Specification<ContactDO>() {
            public Predicate toPredicate(Root<ContactDO> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {

                List<Predicate> predicates = new ArrayList<>();
                if (to!=null) {
                    GregorianCalendar start = new GregorianCalendar();
                    start.add(Calendar.YEAR, -to);

                    predicates.add(builder.greaterThanOrEqualTo(root.<Date>get("dob"), start.getTime()));
                }

                if (from!=null) {
                    GregorianCalendar end = new GregorianCalendar();
                    end.add(Calendar.YEAR, -from);

                    predicates.add(builder.lessThanOrEqualTo(root.<Date>get("dob"), end.getTime()));
                }

                return builder.and(predicates.toArray(new Predicate[0]));

            }
        };
    }



}
