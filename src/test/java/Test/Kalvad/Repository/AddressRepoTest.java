package Test.Kalvad.Repository;

import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Transactional
public class AddressRepoTest {

        @Autowired
        private CustomerRepo customerTestRepo;
        @Autowired
        private AddressRepo testRepo;

        @Test
        @DirtiesContext
        public void testDeleteAddressByCustomer() {
            //Given
            Customer customer1 = new Customer("Test","one","+971 676767 77","Test1@gmail.com");
            Customer customer2 = new Customer("Test", "Two", "+971 333 222 0000", "Test2@gmail.com");

            Address address1 = new Address("home","Sharjah","UAE","Sharjah alqassimia",customer1);
            Address address2 = new Address("home","Sharjah","UAE","Dubai Marina",customer1);

            // Save addresses
            customerTestRepo.save(customer1);
            customerTestRepo.save(customer2);
            testRepo.save(address1);
            testRepo.save(address2);

            // When
            testRepo.deleteAddressByCustomer(address1.getAId(), address1.getCustomer().getId());

            // Then
            assertThat(testRepo.findAll()).hasSize(1);
        }
    }
