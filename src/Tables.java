import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Tables {
	Object[] column = {};
	Object[][] data = {};
	JTable table = new JTable();
	JScrollPane jpane = new JScrollPane();

	public Tables() {
	}

	public JScrollPane createTable() {
		table = new JTable(data, column) {
			public Component prepareRenderer(TableCellRenderer r, int rIndex,
					int cIndex) {
				if (cIndex == 1) {
					setFont(new Font("Arial", Font.BOLD, 12));
				} else {
					setFont(new Font("Arial", Font.ITALIC, 12));
				}
				return super.prepareRenderer(r, rIndex, cIndex);
			}
		};
		return jpane = new JScrollPane(table);
	}

}
