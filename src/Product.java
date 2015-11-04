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

	private String n, l, pn, pa, dm, pid, sl, p, psl, asl, apsl, q, pQ;

	orderLine Line;

	Product() {
		column = new Object[] { "ID ", "Name ", "Price ", "Location ",
				"Porous Needed ", "Quantity", "Porous Quantity" };
	}

	void viewProductDetails(int OID) {
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
			String sql1 = "SELECT productID, quantity, porousQuantity FROM orderline WHERE orderID = "
					+ OID;

			rs = stmt.executeQuery(sql1);

			rs.last();
			int numberOfRows = rs.getRow();
			data = new Object[numberOfRows][7];
			System.out.println("number of row " + numberOfRows);
			rs.beforeFirst();
			System.out.println("woerking 1");

			String sql2 = null;
			String string = "";
			String sql4 = null;
			String strings = "";

			int count1 = 0;
			while (rs.next()) {
				sql2 = "SELECT productID, productName, price, location, porousNeed FROM product WHERE productID in (";
				string += rs.getInt("productID") + ",";

				System.out.println("list " + rs.getInt("productID"));
				q = String.valueOf(rs.getString("quantity"));
				pQ = String.valueOf(rs.getString("porousQuantity"));

				System.out.println(pQ);

				data[count1][5] = q;
				data[count1][6] = pQ;
				count1++;
			}

			sql2 += method(string);
			sql2 += ")";

			System.out.println("woerking 1");

			System.out.println(sql2);
			rs2 = stmt.executeQuery(sql2);

			int count2 = 0;
			while (rs2.next()) {
				pid = String.valueOf(rs2.getInt("productID"));
				n = String.valueOf(rs2.getString("productName"));
				p = String.valueOf(rs2.getInt("price"));
				l = String.valueOf(rs2.getString("location"));
				pn = String.valueOf(rs2.getString("porousNeed"));

				System.out.println("Product ID: " + pid + " Name: " + n
						+ " Available Stock: " + sl + " Allocated Stock: "
						+ asl + " Available Porous Stock: " + psl
						+ " Allocated Porous Stock: " + apsl + " Price: " + p
						+ " Location: " + l + " Porous Need: " + pn);

				data[count2][0] = pid;
				data[count2][1] = n;
				data[count2][2] = p;
				data[count2][3] = l;
				data[count2][4] = pn;
				count2++;
			}
			System.out.println("woerking 3 " + data[0]);
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
