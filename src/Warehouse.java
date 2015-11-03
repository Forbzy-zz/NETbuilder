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

	private Panel tabbedPaneProduct, tabbedPaneOrder, tabbedPanePInfo,
			tabbedPaneOInfo;

	private JButton mProductDetailsButton, mOrderDetailsButton, findProdButton,
			addProd, delProd, delOrder;

	private JTextField productID, productName, stockLevels, porousStockLevel,
			aStockLevels, aPorousStockLevel, price, location, porousNeed;

	private JTextField orderID, orderDate;

	private ArrayList<JTextField> item, quantity;

	public static AccessDB db;

	ArrayList<String> storeIDArray;
	ArrayList<Integer> qList;

	private String storeID, storeName, storeLoc, storePrice, storePSL, storeSL,
			storeAPSL, storeASL, storePNeed, oID;

	private JTextField inputOID, checkOID;

	private String storeOrderID, storeOrderDate, storeOrderStatus, storeTotal;
	ArrayList<Integer> storeQuantity;

	private JPanel labelPanel1, textPanel1, labelPanel2, textPanel2,
			labelPanel3, textPanel3;

	private JLabel idLabel, nameLabel, slLabel, pslLabel, aslLabel, apslLabel,
			pLabel, locLabel, pnLabel, paLabel, dMLabel, totalCost;

	private JLabel oIDLabel, oDLabel, qLabel, tLabel, iLabel;

	int q, c;

	private JComboBox<String> menu;
	int[] arrayOrderID = new int[1000];

	Object[] column = { "ID: ", "Name: ", "Available Stock: ",
			"Available Porous Stock: ", "Allocated Stock: ",
			"Allocated Porous Stock: ", "Price: ", "Location: ",
			"Porous Needed: " };

	Object[][] data = {};

	OrderDetails order;
	orderLine line;
	Product prod;
	AllProductInfo allProdInfo;

	private JLabel l1, l2, l3, l4, l5, l6;

	public Warehouse() {

		order = new OrderDetails();
		line = new orderLine();
		prod = new Product();
		allProdInfo = new AllProductInfo();

		mainFrame = new JFrame("Warehouse Order Tracking System");

		jTabbedPane = new JTabbedPane();

		mainFrame.setSize(600, 800);
		mProductDetailsButton = new JButton("Add Product");
		mOrderDetailsButton = new JButton("Add Order");
		addProd = new JButton("Add Product");
		findProdButton = new JButton("Order ID");
		delProd = new JButton("Delete Product");
		delOrder = new JButton("Delete Order");

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

		l1 = new JLabel("");
		l2 = new JLabel("");
		l3 = new JLabel("");
		l4 = new JLabel("");
		l5 = new JLabel("");
		l6 = new JLabel("");

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
		tabbedPaneProduct.add(delProd);

		tabbedPaneOrder.add(labelPanel2, BorderLayout.WEST);
		tabbedPaneOrder.add(textPanel2, BorderLayout.CENTER);
		tabbedPaneOrder.add(labelPanel3, BorderLayout.CENTER);
		tabbedPaneOrder.add(textPanel3, BorderLayout.EAST);

		labelPanel2.add(oIDLabel);
		labelPanel2.add(oDLabel);
		labelPanel2.add(tLabel);
		labelPanel2.add(iLabel);

		labelPanel3.add(l1);
		labelPanel3.add(l2);
		labelPanel3.add(l3);
		labelPanel3.add(qLabel);

		textPanel2.add(orderID);
		textPanel2.add(orderDate);
		textPanel2.add(totalCost);
		JTextField itemField = new JTextField(10);
		item.add(itemField);
		textPanel2.add(itemField);

		textPanel3.add(l4);
		textPanel3.add(l5);
		textPanel3.add(l6);
		JTextField quantityField = new JTextField(10);
		quantity.add(quantityField);
		textPanel3.add(quantityField);

		tabbedPaneOrder.add(addProd);

		tabbedPaneOrder.add(mOrderDetailsButton);

		tabbedPaneOrder.add(delOrder);

		order.viewOrderDetails();

		allProdInfo.viewProductDetails();

		tabbedPanePInfo.add(allProdInfo.createTable());

		tabbedPaneOInfo.add(inputOID);
		tabbedPaneOInfo.add(findProdButton);
		tabbedPaneOInfo.add(order.createTable());

		tabbedPaneOInfo.add(prod.createTable());// adds Product Table to GUI

		mainFrame.add(jTabbedPane);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);

		delProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeID = productID.getText();
				removeProduct(storeID);

				tabbedPanePInfo.remove(allProdInfo.getJPane());
				allProdInfo.viewProductDetails();
				tabbedPanePInfo.add(allProdInfo.createTable());
				tabbedPanePInfo.revalidate();
			}
		});

		delOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				storeOrderID = orderID.getText();
				removeOrder(storeOrderID);

				tabbedPaneOInfo.remove(order.getJPane());
				order.viewOrderDetails();
				tabbedPaneOInfo.add(order.createTable());
				tabbedPaneOInfo.revalidate();

				tabbedPaneOInfo.remove(prod.getJPane());

				tabbedPaneOInfo.add(prod.createTable());
				tabbedPaneOInfo.revalidate();
			}
		});

		findProdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oID = inputOID.getText();
				line.viewOrderLineResults(oID);

				tabbedPaneOInfo.remove(prod.getJPane());
				prod.viewProductDetails(Integer.valueOf(oID));
				tabbedPaneOInfo.add(prod.createTable());
				tabbedPaneOInfo.revalidate();
				System.out.println(e);

				tabbedPaneOInfo.remove(prod.getJPane());

				tabbedPaneOInfo.add(prod.createTable());
				tabbedPaneOInfo.revalidate();
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
				tabbedPanePInfo.remove(allProdInfo.getJPane());
				allProdInfo.viewProductDetails();
				tabbedPanePInfo.add(allProdInfo.createTable());
				tabbedPanePInfo.revalidate();

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
                    allocate(item, quantity);
					updateOrderLineTable(storeOrderID, item, quantity);
					
					calculateCost(item, quantity);
					createOrder();// adds data to orderDetails table in the
									// database

					tabbedPaneOInfo.remove(order.getJPane());
					order.viewOrderDetails();

					tabbedPaneOInfo.add(order.createTable());
					tabbedPaneOInfo.revalidate();
					System.out.println(e);

					tabbedPaneOInfo.remove(prod.getJPane());

					tabbedPaneOInfo.add(prod.createTable());
					tabbedPaneOInfo.revalidate();

				}
			}
		});

		addProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelPanel2.add(new JLabel("Items: "));
				labelPanel2.validate();
				labelPanel3.add(new JLabel("Quantity: "));
				labelPanel3.validate();

				calculateCost(item, quantity);

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

	public int calculateCost(ArrayList<JTextField> ID,
			ArrayList<JTextField> quantity) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet price = null;

		int c = 0;

		System.out.println("Calculating cost");
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < ID.size(); i++) {

				String sql3 = "SELECT  price FROM product WHERE productID = "
						+ ID.get(i).getText();
				price = stmt.executeQuery(sql3);

				price.next();
				c += price.getInt("price")
						* Integer.parseInt(quantity.get(i).getText());
				totalCost.setText("" + String.valueOf(c));
				System.out.println("" + String.valueOf(c));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Wasnt possible to calculate cost");
		}
		System.out.println("Cost Calculated = " + c);
		return c;
	}

	public ArrayList<Integer> checkStock(ArrayList<JTextField> ID,
			ArrayList<JTextField> quantity) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet stockLevels = null;

		qList = new ArrayList<Integer>();

		System.out.println("Modifying Order");
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < ID.size(); i++) {

				String sql3 = "SELECT  availableStock FROM product WHERE productID = "
						+ ID.get(i).getText();

				stockLevels = stmt.executeQuery(sql3);

				stockLevels.next();

				q = stockLevels.getInt("availableStock")
						- Integer.parseInt(quantity.get(i).getText());
				qList.add(q);
				System.out.println("ID  " + ID.get(i).getText());
				System.out.println("Quantity  " + quantity.get(i).getText());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("stock not avilable to make order");
		}
		System.out.println("Order Modified " + q);
		return qList;
	}

	public void updateOrderLineTable(String orderID,
			ArrayList<JTextField> item, ArrayList<JTextField> quantity2) {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Adding products to Order Line Table");
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();

			for (int i = 0; i < item.size(); i++) {

				String values = "VALUES (0," + orderID + " , "
						+ item.get(i).getText() + " , "
						+ quantity2.get(i).getText() + " , 0)";
				String sql = "INSERT INTO orderline " + values;
				stmt.executeUpdate(sql);
			}
			System.out.println("Products added to Order Line Table");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Products couldnt be added to orderline table");
		}
	}

	////accesses the product table and changes the availableStock and 
	//alloactedStocks fields based on the quantity that the user has 
	//specified in the new product process.
	void allocate(ArrayList<JTextField> item, ArrayList<JTextField> quantity) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet stockLevels = null;
		
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			System.out.println("Attempting to transfer stocks");
			stmt = conn.createStatement();
			
			for (int i = 0; i < item.size(); i++) {
				
			String sql3 = "SELECT  availableStock, allocatedStock FROM product WHERE productID = "
					+ item.get(i).getText();

			stockLevels = stmt.executeQuery(sql3);

			stockLevels.next();

			int q1 = stockLevels.getInt("availableStock")
					- Integer.parseInt(quantity.get(i).getText());
			int q2 = stockLevels.getInt("allocatedStock")
					+ Integer.parseInt(quantity.get(i).getText());
			
			String sql4 = "UPDATE product SET availableStock = " + q1 +
					", allocatedStock = " + q2 + " WHERE productID = " + item.get(i).getText();
			
			System.out.println(sql4);
			stmt.executeUpdate(sql4);
			
			System.out.println("Stocks transfered from avilable to allocated");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			//System.out.println("Stocks couldnt be transfered from avilable to allocated");
		}
	}

	void modifyProductDetails(Object value, int id2, int col) {
		Connection conn = null;
		Statement stmt = null;
		String[] columnNames = new String[] { "orderID", "orderDate",
				"orderStatus", "totalCost" };
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql3 = "UPDATE product "
					+ "SET date = 1994 WHERE id in (1, 2)";
			stmt.executeUpdate(sql3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Couldn't modify product information");
		}
	}
	
    //accesses the orderdetails table and changes the value in a field 
	//using the value the user inputs into the order table no the GUI 
	//The current field that the user is on is idenitfied using the 'id2' 
	//which finds the row and 'col' which finds the column.
	static void modifyOrderDetails(Object value, int id2, int col) {
		Connection conn = null;
		Statement stmt = null;
		String[] columnNames = new String[] { "orderID", "orderDate",
				"orderStatus", "totalCost", "orderType" };

		System.out.println("Modifying Order");
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql3 = "UPDATE orderdetails " + "SET " + columnNames[col]
					+ " = " + value + " WHERE orderID = " + id2;
			stmt.executeUpdate(sql3);
			System.out.println("Order Modified");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Order wasnt created");
		}
	}
	
	//accesses the orderdetails table and inserts some records into a new row in my table. 
		//The String 'values' is made up of string variables that store the data entered
		//into JTextfields by the user
	void createOrder() {

		Connection conn = null;
		Statement stmt = null;

		System.out.println("Creating new Stock Order");
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String values = "VALUES (" + storeOrderID + ", " + storeOrderDate
					+ ", 'not active', "
					+ totalCost.getText() + " " //.replaceAll("", "") 
			+ ")";
			String sql = "INSERT INTO orderdetails " + values;
			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Stock Order Created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("couldnt create order");
		}

	};

	//accesses the product table and inserts some records into a new row in my table. 
	//The String 'values' is made up of string variables that store the data entered
	//into JTextfields by the user
	void createProduct() {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Inserting records into the  product table...");

		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String values = "VALUES (" + storeID + ", ' " + storeName + " ', "
					+ storeSL + ", " + storeASL + ", " + storePSL + ", "
					+ storeAPSL + ", " + storePrice + ", ' " + storeLoc
					+ "' , '" + storePNeed + " ' )";

			String sql = "INSERT INTO product " + values;
			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Records inserted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("couldnt create an product");
		}
	};

	//this method accesses the orderdetails table from my database and 
    //removes a record using the orderID
	void removeOrder(String ID) {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Creating statement...");

		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql4 = "DELETE FROM OrderDetails WHERE orderID = "
					+ ID.toString();
			stmt.executeUpdate(sql4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("couldnt remove order information");
		}
	}

	//this method accesses the product table from my database and 
	//removes a record using the productID
	void removeProduct(String ID) {
		Connection conn = null;
		Statement stmt = null;

		System.out.println("Creating statement...");

		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql4 = "DELETE FROM Product WHERE productID = "
					+ ID.toString();
			stmt.executeUpdate(sql4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("couldnt remove product information");
		}
	}
}