package it.solutionsexmachina.services;

import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;


    public List<ContactDO> searchContacts(ContactFilter filter) {
        List<ContactDO> result = new ArrayList<ContactDO>();
        for (ContactDO contactDO : contactRepository.findAll()) {
            result.add(contactDO);
        }

        return result;
    }

    public ContactDO save(ContactDO entity){
        return contactRepository.save(entity);
    }
}
