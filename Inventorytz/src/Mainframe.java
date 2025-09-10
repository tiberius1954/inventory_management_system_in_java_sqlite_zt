import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import Classes.Grlib;

public class Mainframe extends JFrame {
	static double zoom = 0.0;
	BufferedImage img;
	private MyPanel panel;
	private JPanel gpanel;
	JButton btnpurchase, btnsale, btninventory, btnpartner, btnproduct, btncategory, btnsubcategory,
	btnparam, btncities, btnexit, btnunity, btnbanks, btnbankchoose;
	Container con = getContentPane();
	Grlib gr = new Grlib();

	public static void main(String[] args) throws InterruptedException {
		Mainframe frame = new Mainframe();
		while (zoom < 1.0) {
			zoom += 0.01;
			frame.getPanel().repaint();
			try {
				Thread.sleep(12);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Mainframe() {
	   setSize(1200, 680);	
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		try {
			img = ImageIO.read(getClass().getResource("images/inventory.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		panel = new MyPanel();
		panel.setLayout(null);
		add(panel);

		btnpurchase = gr.sbcs("Purchases");
		btnpurchase.setBackground(Color.yellow);
		btnpurchase.setBounds(50, 100, 200, 40);
		btnpurchase.addActionListener(e -> purchase_call());

		btnsale = gr.sbcs("Sales");
		btnsale.setBackground(Color.yellow);
		btnsale.setBounds(50, 160, 200, 40);
		btnsale.addActionListener(e -> sale_call());
		
		btninventory = gr.sbcs("Inventory");
		btninventory.setBackground(Color.yellow);
		btninventory.setBounds(50, 220, 200, 40);
		btninventory.addActionListener(e -> inventory_call());

		btnpartner = gr.sbcs("Partners");
		btnpartner.setBackground(Color.yellow);
		btnpartner.setBounds(50, 280, 200, 40);
		btnpartner.addActionListener(e -> partner_call());

		btnproduct= gr.sbcs("Products");
		btnproduct.setBackground(Color.yellow);
		btnproduct.setBounds(50, 340, 200, 40);
		btnproduct.addActionListener(e -> product_call());

		btncategory = gr.sbcs("Categories");
		btncategory.setBackground(Color.yellow);
		btncategory.setBounds(50, 400, 200, 40);
	    btncategory.addActionListener(e -> category_call());
		
		btnsubcategory = gr.sbcs("Subcategories");
		btnsubcategory.setBackground(Color.yellow);
		btnsubcategory.setBounds(50, 460, 200, 40);
 		btnsubcategory.addActionListener(e -> subcategory_call());

	
		btnexit = gr.sbcs("Exit");
		btnexit.setBackground(Color.yellow);
		btnexit.setBounds(50, 520, 200, 40);
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x, y, d;
				x = 800;
				y = 500;
				d = 10;
				while (x > 0 && y > 0) {
					setSize(x, y);
					x = x - 2 * d;
					y = y - d;
					setVisible(true);
					try {
						Thread.sleep(10);
					} catch (Exception e1) {
						System.out.println("Error:" + e1);
					}
				}
				dispose();
			}
		});	
		
		btncities = gr.sbcs("Cities");
		btncities.setBackground(Color.yellow);
		btncities.setBounds(950, 100, 200, 40);
		btncities.addActionListener(e -> cities_call());
		
		btnunity = gr.sbcs("Unities");
		btnunity.setBackground(Color.yellow);
		btnunity.setBounds(950, 160, 200, 40);
		btnunity.addActionListener(e -> unities_call());
		
		btnbanks = gr.sbcs("Banks");
		btnbanks.setBackground(Color.yellow);
		btnbanks.setBounds(950, 220, 200, 40);
		btnbanks.addActionListener(e -> banks_call());
		
		btnbankchoose = gr.sbcs("Bank choose");
		btnbankchoose.setBackground(Color.yellow);
		btnbankchoose.setBounds(950, 280, 200, 40);
		btnbankchoose.addActionListener(e -> bankchoose_call());
		
		btnparam = gr.sbcs("Parameters");
		btnparam.setBackground(Color.yellow);
		btnparam.setBounds(950, 340, 200, 40);
		btnparam.addActionListener(e -> param_call());
	
		this.setVisible(true);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				panel.add(btnpurchase);
				panel.add(btnsale);
				panel.add(btninventory);
				panel.add(btnpartner);
				panel.add(btnproduct);
				panel.add(btncategory);
				panel.add(btnsubcategory);
				panel.add(btnexit);
				panel.add(btncities);
				panel.add(btnunity);
				panel.add(btnbanks);
				panel.add(btnbankchoose);
				panel.add(btnparam);			
			}
		}, 1200);

	}

	public MyPanel getPanel() {
		return panel;
	}

	public class MyPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			double width = getWidth();
			double height = getHeight();
			double zoomWidth = width * zoom;
			double zoomHeight = height * zoom;
			double anchorx = (width - zoomWidth) / 2;
			double anchory = (height - zoomHeight) / 2;
			AffineTransform at = new AffineTransform();
			at.translate(anchorx, anchory);
			at.scale(zoom, zoom);
			at.translate(0, 0);
			g2d.setTransform(at);
			g2d.drawImage(img, 0, 0, this);
			g2d.dispose();
		}
	}

	private void sale_call() {
		Purchase_sale us = new Purchase_sale(2);
		us.setVisible(true);
	}

	private void purchase_call() {
		Purchase_sale pu = new Purchase_sale(1);
		pu.setVisible(true);
	}

	private void inventory_call() {
   	Inventory pt = new Inventory();
	//	pt.setVisible(true);
	}

	private void partner_call() {
		Partners py = new Partners();
		py.setVisible(true);
	}

	private void product_call() {
		Products tr = new Products();
		tr.setVisible(true);
	}
	
	private void category_call() {
		Categories tr = new Categories();
		tr.setVisible(true);
	}
	private void subcategory_call() {
		Subcategories tr = new Subcategories();
		tr.setVisible(true);
	}
	private void param_call() {
		Settings ct = new Settings();
		ct.setVisible(true);
	}

	private void cities_call() {
		Lcities ct = new Lcities();
		ct.setVisible(true);
	}
	private void unities_call() {
		Unities ct = new Unities();
		ct.setVisible(true);
	}
	private void banks_call() {
		Banks ba = new Banks();
		ba.setVisible(true);
	}
	
	private void bankchoose_call() {
		Bankchoose ba = new Bankchoose();
		ba.setVisible(true);	
	}
}