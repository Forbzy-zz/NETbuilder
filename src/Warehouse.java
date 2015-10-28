import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import java.io.*;

import javax.swing.JTable;

public class Warehouse extends JFrame {
	private int ID;
	// private int productID;
	// private int quantity;
	private String name;
	// private int orderID;

	private JFrame mainFrame;
	private JTabbedPane jTabbedPane;
	private Panel tabbedPaneProduct, tabbedPaneOrder, tabbedPanePInfo, tabbedPaneOInfo;

	private JButton mProductDetailsButton, mOrderDetailsButton, nextPButton, nextOButton, backPButton, backOButton,
			findProdButton;

	private JTextField productID, productName, stockLevels, porousStockLevel, price, location, porousNeed,
			porousApplied, dateManufactured;

	private JTextField orderID, orderDate, quantity, orderType, totalCost;

	public static AccessDB db;

	private String storeID, storeName, storeLoc, storePrice, storePSL, storeSL, storePNeed, storePApplied, storeDate,
			oID;

	private JTextField inputOID;

	private String storeOrderID, storeOrderDate, storeQuantity, storeOrderType, storeTotal;

	private JPanel labelPanel1, textPanel1, labelPanel2, textPanel2, labelPanel3, textPanel3, labelPanel4, textPanel4,
			labelPanel5, textPanel5, labelPanel6, textPanel6;

	private JLabel idLabel, nameLabel, slLabel, pslLabel, pLabel, locLabel, pnLabel, paLabel, dMLabel;

	private JLabel idLabelV, nameLabelV, slLabelV, pslLabelV, pLabelV, locLabelV, pnLabelV, paLabelV, dMLabelV;

	private JLabel oIDLabel, oDLabel, qLabel, oTLabel, tLabel;

	private JLabel oIDLabelV, oDLabelV, qLabelV, oTLabelV, tLabelV;

	private String n, pls, l, pn, pa, dm, oT, pid, sl, p, oid, oD, q, tC, ato;

	int[] arrayOrderID = new int[1000];

	Object[] column = { "ID: ", "Name: ", "Stock Levels: ", "Porous Stock Level: ", "Price: ", "Location: ",
			"Porous Needed: ", "Porous Applied: ", "Date Manufactured: " };
	Object[][] data = {};

	OrderDetails order;
	orderLine line;
	Product prod;

