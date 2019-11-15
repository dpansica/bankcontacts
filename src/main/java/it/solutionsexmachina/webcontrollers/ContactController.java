package it.solutionsexmachina.webcontrollers;

import it.solutionsexmachina.dto.ContactDTO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.ContactFilter;
import it.solutionsexmachina.services.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @CrossOrigin(origins = "*")
    @PostMapping("/contacts")
    public List<ContactDO> getContacts(@RequestBody ContactFilter filter) {
        return contactService.searchContacts(filter);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/contacts/save")
    public ContactDO save(@RequestBody ContactDTO dto) {

        ContactDO entity = new ContactDO();

        BeanUtils.copyProperties(dto, entity);

        return contactService.save(entity);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/contacts/remove")
    public ContactDO remove(@RequestBody ContactDTO dto) {
        return contactService.delete(dto.getId());
    }

}
