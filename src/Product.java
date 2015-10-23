import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Product extends Tables{

	private int productID;
	private String productName;
	private int stockLevels;
	private int porousStockLevel;
	private double price;
	private String location;
	private boolean porousNeed;
	private boolean porousApplied;
	private int dateManufactured;
	private String assignedToOrder;// 0 means its on an order already, 1 means
									// it isn't
	private int day;
	private int month;
	private int year;

	private String n, l, pn, pa, dm, pid, sl, p, psl, ato;

	orderLine Line;

	Product() {
		column = new Object[]{ "ID ", "Name ", "Stock Levels ",
			"Porous Stock Level ", "Price ", "Location ", "Porous Needed ",
			"Porous Applied ", "Date Manufactured "};
	}

	void viewProductDetails(String[] list) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			System.out.println("Creating statement...");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql2 = "SELECT * FROM product WHERE productID in (";
			String string = list[0];
			for(int i = 1; i < list.length; i++) {
				string += ", " + list[i] ; 
				
				System.out.println("list "+list[i]);
			}
			
			sql2 += string;
			sql2 += ")";
			
			System.out.println(sql2);
			rs = stmt.executeQuery(sql2);
			
			rs.last();
			int numberOfRows = rs.getRow();
			data = new Object[numberOfRows][11];
			System.out.println("number of row " + numberOfRows);
			rs.beforeFirst();
			
			int count = 0;
			while (rs.next()){
				pid = String.valueOf(rs.getInt("productID"));
				n = String.valueOf(rs.getString("productName"));
				sl = String.valueOf(rs.getInt("stockLevels"));
				psl = String.valueOf(rs.getString("porousStockLevels"));
				p = String.valueOf(rs.getInt("price"));
				l = String.valueOf(rs.getString("location"));
				pn = String.valueOf(rs.getString("porousNeed"));
				pa = String.valueOf(rs.getString("porousApplied"));
				dm = String.valueOf(rs.getInt("dateManufactured"));
				ato = String.valueOf(rs.getString("assignedToOrder"));

				System.out.println("Product ID: " + pid + " Name: " + n
						+ " Stock Levels: " + sl + " Porous Stock Levels: "
						+ psl + " Price: " + p + " Location: " + l
						+ " Porous Need: " + pn + " Porous Applied: " + pa
						+ " Date Manufactured: " + dm + " Assigned to order: "
						+ ato);
				
				data[count] = new Object[] { pid, n, sl, psl, p, l, pn, pa, dm, ato };
				count++;
			}
			
			createTable();
			
			// rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

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
