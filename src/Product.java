import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Product extends Tables {

	private int stockLevels;
	private int porousStockLevel;
	private double price;
	private String location;

	private String n, l, pn, pa, dm, pid, sl, p, psl, asl, apsl, ato;

	orderLine Line;

	Product() {
		/*
		 * column = new Object[]{ "ID ", "Name ", "Available Stock ",
		 * "Allocated Stock ", "Available Porous Stock ",
		 * "Allocated Porous Stock ", "Price ", "Location ", "Porous Needed ",
		 * };
		 */
		column = new Object[] { "ID ", "Name ", "Quantity", "Porous Quantity", "Price ", "Location ",
				"Porous Needed " };
	}

	void viewProductDetails(int OID) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			System.out.println("Creating statement...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/wotsdatabase", "root", "NETbuilder");
			stmt = conn.createStatement();
			String sql1 = "SELECT productID FROM orderline WHERE orderID = " + OID;
			String sql3 = "SELECT quantity  FROM orderline WHERE orderID = " + OID;
			rs = stmt.executeQuery(sql1);
			rs2 = stmt.executeQuery(sql3);
			
			String sql2 = null;
			String string = "";

			while (rs.next()) {
				sql2 = "SELECT productID, productName, price, location, porousNeed FROM product WHERE productID in (";
				string += rs.getInt("productID") + ",";
				System.out.println("list " + rs.getInt("productID"));
			}

			sql2 += method(string);
			sql2 += ")";

			System.out.println(sql2);
			rs = stmt.executeQuery(sql2);

			rs.last();
			int numberOfRows = rs.getRow();
			data = new Object[numberOfRows][7];
			System.out.println("number of row " + numberOfRows);
			rs.beforeFirst();

			int count = 0;
			while (rs.next()) {
				pid = String.valueOf(rs.getInt("productID"));
				n = String.valueOf(rs.getString("productName"));
				p = String.valueOf(rs.getInt("price"));
				l = String.valueOf(rs.getString("location"));
				pn = String.valueOf(rs.getString("porousNeed"));

				System.out.println("Product ID: " + pid + " Name: " + n + " Available Stock: " + sl
						+ " Allocated Stock: " + asl + " Available Porous Stock: " + psl + " Allocated Porous Stock: "
						+ apsl + " Price: " + p + " Location: " + l + " Porous Need: " + pn);

				data[count] = new Object[] { pid, n, p, l, pn };
				count++;
			}

			createTable();

			// rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No such orderID in orderLine Table");
		}
	};

	public String method(String str) {
		if (null != str && str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public void setStockLevels(int value) {
		this.stockLevels = value;
	};

	public int getStockLevels() {
		return this.stockLevels;
	};

	public void setPorousStockLevels(int value) {
		this.porousStockLevel = value;
	};

	public int getPorousStockLevels() {
		return this.porousStockLevel;
	};

	public void setPrice(double value) {
		this.price = value;
	};

	public double getPrice() {
		return this.price;
	};

	public void setLocation(String value) {
		this.location = value;
	};

	public String getLocation() {
		return this.location;
	};

}
