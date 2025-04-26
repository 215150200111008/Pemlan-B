package javacradapp;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UserDAO {
    private Connection connection;
    public UserDAO() throws SQLException{
        connection = DBConnection.getConnection();
    }
     public int insertUser(User user){
         try{
         String sql = "INSERT INTO users (first_name, last_name, email, country) VALUES (?,?,?,?)";
         PreparedStatement stmt = connection.prepareStatement(sql);
         stmt.setString(1, user.getFirstname());
         stmt.setString(2, user.getLastname());
         stmt.setString(3, user.getEmail());
         stmt.setString(4, user.getCountry());
         stmt.executeUpdate();
         return 1;
         
         }catch(SQLException e){
          return 0;
         }
         
     }
     public List<User> getUsers(){
         List<User> users = new ArrayList<>();
         try{
         String sql = "SELECT * users";
         PreparedStatement stmt = connection.prepareStatement(sql);
         
         ResultSet rs = stmt.executeQuery();
         while(rs.next()){
             int id = rs.getInt("id");
             String fname = rs.getString("first_name");
             String lname = rs.getString("last_name");
             String email = rs.getString("email");
             String country = rs.getString("country");
             
             users.add(new User(id, fname, lname, email, country));
         }
        
         }catch(SQLException e){
         e.printStackTrace();
         }
         return users;
     }
}
