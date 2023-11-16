package Test.Kalvad.Services;

import Test.Kalvad.DTO.AddressDTO;
import Test.Kalvad.DTO.CustomerDTO;
import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import Test.Kalvad.Repository.AddressRepo;
import Test.Kalvad.Repository.CustomerRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {

    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private AddressRepo addressRepo;


    @InjectMocks
    private CustomerServiceImp underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerServiceImp(customerRepo, addressRepo);
    }


    @Test
    void canGetAll() {
        //when
        underTest.getAll();
        //then
        verify(customerRepo).findAll();
    }

    @Test
    void CanGetCustomerDTO() {
        //Given
        Long customerID = 1L;
        Customer customer = new Customer("Test", "one", "+971 676767 77", "Test1@gmail.com");

        //when
        when(customerRepo.findById(customerID)).thenReturn(Optional.ofNullable(customer));

        CustomerDTO customerReturn = underTest.getCustomerDTO(customerID);

        //then
        Assertions.assertThat(customerReturn).isNotNull();
    }

    @Test
    void canSave() {
        //Given
        Customer customer1 = new Customer("Test", "one", "+971 676767 77", "Test1@gmail.com");
        //when
        underTest.save(customer1);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepo).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertEquals(capturedCustomer, customer1);
    }


    @Test
    void addAddress() {
        // Given
        Long customerId = 1L;
        Customer existingCustomer = new Customer("Test", "one", "+971 676767 77", "Test1@gmail.com");
        Address addressToAdd = new Address("home", "Sharjah", "UAE", "Sharjah alqassimia");
        ;

        //when
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(addressRepo.save(any())).thenReturn(addressToAdd);


        Address addedAddress = underTest.addAddress(customerId, addressToAdd);


        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(customerRepo).findById(idCaptor.capture());

        Long capturedId = idCaptor.getValue();
        assertEquals(customerId, capturedId);

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepo).save(addressCaptor.capture());

        Address capturedAddress = addressCaptor.getValue();
        assertNotNull(capturedAddress);

        //then
        assertEquals(addressToAdd, addedAddress);

    }


    @Test
    void deleteAddress() {
        // given
        Long customerId = 1L;
        Long addressId = 2L;

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(new Customer("Test", "one", "+971 676767 77", "Test1@gmail.com")));

        // when
        underTest.deleteAddress(addressId, customerId);

        // then
        verify(customerRepo, times(1)).findById(customerId);
        verify(addressRepo, times(1)).deleteAddressByCustomer(addressId, customerId);

    }

    @Test
    void getCityCustomer() {
        // Given
        List<Customer> expectedCustomers = new ArrayList<>();
        Customer customer1 = new Customer("Test","one","+971 676767 77","Test1@gmail.com");
        Customer customer2 = new Customer("Test", "Two", "+971 333 222 0000", "Test2@gmail.com");

        when(customerRepo.findCustomersInCity("Sharjah")).thenReturn(expectedCustomers);

        List<CustomerDTO> actualCustomers = underTest.getCityCustomer("Sharjah");


    }

    @Test
    void getCustomersByPrefix() {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        String phonePrefix = "+971";
        List<Customer> expectedCustomers = new ArrayList<>();
        expectedCustomers.add(new Customer("test"," one", "+971 676767 77", "Test1@gmail.com"));
        expectedCustomers.add(new Customer("test","Test two", "+971 000000 77", "Test2@gmail.com"));


        when(customerRepo.findAllByPhoneNumberStartingWith(phonePrefix)).thenReturn(expectedCustomers);



        //when
        List<CustomerDTO> actualCustomers = underTest.getCustomersByPrefix(phonePrefix);

        //then
        assertEquals(expectedCustomers.get(0).getEmail(), actualCustomers.get(0).getEmail());
    }
}