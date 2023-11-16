package Test.Kalvad.Services;

import Test.Kalvad.DTO.AddressDTO;
import Test.Kalvad.DTO.CustomerDTO;
import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import Test.Kalvad.ExceptionHandling.DuplicatedException;
import Test.Kalvad.ExceptionHandling.NotFoundException;
import Test.Kalvad.Repository.AddressRepo;
import Test.Kalvad.Repository.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public List<CustomerDTO> getAll() {

        return customerRepo.findAll().stream()
                .map(customer -> new CustomerDTO(
                         customer,
                        customer.getAddressesList().stream().map(address -> new AddressDTO(address.getAId(), address)).collect(Collectors.toList())

                )).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerDTO(Long id) {
        return customerRepo.findById(id).map(customer -> new CustomerDTO(
                        customer,
                        customer.getAddressesList().stream().map(address -> new AddressDTO(address.getAId(), address)).collect(Collectors.toList())
                ))
                .orElseThrow(() -> new NotFoundException("Customer doesn't exist", LocalDateTime.now(), HttpStatus.NOT_FOUND));
    }

    @Override
    public Customer get(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer doesn't exist", LocalDateTime.now(), HttpStatus.NOT_FOUND));
    }

    @Override
    public Customer save(Customer customer) {
        String email = customer.getEmail();
        if (customerRepo.findByEmail(email).isPresent())
            throw new DuplicatedException("Email is already used !", LocalDateTime.now(), HttpStatus.CONFLICT);

        return customerRepo.save(customer);
    }

    @Override
    public void delete(Long id) {

        if (customerRepo.findById(id).isEmpty())
            throw new NotFoundException("Customer doesn't exist", LocalDateTime.now(), HttpStatus.NOT_FOUND);

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
            throw new NotFoundException("Customer doesn't exist", LocalDateTime.now(), HttpStatus.NOT_FOUND);

        if(addressRepo.findById(id).isEmpty())
            throw new NotFoundException("there is no address with this id for the customer ",LocalDateTime.now(),HttpStatus.NOT_FOUND);

        addressRepo.deleteAddressByCustomer(id, cid);

    }

    @Override
    public List<CustomerDTO> getCityCustomer(String city) {

        return customerRepo.findCustomersInCity(city).stream()
                .map(customer -> new CustomerDTO(
                        customer,
                        customer.getAddressesList().stream().map(address -> new AddressDTO(address.getAId(), address)).collect(Collectors.toList())

                )).collect(Collectors.toList());

    }

    @Override
    public List<CustomerDTO> getCustomersByPrefix(String prefix) {
        return customerRepo.findAllByPhoneNumberStartingWith(prefix).stream()
                .map(customer -> new CustomerDTO(
                        customer,
                        customer.getAddressesList().stream().map(address -> new AddressDTO(address.getAId(), address)).collect(Collectors.toList())

                )).collect(Collectors.toList());

    }
}

