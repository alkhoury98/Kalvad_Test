package Test.Kalvad.Services;

import Test.Kalvad.DTO.CustomerDTO;
import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerDTO> getAll();

    CustomerDTO getCustomerDTO(Long id);

    Customer get(Long id);

    Customer save(Customer customer);

    void delete(Long id);

    Address addAddress(Long cid, Address address);

    void deleteAddress(Long id, Long cid);

    List<CustomerDTO> getCityCustomer(String city);

    List<CustomerDTO> getCustomersByPrefix(String prefix);

}
