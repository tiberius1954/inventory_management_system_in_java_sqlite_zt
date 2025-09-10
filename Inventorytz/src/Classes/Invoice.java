package Classes;

public class Invoice {
	
	Double amount;
	Double discount;
	Double discountp;
	Double discountprice;
	Double quantity;
	Double price;
	Double vatp;
	Double vat;	
	
public Invoice(){
		
	}

	public Invoice( Double nqty, Double  nprice, Double ndiscountp, Double nvatp){
		this.quantity = nqty;
		this.price =nprice;
		this.discountp = ndiscountp;
		this.vatp = nvatp;
		this.vat = 0.0;
		this.amount = 0.0;
		this.discountprice= 0.0;
		this.discount = 0.0;
	}
public Invoice( Double nqty, Double  nprice){
		this.quantity = nqty;
		this.price =nprice;		
		this.amount = 0.0;
		this.discountprice= 0.0;
		this.discount = 0.0;
		this.vat=0.0;
	}
	
	public void setQuantity(Double nqty)
    {
        quantity = nqty;   
    }
	 public void setPrice(double nprice)
	    {
	        price = nprice;	  
	    }
	 public void setDiscountp(double ndiscountp)
	    {
	        discountp = ndiscountp;  
	    }
	 
	 public Double  getDiscountp()
	    {
	        return discountp;
	    }
	 
	 public Double  getDiscount()
	    {
	        return discount;
	    }
	 
	 public void   setDiscount(Double discount)
	    {
	        discount =  discount;
	    }
	 
	 
	 public Double  getVat() {		 
	        return vat;
	 }	        
	 
	 public Double getQuantity()
	    {
	        return quantity;
	    }
	 public double getPrice()
	    {
	        return price;
	    }
	 
	public Double getAmount(){
		if (quantity != 0.0 && price !=  0.0 && discountp != 0.0) {
		     amount = quantity * price;
		      discount = (amount * discountp) / 100;
		       discountprice = price * ((100.0 - discountp) / 100.0);			
		    	amount = quantity * discountprice;	
		    	vat = (vatp * amount) / 100.0;		
			} else {
				  amount = quantity * price;
					vat = (vatp * amount) / 100.0;	
			}
		return amount;
	}
	
	
	 

}
