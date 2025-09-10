
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import Classes.Allvalid;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Settings extends JFrame{
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Allvalid allv = new Allvalid();
	
	Settings(){
		initcomponents();
		reading();
    	hh.iconhere(this);
		txname.requestFocus();
		
	}
	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));

		setTitle("Settings");
		setSize(835, 545);
		setLayout(null);
		setLocationRelativeTo(null);	 
		cp = getContentPane();
		cp.setBackground(hh.citrom);	
		lbheader = hh.flabel("SETTINGS");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setForeground(Color.orange);
		add(lbheader);		
		lpanel = new JPanel(null);
		
		lpanel.setBounds(10,60, 520, 350);
	    lpanel.setBackground(hh.citrom);
	    lpanel.setBorder(hh.ztroundborder(hh.szold));
		add(lpanel);
		
		lbname = hh.clabel("Name of firm:");
		lbname.setBounds(10, 20, 120, 25);
		lpanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(150, 20, 340, 25);
		lpanel.add(txname);
		txname.addKeyListener(hh.MUpper());
		
		lbcountry= hh.clabel("Country:");
		lbcountry.setBounds(10, 60, 120, 25);
		lpanel.add(lbcountry);

		txcountry= cTextField(25);
		txcountry.setBounds(150, 60, 340, 25);
		lpanel.add(txcountry);
		txcountry.addKeyListener(hh.MUpper());		
		
		lbcity = hh.clabel("City:");
		lbcity.setBounds(10, 100, 120, 25);
		lpanel.add(lbcity);

		txcity = cTextField(25);
		txcity.setBounds(150, 100, 340, 25);
		lpanel.add(txcity);
		txcity.addKeyListener(hh.MUpper());
		
		lbaddress = hh.clabel("Address:");
		lbaddress.setBounds(10, 140, 120, 25);
		lpanel.add(lbaddress);

		txaddress = cTextField(25);
		txaddress.setBounds(150, 140, 340, 25);
		lpanel.add(txaddress);
		txaddress.addKeyListener(hh.MUpper());		
		
		lbpostcode = hh.clabel("Postcode:");
		lbpostcode.setBounds(10, 180, 120, 25);
		lpanel.add(lbpostcode);

		txpostcode = cTextField(25);
		txpostcode.setBounds(150, 180, 340, 25);
		lpanel.add(txpostcode);
		
		lbphone = hh.clabel("Phone:");
		lbphone.setBounds(10, 220, 120, 25);
		lpanel.add(lbphone);

		txphone = cTextField(25);
		txphone.setBounds(150, 220, 340, 25);
		lpanel.add(txphone);
		
		lbemail = hh.clabel("Email:");
		lbemail.setBounds(10, 260, 120, 25);
		lpanel.add(lbemail);

		txemail = cTextField(25);
		txemail.setBounds(150, 260, 340, 25);
		lpanel.add(txemail);		
		
		lbbankacc = hh.clabel("Bank account");
		lbbankacc.setBounds(10, 300, 120, 25);
		lpanel.add(lbbankacc);

		txbankacc = cTextField(25);
		txbankacc.setBounds(150, 300, 340, 25);
		lpanel.add(txbankacc);		
		
		rpanel = new JPanel(null);
		rpanel.setBounds(531,60, 275, 350);
	    rpanel.setBackground(hh.citrom);
	    rpanel.setBorder(hh.ztroundborder(hh.szold));
		add(rpanel);
		
		lbcurrentyear = hh.clabel("Current year:");
		lbcurrentyear.setBounds(10, 20, 160, 25);
		rpanel.add(lbcurrentyear);

		txcurrentyear= cTextField(25);
		txcurrentyear.setBounds(180, 20, 70, 25);
		rpanel.add(txcurrentyear);
		txcurrentyear.addKeyListener(hh.Onlynum());
		
		lbinprefix = hh.clabel("Prefix in number:");
		lbinprefix.setBounds(10, 60, 160, 25);
		rpanel.add(lbinprefix);

		txinprefix = cTextField(2);
		txinprefix.setBounds(180, 60, 70, 25);
		rpanel.add(txinprefix);	
		txinprefix.addKeyListener(hh.Onlyalphabet(txinprefix));		
		
		lbin_number = hh.clabel("In Number: ");
		lbin_number.setBounds(10, 100, 160, 25);
		rpanel.add(lbin_number);

		txinnumber= cTextField(25);
		txinnumber.setBounds(180, 100, 70, 25);
		txinnumber.setHorizontalAlignment(JTextField.RIGHT);
		rpanel.add(txinnumber);
		txinnumber.addKeyListener(hh.Onlynum());
		
		lboutprefix = hh.clabel("Prefix out number:");
		lboutprefix.setBounds(10, 150, 160, 25);
		rpanel.add(lboutprefix);

		txoutprefix = cTextField(2);
		txoutprefix.setBounds(180, 150, 70, 25);
		rpanel.add(txoutprefix);	
		txoutprefix.addKeyListener(hh.Onlyalphabet(txoutprefix));		
		
		lbout_number = hh.clabel("Out number:");
		lbout_number.setBounds(10, 190, 160, 25);
		rpanel.add(lbout_number);

		txoutnumber= cTextField(25);
		txoutnumber.setBounds(180, 190, 70, 25);
		txoutnumber.setHorizontalAlignment(JTextField.RIGHT);
		rpanel.add(txoutnumber);
		txoutnumber.addKeyListener(hh.Onlynum());
		
		btnsave = hh.cbutton("Save");
		btnsave.setBounds(300, 440, 120, 32);
		btnsave.setBackground(hh.vpiros1);
		add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				 savebuttrun();
			}
		});
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(425, 440, 120, 32);
		add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			dispose();
			}
		});	
	}
	private void reading() {
		try {
			 String sql = " select * from params where parid = '1'";  
			
			rs = dh.GetData(sql);
			if (rs.next()) {
			      txname.setText(rs.getString("name"));
			      txcountry.setText(rs.getString("country"));
			      txcity.setText(rs.getString("city"));
			      txaddress.setText(rs.getString("address"));			 
			      txpostcode.setText(rs.getString("postcode"));
			      txphone.setText(rs.getString("phone"));
			      txemail.setText(rs.getString("email"));
			      txinprefix.setText(rs.getString("inprefix"));
			      txoutprefix.setText(rs.getString("outprefix"));
			      txcurrentyear.setText(rs.getString("currentyear"));
			      txinnumber.setText(rs.getString("innumber"));
			      txoutnumber.setText(rs.getString("outnumber"));	
			      txbankacc.setText(rs.getString("bankaccount"));
			} 
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}	  	
	   }
	private void  savebuttrun() {
		String sql = "";
		String jel = "";		
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String address = txaddress.getText();
		String country = txcountry.getText();		
		String city = txcity.getText();	
		String postcode = txpostcode.getText();
		String currentyear = txcurrentyear.getText();
		String inprefix = txinprefix.getText();
		String innumber= txinnumber.getText();	
		String outprefix = txoutprefix.getText();
		String outnumber = txoutnumber.getText();
       String bankacc = txbankacc.getText();
		if (ppvalidation(name, country, city, address,  currentyear, inprefix, outprefix) == false) {
			return;
		}		
		sql = "update  params set name= '" + name + "', phone= '" + phone + "'," + "email = '" + email
				+ "', country = '" + country + "', address= '" + address + "',city = '" + city
				+ "', postcode = '" + postcode +"',bankaccount= '" + bankacc +"', currentyear= '" + currentyear 
				 + "', inprefix='" + inprefix + "', innumber='" + innumber
				 + "', outprefix='" + outprefix+ "', outnumber='" + outnumber + "' where parid = '1'";
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			hh.ztmessage("Success", "Message");
		}		
	}
		private Boolean ppvalidation(String name, String country, String city, String address, String currentyear,
				String inprefix, String outprefix) {
			Boolean ret = true;
			ArrayList<String> err = new ArrayList<String>();

			if (!allv.namevalid(name)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.countryvalid(country)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.addressvalid(address)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.cityvalid(city)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.currentyearvalid(currentyear)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.prefixvalid(inprefix)) {
				err.add(allv.mess);
				ret = false;
		}
			if (!allv.prefixvalid(outprefix)) {
				err.add(allv.mess);
				ret = false;
		}
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
			return ret;
		}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		// textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	public static void main(String args[]) {
		Settings st = new Settings();		
		st.setVisible(true);
	}
JLabel lbheader;
JPanel fejpanel,lpanel, rpanel;
Container cp;
JComboBox cmbcountries;
JTextField txname, txpostcode, txaddress, txcity, txcountry, txphone, txemail,
txinnumber, txoutnumber, txinprefix, txoutprefix, txcurrentyear, txbankacc;
JLabel lbname, lbpostcode, lbcountry, lbaddress, lbcity, lbphone, lbemail, lbin_number, lbbankacc, 
lbout_number, lbinprefix, lboutprefix,
lbcurrentyear;
JButton btnsave, btncancel;
}
