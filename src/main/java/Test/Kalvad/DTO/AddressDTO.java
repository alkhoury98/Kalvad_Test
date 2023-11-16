package Test.Kalvad.DTO;

import Test.Kalvad.Entity.Address;

public class AddressDTO {

    private final Long address_id;

    private final String typeOfAddress;


    private final String city;

    private final String country;


    private final String addressLine;


    public AddressDTO(Long addressId, Address address) {
        this.address_id = addressId;
        this.typeOfAddress = address.getTypeOfAddress();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.addressLine = address.getAddressLine();
    }

    public Long getAddress_id() {
        return address_id;
    }

    public String getTypeOfAddress() {
        return typeOfAddress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressLine() {
        return addressLine;
    }
}
