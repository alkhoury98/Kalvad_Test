package Test.Kalvad.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    @NotNull
    private String typeOfAddress;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private String addressLine;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;


    public Address(String typeOfAddress, String city, String country, String addressLine) {
        this.typeOfAddress = typeOfAddress;
        this.city = city;
        this.country = country;
        this.addressLine = addressLine;
    }

    public Address(String typeOfAddress, String city, String country, String addressLine, Customer customer) {
        this.typeOfAddress = typeOfAddress;
        this.city = city;
        this.country = country;
        this.addressLine = addressLine;
        this.customer = customer;
    }

    public Address(Long aId, String typeOfAddress, String city, String country, String addressLine, Customer customer) {
        this.aId = aId;
        this.typeOfAddress = typeOfAddress;
        this.city = city;
        this.country = country;
        this.addressLine = addressLine;
        this.customer = customer;
    }

    public Address() {
    }

    public Long getAId() {
        return aId;
    }

    public void setAId(Long id) {
        this.aId = id;
    }

    public String getTypeOfAddress() {
        return typeOfAddress;
    }

    public void setTypeOfAddress(String typeOfAddress) {
        this.typeOfAddress = typeOfAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