	public Warehouse() {

		order = new OrderDetails();
		line = new orderLine();
		prod = new Product();

		mainFrame = new JFrame("Warehouse Order Tracking System");

		jTabbedPane = new JTabbedPane();

		mainFrame.setSize(400, 400);
		mProductDetailsButton = new JButton("Add Product");
		mOrderDetailsButton = new JButton("Add Order");
		nextPButton = new JButton("Next Product");
		nextOButton = new JButton("Next Order");
		backPButton = new JButton("Previous Product");
		backOButton = new JButton("Previous Order");
		findProdButton = new JButton("Order ID");

		labelPanel1 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel1 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel2 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel2 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel3 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel3 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel4 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel4 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel5 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel5 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel6 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel6 = new JPanel(new GridLayout(9, 1, 4, 4));

		//
		idLabel = new JLabel("ID: ");
		nameLabel = new JLabel("Name: ");
		slLabel = new JLabel("Stock Levels: ");
		pslLabel = new JLabel("Porous Stock Level: ");
		pLabel = new JLabel("Price: ");
		locLabel = new JLabel("Location: ");
		pnLabel = new JLabel("Porous Needed: ");
		paLabel = new JLabel("Porous Applied: ");
		dMLabel = new JLabel("Date Manufactured: ");

		idLabelV = new JLabel(pid);
		nameLabelV = new JLabel(name);
		slLabelV = new JLabel(sl);
		pslLabelV = new JLabel(pls);
		pLabelV = new JLabel(p);
		locLabelV = new JLabel(l);
		pnLabelV = new JLabel(pn);
		paLabelV = new JLabel(pa);
		dMLabelV = new JLabel(dm);

		//
		oIDLabel = new JLabel("Order ID: ");
		oDLabel = new JLabel("Date Ordered: ");
		qLabel = new JLabel("Quantity: ");
		oTLabel = new JLabel("Order Type: ");
		tLabel = new JLabel("Total: ");

		oIDLabelV = new JLabel(oid);
		oDLabelV = new JLabel(oD);
		qLabelV = new JLabel(q);
		oTLabelV = new JLabel(oT);
		tLabelV = new JLabel(tC);

		// input boxes for the product attributes
		productID = new JTextField(10);
		productName = new JTextField(10);
		stockLevels = new JTextField(10);
		porousStockLevel = new JTextField(10);
		price = new JTextField(10);
		location = new JTextField(10);
		porousNeed = new JTextField(10);
		porousApplied = new JTextField(10);
		dateManufactured = new JTextField(10);

		// input boxes for the order details attributes
		orderID = new JTextField();
		// productID = new JTextField();
		orderDate = new JTextField(10);
		quantity = new JTextField(10);
		orderType = new JTextField(10);
		totalCost = new JTextField(10);

		inputOID = new JTextField(10);

		/*
		 * mainFrame.addWindowListener(new WindowAdapter() { public void
		 * windowClosing(WindowEvent windowEvent) { System.exit(0); } });
		 */

		tabbedPaneProduct = new Panel();
		tabbedPaneOrder = new Panel();
		tabbedPanePInfo = new Panel();
		tabbedPaneOInfo = new Panel();

		jTabbedPane.addTab("Add Product", tabbedPaneProduct);
		jTabbedPane.addTab("Add Order", tabbedPaneOrder);
		jTabbedPane.addTab("Product Information", tabbedPanePInfo);
		jTabbedPane.addTab("Order Information", tabbedPaneOInfo);

		tabbedPaneProduct.add(labelPanel1, BorderLayout.WEST);
		tabbedPaneProduct.add(textPanel1, BorderLayout.EAST);

		textPanel1.add(productID);
		textPanel1.add(productName);
		textPanel1.add(stockLevels);
		textPanel1.add(porousStockLevel);
		textPanel1.add(price);
		textPanel1.add(location);
		textPanel1.add(porousNeed);
		textPanel1.add(porousApplied);
		textPanel1.add(dateManufactured);

		tabbedPaneProduct.add(mProductDetailsButton);

		tabbedPaneOrder.add(labelPanel2, BorderLayout.WEST);
		tabbedPaneOrder.add(textPanel2, BorderLayout.EAST);

		textPanel2.add(orderID);
		textPanel2.add(orderDate);
		textPanel2.add(quantity);
		textPanel2.add(orderType);
		textPanel2.add(totalCost);

		tabbedPaneOrder.add(mOrderDetailsButton);

		// Prod.viewProductDetails();

		tabbedPanePInfo.add(prod.createTable());// adds Product Table to GUI

		// tabbedPanePInfo.add(labelPanel3, BorderLayout.WEST);
		// tabbedPanePInfo.add(labelPanel4, BorderLayout.EAST);

		labelPanel4.add(idLabelV);
		labelPanel4.add(nameLabelV);
		labelPanel4.add(slLabelV);
		labelPanel4.add(pslLabelV);
		labelPanel4.add(pLabelV);
		labelPanel4.add(locLabelV);
		labelPanel4.add(pnLabelV);
		labelPanel4.add(paLabelV);
		labelPanel4.add(dMLabelV);

		tabbedPanePInfo.add(nextPButton);
		tabbedPanePInfo.add(backPButton);

		order.viewOrderDetails();

		tabbedPaneOInfo.add(inputOID);
		tabbedPaneOInfo.add(findProdButton);
		tabbedPaneOInfo.add(order.createTable());

		// tabbedPaneOInfo.add(labelPanel5, BorderLayout.WEST);
		// tabbedPaneOInfo.add(labelPanel6, BorderLayout.EAST);

		labelPanel6.add(oIDLabelV);
		labelPanel6.add(oDLabelV);
		labelPanel6.add(qLabelV);
		labelPanel6.add(oTLabelV);
		labelPanel6.add(tLabelV);

		tabbedPaneOInfo.add(nextOButton);
		tabbedPaneOInfo.add(backOButton);

		mainFrame.add(jTabbedPane);

		mainFrame.setVisible(true);
		mainFrame.setSize(500, 500);

		findProdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oID = inputOID.getText();
				line.viewOrderLineResults(oID);
			}
		});

		mProductDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeID = productID.getText();
				storeName = productName.getText();
				storeSL = stockLevels.getText();
				storePSL = porousStockLevel.getText();
				storePrice = price.getText();
				storeLoc = location.getText();
				storePNeed = porousNeed.getText();
				storePApplied = porousApplied.getText();
				storeDate = dateManufactured.getText();

				createProduct();

			}
		});

		mOrderDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeOrderID = orderID.getText();
				storeOrderDate = orderDate.getText();
				storeQuantity = quantity.getText();
				storeOrderType = orderType.getText();
				storeTotal = totalCost.getText();

				createOrder();

				tabbedPaneOInfo.remove(order.getJPane());
				order.viewOrderDetails();
				tabbedPaneOInfo.add(order.createTable());
				tabbedPaneOInfo.revalidate();
				System.out.println(e);
			}
		});

		jTabbedPane.addChangeListener(new tabOnFocus(this));

	}

	public void tabsOnFocus(ChangeEvent e) {
		int selectedIndex = jTabbedPane.getSelectedIndex();

		if (selectedIndex == 0) {
			labelPanel1.add(idLabel);
			labelPanel1.add(nameLabel);
			labelPanel1.add(slLabel);
			labelPanel1.add(pslLabel);
			labelPanel1.add(pLabel);
			labelPanel1.add(locLabel);
			labelPanel1.add(pnLabel);
			labelPanel1.add(paLabel);
			labelPanel1.add(dMLabel);
		}
		if (selectedIndex == 1) {
			labelPanel2.add(oIDLabel);
			labelPanel2.add(oDLabel);
			labelPanel2.add(qLabel);
			labelPanel2.add(oTLabel);
			labelPanel2.add(tLabel);
		}
		if (selectedIndex == 2) {
			labelPanel3.add(idLabel);
			labelPanel3.add(nameLabel);
			labelPanel3.add(slLabel);
			labelPanel3.add(pslLabel);
			labelPanel3.add(pLabel);
			labelPanel3.add(locLabel);
			labelPanel3.add(pnLabel);
			labelPanel3.add(paLabel);
			labelPanel3.add(dMLabel);
		}
		if (selectedIndex == 3) {
			labelPanel5.add(oIDLabel);
			labelPanel5.add(oDLabel);
			labelPanel5.add(qLabel);
			labelPanel5.add(oTLabel);
			labelPanel5.add(tLabel);
		}
	}

	public static void main(String[] args) {
		db = new AccessDB();
		Warehouse sD = new Warehouse();
		// sD.setVisible(true);
		db.accessBD();
	}

	void modifyProductDetails() {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql3 = "UPDATE Languages " + "SET date = 1994 WHERE id in (1, 2)";
			stmt.executeUpdate(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void modifyOrderDetails(Object value, int row, int col) {
		Connection conn = null;
		Statement stmt = null;
		String[] columnNames = new String[] { "orderID", "oderDate", "quantity", "orderType", "totalCost" };
		String[] rowNames = new String[]{};

		for(int i = 0; i<1000; i++){
			rowNames[i] = String.valueOf(i);
		}
		
		System.out.println("Modifying Order");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String sql3 = "UPDATE orderdetails " + "SET " + columnNames[col] + 
					" = " + value + " FROM orderdetails( SELECT " + columnNames[col] + 
					", ROW_NUMBER() OVER (ORDER BY " + columnNames[col] + ") AS rowNum)" +
				    " WHERE rowNum = " + rowNames[row];
			
			stmt.executeUpdate(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Order Modified");

	}

	void createOrder() {

		Connection conn = null;
		Statement stmt = null;

		System.out.println("Inserting records into the table...");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String values = "VALUES (" + storeOrderID + "," + storeOrderDate + "," + storeQuantity + ",' "
					+ storeOrderType + " ' ," + storeTotal + " )";
			String sql = "INSERT INTO orderdetails " + values;
			stmt.executeUpdate(sql);
			System.out.println("Inserted records into the table...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	void createProduct() {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Inserting records into the table...");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String values = "VALUES (" + storeID + ", ' " + storeName + " ', " + storeSL + ", " + storePSL + ", "
					+ storePrice + ", ' " + storeLoc + "' , '" + storePNeed + "', '" + storePApplied + "', " + storeDate
					+ ")";

			String sql = "INSERT INTO product " + values;
			stmt.executeUpdate(sql);
			System.out.println("Inserted records into the table...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	void removeOrder() {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Creating statement...");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String sql4 = "DELETE FROM OrderDetails " + "WHERE orderID = 1";
			stmt.executeUpdate(sql4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void removeProduct() {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Creating statement...");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String sql4 = "DELETE FROM Product " + "WHERE productID = 1";
			stmt.executeUpdate(sql4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void next() {

	}

	void back() {
	}

	void checkStockLevels() {
	};

	void checkPorousStockLevels() {
	}

}

class tabOnFocus implements ChangeListener {
	private Warehouse w;

	tabOnFocus(Warehouse adaptee) {
		w = adaptee;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		w.tabsOnFocus(e);
		Object source = e.getSource();

	};
}