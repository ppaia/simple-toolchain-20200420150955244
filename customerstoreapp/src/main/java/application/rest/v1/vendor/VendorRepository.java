package application.rest.v1.vendor;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long>{

	public List<Vendor> findBypincode(long pincode);}