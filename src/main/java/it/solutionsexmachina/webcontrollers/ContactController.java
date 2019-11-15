package it.solutionsexmachina.webcontrollers;

import it.solutionsexmachina.dto.ContactDTO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.services.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public List<ContactDO> getContacts(@RequestBody ContactFilter filter) {
        return contactService.searchContacts(filter);
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
