


import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcDriver {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/assign2";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "iiita";
    
    /*
    id, name, f_name, address, age, mobile_no, cgpa
    */
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Connection conn = null;
            Statement stmnt = null;
            
         
             Class.forName(JDBC_DRIVER);
            
             
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            stmnt = conn.createStatement();
            boolean shutDown = false;
            while(true)
            {
                 System.out.println("1. Add user\n2. Delete User\n3. Update User\n4. Show Users\n5. Search User by ID\n6. Exit");
                 
                 int x;
                 Scanner sc = new Scanner(System.in);
                 x = sc.nextInt();
                 
                 switch(x)
                 {
                     case 1:
                         addUser(conn, stmnt);
                         break;
                     case 2:
                         deleteUser(conn, stmnt);
                         break;
                     case 3:
                         updateUser(conn, stmnt);
                         break;
                     case 4:
                         showUsers(conn, stmnt);
                         break;
                     case 5:
                         searchUser(conn, stmnt);
                         break;
                     case 6:
                         shutDown = true;
                         break;
                     default:
                         System.out.println("Please select valid input");
                 }
                 if(shutDown)
                    break;
                 
            }
           
            
//            ResultSet rs = stmnt.executeQuery(sql);
//            
//            while(rs.next())
//            {
//                System.out.print("ID: " + rs.getInt("id"));
//                System.out.print(", name: " + rs.getString("name"));
//
//            }
//            rs.close();
//            stmnt.close();
//            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void addUser(Connection conn, Statement stmnt) {
        try {
            System.out.println("Enter  name, fathers name, address, age, mobile_no, cgpa");
            String name, f_name, address, mobile_no;
            int age;
            float cgpa;
            Scanner sc = new Scanner(System.in);
            name = sc.next();
            f_name = sc.next();
            address = sc.next();
            age = sc.nextInt();
            mobile_no = sc.next();
            cgpa = sc.nextFloat();
            String sql = "INSERT INTO student_db VALUES (NULL, \"" 
                    + name + "\", \""+ f_name + "\", \""+ address 
                    + "\", \""+ age+ "\", \"" + mobile_no + "\", \"" +cgpa + "\");";
            //System.out.println(sql);
            stmnt.executeUpdate(sql);
            
            System.out.println("Inserted successfully");
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void deleteUser(Connection conn, Statement stmnt) {
        try {
            System.out.println("Enter  ID of student to be deleted");
            
            int id;
            
            Scanner sc = new Scanner(System.in);
            id = sc.nextInt();
            
            String sql = "DELETE FROM student_db where id = \"" + id + "\";";
            //System.out.println(sql);
            int resultSet = stmnt.executeUpdate(sql);
            if(resultSet == 0)
            {
                 System.out.println("Student not found");
                return;
            }
            System.out.println("Deleted successfully");
                    
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    private static void updateUser(Connection conn, Statement stmnt) {
        try {
            System.out.println("Enter  ID of student to be updated");
            
            int id;
            
            Scanner sc = new Scanner(System.in);
            id = sc.nextInt();
            
            System.out.println("Enter  name, fathers name, address, age, mobile_no, cgpa");
            String name, f_name, address, mobile_no;
            int age;
            float cgpa;
           
            name = sc.next();
            f_name = sc.next();
            address = sc.next();
            age = sc.nextInt();
            mobile_no = sc.next();
            cgpa = sc.nextFloat();
            
            String sql = "UPDATE student_db SET name = \"" + name 
                    + "\", f_name =\"" + f_name + "\", address = \"" 
                    + address + "\", age =\"" + age + "\", mobile_no =\"" 
                    + mobile_no + "\", cgpa =\"" + cgpa + "\" where id = "+ id +";" ;
            
            //System.out.println(sql);
            stmnt.executeUpdate(sql);
            
            
            System.out.println("Updated successfully");
                    
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    private static void showUsers(Connection conn, Statement stmnt) {
        try {
            String sql = "select * from student_db order by name ASC;";
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next())
            {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", name: " + rs.getString("name"));
                System.out.print(", fathers_name: " + rs.getString("f_name"));
                System.out.print(", address: " + rs.getString("address"));
                System.out.print(", mobile number: " + rs.getString("mobile_no"));
                System.out.print(", age: " + rs.getInt("age"));
                System.out.println(", cgpa: " + rs.getFloat("cgpa"));

            }
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void searchUser(Connection conn, Statement stmnt) {
        try {
            System.out.println("Enter  ID of student to be searched");
            
            int id;
            
            Scanner sc = new Scanner(System.in);
            id = sc.nextInt();
            
            
            
            String sql = "SELECT * from student_db where id = "+ id +";" ;
            
            //System.out.println(sql);
            ResultSet rs = stmnt.executeQuery(sql);
            if(rs.next() == false)
            {
                System.out.println("Student not found");
                return;
                       
            }
            while(rs.next())
            {
                System.out.print("ID: " + rs.getInt("id"));
                System.out.print(", name: " + rs.getString("name"));
                System.out.print(", fathers_name: " + rs.getString("f_name"));
                System.out.print(", address: " + rs.getString("address"));
                System.out.print(", mobile number: " + rs.getString("mobile_no"));
                System.out.print(", age: " + rs.getInt("age"));
                System.out.println(", cgpa: " + rs.getFloat("cgpa"));

            }
            rs.close();
            
            //System.out.println("Updated successfully");
                    
        } catch (SQLException ex) {
            Logger.getLogger(JdbcDriver.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
}
