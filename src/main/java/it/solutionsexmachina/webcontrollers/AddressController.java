package it.solutionsexmachina.webcontrollers;

import it.solutionsexmachina.dto.AddressDTO;
import it.solutionsexmachina.entities.AddressDO;
import it.solutionsexmachina.entities.ContactDO;
import it.solutionsexmachina.filters.AddressFilter;
import it.solutionsexmachina.services.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/addresses", method = RequestMethod.POST)
    public List<AddressDTO> getAddresses(@RequestBody AddressFilter filter) {

        List<AddressDTO> results = new ArrayList<>();

        List<AddressDO> addresses = contactService.searchAddresses(filter);
        for (AddressDO address : addresses) {
            AddressDTO dto = new AddressDTO();

            BeanUtils.copyProperties(address, dto);

            results.add(dto);
        }

        return results;
    }

    @RequestMapping(value = "/address/save", method = RequestMethod.POST)
    public AddressDTO save(@RequestBody AddressDTO dto) {

        AddressDO entity = new AddressDO();

        BeanUtils.copyProperties(dto, entity);
        entity.setContact(new ContactDO());
        entity.getContact().setId(dto.getContactId());

        AddressDO addressDO = contactService.saveAddress(entity);

        BeanUtils.copyProperties(addressDO, dto);

        return dto;
    }

    @RequestMapping(value = "/address/remove", method = RequestMethod.POST)
    public void remove(@RequestBody AddressDTO dto) {
        contactService.deleteAddress(dto.getId());
    }
}
