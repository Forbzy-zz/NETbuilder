import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OrderDetails extends Tables {
	int orderID;
	int productID;
	int orderDate;
	int day;
	int month;
	int year;
	int quantity;
	boolean orderType;
	int totalCost;
	private String oT, oid, oD, q, tC;

	orderLine Line;

	OrderDetails() {
		column = new Object[]{ "OrderID ", "Order Date", "Quantity","Order Type", "Total Cost" };
		Line = new orderLine();
        
	}

	void viewOrderDetails() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			System.out.println("Creating statement...");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql2 = "SELECT * FROM orderDetails";

			// String sql2 = "select * from orderDetails where orderID "+
			// "= (select min(orderID) from orderDetails where orderID >=0 )";

			rs = stmt.executeQuery(sql2);

			rs.last();
			int numberOfRows = rs.getRow();
			data = new Object[numberOfRows][5];
			System.out.println("numbers " + numberOfRows);
			rs.beforeFirst();

			int count = 0;
			while (rs.next()) {
				oid = String.valueOf(rs.getInt("orderID"));
				oD = String.valueOf(rs.getInt("orderDate"));
				q = String.valueOf(rs.getInt("quantity"));
				oT = rs.getString("orderType");
				tC = String.valueOf(rs.getDouble("totalCost"));
				
				System.out.println("ID: " + oid + ", OrderDate: " + oD
						+ ", Quantity: " + q + ", OrderType: " + oT
						+ ", TotalCost: £" + tC);

				data[count] = new Object[] { oid, oD, q, oT, tC };
				count++;
			}
			
			createTable();
			/*
			System.out.println("No more records");

			System.out.print("Enter Order ID:");
			Scanner in = new Scanner(System.in);

			int i = in.nextInt();

			Line.viewOrderLineResults(i);
*/
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	void findOrder() {
	}

	boolean checkAvailability() {
		return false;
	}

	void calculateTotalPrice() {
	}

	boolean updateStock() {
		return orderType;
	};

	void checkOrderType() {
	};
}
