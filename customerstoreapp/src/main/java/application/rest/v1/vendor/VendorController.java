package application.rest.v1.vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import application.rest.v1.product.Product;
import application.rest.v1.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class VendorController {
	
	@Autowired
	private VendorService vendorService;

	@Autowired
	ProductService productService;

	@PostMapping("/vendors")
	public Vendor addVendor(@RequestBody Vendor vendor){
		return vendorService.createVendor(vendor);
	}

	@GetMapping("/vendors")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		List<Vendor> list = vendorService.getAllVendors();
		return new ResponseEntity<List<Vendor>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/vendors/{id}")
	public ResponseEntity<List<Vendor>> getVendorsByZipCode(@PathVariable("id") Long id)
													throws VendorNotFoundException {
		List<Vendor> vendor = vendorService.getVendorsByZipCode(id);
		return ResponseEntity.ok().body(vendor);

	}
	@PutMapping("/vendors/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable(value = "id") Long vendorId,
			@Valid @RequestBody Vendor vendorDetails) throws VendorNotFoundException {
		Vendor vendor = vendorService.updateVendor(vendorId, vendorDetails);
		 return ResponseEntity.ok(vendor);
	}

	@PostMapping("/vendors/zipcode")
	public @ResponseBody Map getVendorsByZipCodeWebhook(@RequestBody Map map)
			throws VendorNotFoundException {
		Map messageMap = new HashMap();
		StringBuffer message = new StringBuffer("");
		Integer zipcode = (Integer)map.get("zipcode");
		if("zipcode".equalsIgnoreCase((String) map.get("action"))) {
			List<Vendor> vendor = vendorService.getVendorsByZipCode(new Long(zipcode));
			vendor.forEach(vendor1 ->
					message.append("<b>").append(vendor1.getShopname()).append("</b></br>")
					.append(vendor1.getAddress1()).append(",").append(vendor1.getAddress2()).append("</br>")
					.append(vendor1.getCity()).append(",").append(vendor1.getState()).append("</br>")
					.append("Pincode:").append(vendor1.getPincode()).append(", Phone:").append(vendor1.getPhone()).append("</br></br>")
			);
			if(vendor == null || vendor.size() == 0) {
				message.append("No vendors registered for zipcode:").append(zipcode).append(". Try a different zipcode.</br>");
			}
		} else if("product".equalsIgnoreCase((String) map.get("action"))) {
			String name = (String) map.get("name");
			List<Product> products = productService.getProdByNameAndZipCode(name, new Long(zipcode));
			for(Product product : products) {
				message.append(product.getName()).append(" (");
				if(0 == product.getStock())
					message.append("Unavailable");
				else
					message.append("Available");
				message.append(") </br>");
				Vendor vendor = product.getCategory().getVendor();
				message.	append("Address : <b>").append(vendor.getShopname()).append("</b></br>")
						.append(vendor.getAddress1()).append(",").append(vendor.getAddress2()).append("</br>")
						.append(vendor.getCity()).append(",").append(vendor.getState()).append("</br>")
						.append("Pincode:").append(vendor.getPincode()).append(", Phone:").append(vendor.getPhone()).append("</br></br>");
			}
			if(products == null || products.size() == 0) {
				message.append(name).append(" not available for zipcode:").append(zipcode).append(". Check for a different product.</br>");
			}
		}
		System.out.println(message.toString());
		messageMap.put("message",message.toString());
		return messageMap;

	}


}
