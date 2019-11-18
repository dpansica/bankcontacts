package it.solutionsexmachina.webcontrollers;

import it.solutionsexmachina.dto.PhoneNumberDTO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.entities.PhoneNumberDO;
import it.solutionsexmachina.filters.PhoneFilter;
import it.solutionsexmachina.services.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PhoneController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/phones", method = RequestMethod.POST)
    public List<PhoneNumberDTO> getPhones(@RequestBody PhoneFilter filter) {

        List<PhoneNumberDTO> results = new ArrayList<>();

        List<PhoneNumberDO> addresses = contactService.searchPhones(filter);
        for (PhoneNumberDO address : addresses) {
            PhoneNumberDTO dto = new PhoneNumberDTO();

            BeanUtils.copyProperties(address, dto);

            results.add(dto);
        }

        return results;
    }

    @RequestMapping(value = "/phone/save", method = RequestMethod.POST)
    public PhoneNumberDTO save(@RequestBody PhoneNumberDTO dto) {

        PhoneNumberDO entity = new PhoneNumberDO();

        BeanUtils.copyProperties(dto, entity);
        entity.setContact(new ContactDO());
        entity.getContact().setId(dto.getPhoneContactId());

        PhoneNumberDO phoneNumberDO = contactService.savePhone(entity);

        BeanUtils.copyProperties(phoneNumberDO, dto);

        return dto;
    }

    @RequestMapping(value = "/phone/remove", method = RequestMethod.POST)
    public void remove(@RequestBody PhoneNumberDTO dto) {
        contactService.deletePhone(dto.getId());
    }
}
