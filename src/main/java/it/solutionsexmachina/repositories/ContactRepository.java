package it.solutionsexmachina.repositories;

import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ContactRepository extends CrudRepository<ContactDO, String>, QueryByExampleExecutor<AddressDO>, JpaSpecificationExecutor<ContactDO> {

}
