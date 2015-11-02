import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class orderLine {
	int orderID;
	int productID;
	int quantity;
	private static String id, pid, oid, q;
	Product prod;
	private String arrayPID[];
	
	orderLine() {
		prod = new Product();
	}

	void viewOrderLineResults(String i) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			System.out.println("Creating statement...");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/wotsdatabase",
							"root", "NETbuilder");
			stmt = conn.createStatement();
			String sql2 = "SELECT * FROM orderline WHERE orderID = " + i;
			rs = stmt.executeQuery(sql2);
			
			rs.last();
			int numberOfRows = rs.getRow();
			arrayPID = new String[numberOfRows];
			System.out.println("numbers " + numberOfRows);
			rs.beforeFirst();
			
			int count = 0;
			while (rs.next()) {
				
				oid = String.valueOf(rs.getString("orderID"));
				pid = String.valueOf(rs.getInt("productID"));
				q = String.valueOf(rs.getString("quantity"));

				System.out.println( "OrderID: " + oid
						+ ", Product ID: " + pid + " Quantity: " + q);
				arrayPID[count] = pid;
						count++;
			}

			System.out.println("No more records");
/*
			System.out.print("Enter Product ID:");
			Scanner in = new Scanner(System.in);

			int j = in.nextInt();
*/
			//prod.viewProductDetails(arrayPID);

			// rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
