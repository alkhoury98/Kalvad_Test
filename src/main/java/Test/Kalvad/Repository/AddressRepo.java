package Test.Kalvad.Repository;

import Test.Kalvad.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM address WHERE a_id = :id And customer_id= :cID", nativeQuery = true)
    void deleteAddressByCustomer(@Param("id") Long id, @Param("cID") Long cId);


}
