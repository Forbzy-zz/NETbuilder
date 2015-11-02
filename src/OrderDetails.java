import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class OrderDetails extends Tables {

	private String oid, oD, oS, tC;

	orderLine Line;

	OrderDetails() {
		column = new Object[]{ "OrderID ", "Order Date",  "Order Status", "Total Cost",  };
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
			data = new Object[numberOfRows][4];
			System.out.println("numbers " + numberOfRows);
			rs.beforeFirst();

			int count = 0;
			while (rs.next()) {
				oid = String.valueOf(rs.getInt("orderID"));
				oD = String.valueOf(rs.getInt("orderDate"));
				oS = rs.getString("orderStatus");
				tC = String.valueOf(rs.getDouble("totalCost"));
				
				System.out.println("ID: " + oid + ", OrderDate: " + oD
						 + ", Order Status: " + oS + ", TotalCost: £" + tC );

				data[count] = new Object[] { oid, oD, oS, tC };
				count++;
			}
			
			createTable();
			
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

	void updateStock() {
		
	};

	void checkOrderType() {
	};
}
