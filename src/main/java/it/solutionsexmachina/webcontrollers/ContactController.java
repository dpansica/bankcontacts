package it.solutionsexmachina.webcontrollers;

import it.solutionsexmachina.dto.ContactDTO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.services.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public List<ContactDTO> getContacts(@RequestBody ContactFilter filter) {

        List<ContactDTO> results = new ArrayList<>();

        List<ContactDO> contacts = contactService.searchContacts(filter);
        for (ContactDO contact : contacts) {
            ContactDTO dto = new ContactDTO();

            BeanUtils.copyProperties(contact, dto);

            results.add(dto);
        }

        return results;
    }

    @RequestMapping(value = "/contacts/save", method = RequestMethod.POST)
    public ContactDO save(@RequestBody ContactDTO dto) {

        ContactDO entity = new ContactDO();

        BeanUtils.copyProperties(dto, entity);

        return contactService.save(entity);
    }

    @RequestMapping(value = "/contacts/remove", method = RequestMethod.POST)
    public void remove(@RequestBody ContactDTO dto) {
        contactService.delete(dto.getId());
    }
}
