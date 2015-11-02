import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class Tables {

	private Warehouse w;
	protected Object[] column = {};
	protected Object[][] data = {};
	JTable table = new JTable();
	JScrollPane jpane = new JScrollPane();
	private TableModel model;

	public Tables() {
	}

	public JScrollPane createTable() {
		table = new JTable(data, column) {
			public Component prepareRenderer(TableCellRenderer r, int rIndex, int cIndex) {
				if (cIndex == 1) {
					setFont(new Font("Arial", Font.BOLD, 12));
				} else {
					setFont(new Font("Arial", Font.ITALIC, 12));
				}
				return super.prepareRenderer(r, rIndex, cIndex);
			}
		};

		table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int col = e.getColumn();
				int rowID = table.getSelectedRow();
				Object value = getValueAt(row, col);
				Warehouse.modifyOrderDetails(value, rowID, col);
			}
		});

		return jpane = new JScrollPane(table);
	}

	public JScrollPane getJPane() {
		return jpane;
	}

	public JTable getJTable() {
		return table;
	}

	public int getColumnCount() {
		return column.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getColumnName(int col) {
		return column[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}
}
