import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AllProductInfo extends Tables {

	private int stockLevels;
	private int porousStockLevel;
	private double price;
	private String location;

	private String n, l, pn, pa, dm, pid, sl, p, psl, asl, apsl, ato;

	orderLine Line;

	AllProductInfo() {

		column = new Object[] { "ID ", "Name ", "Available Stock ",
				"Allocated Stock ", "Available Porous Stock ",
				"Allocated Porous Stock ", "Price ", "Location ",
				"Porous Needed ", };
	}

	void viewProductDetails() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			System.out.println("Creating statement...");
			conn = DriverManager.getConnection(
					"jdbc:mysql://10.50.15.38:3306/wotsdatabase", "root",
					"NETbuilder");
			stmt = conn.createStatement();
			String sql1 = "SELECT productID FROM orderline";

			rs = stmt.executeQuery(sql1);

			String sql2 = null;
			String string = "";

			sql2 = "SELECT *FROM product";
			System.out.println(sql2);
			rs = stmt.executeQuery(sql2);

			rs.last();
			int numberOfRows = rs.getRow();
			data = new Object[numberOfRows][11];
			System.out.println("number of row " + numberOfRows);
			rs.beforeFirst();

			int count = 0;
			while (rs.next()) {
				pid = String.valueOf(rs.getInt("productID"));
				n = String.valueOf(rs.getString("productName"));
				sl = String.valueOf(rs.getString("availableStock"));
				asl = String.valueOf(rs.getInt("allocatedStock"));
				psl = String.valueOf(rs.getString("availablePorousStock"));
				apsl = String.valueOf(rs.getString("allocatedPorousStock"));
				p = String.valueOf(rs.getInt("price"));
				l = String.valueOf(rs.getString("location"));
				pn = String.valueOf(rs.getString("porousNeed"));

				System.out.println("Product ID: " + pid + " Name: " + n
						+ " Available Stock: " + sl + " Allocated Stock: "
						+ asl + " Available Porous Stock: " + psl
						+ " Allocated Porous Stock: " + apsl + " Price: " + p
						+ " Location: " + l + " Porous Need: " + pn);

				data[count] = new Object[] { pid, n, sl, asl, psl, apsl, p, l,
						pn };
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

}