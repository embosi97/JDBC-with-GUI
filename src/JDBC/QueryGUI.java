package JDBC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class QueryGUI extends JFrame{

  private static final String title = "JDBC Run Query";
  /** The initial user interface width, in pixels */
  private static final int width  = 270;
  /** The initial user interface height, in pixels */
  private static final int height = 345;
  /** Provides methods for displaying a SQL result set in a JTable */
  // Commented out for now so the program can run without it.
  private static final int location1 = 100, location2 = 100;
  /** Used to display the SQL result set in a cell format */
  // private JTable table;
  public static int queryCount = 0;

  public QueryGUI(){
		setTitle(this.title);
		setSize(this.width, this.height);
		setLocation(this.location1, this.location2);
		setDefaultCloseOperation(QueryGUI.EXIT_ON_CLOSE);
    setLayout(new FlowLayout()); 
    setResizable(false);
    Label urlLabel = new Label("Docker Port: "), dbnameLabel = new Label("Database Name: "), userLabel = new Label("Username: "), passLabel = new Label("Password: "), queryLabel = new Label("Query: ");
    JTextField urlInput = new JTextField("12001", 20), /* dbnameInput = new JTextField(20), */ userInput = new JTextField("sa", 20), passInput = new JTextField("PH@123456789", 20), queryInput = new JTextField(20);
    String[] dbList = {"NorthWinds2019TSQLV5", "AdventureWorksDW2016", "AdventureWorks2014"};
    JComboBox<String> dbnameInput = new JComboBox<>(dbList);
    Container myContentPane = this.getContentPane();
    JButton submit = new JButton("Submit Query");
    submit.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand();
        if(action.equals("Submit Query")){
          queryCount++;
          String url = urlInput.getText(), dbname = (String) dbnameInput.getSelectedItem(), user = userInput.getText(),
              pass = passInput.getText(), query = queryInput.getText();
          Connection conn = null;
          DatabaseConnection dbConnect = new DatabaseConnection(url, dbname, user, pass);
          try {
            java.sql.ResultSet resultSet = dbConnect.execQueryToHTML(conn, query, "demo.html");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            System.out.println(rsmd);
            int columnCount = rsmd.getColumnCount()+1;
            System.out.println(columnCount);
            Object[] cols = new Object[columnCount];
            cols[0] = "";
            for(int i=1;i<columnCount;i++){
              cols[i] = rsmd.getColumnLabel(i);
            }
            ArrayList<ArrayList<Object>> listOfLists = new ArrayList<ArrayList<Object>>();
            int rowCount=0, i=0;;
            while(resultSet.next()){
              rowCount++;
              listOfLists.add(new ArrayList<>());
              listOfLists.get(i).add(rowCount);
              for (int j = 1; j < columnCount; j++) {
                listOfLists.get(i).add(resultSet.getString(j));
              }
              i++;
            }
            Object[][] rows = new Object[rowCount][columnCount];
            for(int j=0;j<rows.length;j++){
              for(int k=0;k<rows[0].length;k++){
                rows[j][k] = listOfLists.get(j).get(k);
              }
            }
            ResultsGUI results = new ResultsGUI(queryCount, resultSet, rsmd, rows, cols);
          } catch (Exception exc) {
              exc.printStackTrace();
          }finally {
            try {
              if (conn != null && !conn.isClosed()) {
                conn.close();
      
              }
            } catch (SQLException se) {
              se.printStackTrace();
            }
          }
        }
      }
    });
    myContentPane.add(urlLabel);
    myContentPane.add(urlInput);
    myContentPane.add(dbnameLabel);
    myContentPane.add(dbnameInput);
    myContentPane.add(userLabel);
    myContentPane.add(userInput);
    myContentPane.add(passLabel);
    myContentPane.add(passInput);
    myContentPane.add(queryLabel);
    myContentPane.add(queryInput);
    myContentPane.add(submit);
    setVisible(true);
  }

}