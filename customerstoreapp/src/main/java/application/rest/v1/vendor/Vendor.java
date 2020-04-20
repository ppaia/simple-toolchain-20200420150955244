package application.rest.v1.vendor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long pincode) {
		this.pincode = pincode;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", shopname=" + shopname + ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", pincode=" + pincode + ", phone=" + phone + "]";
	}
	public Vendor() {
		super();
	}
	public Vendor(long id) {
		super();
		this.id = id;
	}
	public Vendor(long id, String shopname, String address1, String address2, String city, String state, long pincode,
			long phone) {
		super();
		this.id = id;
		this.shopname = shopname;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.phone = phone;
	}
	private String shopname;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private long pincode;
	private long phone;
}