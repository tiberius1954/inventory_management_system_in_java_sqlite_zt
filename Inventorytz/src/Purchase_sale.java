import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Product;
import Classes.Partner;
import Classes.Unity;
import Classes.Invoice;

public class Purchase_sale extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Invoice inv = new Invoice();
	protected String[] paymodes= dd.paymodes();
	Partner prr;
	Product prod;
	String rowid = "";
	int myrow = 0;
	JFrame myframe = this;
	static int Trtype = 2;
	  String tranname;
	  String partnername;	 
	  String tranheader[];

	Purchase_sale() {
		if (Trtype ==1) {
		      tranname ="PURCHASES";
		      partnername ="Supplier";
		}else {
			 tranname ="SALES";
			 partnername="Customer";
		}
		init();
		 dd.unitiescombofill(cmbunities);
	    dd.trantable_update(trantable, "transaction_type ="+ Trtype, tranheader);
		 dd.productscombo(cmbproducts);
		 hh.iconhere(this);
	}
	Purchase_sale(int trtype) {
		Trtype = trtype;		
		if (Trtype ==1) {
		      tranname ="PURCHASES";
		      partnername ="Supplier";
		}else {
			 tranname ="SALES";
			 partnername="Customer";
		}
		init();
		 dd.unitiescombofill(cmbunities);
		 dd.trantable_update(trantable, "transaction_type ="+ Trtype, tranheader);
		 dd.productscombo(cmbproducts);
		 hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel) == "table") {
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "table");
				} else {
					cards.show(cardPanel, "table");
					// cmbguest.requestFocus();
				}
			}
		});
		
		setTitle(tranname);	  
		getContentPane().setBackground(hh.citrom);
		setSize(1210, 735);
		setLayout(null);
		setLocationRelativeTo(null);
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1170, 670);
		cardPanel.setBorder(hh.border4);
		tPanel = maketpanel();
		tPanel.setName("table");
		cardPanel.add(tPanel, "table");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(ePanel, "edit");
		etPanel = makeetpanel();
		etPanel.setName("etable");
		cardPanel.add(etPanel, "etable");
		add(cardPanel);

		cards.show(cardPanel, "table");
