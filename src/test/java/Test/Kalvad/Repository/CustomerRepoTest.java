package Test.Kalvad.Repository;

import Test.Kalvad.Entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@Transactional
class CustomerRepoTest {


    @Autowired
    private CustomerRepo testRepo;



    @Test
    public void testFindCustomersInCity() {
        // Given
        Customer customer1 = new Customer("Test","one","+971 676767 77","Test1@gmail.com");
        Customer customer2 = new Customer("Test", "Two", "+971 333 222 0000", "Test2@gmail.com");

        testRepo.save(customer1);
        testRepo.save(customer2);


        // When
        List<Customer> customersInCity = testRepo.findCustomersInCity("");

        // Then
        assertThat(customersInCity).hasSize(0);

    }

    @Test
    void findAllByPhoneNumberStartingWith() {
        Customer customer1 = new Customer("Test","one","+971 676767 77","Test1@gmail.com");
        Customer customer2 = new Customer("Test", "Two", "+971 333 222 0000", "Test2@gmail.com");


        // Save customers with addresses in the specific city
        testRepo.save(customer1);
        testRepo.save(customer2);

        // When
        List<Customer> customersInCity = testRepo.findAllByPhoneNumberStartingWith("+971");

        // Then
        assertThat(customersInCity).hasSize(2);
        // Add more assertions based on your requirements
    }

    @Test
    public void testFindByEmail() {
        // Given
        Customer customer1 = new Customer(1L,"Test","TWO","+971 676767 77","Test1@gmail.com");


        // Save the customer
        testRepo.save(customer1);

        // When
        Optional<Customer> foundCustomer = testRepo.findByEmail("Test1@gmail.com");

        // Then
        assertThat(foundCustomer).isPresent();
        // Add more assertions based on your requirements
    }
}