package JDBC;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ResultsGUI extends JFrame{

  private JTable table;

  public ResultsGUI(int queryCount, ResultSet rs, ResultSetMetaData rsmd, Object[][] rows, Object[] cols){
    setTitle("Results for Query #"+queryCount+" : "+rows.length+" Row(s) Processed");
		setSize(557, 480);
		setLocation(470, 190);
		setDefaultCloseOperation(ResultsGUI.HIDE_ON_CLOSE);
    setLayout(new FlowLayout());
    setResizable(false);
    table = new JTable(rows, cols);
    table.setBounds(470, 190, 2400, 1500);
    TableColumnModel colModel=table.getColumnModel();
    for(int i=1;i<cols.length;i++){
      TableColumn col=colModel.getColumn(i);
      col.setPreferredWidth(250);
    }
    JScrollPane scroller = new JScrollPane(table);
    scroller.addComponentListener(new ScrollingTableFix(table, scroller));
    this.add(scroller);

    // this.add(table);

    // JScrollPane sp = new JScrollPane(table); 
    // this.add(sp); 

    setVisible(true);
  }

}