
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Main {
    
    public static void main(String[] args) {
        
        
        Connection conn = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;
        
        Scanner input = new Scanner(System.in);
        
        String dbName = "CREAMYTURTLE.FriendsDB";
        
        //BUILD UI HERE
        
        System.out.println("======================");
        System.out.println("==Peter's Friends DB==");
        System.out.println("======================");
        System.out.println("");
              
        
        
        //CONNECT TO DB
          
        String host = "jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.sa-bogota-1.oraclecloud.com))(connect_data=(service_name=ga25997eb9f0ce9_creamyturtle2_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
        String username = "creamyturtle";
        String password = "Foofram77!!!!";
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(host, username, password);
            System.out.println("Connected to MySQL database successfully");
            

            
        } catch (Exception e) {
            System.out.println("Failed to connect to MySQL database, load driver, or update DB.  Check stacktrace.");
            e.printStackTrace();
        } 
        
        
        while(true) {
        
        
            System.out.println("");


            System.out.println("What would you like to do?");
            System.out.println("1: Print Database");
            System.out.println("2: Add Record");
            System.out.println("3: Remove Record");
            System.out.println("4: Update Record");
            System.out.println("5: Print Specific Record");
            System.out.println("6: Quit");

            int command = Integer.valueOf(input.nextLine());


            if (command == 1) {

                DBTablePrinter.printTable(conn, dbName);

            }

            if (command == 2) {

                //ADD USER DATA TO DATABASE

                try {


                    st = conn.createStatement();
                    st.executeUpdate(addRecord(input, dbName));


                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }


                //example statement
                //st.executeUpdate("INSERT INTO CREAMYTURTLE.FriendsDB " + "VALUES ('Nayeth De La Cruz', 25, 'Medellin', 1234567899, 'Female', 'Girlfriend')");

                System.out.println("Database updated successfully");

            }

            if (command == 3) {

                //DELETE RECORD

                System.out.println("Which person would you like to remove?");
                String personToRemove = input.nextLine();


                try {

                    st = conn.createStatement();
                    st.executeUpdate("DELETE FROM " + dbName + " WHERE Name='" + personToRemove + "'");

                    System.out.println("Person successfully removed");

                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }





            }

            if (command == 4) {

                //UPDATE RECORD
                
                System.out.println("Which person would you like to edit?");
                String persona = input.nextLine();
                
                System.out.println("");
                System.out.println("Which field would you like to change?");
                System.out.println("Name / Age / Location / PhoneNumber / MaleFemale / Comments");
                System.out.println("");
                System.out.println("(write string exactly as shown)");
                System.out.println("");
                
                String columnID = input.nextLine();
                
                
                System.out.println("Enter new data for field:");
                String newData = input.nextLine();
                
                
                String query = "UPDATE " + dbName + " SET " + columnID + " = '" + newData + "' WHERE Name = '" + persona + "'";

                
                try {

                    st = conn.createStatement();
                    rs = st.executeQuery(query);

                    System.out.println("Database updated successfully!");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
                

            }

            if (command == 5) {

                //PRINT SPECIFIC RECORD

                System.out.println("Which person would you like to view?");
                String person = input.nextLine();




                try {

                    
                    String query = "SELECT * FROM " + dbName + " WHERE Name = '" + person + "'";

                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                    while (rs.next()) {
                       System.out.println("Name: " + rs.getString("Name"));
                       System.out.println("Age: " + rs.getString("Age"));
                       System.out.println("Location: " + rs.getString("Location"));
                       System.out.println("Phone: " + rs.getString("PhoneNumber"));
                       System.out.println("M/F: " + rs.getString("MaleFemale"));
                       System.out.println("Comment: " + rs.getString("Comments"));

                    }

                }
                catch (Exception e) {
                   e.printStackTrace();
                }
                
                
                


            }

            if (command == 6) {

                //CLOSE DB CONNECTION

                try {

                    conn.close();
                    System.out.println("");
                    System.out.println("Database connection closed successfully");


                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                break;

            }
        
        }
        

        
    }
    

    
    
    static String addRecord(Scanner input, String dbName) {
        
        //COLLECT USER DATA
            
        System.out.println("");
        System.out.println("Please enter the following information:");

        System.out.println("Name:");
        String name = input.nextLine();

        System.out.println("");
        System.out.println("Age");
        int age = Integer.valueOf(input.nextLine());

        System.out.println("");
        System.out.println("Location:");
        String location = input.nextLine();

        System.out.println("");
        System.out.println("Phone Number:");
        long phone = Long.valueOf(input.nextLine());

        System.out.println("");
        System.out.println("Male/Female:");
        String mf = input.nextLine();

        System.out.println("");
        System.out.println("Comments:");
        String comment = input.nextLine();

        System.out.println("");
        
        
        String longText = "INSERT INTO " + dbName + " VALUES ('" + name + "', " + age + ", '" + location + "', " + phone + ", '" + mf + "', '" + comment + "')";
         
        
        return longText;
        
    }
    
    
}