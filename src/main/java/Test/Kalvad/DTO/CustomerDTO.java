package Test.Kalvad.DTO;

import Test.Kalvad.Entity.Customer;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private final String fullName;
    private final String phoneNumber;
    private final String email;
    private final List<AddressDTO> addressList;

    public CustomerDTO( Customer customer, List<AddressDTO> adressDTOList) {

        this.fullName = customer.getFirstName() + " " + customer.getLastName();
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.addressList = adressDTOList;

    }

    public CustomerDTO(Long id, String fullName, String phoneNumber, String email, List<AddressDTO> addressList) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.addressList = addressList;
    }



    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public List<AddressDTO> getAdressDTOList() {
        return addressList;
    }


    public Long getId() {
        return id;
    }


}
