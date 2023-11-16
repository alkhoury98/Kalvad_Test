package Test.Kalvad.Controller;

import Test.Kalvad.DTO.AddressDTO;
import Test.Kalvad.DTO.CustomerDTO;
import Test.Kalvad.Entity.Address;
import Test.Kalvad.Entity.Customer;
import Test.Kalvad.Services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void showCustomerList() throws Exception {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        List<CustomerDTO> customerDTOS = Arrays.asList(
                new CustomerDTO(1L, "Test one", "+971 676767 77", "Test1@gmail.com", addressDTOS),
                new CustomerDTO(1L, "Test two", "+971 09888 77", "Test2@gmail.com", addressDTOS)
        );
        when(customerService.getAll()).thenReturn(customerDTOS);

        mockMvc.perform(get("/customer", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test one"));


    }

    @Test
    void canSaveCustomer_ReturnCreated() throws Exception {

        Customer customer = new Customer("Test", "one", "+971 676767 77", "Test1@gmail.com");
        when(customerService.save(customer)).thenReturn(customer);

        mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    void test_getCustomer_ReturnOk() throws Exception {

        List<AddressDTO> addressDTOS = new ArrayList<>();
        CustomerDTO customer = new CustomerDTO(1L, "Test one", "+971 676767 77", "Test1@gmail.com", addressDTOS);
        when(customerService.getCustomerDTO(1L)).thenReturn(customer);

        mockMvc.perform(get("/customer/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Test one"));
    }

    @Test
    void addAddressToNotExistCustomer_ReturnCreated() throws Exception {

        Address address = new Address("home", "Sharjah", "UAE", "Sharjah ALqassimia");

        when(customerService.addAddress(1L, address)).thenReturn(address);

        mockMvc.perform(post("/customer/{id}/address", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteAddressFromCustomer_ReturnOk() throws Exception {


        Long addressID = 1L;
        Address address1 = new Address("home", "Sharjah", "UAE", "Sharjah ALqassimia");

        AddressDTO address = new AddressDTO(1L, address1);
        List<AddressDTO> addressDTOS = new ArrayList<>();
        addressDTOS.add(address);
        Customer customer = new Customer(1L, "test", "one", "+971 565 65655", "test1@gmail.com");
        doNothing().when(customerService).deleteAddress(1L, addressID);

        mockMvc.perform(delete("/customer/{id}/address/{address_id}", 1L, addressID).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customer)))

                .andExpect(status().isOk());

    }

    @Test
    void listCustomerByCity_ReturnOk() throws Exception {

        List<AddressDTO> addressList= new ArrayList<>();
        Address address1 = new Address("home", "Sharjah", "UAE", "Sharjah ALqassimia");
        addressList.add(new AddressDTO(1L,address1));
        List<CustomerDTO> customerDTOS = Arrays.asList(
                new CustomerDTO(1L, "Test one", "+971 676767 77", "Test1@gmail.com", addressList),
                new CustomerDTO(1L, "Test two", "+971 09888 77", "Test2@gmail.com", addressList)
        );

        when(customerService.getCityCustomer("Sharjah")).thenReturn(customerDTOS);

        mockMvc.perform(get("/city/{city}", "Sharjah").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test one"))
                .andExpect(jsonPath("$[1].fullName").value("Test two"));

    }


    @Test
    void listCustomersByPrefix_ReturnOk() throws Exception {
        List<AddressDTO> addressDTOS = new ArrayList<>();
        List<CustomerDTO> customerDTOS = Arrays.asList(
                new CustomerDTO(1L, "Test one", "+971 676767 77", "Test1@gmail.com", addressDTOS),
                new CustomerDTO(1L, "Test two", "+971 09888 77", "Test2@gmail.com", addressDTOS)
        );
        when(customerService.getCustomersByPrefix("+971")).thenReturn(customerDTOS);

        mockMvc.perform(get("/phone/{prefix}", "+971").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Test one"));
    }
}