import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class Warehouse extends JFrame {
	private int ID;

	private String name;

	private JFrame mainFrame;

	private JTabbedPane jTabbedPane;

	private Panel tabbedPaneProduct, tabbedPaneOrder, tabbedPanePInfo, tabbedPaneOInfo;

	private JButton mProductDetailsButton, mOrderDetailsButton, findProdButton, addProd;

	private JTextField productID, productName, stockLevels, porousStockLevel, aStockLevels, aPorousStockLevel, price,
			location, porousNeed;

	private JTextField orderID, orderDate;

	private ArrayList<JTextField> item, quantity;

	public static AccessDB db;

	ArrayList<String> storeIDArray;
	ArrayList<Integer> qList;

	private String storeID, storeName, storeLoc, storePrice, storePSL, storeSL, storeAPSL, storeASL, storePNeed, oID;

	private JTextField inputOID, checkOID;

	private String storeOrderID, storeOrderDate, storeOrderStatus, storeTotal;
	ArrayList<Integer> storeQuantity;

	private JPanel labelPanel1, textPanel1, labelPanel2, textPanel2, labelPanel3, textPanel3;

	private JLabel idLabel, nameLabel, slLabel, pslLabel, aslLabel, apslLabel, pLabel, locLabel, pnLabel, paLabel,
			dMLabel, totalCost;

	private JLabel oIDLabel, oDLabel, qLabel, tLabel, iLabel;

	int q, c;

	private JComboBox<String> menu;
	int[] arrayOrderID = new int[1000];

	Object[] column = { "ID: ", "Name: ", "Available Stock: ", "Available Porous Stock: ", "Allocated Stock: ",
			"Allocated Porous Stock: ", "Price: ", "Location: ", "Porous Needed: " };
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

		mainFrame.setSize(600, 500);
		mProductDetailsButton = new JButton("Add Product");
		mOrderDetailsButton = new JButton("Add Order");
		addProd = new JButton("Add Product");
		findProdButton = new JButton("Order ID");

		labelPanel1 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel1 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel2 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel2 = new JPanel(new GridLayout(9, 1, 4, 4));
		labelPanel3 = new JPanel(new GridLayout(9, 1, 4, 8));
		textPanel3 = new JPanel(new GridLayout(9, 1, 4, 4));

		//
		idLabel = new JLabel("ID: ");
		nameLabel = new JLabel("Name: ");
		slLabel = new JLabel("Available Stock: ");
		pslLabel = new JLabel("Available Porous Stock: ");
		aslLabel = new JLabel("Allocated Stock: ");
		apslLabel = new JLabel("Allocated Porous Stock: ");
		pLabel = new JLabel("Price: ");
		locLabel = new JLabel("Location: ");
		pnLabel = new JLabel("Porous Needed: ");

		//
		oIDLabel = new JLabel("Order ID: ");
		oDLabel = new JLabel("Date Ordered: ");
		tLabel = new JLabel("Total: ");
		iLabel = new JLabel("Items: ");
		qLabel = new JLabel("Quantity: ");

		// input boxes for the product attributes
		productID = new JTextField(10);
		productName = new JTextField(10);
		stockLevels = new JTextField(10);
		aStockLevels = new JTextField(10);
		porousStockLevel = new JTextField(10);
		aPorousStockLevel = new JTextField(10);
		price = new JTextField(10);
		location = new JTextField(10);
		porousNeed = new JTextField(10);

		// input boxes for the order details attributes
		orderID = new JTextField(10);
		item = new ArrayList<JTextField>();
		orderDate = new JTextField(10);
		totalCost = new JLabel();
		quantity = new ArrayList<JTextField>();

		inputOID = new JTextField(10);
		checkOID = new JTextField(10);

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

		labelPanel1.add(idLabel);
		labelPanel1.add(nameLabel);
		labelPanel1.add(slLabel);
		labelPanel1.add(aslLabel);
		labelPanel1.add(pslLabel);
		labelPanel1.add(apslLabel);
		labelPanel1.add(pLabel);
		labelPanel1.add(locLabel);
		labelPanel1.add(pnLabel);

		textPanel1.add(productID);
		textPanel1.add(productName);
		textPanel1.add(stockLevels);
		textPanel1.add(aStockLevels);
		textPanel1.add(porousStockLevel);
		textPanel1.add(aPorousStockLevel);
		textPanel1.add(price);
		textPanel1.add(location);
		textPanel1.add(porousNeed);

		tabbedPaneProduct.add(mProductDetailsButton);

		tabbedPaneOrder.add(labelPanel2, BorderLayout.WEST);
		tabbedPaneOrder.add(textPanel2, BorderLayout.CENTER);
		tabbedPaneOrder.add(labelPanel3, BorderLayout.CENTER);
		tabbedPaneOrder.add(textPanel3, BorderLayout.EAST);

		labelPanel2.add(oIDLabel);
		labelPanel2.add(oDLabel);
		labelPanel2.add(tLabel);
		labelPanel2.add(iLabel);
		labelPanel3.add(qLabel);

		textPanel2.add(orderID);
		textPanel2.add(orderDate);
		textPanel2.add(totalCost);
		JTextField itemField = new JTextField(10);
		item.add(itemField);
		textPanel2.add(itemField);
		JTextField quantityField = new JTextField(10);
		quantity.add(quantityField);
		textPanel3.add(quantityField);

		tabbedPaneOrder.add(addProd);

		tabbedPaneOrder.add(mOrderDetailsButton);

		tabbedPanePInfo.setSize(600, 500);
		tabbedPanePInfo.add(prod.createTable());// adds Product Table to GUI

		order.viewOrderDetails();

		tabbedPaneOInfo.add(inputOID);
		tabbedPaneOInfo.add(findProdButton);
		tabbedPaneOInfo.add(order.createTable());

		mainFrame.add(jTabbedPane);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

		findProdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oID = inputOID.getText();
				line.viewOrderLineResults(oID);

				tabbedPanePInfo.remove(prod.getJPane());
				prod.viewProductDetails(Integer.valueOf(oID));
				tabbedPanePInfo.add(prod.createTable());
				tabbedPanePInfo.revalidate();
				System.out.println(e);

			}
		});

		mProductDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeID = productID.getText();
				storeName = productName.getText();
				storeSL = stockLevels.getText();
				storeASL = aStockLevels.getText();
				storeAPSL = porousStockLevel.getText();
				storePSL = aPorousStockLevel.getText();
				storePrice = price.getText();
				storeLoc = location.getText();
				storePNeed = porousNeed.getText();

				createProduct();
			}
		});

		mOrderDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeOrderID = orderID.getText();
				storeOrderDate = orderDate.getText();
				storeTotal = totalCost.getText();
				
				checkStock(item, quantity);
				if (Collections.min(qList) <= 0) {// if there is a zero in this
													// array do this
					totalCost.setText("");
					System.out.println("stock not available");
				} else {
					calculateCost(item, quantity);
				/*	updateOrderLineTable(storeOrderID, item, quantity);
					createOrder();// adds data to orderDetails table in the
									// database

					tabbedPaneOInfo.remove(order.getJPane());
					order.viewOrderDetails();
					tabbedPaneOInfo.add(order.createTable());
					tabbedPaneOInfo.revalidate();
					System.out.println(e);*/
				}
			}
		});

		addProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelPanel2.add(new JLabel("Items: "));
				labelPanel2.validate();
				labelPanel3.add(new JLabel("Quantity: "));
				labelPanel3.validate();

				JTextField itemField = new JTextField(10);
				item.add(itemField);
				textPanel2.add(itemField);
				textPanel2.validate();

				JTextField quantityField = new JTextField(10);
				quantity.add(quantityField);
				textPanel3.add(quantityField);
				textPanel3.validate();

			}
		});
	}

	public static void main(String[] args) {
		db = new AccessDB();
		Warehouse sD = new Warehouse();
		// sD.setVisible(true);
		db.accessBD();
	}

	public int calculateCost(ArrayList<JTextField> ID, ArrayList<JTextField> quantity) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet price = null;

		int c = 0;

		System.out.println("Calculating cost");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < ID.size(); i++) {

				String sql3 = "SELECT  price FROM product WHERE productID = " + ID.get(i).getText();
				price = stmt.executeQuery(sql3);

				price.next();
				c += price.getInt("price") * Integer.parseInt(quantity.get(i).getText());
				totalCost.setText("£" + String.valueOf(c));
				System.out.println("£" + String.valueOf(c));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Cost Calculated = £" + c);
		return c;
	}

	public ArrayList<Integer> checkStock(ArrayList<JTextField> ID, ArrayList<JTextField> quantity) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet stockLevels = null;

		qList = new ArrayList<Integer>();

		System.out.println("Modifying Order");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < ID.size(); i++) {

				String sql3 = "SELECT  availableStock FROM product WHERE productID = " + ID.get(i).getText();

				stockLevels = stmt.executeQuery(sql3);

				stockLevels.next();

				q = stockLevels.getInt("availableStock") - Integer.parseInt(quantity.get(i).getText());
				qList.add(q);
				System.out.println("ID  " + ID.get(i).getText());
				System.out.println("Quantity  " + quantity.get(i).getText());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Order Modified " + q);
		return qList;
	}

	public void updateOrderLineTable(String orderID, ArrayList<JTextField> item, ArrayList<JTextField> quantity2) {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Adding products to Order Line Table");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < item.size(); i++) {

				String values = "VALUES (" + orderID + " , " + item.get(i).getText() + " , "
						+ quantity2.get(i).getText() + " )";
				String sql = "INSERT INTO orderline " + values;
				stmt.executeUpdate(sql);
				System.out.println("Products added to Order Line Table");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void modifyProductDetails(Object value, int id2, int col) {
		Connection conn = null;
		Statement stmt = null;
		String[] columnNames = new String[] { "orderID", "orderDate", "orderStatus", "totalCost" };
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql3 = "UPDATE product " + "SET date = 1994 WHERE id in (1, 2)";
			stmt.executeUpdate(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void modifyOrderDetails(Object value, int id2, int col) {
		Connection conn = null;
		Statement stmt = null;
		String[] columnNames = new String[] { "orderID", "orderDate", "orderStatus", "totalCost" };

		System.out.println("Modifying Order");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String sql3 = "UPDATE orderdetails " + "SET " + columnNames[col] + " = " + value + " WHERE orderID = "
					+ id2;
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

		System.out.println("Creating new Stock Order");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String values = "VALUES (" + storeOrderID + " , " + storeOrderDate + " , " + storeOrderStatus + " , "
					+ storeTotal + " )";
			String sql = "INSERT INTO orderdetails " + values;
			stmt.executeUpdate(sql);
			System.out.println("Stock Order Created");
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
			String values = "VALUES (" + storeID + ", ' " + storeName + " ', " + storeSL + ", " + storeASL + ", "
					+ storePSL + ", " + storeAPSL + ", " + storePrice + ", ' " + storeLoc + "' , '" + storePNeed + ")";

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
}