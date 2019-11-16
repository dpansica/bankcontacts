package it.solutionsexmachina.repositories;

import it.solutionsexmachina.entities.AddressDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface AddressRepository extends CrudRepository<AddressDO, String>, QueryByExampleExecutor<AddressDO> {
}
