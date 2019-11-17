package it.solutionsexmachina.repositories;

import it.solutionsexmachina.entities.PhoneNumberDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumberDO, String>, QueryByExampleExecutor<PhoneNumberDO> {
}
