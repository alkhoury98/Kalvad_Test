package Test.Kalvad.Services;

import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> getAll();

    Customer get(Long id);

    Customer save(Customer customer);

    void delete(Long id);

    Address addAddress(Long cid, Address address);

    void deleteAddress(Long id, Long cid);

    List<Customer> getCityCustomer(String city);

    List<Customer> getCustomersByPrefix(String prefix);

}
