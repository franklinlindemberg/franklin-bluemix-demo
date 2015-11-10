package com.ibm.dao;

import com.ibm.model.User;
import com.ibm.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection = SQLUtil.getConnection();;

    public UserDAO() {
        
    }

    public void addUser(User user) {
    	
        try {
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into user(first_name, last_name, address, sex, email, password) values (?, ?, ?, ?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setString(4, user.getSex());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.executeUpdate();
            try {
    			// Close the Statement
            	preparedStatement.close();
    			// Connection must be on a unit-of-work boundary to allow close
    			connection.commit();
    		} catch (SQLException e) {
    			System.out.println("Erro fechando as conexoes");
                System.out.println("SQL Exception: " + e);
    		}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int loginUser(User user) {
    	
    	int ret = 0;
    	
    	connection = SQLUtil.getConnection();
    	
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from user " +
                            "where email = ? and password = ?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
            	ret = 1;
            }
            else{
            	ret = 0;
            }
            try {
    			// Close the Statement
            	preparedStatement.close();
    			// Connection must be on a unit-of-work boundary to allow close
    			connection.commit();
    		} catch (SQLException e) {
    			System.out.println("Erro fechando as conexoes");
                System.out.println("SQL Exception: " + e);
    		}
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return ret;
    }
}