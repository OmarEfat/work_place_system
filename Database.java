import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    static String type = "";
    private static Connection connection;
    public Database() {
        connection = null;
        try{
            String link = "jdbc:sqlite:employee.db";
            connection = DriverManager.getConnection(link);
            System.out.println("Connection succesful");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void close(){
        try{
            if(connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean found(String user_name, String password)
    {

        Boolean check = false;
        try {
            String sql = "SELECT * from employee";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            ResultSetMetaData meta = set.getMetaData();
            while(!check && set.next())
            {
                String user_search = set.getString(1);
                String pass_search = set.getString(2);
                if (user_search.equals(user_name) && pass_search.equals(password)) {
                    check = true;
                    Database.type = set.getString(3);
                    set.close();
                }
            }


        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }
    private int[] getColTypes(ResultSet set) throws SQLException {
        ResultSetMetaData setMeta = set.getMetaData();
        int colCount = setMeta.getColumnCount();
        int[] colTypes = new int[colCount];
        for(int i = 1; i < colCount+1; i++) {
            colTypes[i-1] = setMeta.getColumnType(i);
        }

        return colTypes;
    }

    private ArrayList<String[]> getTableInfo(ResultSet set, int[] colTypes) throws SQLException {
        ArrayList<String[]> table = new ArrayList<String[]>();
        ResultSetMetaData meta = set.getMetaData();

        //add the colnames first to the list
        String[] colNames = new String[colTypes.length];
        for(int i = 0; i < colTypes.length; i++) {
            colNames[i] = meta.getColumnName(i+1);
        }

        table.add(colNames);
        //goes through each row
        while(set.next()) {

            String[] row = new String[colTypes.length];
            for(int i = 0; i < colTypes.length; i++) {
                if(colTypes[i] == 12) {
                    String value = set.getString(i+1);
                    if(set.wasNull()) { //checks if its a null value
                        row[i] = "null";
                    } else {
                        row[i] = value;
                    }

                } else if(colTypes[i] == 4) {
                    //need to check if its null
                    int val = set.getInt(i+1);
                    if(set.wasNull()) {
                        row[i] = "null";
                    } else {
                        row[i] = String.valueOf(val); //conver to a string
                    }
                }
            }
            table.add(row);
        }

        return table;
    }

    public ArrayList<String[]>employee_table()
    {
        return regularQuery("Select * from Employee");
    }

    private ArrayList<String[]> regularQuery(String sql) {
        try(Statement stmt = connection.createStatement()){
            ResultSet set = stmt.executeQuery(sql);
            ArrayList<String[]> table = getTableInfo(set, getColTypes(set));
            set.close();
            return table;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void addEmployee(int user_name , String password , String type , String name)
    {
        try {
            Statement statement = connection.createStatement();
            char ch = '"';
            statement.executeUpdate("INSERT INTO Employee VALUES (" + user_name + "," + ch + password + ch + "," + ch + type + ch + "," + ch + name + ch + ");");
            System.out.println("done");
        } catch (SQLException e)
        {
            e.getMessage();
        }
    }

    public void deleteEmployee(int user_name)
    {
        try {
            Statement statement = connection.createStatement();
            char ch = '"';
            statement.executeUpdate("DELETE FROM Employee WHERE username =" + user_name);
            System.out.println("done");
        } catch (SQLException e)
        {
            e.getMessage();
        }
    }

    public ArrayList<String> employee_list()
    {
        ArrayList<String> list = new ArrayList<>();
        try
        {
            String sql = "SELECT * from employee";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next())
            {
                String user_search = set.getString(1);
                if(set.getString(3).equals("Employee"))
                    list.add(user_search);
            }
            set.close();
        }catch (SQLException e)
        {
            e.getMessage();
        }
        return list;
    }
    public void add_task(String username , String task)
    {
        try {
            Statement statement = connection.createStatement();
            char ch = '"';
            System.out.println("start");
            statement.executeUpdate("INSERT INTO Employee_tasks VALUES("+Integer.parseInt(username) + ","+ch + task +ch+ ");");
            System.out.println("done");
        } catch (SQLException e)
        {
            e.getMessage();
        }
    }

    public ArrayList<String>employee_tasks(String username)
    {
        ArrayList<String> tasks = new ArrayList<>();
        try
        {
            String sql = "SELECT * from Employee_tasks";
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next())
            {
                if(set.getString(1).equals(username))
                    tasks.add(set.getString(2));
            }
            set.close();
        }catch (SQLException e)
        {
            e.getMessage();
        }
        return tasks;

    }
    public void completed_task(String username , String task)
    {
        try {
            Statement statement = connection.createStatement();
            char ch = '"';
            System.out.println("start");
            statement.executeUpdate("INSERT INTO completed_tasks VALUES("+Integer.parseInt(username) + ","+ch + task +ch+ ");");
            statement.executeUpdate("DELETE FROM Employee_tasks where task = " + ch + task + ch + ";");
            System.out.println("done");
        } catch (SQLException e)
        {
            e.getMessage();
        }
    }

    public ArrayList<String[]> completed_tasksList()
    {
         return regularQuery("Select * from completed_tasks");
    }
    public void delete_completed()
    {
        try(Statement stmt = connection.createStatement()){
            ResultSet set = stmt.executeQuery("DELETE FROM completed_tasks");
            set.close();
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
