package it.solutionsexmachina.services;

import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.AddressFilter;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.repositories.AddressRepository;
import it.solutionsexmachina.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;


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

    public void delete(String id) {
        ContactDO entity = contactRepository.findById(id).get();
        contactRepository.delete(entity);
    }

    public ContactDO getById(String id) {
        return contactRepository.findById(id).get();
    }

    public AddressDO saveAddress(AddressDO entity) {
        ContactDO contact = contactRepository.findById(entity.getContact().getId()).get();

        entity.setContact(contact);

        return addressRepository.save(entity);
    }

    public void deleteAddress(String id) {
        AddressDO entity = addressRepository.findById(id).get();
        addressRepository.delete(entity);
    }

    public List<AddressDO> searchAddresses(AddressFilter filter) {
        List<AddressDO> result = new ArrayList<AddressDO>();

        AddressDO example = new AddressDO();
        example.setContact(new ContactDO());
        example.getContact().setId(filter.getContactId());

        for (AddressDO addressDO : addressRepository.findAll(Example.of(example))) {
            result.add(addressDO);
        }

        return result;
    }
}
