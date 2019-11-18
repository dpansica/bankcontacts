package it.solutionsexmachina.services;

import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.entities.PhoneNumberDO;
import it.solutionsexmachina.filters.AddressFilter;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.filters.PhoneFilter;
import it.solutionsexmachina.repositories.AddressRepository;
import it.solutionsexmachina.repositories.ContactRepository;
import it.solutionsexmachina.repositories.PhoneNumberRepository;
import it.solutionsexmachina.repositories.specs.ContactSpecs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private PhoneNumberRepository phoneNumberRepository;


    public List<ContactDO> searchContacts(ContactFilter filter) {
        List<ContactDO> result = new ArrayList<ContactDO>();

        Specification<ContactDO> all = ContactSpecs.all();
        if (StringUtils.isNotEmpty(filter.getName())){
            all = all.and(ContactSpecs.likeThisName(filter.getName()));
        }
        if (StringUtils.isNotEmpty(filter.getAddress())){
            all = all.and(ContactSpecs.withThisAddress(filter.getAddress()));
        }
        if (filter.getFromAge()!=null || filter.getToAge()!=null){
            all = all.and(ContactSpecs.betweenTheseAges(filter.getFromAge(), filter.getToAge()));
        }

        result = contactRepository.findAll(all);

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

    public PhoneNumberDO savePhone(PhoneNumberDO entity) {
        ContactDO contact = contactRepository.findById(entity.getContact().getId()).get();

        entity.setContact(contact);

        return phoneNumberRepository.save(entity);
    }

    public void deletePhone(String id) {
        PhoneNumberDO entity = phoneNumberRepository.findById(id).get();
        phoneNumberRepository.delete(entity);
    }

    public List<PhoneNumberDO> searchPhones(PhoneFilter filter) {
        List<PhoneNumberDO> result = new ArrayList<PhoneNumberDO>();

        PhoneNumberDO example = new PhoneNumberDO();
        example.setContact(new ContactDO());
        example.getContact().setId(filter.getContactId());

        for (PhoneNumberDO phoneNumberDO : phoneNumberRepository.findAll(Example.of(example))) {
            result.add(phoneNumberDO);
        }

        return result;
    }

}
