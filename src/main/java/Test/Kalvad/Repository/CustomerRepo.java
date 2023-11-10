package Test.Kalvad.Repository;

import Test.Kalvad.Entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {


    //query that join two table to gets list of customer that have addresses in specific city.
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM taskone.customer c JOIN taskone.address on c.id = a_id WHERE city = :city", nativeQuery = true)
    List<Customer> findCustomersInCity(@Param("city") String city);


    //getting all customers that have a specific prefix.
    List<Customer> findAllByPhoneNumberStartingWith(String prefix);

    Optional<Customer> findByEmail(String email);
}
