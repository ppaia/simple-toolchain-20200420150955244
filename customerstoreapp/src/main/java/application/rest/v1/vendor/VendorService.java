package application.rest.v1.vendor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

	@Autowired
	VendorRepository vendorRepository;

	public Vendor createVendor(Vendor vendor) {
		return vendorRepository.save(vendor);
	}

	public List<Vendor> getAllVendors() {
		List<Vendor> vendorList = (List<Vendor>) vendorRepository.findAll();

		if (vendorList.size() > 0) {
			return vendorList;
		} else {
			return new ArrayList<Vendor>();
		}
	}

	public List<Vendor> getVendorsByZipCode(Long pincode) throws VendorNotFoundException {
		List<Vendor> vendor = vendorRepository.findBypincode(pincode);

		if (vendor != null) {
			return vendor;
		} else {
			throw new VendorNotFoundException("No Vendors are available for the following Zipcode");
		}

	}

	public Vendor updateVendor(Long vendorId, Vendor vendorDetails) throws VendorNotFoundException {
		Vendor vendor = vendorRepository.findById(vendorId)
				.orElseThrow(() -> new VendorNotFoundException("Vendor not found:: " + vendorId));
		vendor.setShopname(vendorDetails.getShopname());
		vendor.setAddress1(vendorDetails.getAddress1());
		vendor.setAddress2(vendorDetails.getAddress2());
		vendor.setCity(vendorDetails.getCity());
		vendor.setState(vendorDetails.getState());
		vendor.setPincode(vendorDetails.getPincode());
		vendor.setPhone(vendorDetails.getPhone());
		final Vendor updatedVendor = vendorRepository.save(vendor);
		return updatedVendor;

	}

}
