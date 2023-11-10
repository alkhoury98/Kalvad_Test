package Test.Kalvad.Services;

import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import Test.Kalvad.ExceptionHandling.DuplicatedException;
import Test.Kalvad.ExceptionHandling.NotFoundException;
import Test.Kalvad.Repository.AddressRepo;
import Test.Kalvad.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Customer get(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer doesn't exist"));
    }

    @Override
    public Customer save(Customer customer) {
        String email = customer.getEmail();
        if (customerRepo.findByEmail(email).isPresent())
            throw new DuplicatedException("Email is already used !");

       return customerRepo.save(customer);
    }

    @Override
    public void delete(Long id) {

        if (customerRepo.findById(id).isEmpty())
            throw new NotFoundException("there is no customer with this id ");

        customerRepo.deleteById(id);
    }

    @Override
    public Address addAddress(Long cId, Address address) {
        Customer customer = get(cId);
        address.setCustomer(customer);
        return addressRepo.save(address);
    }


    @Override
    public void deleteAddress(Long id, Long cid) {
        if (customerRepo.findById(cid).isEmpty())
            throw new NotFoundException("there is no customer with this id ");

        addressRepo.deleteAddressByCustomer(id, cid);

    }

    @Override
    public List<Customer> getCityCustomer(String city) {

        return customerRepo.findCustomersInCity(city);

    }

    @Override
    public List<Customer> getCustomersByPrefix(String prefix) {
        return customerRepo.findAllByPhoneNumberStartingWith(prefix);
    }
}

