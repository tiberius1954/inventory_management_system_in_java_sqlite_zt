package Classes;

public class Partner {
	int parid;
	String name;
	String phone;
	String email;
	String role;
	String country;
	int cid;
	String city;
	String address;
	String postcode;
	
public Partner() {
		
  	}
public Partner(int parid, String name, String role) {
    this.parid = parid;
    this.name = name;
    this.role = role;
	}
public Partner(int parid, String name, String phone, String email,  String role, String country, int cid, String city,
		String address, String postcode) {
	     this.parid = parid;
	     this.name = name;
	     this.phone = phone;
	     this.email=email;
	     this.role = role;
	     this.country = country;
	     this.cid = cid;
	     this.city = city;
	     this.address= address;
	     this.postcode= postcode;	     
	}

public Partner(int parid, String name, String phone, String country, int cid, String city,
		String address, String postcode) {
	     this.parid = parid;
	     this.name = name;
	     this.phone = phone;	    
	     this.country = country;
	     this.cid = cid;
	     this.city = city;
	     this.address= address;
	     this.postcode= postcode;	     
	}

public void setParid(int parid) {
    this.parid = parid;
}
public int getParid() {
    return parid;
}

public void setName(String Name) {
    this.name = name;
}
public  String getName() {
    return name;
}

public void setPhone(String Phone) {
    this.phone = phone;
}
public  String getPhone() {
    return phone;
}

public void setEmail(String email) {
    this.email = email;
}
public  String getEmail() {
    return email;
}

public void setRole(String role) {
    this.role = role;
}
public  String getRole() {
    return role;
}

public void setCountry(String country) {
    this.country = country;
}
public  String getCountry() {
    return country;
}
public void setCid(int cid) {
    this.cid = cid;
}
public int getCid() {
    return cid;
}
public void setCity(String city) {
    this.city = city;
}
public  String getCity() {
    return city;
}

public void setAddress(String Address) {
    this.address = address;
}
public  String getAddress() {
    return address;
}

public void setPostcode(String postcode) {
    this.postcode = postcode;
}
public  String getPostcode() {
    return postcode;
}
@Override
public String toString() 
{ 	
   	return name + " " + role;
} 

}
