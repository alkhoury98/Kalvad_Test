package Test.Kalvad.Controller;

import Test.Kalvad.DTO.CustomerDTO;
import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import Test.Kalvad.Services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@AllArgsConstructor
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    // Getting all Customers Controller
    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> showCustomerList() {
        List<CustomerDTO> customerUsers = customerService.getAll();
        return ResponseEntity.ok().body(customerUsers);
    }

    // Creating new customer Controller
    @PostMapping("/customer")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.save(customer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/customer")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCustomer);
    }

    // Getting Customer with specific id With Handling Not found exception.
    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerDTO(id);
        return ResponseEntity.ok(customer);
    }


    // adding an address to a specific and existed customer.
    @PostMapping("/customer/{id}/address")
    public ResponseEntity<Address> addAddressToCustomer(@PathVariable Long id, @RequestBody Address address) {
        Address createdAddress = customerService.addAddress(id, address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("customer/{id}}/address")
                .buildAndExpand(createdAddress.getAId())
                .toUri();
        return ResponseEntity.created(location).body(createdAddress);
    }


    //Deleting specific address from specific Customer.
    @DeleteMapping("/customer/{id}/address/{address_id}")
    public ResponseEntity<String> DeleteAddressFromCustomer(@PathVariable Long id, @PathVariable Long address_id) {
        customerService.deleteAddress(address_id, id);
        return ResponseEntity.ok().body("Deleted Successfully");
    }


    // Listing all the customers that have addresses in a specific city.
    @GetMapping("/city/{city}")
    public List<CustomerDTO> listCustomerByCity(@PathVariable String city) {
        return customerService.getCityCustomer(city);
    }


    //Listing all the customers that have the same prefix as country code.
    @GetMapping("/phone/{prefix}")
    public List<CustomerDTO> listCustomersByPrefix(@PathVariable String prefix) {
        return customerService.getCustomersByPrefix(prefix);
    }


}
