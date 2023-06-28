package com.cdac.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// For CRUD Operation
public class UserDAO {

	private static final String INSERT_USER_SQL="INSERT INTO customer"+"(name,email,password) VALUES"+"(?,?,?);";
	private static final String SELECT_USER_BY_ID="select id,name,email,password from customer where id=?;";
	private static final String SELECT_ALL_USERS="select *from customer;";
	private static final String DELETE_USERS_SQL="delete from customer where id=?;";
	private static final String UPDATE_USER_SQL="update customer set name=?,email=?,password=? where id=?;";
    
	protected Connection getConnection() throws Exception {
		Connection connection=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adjava", "Madhu", "Patil");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	//Create user or insert user
	public void insertUser(User user) throws SQLException, Exception
	{
		try(Connection connection=getConnection();
			PreparedStatement preparedStatement =connection.prepareStatement(INSERT_USER_SQL)){
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setString(3,user.getPassword());
			preparedStatement.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//Update USer
	public boolean updateUser(User user) throws SQLException, Exception {
		boolean rowUpdated;
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement =connection.prepareStatement(UPDATE_USER_SQL)){
				preparedStatement.setString(1,user.getName());
				preparedStatement.setString(2,user.getEmail());
				preparedStatement.setString(3,user.getPassword());
				preparedStatement.setInt(4,user.getId());
				
				rowUpdated= preparedStatement.executeUpdate()>0;
			}
		return rowUpdated;
	}
	//Select user by id
	public User selectUser(int id) {
		User user = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				user = new User(id, name, email, password);
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return user;
	}
  //select All user
	public List<User> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				//System.out.println(name);
				String email = rs.getString("email");
				String password = rs.getString("password");
				users.add(new User(id, name, email, password));
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return users;
	}
	//delete user
	
	public boolean deleteUser(int id) throws Exception {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
}