//	cards.show(cardPanel, "edit");
//  cards.show(cardPanel, "etable");

		lbheader = new JLabel(tranname);
		lbheader.setBounds(30, 5, 290, 30);
		lbheader.setForeground(Color.orange);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		tPanel.add(lbheader);		
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(hh.citrom);
		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(250, 10, 70, 25);
		ttpanel.add(lbsearch);

		txsearch = gr.gTextField(25);
		txsearch.setBounds(325, 10, 200, 30);
	//	txsearch.setFocusable(false);
		ttpanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(525, 10, 25, 30);
		btnclear.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnclear.setText("x");
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				// dd.rectable_update(rectable, "");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(560, 10, 180, 30);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		if (Trtype ==1) {
	        	cmbsearch.addItem("Supplier");
		}else {
		    	cmbsearch.addItem("Customer");
		}
		cmbsearch.addItem("Remark");
		ttpanel.add(cmbsearch);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(750, 10, 130, 33);
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// sqlgyart();
			}
		});
		
	if (	Trtype== 2) {
		btnprint = gr.sbcs("Print invoice");
		btnprint.setForeground(Color.black);
		btnprint.setBackground(Color.ORANGE);
		btnprint.setBounds(1010, 10, 130, 33);
		ttpanel.add(btnprint);
		btnprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) trantable.getModel();
				try {
					int row = trantable.getSelectedRow();
					if (row > -1) {
						String mydid = model.getValueAt(row, 0).toString();
						Invoiceprint invprint = new Invoiceprint(mydid);	
						invprint.setVisible(true);
					}
				} catch (Exception exx) {
					System.out.println("sql error!!!");
				}			
			}
		});
		
	}
		trantable = hh.ztable();
		trantable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) trantable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		trantable.setTableHeader(new JTableHeader(trantable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		trantable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) trantable.getModel();
				try {
					int row = trantable.getSelectedRow();
					if (row > -1) {
						String mydid = model.getValueAt(row, 0).toString();
					  dd.edettable_update(dettable, mydid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(trantable);
		trantable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		tranPane = new JScrollPane(trantable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		       tranheader = new String[] {  "tid", "Transaction_no", "Income_no", "Date", "Payment mode", "parid", 
				partnername, "Subtotal", "Vat", "Grandtotal", "Discount", "Remark" ,"Totalpaid", "Balance" }; 

		trantable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null } },
				tranheader));		
		TableColumnModel cm = trantable.getColumnModel();	
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);	
		cm.getColumn(5).setWidth(0);
		cm.getColumn(5).setMinWidth(0);
		cm.getColumn(5).setMaxWidth(0);			
		int[][] wcols = { { 1, 120 }, { 2, 120 }, { 3, 100 }, { 4, 100 }, { 6, 250 }, { 7, 100 }, { 8, 100 },
				{ 9, 100 }, { 10, 110 }, { 11, 200 }, { 12, 100 }, { 13, 100 } };
		hh.cellswidth(trantable, wcols);
		int[] cols = { 7, 8, 9, 10, 12, 13 };
		hh.cellsright(trantable, cols);		

		tranPane.setViewportView(trantable);
		tranPane.setBounds(30, 60, 1110, 215);
		tranPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(tranPane);

		btnnew = gr.sbcs("New");
		btnnew.setBounds(460, 300, 100, 30);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			   make_new_record();
				cards.show(cardPanel, "edit");
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(565, 300, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		btndelete.addActionListener(e -> data_delete());
		ttpanel.add(btndelete);

		dettable = hh.ztable();
		dettable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer rrenderer = (DefaultTableCellRenderer) dettable.getDefaultRenderer(Object.class);
		rrenderer.setHorizontalAlignment(SwingConstants.LEFT);
		dettable.setTableHeader(new JTableHeader(dettable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(dettable);
		detPane = new JScrollPane(dettable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dettable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null, null, null } },
				new String[] { "pid", "Productcode", "Name", "Quantity", "Unit", "Price", "Discount price", 
						"Amount", "Vat%","Vat", "Totalamount", "Discount%", "Discount"}));

		detPane.setBounds(20, 350, 1120, 300);
		detPane.setViewportView(dettable);
		detPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(detPane);
		TableColumnModel ccm = dettable.getColumnModel();
		ccm.getColumn(0).setWidth(0);
		ccm.getColumn(0).setMinWidth(0);
		ccm.getColumn(0).setMaxWidth(0);		
		int[][] wcolss = { { 1, 100 }, { 2, 220 }, { 3, 90 }, { 4, 80 }, { 5, 100 }, { 6, 110 }, { 7, 120 },
				{ 8, 90 },{ 9, 100 }, { 10, 120 }, { 11, 80 }, { 12, 120 }};		
		hh.cellswidth(dettable, wcolss);		
		int[] colss = { 3, 4, 5, 6, 7, 8, 9, 10, 11,12 };
		hh.cellsright(dettable, colss);
		return ttpanel;

	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(hh.citrom);		
		
		lbheadere = new JLabel("HEADER");
		lbheadere.setBounds(30, 5, 290, 30);
		lbheadere.setForeground(Color.orange);
		lbheadere.setFont(new Font("tahoma", Font.BOLD, 24));
		eepanel.add(lbheadere);

		h1panel = new JPanel(null);
		h1panel.setBounds(75, 150, 1010, 280);
		h1panel.setBorder(hh.border4);
		h1panel.setBackground(hh.citrom);
		eepanel.add(h1panel);

		lbtran_no = hh.clabel((Trtype==1) ?  "PurchaseNo" : "InvoiceNo");
		lbtran_no.setBounds(20, 30, 130, 25);
		h1panel.add(lbtran_no);

		txtran_no = hh.dTextField(150);
		txtran_no.setBounds(160, 30, 140, 25);
		h1panel.add(txtran_no);
		
		lbincomeno = hh.clabel("Income No");
		lbincomeno.setBounds(300, 30, 100, 25);
		h1panel.add(lbincomeno);

		txincomeno = hh.cTextField(150);
		txincomeno.setBounds(410, 30, 150, 25);
		h1panel.add(txincomeno);		

		lbinvoicedate = hh.clabel("Date");
		lbinvoicedate.setBounds(570, 30, 40, 25);
		h1panel.add(lbinvoicedate);

		txtdate = hh.cTextField(150);
		txtdate.setBounds(620, 30, 100, 25);
		h1panel.add(txtdate);
		txtdate.addKeyListener(hh.Onlydate());

		lbpaymentmode = hh.clabel("Payment ");
		lbpaymentmode.setBounds(735, 30, 78, 25);
		h1panel.add(lbpaymentmode);

		cmbpaymode = hh.cbcombo();	
		cmbpaymode.setModel(new DefaultComboBoxModel(paymodes));

		cmbpaymode.setBounds(818, 30, 150, 25);
		h1panel.add(cmbpaymode);
	
		lbpartner = hh.clabel((Trtype==1) ?  "Supplier name" : "Customer name");
		lbpartner.setBounds(20, 70, 130, 25);
		h1panel.add(lbpartner);

		txpartner = hh.dTextField(150);
		txpartner.setBounds(160, 70, 250, 25);
		h1panel.add(txpartner);

		btnpartnersearch = gr.sbcs("Search");
		btnpartnersearch.setForeground(Color.black);
		btnpartnersearch.setBackground(Color.ORANGE);
		btnpartnersearch.setBounds(440, 70, 100, 32);
		btnpartnersearch.setFocusable(false);
		h1panel.add(btnpartnersearch);
		btnpartnersearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Partners pa = new Partners(myframe, "purchase_sale");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});

		lbcountry = hh.clabel("Country ");
		lbcountry.setBounds(540, 70, 90, 25);
		h1panel.add(lbcountry);

		txcountry = hh.dTextField(150);
		txcountry.setBounds(640, 70, 330, 25);
		h1panel.add(txcountry);

		txcid = hh.dTextField(50);
		txparid = hh.dTextField(40);

		lbcity = hh.clabel("City");
		lbcity.setBounds(20, 120, 130, 25);
		h1panel.add(lbcity);

		txcity = hh.dTextField(150);
		txcity.setBounds(160, 120, 250, 25);
		h1panel.add(txcity);

		lbaddress = hh.clabel("Address");
		lbaddress.setBounds(420, 120, 90, 25);
		h1panel.add(lbaddress);

		txaddress = hh.dTextField(150);
		txaddress.setBounds(520, 120, 450, 25);
		h1panel.add(txaddress);

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(20, 170, 130, 25);
		h1panel.add(lbphone);

		txphone = hh.dTextField(150);
		txphone.setBounds(160, 170, 250, 25);
		h1panel.add(txphone);

		lbremark = hh.clabel("Remark");
		lbremark.setBounds(420, 170, 90, 25);
		h1panel.add(lbremark);

		txremark = hh.cTextField(150);
		txremark.setBounds(520, 170, 450, 25);
		h1panel.add(txremark);
		txremark.addKeyListener(hh.MUpper());

		btnnext = gr.sbcs("Next");
		btnnext.setForeground(Color.black);
		btnnext.setBackground(Color.ORANGE);
		btnnext.setBounds(480, 220, 130, 33);
		btnnext.setFocusable(false);
		h1panel.add(btnnext);
		btnnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(cardPanel, "etable");
				cmbproducts.grabFocus();
			}
		});

		btnback = gr.sbcs("Back");
		btnback.setForeground(Color.black);
		btnback.setBackground(Color.ORANGE);
		btnback.setBounds(340, 220, 130, 33);
		btnback.setFocusable(false);
		h1panel.add(btnback);
		btnback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(cardPanel, "table");
			}
		});

		return eepanel;
	}

	private JPanel makeetpanel() {
		JPanel etpanel = new JPanel(null);
		etpanel.setBackground(hh.citrom);
		h3panel = new JPanel(null);
		h3panel.setBounds(110, 20, 470, 290);
		h3panel.setBorder(hh.border4);
		h3panel.setBackground(hh.citrom);
		add(h3panel);
		etpanel.add(h3panel);

		lbheaderf = new JLabel("ITEMS");
		lbheaderf.setBounds(10, 5, 290, 30);
		lbheaderf.setForeground(Color.orange);
		lbheaderf.setFont(new Font("tahoma", Font.BOLD, 24));
		etpanel.add(lbheaderf);

		lbproductname = hh.clabel("Product");
		lbproductname.setBounds(10, 10, 70, 25);
		h3panel.add(lbproductname);

		cmbproducts = gr.grcombo();
		cmbproducts.setName("products");
		cmbproducts.setBounds(90, 10, 240, 30);
		h3panel.add(cmbproducts);
	     cmbproducts.addFocusListener(cFocusListener);

		lbqty = hh.clabel("Qty");
		lbqty.setBounds(10, 50, 110, 25);
		h3panel.add(lbqty);

		txqty = hh.cTextField(150);
		txqty.setBounds(130, 50, 200, 25);
		txqty.setHorizontalAlignment(JTextField.RIGHT);
		h3panel.add(txqty);
		txqty.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
    	txqty.addKeyListener(mustcompute());

		lbunit = hh.clabel("Unit");
		lbunit.setBounds(10, 90, 110, 25);
		h3panel.add(lbunit);

		cmbunities = hh.cbcombo();
		cmbunities.setName("unities");
		cmbunities.setBounds(130, 90, 200, 25);
		cmbunities.setName("unities");
		h3panel.add(cmbunities);

		lbprice = hh.clabel("Price");
		lbprice.setBounds(10, 130, 110, 25);
		h3panel.add(lbprice);

		txprice = hh.cTextField(150);
		txprice.setBounds(130, 130, 200, 25);
		txprice.setHorizontalAlignment(JTextField.RIGHT);
		txprice.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		h3panel.add(txprice);
	    txprice.addKeyListener(mustcompute());

		lbdiscountp = hh.clabel("Discount");
		lbdiscountp.setBounds(10, 170, 110, 25);
		h3panel.add(lbdiscountp);

		txdiscountp = hh.cTextField(150);
		txdiscountp.setBounds(130, 170, 40, 25);
		txdiscountp.setHorizontalAlignment(JTextField.RIGHT);
		h3panel.add(txdiscountp);
		txdiscountp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
	 	txdiscountp.addKeyListener(mustcompute());

		lbdiscountpp = hh.clabel("%");
		lbdiscountpp.setBounds(175, 170, 25, 25);
		h3panel.add(lbdiscountpp);

		txdiscount = hh.dTextField(150);
		txdiscount.setBounds(210, 170, 120, 25);
		txdiscount.setHorizontalAlignment(JTextField.RIGHT);
		h3panel.add(txdiscount);
	//	txdiscount.addKeyListener(hh.Onlynum());

		lbamount = hh.clabel("Amount");
		lbamount.setBounds(10, 210, 110, 25);
		h3panel.add(lbamount);

		txamount = hh.cTextField(150);
		txamount.setBounds(130, 210, 200, 25);
		txamount.setHorizontalAlignment(JTextField.RIGHT);
		txamount.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		h3panel.add(txamount);
	   txamount.addKeyListener(mustcompute());

		lbvatp = hh.clabel("VAT");
		lbvatp.setBounds(10, 250, 110, 25);
		h3panel.add(lbvatp);

		txvatp = hh.dTextField(150);
		txvatp.setBounds(130, 250, 40, 25);
		txvatp.setHorizontalAlignment(JTextField.RIGHT);
		h3panel.add(txvatp);
        txvatp.addKeyListener(hh.Onlynum());

		lbvatpp = hh.clabel("%");
		lbvatpp.setBounds(180, 250, 25, 25);
		h3panel.add(lbvatpp);

		txvat = hh.dTextField(150);
		txvat.setBounds(210, 250, 120, 25);
		txvat.setHorizontalAlignment(JTextField.RIGHT);
		h3panel.add(txvat);
//		txvat.addKeyListener(hh.Onlynum());

		btnsearchp = gr.sbcs("Search");
		btnsearchp.setForeground(Color.black);
		btnsearchp.setBackground(Color.ORANGE);
		btnsearchp.setBounds(350, 10, 100, 32);
		btnsearchp.setFocusable(false);
		h3panel.add(btnsearchp);
		btnsearchp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Products pro = new Products(myframe, "purchase_sale");
			//	pro.setLayout(null);
			//	pro.setLocationRelativeTo(null);
				pro.setVisible(true);
			}
		});

		btnadd = gr.sbcs("Add");
		btnadd.setForeground(Color.black);
		btnadd.setBackground(hh.lpiros);
		btnadd.setBounds(350, 60, 100, 32);
		btnadd.setFocusable(false);
		h3panel.add(btnadd);
       btnadd.addActionListener(e -> buttaddfv());

		btnremove = gr.sbcs("Remove");
		btnremove.setForeground(Color.black);
		btnremove.setBackground(Color.green);
		btnremove.setBounds(350, 110, 100, 32);
		btnremove.setFocusable(false);
		h3panel.add(btnremove);
		btnremove.addActionListener(e -> buttremovefv());

		buttPanel = new JPanel(null);
		buttPanel.setBounds(590, 20, 180, 290);
		buttPanel.setBorder(hh.border4);
		buttPanel.setBackground(hh.citrom);
		add(buttPanel);
		etpanel.add(buttPanel);

		btnsave = gr.sbcs("Save all");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(35, 10, 120, 32);
		btnsave.setFocusable(false);
		buttPanel.add(btnsave);
	    btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel all");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(35, 60, 120, 32);
		btncancel.setFocusable(false);
		buttPanel.add(btncancel);
		btncancel.addActionListener(e -> cancelall());

		btnbackp = gr.sbcs("Back");
		btnbackp.setForeground(Color.black);
		btnbackp.setBackground(Color.ORANGE);
		btnbackp.setBounds(35, 110, 120, 32);
		btnbackp.setFocusable(false);
		buttPanel.add(btnbackp);
		btnbackp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cards.show(cardPanel, "edit");
			}
		});

		h4panel = new JPanel(null);
		h4panel.setBounds(780, 20, 285, 290);
		h4panel.setBorder(hh.border4);
		h4panel.setBackground(hh.citrom);
		add(h4panel);
		etpanel.add(h4panel);

		lbsubtotal = hh.clabel("Sub total");
		lbsubtotal.setHorizontalAlignment(JLabel.LEFT);
		lbsubtotal.setBounds(10, 20, 130, 25);
		h4panel.add(lbsubtotal);

		txsubtotal = hh.dTextField(200);
		txsubtotal.setBounds(125, 20, 150, 25);
		txsubtotal.setHorizontalAlignment(JTextField.RIGHT);
		h4panel.add(txsubtotal);

		lbsumdiscount = hh.clabel("Sum discount");
		lbsumdiscount.setHorizontalAlignment(JLabel.LEFT);
		lbsumdiscount.setBounds(10, 170, 110, 25);
		h4panel.add(lbsumdiscount);

		txsumdiscount = hh.dTextField(150);
		txsumdiscount.setBounds(125, 170, 150, 25);
		txsumdiscount.setHorizontalAlignment(JTextField.RIGHT);
		h4panel.add(txsumdiscount);

		lbgtotal = hh.clabel("Grand total");
		lbgtotal.setHorizontalAlignment(JLabel.LEFT);
		lbgtotal.setBounds(10, 120, 140, 25);
		h4panel.add(lbgtotal);

		txgtotal = hh.dTextField(150);
		txgtotal.setBounds(125, 120, 150, 25);
		txgtotal.setHorizontalAlignment(JTextField.RIGHT);
		h4panel.add(txgtotal);

		lbsumvat = hh.clabel("Sum vat");
		lbsumvat.setHorizontalAlignment(JLabel.LEFT);
		lbsumvat.setBounds(10, 70, 140, 25);
		h4panel.add(lbsumvat);

		txsumvat= hh.dTextField(150);
		txsumvat.setBounds(125, 70, 150, 25);
		txsumvat.setHorizontalAlignment(JTextField.RIGHT);
		h4panel.add(txsumvat);	

		tablepanel = new JPanel(null);
		tablepanel.setBounds(10, 330, 1145, 320);
		tablepanel.setBorder(hh.border4);
		tablepanel.setBackground(hh.citrom);
		etpanel.add(tablepanel);
		edettable = hh.ztable();
		edettable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer erenderer = (DefaultTableCellRenderer) edettable.getDefaultRenderer(Object.class);
		erenderer.setHorizontalAlignment(SwingConstants.LEFT);
		edettable.setTableHeader(new JTableHeader(edettable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		edettable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
			}
		});
		hh.madeheader(edettable);
		edetPane = new JScrollPane(edettable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		edettable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "pid", "Productcode", "Product name", "Qty", "Unit", "Price", "Discount price",
						"Amount", "Vat%", "Vat", "Totalamount", "Discount%", "Discount","Uid" }));		
		TableColumnModel cm = edettable.getColumnModel();
		cm.getColumn(0).setWidth(0);
		cm.getColumn(0).setMinWidth(0);
		cm.getColumn(0).setMaxWidth(0);	
		cm.getColumn(13).setWidth(0);
		cm.getColumn(13).setMinWidth(0);
		cm.getColumn(13).setMaxWidth(0);	
		
		int[][] wcols = { { 1, 100 }, { 2, 200 }, { 3, 90 }, { 4, 100 },{ 5, 100 }, { 6, 110 }, { 7, 100 }, { 8, 80 },
				{ 9, 120 }, { 10, 120 }, { 11, 90 }, { 12, 110 } };
		hh.cellswidth(edettable, wcols);
		
		int[] cols = { 3,4,5, 6, 7, 8, 9, 10, 11, 12 };
		hh.cellsright(edettable, cols);

		edetPane.setViewportView(edettable);
		edetPane.setBounds(10, 10, 1123, 300);
		tablepanel.add(edetPane);
		return etpanel;
	}
	
	private void make_new_record() {
		String snumber;
		clearFields(1);
		clearFields(2);		
		if(Trtype ==1) {
			 snumber = dd.make_snumber("in", "no");
		}else {
			 snumber = dd.make_snumber("out", "no");
		}
		txtran_no.setText(snumber);
		txtdate.setText(hh.currentDate());
		SwingUtilities.invokeLater(() -> txincomeno.requestFocusInWindow());
	}
	private void compute() {
		Double namount = 0.0;
		Double ndiscount = 0.0;
		Double nvat = 0.0;
		Double nvatp = hh.stodd(txvatp.getText());
		Double nqty = hh.stodd(txqty.getText());
		Double nprice = hh.stodd(txprice.getText());
		Double ndiscountp = hh.stodd(txdiscountp.getText());
		inv = new Invoice(nqty, nprice, ndiscountp, nvatp);		
		namount = inv.getAmount();
		ndiscount = inv.getDiscount();
		nvat = inv.getVat();
		txdiscount.setText(hh.sf(ndiscount));
		txamount.setText(hh.sf(namount));
		txvat.setText(hh.sf(nvat));
	}	
	private void clearFields(int par) {
		if (par == 1) {
			txtran_no.setText("");
			txincomeno.setText("");
			txtdate.setText("");		
			txpartner.setText("");
			txcid.setText("");
			txparid.setText("");
			txcity.setText("");
			txaddress.setText("");
			txphone.setText("");
			txremark.setText("");
			txcountry.setText("");
		} else if (par == 2) {
			txqty.setText("");
			txprice.setText("");
			txamount.setText("");
			txsubtotal.setText("");
			txdiscount.setText("");
			txdiscountp.setText("");
			txsumdiscount.setText("");		
			txsumvat.setText("");
			txgtotal.setText("");	
			cmbproducts.setSelectedIndex(0);
			cmbunities.setSelectedIndex(0);
			DefaultTableModel d2 = (DefaultTableModel) edettable.getModel();
			d2.setRowCount(0);
		} else if (par == 3) {
			txqty.setText("");
			txprice.setText("");
			txamount.setText("");
			txdiscount.setText("");
			txdiscountp.setText("");
			txvatp.setText("");
			txvat.setText("");
			cmbproducts.setSelectedIndex(0);
			cmbunities.setSelectedIndex(0);
		}
	}

	private void buttaddfv() {
		DefaultTableModel d1 = (DefaultTableModel) edettable.getModel();
		int pid = (int) ((Product) cmbproducts.getSelectedItem()).getPid();
		String uname = (String) ((Unity) cmbunities.getSelectedItem()).getUname();		
		prod = dd.getproductrecord(pid);
		String productcode = prod.getProductcode();
		int uid = prod.getUid();
		String pname = prod.getPname();
		String qty = hh.bsf(txqty.getText());
		String price = hh.bsf(txprice.getText());	
		String discountp =hh.bsf( txdiscountp.getText());
		String discount = hh.bsf(txdiscount.getText());
		String discountprice =	"";		
		String amount = txamount.getText();
		String vatp =hh.bsf( txvatp.getText());
		String vat = hh.bsf(txvat.getText());
	    Double nvat = hh.stodd(vat);
		Double nqty = hh.stodd(qty);
		Double nprice = hh.stodd(price);
		Double ndiscountp = hh.stodd(discountp);
		Double namount = hh.stodd(amount);
		Double ndiscountprice = 0.0;
		if (ndiscountp > 0.0) {
		    ndiscountprice= nprice * ((100.0 - ndiscountp) / 100.0);		  		
		}
		discountprice = hh.sf(ndiscountprice);		
		Double ntotalamount = namount + nvat;
	 	String totalamount =hh.sf(ntotalamount);		
		d1.addRow(new Object[] { pid, productcode, pname,  qty, uname,  price, discountprice, 
				amount, vatp, vat, totalamount, discountp, discount, uid });
    	subtotalcount();
	    clearFields(3);
		cmbproducts.grabFocus();
	}

	private void buttremovefv() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) edettable.getModel();
		int sIndex = edettable.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		d1.removeRow(sIndex);
		subtotalcount();
		clearFields(3);	
	}

	private void cancelall() {
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to cancel all ?");
		if (a == JOptionPane.YES_OPTION) {
			DefaultTableModel d1 = (DefaultTableModel) edettable.getModel();
			d1.setRowCount(0);
			clearFields(2);
			clearFields(3);
		}
	}
	private void subtotalcount() {
		Double stotal = 0.0;
		Double gtotal = 0.0;
		Double sumvat = 0.0;
		Double sumdiscount = 0.0;
		String totalamt ="";
		DefaultTableModel d1 = (DefaultTableModel) edettable.getModel();
		int row = edettable.getRowCount();
		int column = edettable.getColumnCount();
		for (int i = 0; i < row; i++) {
			totalamt = (String) d1.getValueAt(i, 7).toString();			
			stotal = stotal + hh.stodd(totalamt);
			totalamt = (String) d1.getValueAt(i, 10).toString();
			gtotal = gtotal + hh.stodd(totalamt);
			totalamt = (String) d1.getValueAt(i, 12).toString();
			sumdiscount  = sumdiscount + hh.stodd(totalamt);
			totalamt = (String) d1.getValueAt(i, 9).toString();
			sumvat  = sumvat + hh.stodd(totalamt);
		}
		txsubtotal.setText(hh.sf(stotal));
		txgtotal.setText(hh.sf(gtotal));
		txsumdiscount.setText(totalamt);
		txsumvat.setText(totalamt);
	}
	
	private void data_save() {
		String tnumber;
		int flag = 0;
		int myid = 0;
		DefaultTableModel d1 = (DefaultTableModel) trantable.getModel();
		if(Trtype==1) {
		     tnumber = dd.make_snumber("in", "yes");
		}else {
			tnumber = dd.make_snumber("out", "yes");
		}
		
		String tdate = txtdate.getText();
		String incomeno = txincomeno.getText();
		String partner = txpartner.getText();
		String remark = txremark.getText();
		String ptype = cmbpaymode.getSelectedItem().toString();
		String parid = txparid.getText();
		String subtotal = hh.bsf(txsubtotal.getText());
		String sumdiscount = hh.bsf(txsumdiscount.getText());
		String grandtotal = hh.bsf(txgtotal.getText());
		String sumvat = hh.bsf(txsumvat.getText());
		String totalpaid ="";
		if (ptype == "By cash" ||  ptype=="Credit") {
			totalpaid = grandtotal;
		}

		if (hh.zempty(partner) || hh.zempty(ptype) || !hh.validatedate(tdate, "I")) {
			JOptionPane.showMessageDialog(null, "Please fill the header fields !");
			return;
		}

		String sql = "insert into stransaction (tnumber, incomeno, transaction_type, tdate, ptype, parid, subtotal, "
				+ "vat, grandtotal, discount, remark, totalpaid ) values ( '" + tnumber+"','"+incomeno+"','"+Trtype +"','"
				 + tdate + "','" + ptype + "','" + parid + "','" + subtotal + "','" + sumvat +"','"+grandtotal +"','"
				+  sumdiscount +"','" + remark+  "', '" + totalpaid +"')";
		try {
			flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(tid) AS max_id FROM stransaction");
					d1.addRow(new Object[] { myid, tnumber,incomeno, tdate, ptype, parid, partner, subtotal,
							sumvat, grandtotal, sumdiscount, remark, totalpaid});
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert error");
		}
		if (flag == 1) {
	    	purchase_detail_save(myid);
			hh.gotolastrow(trantable);
		}
		clearFields(1);
		clearFields(2);
		clearFields(3);
		cards.show(cardPanel, "table");
	}

	private void purchase_detail_save(int myid) {
		String sql1;
		DefaultTableModel d1 = (DefaultTableModel) edettable.getModel();
		int row = d1.getRowCount();
		int column = d1.getColumnCount();
		for (int i = 0; i < row; i++) {
			String pid = d1.getValueAt(i, 0).toString();
			String qty = hh.bsf(d1.getValueAt(i, 3).toString());
			String price = (String) d1.getValueAt(i, 5).toString();
			String discountprice = (String) d1.getValueAt(i, 6).toString();
			String amount= (String) d1.getValueAt(i, 7).toString();
			String vatp = (String) d1.getValueAt(i, 8).toString();
			String vat = (String) d1.getValueAt(i, 9).toString();
			String totalamount = (String) d1.getValueAt(i, 10).toString();
			String discountp = (String) d1.getValueAt(i, 11).toString();
			String discount = (String) d1.getValueAt(i, 12).toString();
			String uid = (String) d1.getValueAt(i, 13).toString();
			String tdate = txtdate.getText();
			String lastprice= price;
			if (! hh.zempty(discountp)){
				lastprice= discountprice;
			}
			
			String sql = "insert into stransaction_detail (pid, tid, pid, transaction_type, qty, uid, price, "
					+ " discountprice, amount, vatp, vat, discountp, discount, totalamount, tdate"
					+ " ) values ( '" + pid +"'," +myid+",'"+pid+"','"+Trtype +"','"+ qty +"','"+uid+"','"
					+ price+"','"+ discountprice+"','" + amount + "','" +vatp+"','"+vat+"','"+
					discountp+"','"+discount+"','"+totalamount+"','"+tdate + "')";
	 //           System.out.println(sql);
			if (Trtype==1) {
			       sql1 = " update inventory set last_updated = '" + tdate +"',  inqty = inqty +" + qty + 
					" , stock= stock +"+ qty + ", costprice= '"+ lastprice +"'  where pid=" + pid;

			}else{
				 sql1 = " update inventory set   outqty = outqty +" + qty + 
							" , stock= stock -"+ qty + "  where pid=" + pid;
			}
	
			try {
				int flag = dh.Insupdel(sql);
				if (flag != 1) {
					JOptionPane.showMessageDialog(null, "sql insert error !");
				}
				flag = dh.Insupdel(sql1);
				if (flag != 1) {
					JOptionPane.showMessageDialog(null, "sql insert error !");
				}
			} catch (Exception e) {
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "sql insert error");
			}
		}
	}
	private void data_delete() {
		int flag = 0;
		String sql = "";
		DefaultTableModel d1 = (DefaultTableModel) trantable.getModel();
		int sIndex = trantable.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String tid = d1.getValueAt(sIndex, 0).toString();
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			if (!hh.zempty(tid)) {
				sql = " delete from stransaction where tid  =" + tid;
				flag = dh.Insupdel(sql);
				if (flag > 0) {
					d1.removeRow(sIndex);
				}
				if (trantable.getRowCount() > 0) {
					int row = trantable.getRowCount() - 1;
					trantable.setRowSelectionInterval(row, row);
				}
			}
		
			DefaultTableModel d2 = (DefaultTableModel) dettable.getModel();
			int row = d2.getRowCount();		
			for (int i = 0; i < row; i++) {
				String pid = d2.getValueAt(i, 0).toString();
				String qty = hh.bsf(d2.getValueAt(i, 3).toString());
				if (Trtype==1) {
				       sql = " update inventory set inqty = inqty -" + qty + 
						" , stock= stock -"+ qty  +"'  where pid=" + pid;
				}else{
					 sql = " update inventory set   outqty = outqty -" + qty + 
								" , stock= stock +"+ qty +"  where pid=" + pid;
				}		
				flag = dh.Insupdel(sql);					
			}		
			sql = " delete from stransaction_detail where tid  =" + tid;
			flag = dh.Insupdel(sql);
			if (flag > 0) {				
				d2.setRowCount(0);
			}
		}
	}

	public void passtocmbproducts(int number) {
		dd.productscombo(cmbproducts);
		hh.setSelectedValue(cmbproducts, number);
		cmbproducts.updateUI();
		  prod = dd.getproductrecord(number);
			txvatp.setText(prod.getVatp());			
			int uid = prod.getUid();
			hh.setSelectedValue(cmbunities, uid);
			cmbunities.updateUI();
			if (Trtype == 2) {
				String sellprice = prod.getSellprice();
		     	txprice.setText(sellprice);
			}
	}
	public void passtopartner(int parid) {
		prr = dd.getpartnerrecord(parid);
		txpartner.setText(prr.getName());
		txcountry.setText(prr.getCountry());
		txcity.setText(prr.getCity());
		txaddress.setText(prr.getAddress());
		txphone.setText(prr.getPhone());
		txcid.setText(hh.itos(prr.getCid()));
		txparid.setText(hh.itos(parid));
	}


	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Purchase_sale();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public KeyListener  mustcompute() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == '-' || c == '.') {
				} else if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
					e.getSource();
				}
			}

			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == evt.VK_TAB || evt.getKeyCode() == evt.VK_ENTER) {
					compute();
					if (evt.getSource() == txqty) {
						txprice.grabFocus();
					} else if (evt.getSource() == txprice) {
						txdiscountp.grabFocus();
					} else if (evt.getSource() == txdiscountp) {
						compute();
						txamount.grabFocus();
					}
				}
			}
		};
		return keyListener;
	}
	private final FocusListener cFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
			// c.setBorder(hh.borderz);
		}

		@Override
		public void focusLost(FocusEvent e) {
			boolean ret = true;
			JComponent b = (JComponent) e.getSource();
			JComboBox bb = (JComboBox) e.getSource();
			if (bb.getName() == "products") {
				Product product = (Product) cmbproducts.getSelectedItem();
				int pid = (int) ((Product) cmbproducts.getSelectedItem()).getPid();
				int uid = (int) ((Product) cmbproducts.getSelectedItem()).getUid();
				String sellprice = (String) ((Product) cmbproducts.getSelectedItem()).getSellprice();
				String pname = (String) ((Product) cmbproducts.getSelectedItem()).toString();
				hh.setSelectedValue(cmbunities, uid);
				cmbunities.updateUI();
		        prod = dd.getproductrecord(pid);
				txvatp.setText(prod.getVatp());
				if (Trtype == 2) {
			     	txprice.setText(sellprice);
				}
				txqty.grabFocus();
			}
		}
	};

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, hh.zold));
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		textField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		return textField;
	}

	CardLayout cards;
	JPanel h1panel, h2panel, h3panel, h4panel, tablepanel, cardPanel, ePanel, tPanel, etPanel, buttPanel;
	JLabel lbheader, lbsearch;
	JLabel lbheadere, lbtran_no, lbinvoicedate, lbpaymentmode, lbpartner, lbcountry, lbaddress, lbcity, lbphone,
	lbremark, lbincomeno;
	JLabel lbheaderf, lbproductname, lbqty, lbprice, lbunit, lbdiscountp, lbdiscountpp, lbamount, lbvatp, lbvatpp;
	JTextField txtran_no, txtdate, txpartner, txcountry,txaddress, txcity, txphone, txcid, txparid, txremark,
	txincomeno, txqty, txprice, txdiscountp, txdiscount, txamount, txvatp, txvat;
	JComboBox cmbsearch, cmbpaymode, cmbproducts, cmbunities;
	JButton btnsearch, btnclear, btnnew, btndelete, btnpartnersearch, btnnext, btnback, btnsearchp, btnadd,
	btnremove, btnsave, btncancel, btnbackp, btnprint;
	JLabel lbsubtotal,lbsumvat, lbsumdiscount, lbgtotal;
	JTextField txsearch, txsubtotal, txsumvat, txsumdiscount, txgtotal;
	JTable trantable, dettable,edettable;
	 JScrollPane tranPane, detPane, edetPane;
}
