package it.solutionsexmachina.repositories;

import it.solutionsexmachina.entities.ContactDO;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<ContactDO, String> {
}
