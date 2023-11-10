package Test.Kalvad.Configuration;

import Test.Kalvad.Entity.Customer;
import Test.Kalvad.Services.CustomerService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeDatabase {


    @Autowired
    private CustomerService customerService;


    //Start up Method so it make the testing easy

    @PostConstruct
    public void DatabaseStarting() {
        Customer customer1 = new Customer("Test", "One", "+971 454 444 4444", "Test1@gmail.com");
        Customer customer2 = new Customer("Test", "Two", "+971 333 222 0000", "Test2@gmail.com");


        customerService.save(customer1);
        customerService.save(customer2);

    }


}
