package Databaseop;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import Classes.Hhelper;
import Classes.Partner;
import Classes.Unity;


public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();	
	Product pro;
	
	private String[]  rolenames   = { "Customer","Other" ,"Salesman","Supplier", "User"};
	 public String[] rolnames() {		 
			return rolenames;
		}
	 
	private String [] paymodes = { "By cash", "Credit", "By cheque" };
	 public String[] paymodes() {
			return paymodes;
		}
	
	
public String myidfv(JTable dtable) {
String retid ="";
try {
	DefaultTableModel model = (DefaultTableModel) dtable.getModel();		
	int row = dtable.getSelectedRow();
	if (row > -1) {
	 retid = model.getValueAt(row, 0).toString();	
	}
} catch (Exception e) {
	System.out.println("sql error!!!");		
}
return retid;
}

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = dtable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
			}
			if (dtable.getRowCount() > 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
		}
		return flag;
	}

	public void rtable_delete(JTable dtable, String pid) {
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		String sql;
		int flag = 0;
		int sIndex = dtable.getSelectedRow();
		int selections[] = dtable.getSelectedRows();
		int lastIndex = selections[0];
		sql = "delete from properties where pid='" + pid + "'";
		try {
			flag = dh.Insupdel(sql);
			d1.removeRow(sIndex);
			if (dtable.getRowCount() > 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
		} catch (Exception e1) {
			if (dtable.getRowCount() > 0)
				dtable.setRowSelectionInterval(0, 0);
		}	
	}

	public int tdata_delete(JTable dtable, String sql, int row) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.removeRow(row);
		}
		return flag;
	}

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}
	public boolean recordfound(String Query) {
		boolean found = false;
		int count = 0;
		try {			
			
			rs = dh.GetData(Query);
			while (rs.next()) {	
			count = rs.getInt("count(*)");
	       	}
			dh.CloseConnection();	
		}
		catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}
		if (count >0) {
	        found= true;
		}
		return found;
	}	
	
	public int manyrecord(String Query) {	
		int count = 0;
		try {				
			rs = dh.GetData(Query);
			while (rs.next()) {	
			count = rs.getInt("count(*)");
	       	}
			dh.CloseConnection();	
		}
		catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}	
		return count;
	}	
	
	public void cmbpartnercombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Partner  A = new Partner (0, "","");
		mycombo.addItem(A);	
		sql= "select parid, name, role from partner order by upper(name)";	
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Partner(rs.getInt("parid"), rs.getString("name"), rs.getString("role"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();	
		} catch (SQLException e) {			
			e.printStackTrace();
		}			
	}	

	
	public void countrycombofill(JComboBox ccombo) {
		String Sql = " select countryname from countries order by  upper(countryname)";
		ccombo.removeAllItems();
		ccombo.addItem("");	
		try {
			ResultSet res = dh.GetData(Sql);
			while (res.next()) {
				ccombo.addItem(res.getString("countryname"));
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void unitiescombofill(JComboBox ccombo) {
		ccombo.removeAllItems();
		Unity A = new Unity(0, " ");
		ccombo.addItem(A);
		String sql = "select uid, uname  from unities order by upper(uname)";
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				A = new Unity(rs.getInt("uid"), rs.getString("uname"));
				ccombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void btypescombofill(JComboBox mycombo) {
		mycombo.removeAllItems();
		Btype  A = new Btype (0, "");
		A = new Btype(1, "Deposit");
		mycombo.addItem(A);
		A = new Btype(2, "Withdrawal");
		mycombo.addItem(A);		
	}

	
	public void productscombo(JComboBox mycombo) {
		mycombo.removeAllItems();
		Product  A = new Product (0, "","","",0);
		mycombo.addItem(A);
		String sql = "select pid, pname, costprice, sellprice, uid  from product order by upper(pname)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Product(rs.getInt("pid"), rs.getString("pname"), rs.getString("costprice"),
						rs.getString("sellprice"), rs.getInt("uid"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		dh.CloseConnection();		
	}
	public void subcategoriescombofill(JComboBox mycombo) {
		mycombo.removeAllItems();
		Subcategory  A = new Subcategory (0, "",0);
		mycombo.addItem(A);
		String sql = "select scid, sname, cid  from subcategory order by upper(sname)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Subcategory(rs.getInt("scid"), rs.getString("sname"), rs.getInt("cid"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		dh.CloseConnection();		
	}
	
	public void categoriescombofill(JComboBox mycombo) {
		mycombo.removeAllItems();
		Category  A = new Category (0, "");
		mycombo.addItem(A);
		String sql = "select cid, cname from category order by upper(cname)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Category(rs.getInt("cid"), rs.getString("cname"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		dh.CloseConnection();		
	}
	
	public class Btype {
		   String name;
		   int id;		  
		  public Btype() {			  
		  }

		  public Btype (int id, String name) {
		    this.name = name;
		    this.id = id;
		  }
		  @Override
		  public String toString() {
		    return name;
		  }

		  public String getName() {
		    return name;
		  }

		  public void setName(String name) {
		    this.name = name;
		  }

		  public int getId() {
		    return id;
		  }

		  public void setId(int id) {
		    this.id = id;
		  }
		}
	
	public class Subcategory {
		  int scid;
		  int cid;
		  String sname;	
		  public Subcategory() {			  
		  }
		  public Subcategory (int scid, String sname, int cid) {
		    this.scid = scid;
		    this.sname = sname;		
		    this.cid = cid;
		  }
		  @Override
		  public String toString() 
		  { 	
		     	return sname; 	 
		  } 
		 public int getScid() {
			  return scid;
		  }		
		 public void setScid(int scid) {
			  this.scid = scid;
		  }
		 
		 public int getCid() {
			  return cid;
		  }
		 public void setSname(String sname) {
			  this.sname = sname;
		  }
		 public String getSname() {
			 return sname;
		 }		
		}
	public class Category {
		  int cid;	
		  String cname;	
		  public Category() {			  
		  }
		  public Category (int cid, String cname) {
		    this.cid = cid;
		    this.cname = cname;			 
		  }
		  @Override
		  public String toString() 
		  { 	
		     	return cname; 	 
		  } 
		 public int getCid() {
			  return cid;
		  }		
		 public void setCid(int scid) {
			  this.cid = cid;
		  }
		
		 public void setCname(String cname) {
			  this.cname = cname;
		  }
		 public String getCname() {
			 return cname;
		 }		
		}
	
	public class Cities {
		  int cid;	
		  String name;	
		  public Cities() {			  
		  }
		  public Cities (int cid, String name) {
		    this.cid = cid;
		    this.name = name;		  
		  }
		  @Override
		  public String toString() 
		  { 	
		     	return name; 	 
		  } 
		 public int getCid() {
			  return cid;
		  }
		 public String getName() {
			  return name;
		  }
		}
	public void cmbcitiescombofill(JComboBox mycombo)  {
		mycombo.removeAllItems();
		Cities  A = new Cities (0, "");
		mycombo.addItem(A);
		String sql = "select cid, name  from cities order by name";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Cities(rs.getInt("cid"), rs.getString("name"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		dh.CloseConnection();
	}	
	
	public class Product{
		  int pid;	
		  String pname;	
		  String costprice;
		  String sellprice;
		  String productcode;
		  String discountp;
		  String vatp;
		  int uid;
		  
		  public Product() {			  
		  }
		  
		  public Product (int pid, String pname, String costprice, String sellprice, int uid) {
			    this.pid = pid;
			    this.pname = pname;	
			    this.costprice = costprice;		
			    this.sellprice = sellprice;
			    this.uid = uid;
			  }
		  
		  public Product (int pid, String pname, String costprice, String productcode, String sellprice, 
				  String discountp, String vatp, int uid) {
		    this.pid = pid;
		    this.pname = pname;	
		    this.costprice = costprice;
		    this.productcode=productcode;
		    this.sellprice = sellprice;
		    this.discountp = discountp;
		    this.vatp = vatp;
		    this.uid = uid;
		  }
		  @Override
		  public String toString() 
		  { 	
		     	return pname; 	 
		  } 
		 public int getPid() {
			  return pid;
		  }
		 public String getPname() {
			 return pname;
		 }
		 public String getProductcode() {
			 return productcode;
		 }
		 public String getCostprice() {
			 return costprice;
		 }
		 public String getSellprice() {
			 return sellprice;
		 }
		 public String getVatp() {
			 return vatp;
		 }
		 public String getDiscountp() {
			 return discountp;
		 }
		 public int getUid() {
			  return uid;
		  }
		}

		public void edettable_update(JTable dtable,  String pptid) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);		
			String sql= "select d.dtid, d.pid, p.productcode, p.pname, d.qty,  d.uid, u.uname, d.price, "
					+ "d.discountprice, d.amount, d.vatp, d.vat, d.totalamount, d.discountp, d.discount"
			    + " from stransaction_detail d left join product p on d.pid = p.pid "
			    + " left join unities u  on d.uid = u.uid where tid='" + pptid +"'";	
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String dtid= rs.getString("dtid");				
				String pid = rs.getString("pid");
				String uname =  rs.getString("uname");
				String productcode = rs.getString("productcode");
				String pname = rs.getString("pname");
				String qty = hh.bsf(rs.getString("qty"));				
				String price = hh.bsf(rs.getString("price"));
				String discountprice = hh.bsf(rs.getString("discountprice"));
				String amount = hh.bsf(rs.getString("amount"));
				String vatp = hh.bsf(rs.getString("vatp"));
				String vat = hh.bsf(rs.getString("vat"));
				String totalamount= hh.bsf(rs.getString("totalamount"));
				String discountp = hh.bsf(rs.getString("discountp"));
				String discount = hh.bsf(rs.getString("discount"));					
				 m1.addRow(new Object[] {pid ,productcode, pname, qty, uname, price, discountprice, amount, 
						 vatp, vat, totalamount, discountp, discount});	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
				
		 String[] fej = {"pid", "Productcode", "Product name", "Qty", "Unit", "Price", "Discount price",
					"Amount", "Vat%", "Vat", "Totalamount", "Discount%", "Discount" };				
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);		
		TableColumnModel cm = dtable.getColumnModel();	
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);		
		
		int[][] wcols = { { 1, 100 }, { 2, 220 }, { 3, 90 }, { 4, 80 }, { 5, 100 }, { 6, 110 }, { 7, 120 },
				{ 8, 90 },{ 9, 100 }, { 10, 105 }, { 11, 80 }, { 12, 120 }};			
		hh.cellswidth(dtable, wcols);		
		int[] cols = { 3, 4, 5, 6, 7, 8, 9, 10, 11,12 };
		hh.cellsright(dtable, cols);
		
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}		
	
	public void trantable_update(JTable dtable, String what, String tranheader[]) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String sql = "";		

		if (what == "") {			
			sql= "select t.tid, t.tnumber, t.incomeno, t.transaction_type, t.tdate, t.ptype, t.parid, s.name, t.subtotal, t.vat,"
					+ " t.grandtotal, t.totalpaid, t.balance, t.discount, t.remark "
					+ " from stransaction t left  join partner s on t.parid = s.parid";			
		} else {
			sql= "select t.tid, t.tnumber, t.incomeno,t.transaction_type, t.tdate, t.ptype, t.parid, s.name, t.subtotal, t.vat, "
					+ "t.grandtotal, t.totalpaid, t.balance, t.discount, t.remark "
				    + " from stransaction t  left  join partner s on t.parid = s.parid where " + what;
		}		
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String tid = rs.getString("tid");
				String tnumber = rs.getString("tnumber");
				String incomeno = rs.getString("incomeno");
				String tdate = rs.getString("tdate");
				String ptype = rs.getString("ptype");
				String parid = rs.getString("parid");
				String name = rs.getString("name");
				String subtotal =hh.bsf( rs.getString("subtotal"));
				String vat =hh.bsf( rs.getString("vat"));
				String grandtotal=hh.bsf( rs.getString("grandtotal").toString());
				String totalpaid= hh.bsf(rs.getString("totalpaid").toString());
				String remark = rs.getString("remark").toString();	
				String balance=hh.bsf( rs.getString("balance").toString());
				String discount=hh.bsf( rs.getString("discount").toString());				
				
				m1.addRow(new Object[] { tid, tnumber,incomeno, tdate, ptype, parid, name, subtotal, 
						vat, grandtotal, discount, remark, totalpaid, balance });
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();			
		}	

		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(tranheader);	
		TableColumnModel cm = dtable.getColumnModel();	
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);	
		cm.getColumn(5).setWidth(0);
		cm.getColumn(5).setMinWidth(0);
		cm.getColumn(5).setMaxWidth(0);		
		
		int[][] wcols = { { 1, 120 }, { 2, 120 }, { 3, 100 }, { 4, 100 }, { 6, 250 }, { 7, 100 }, { 8, 100 },
				{ 9, 100 }, { 10, 110 }, { 11, 200 }, { 12, 100 }, { 13, 100 } };
		hh.cellswidth(dtable, wcols);

		int[] cols = { 7, 8, 9, 10, 12, 13 };
		hh.cellsright(dtable, cols);		
	
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}		
	

	public Product getproductrecord(int ppid ){
		pro= new Product();
		String sql = "select  p.pid, p.productcode, p.pname, p.scid, s.sname, p.description, p.costprice, p.sellprice,  "
				+ "p.uid, p.discountp, p.openstock, p.vatp from product p "
				+ " left join subcategory s  on p.scid = s.scid where pid=" + ppid;
		
		try{
			rs = dh.GetData(sql);
			while (rs.next()){
				int pid = rs.getInt("pid");
				String productcode = rs.getString("productcode");
				String pname = rs.getString("pname");
				String costprice = rs.getString("costprice");
				String sellprice = rs.getString("sellprice");
				String discountp = rs.getString("discountp");
				String vatp = rs.getString("vatp");	
				int uid = rs.getInt("uid");
				pro = new Product (pid,pname,costprice, productcode, sellprice, discountp,vatp, uid ); 			
			}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				dh.CloseConnection();
			}		
		return pro;
	}


	public Partner getpartnerrecord(int pparid ){	
		String sql = "select  p.parid, p.name, p.phone, p.email, p.role,  p.country, p.cid, "
				+ "c.name as city, p.address, p.postcode, p.remark, p.gender, p.dob, p.regdate   from partner p  "
				+ " left join cities c  on p.cid = c.cid where parid =" + pparid;		
		Partner pr= new Partner();
		try{
			rs = dh.GetData(sql);
			while (rs.next()){
			int parid = rs.getInt("parid");
			String name = rs.getString("name");			
			String phone = rs.getString("phone");
			String email = rs.getString("email");
			String role = rs.getString("role");		
			String country= rs.getString("country");	
			int cid = rs.getInt("cid");
			String city = rs.getString("city");
			String address = rs.getString("address");
			String postcode = rs.getString("postcode");			
			pr = new Partner (parid, name, phone, email, role, country, cid, city,  address, postcode); 				
			}		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			dh.CloseConnection();
		}
		return pr;	
		}	
	
	public String getregszam(String direction, String doing) throws SQLException {
		String snumber = "";
		int number = 0;
		String sql = "";
		String prefix = "";	
		String year = "";
		int ng = 0;
		if (direction.equals("in")) {
			sql = "select innumber, inprefix, currentyear from params  where parid ='1'";
			ng = 1;
		} else {
			ng = 2;
			sql = "select outnumber, outprefix, currentyear from params  where parid ='1'";
		}
		rs = dh.GetData(sql);
		if (rs.next()) {
			year = rs.getString("currentyear");		
			if (ng == 1) {
				prefix = rs.getString("inprefix");
				number = rs.getInt("innumber") + 1;
			} else {
				prefix = rs.getString("outprefix");
				number = rs.getInt("outnumber") + 1;
			}
		}
		dh.CloseConnection();
		if (doing == "no") {
			snumber = prefix+"-"+ year + "/" + hh.padLeftZeros(hh.itos(number), 5);
			return snumber;
		}	else {
		if (ng == 1) {
			sql = " update  params set innumber ='" + number + "'";
		} else {
			sql = " update  params set outnumber = '" + number + "'";
		}
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			snumber = prefix+"-"+ year + "/" + hh.padLeftZeros(hh.itos(number), 5);
		}
		}
		return snumber;
	}
	
	public String make_snumber(String direction, String doing) {
		String regnumber = "";
		try {
			regnumber = getregszam(direction, doing);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return regnumber;
	}
	}
