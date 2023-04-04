package com.havrulyk;

import com.havrulyk.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends DataAccessObject<Customer> {

    private static final String INSERT = "INSERT INTO customer(first_name, last_name, email, phone, address, city, " +
            "state, zipcode) VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_ONE = "SELECT customer_id, first_name, last_name, email, phone, address, city, " +
            "state, zipcode FROM customer WHERE customer_id=?";
    private static final String UPDATE = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
            "address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";
    private static final String DELETE = "DELETE FROM customer WHERE customer_id = ?";

    private static final String GET_ALL_LMT = "SELECT customer_id, first_name, last_name, email, phone, address, " +
            "city, state, zipcode FROM customer ORDER BY last_name, first_name LIMIT ?";

    private static final String GET_ALL_PAGED = "SELECT customer_id, first_name, last_name, email, phone, address, " +
            "city, state, zipcode FROM customer ORDER BY last_name, first_name LIMIT ? OFFSET ? ";

    public CustomerDao(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try(PreparedStatement statement = connection.prepareStatement(GET_ONE)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstname(resultSet.getString("first_name"));
                customer.setLastname(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer = null;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try(PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, dto.getFirstname());
            statement.setString(2, dto.getLastname());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.setLong(9, dto.getId());
            statement.execute();
            connection.commit();
            customer = this.findById(dto.getId());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return  customer;
    }

    @Override
    public Customer create(Customer dto) {
        try(PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, dto.getFirstname());
            statement.setString(2, dto.getLastname());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipCode());
            statement.execute();
            int id = getLastVal(CUSTOMER_SEQUENCE);
            return findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Customer> findAllSorted(int limit){
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_LMT)){
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstname(resultSet.getString("first_name"));
                customer.setLastname(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customers;
    }

    public List<Customer> findAllPaged(int limit, int pageNumber){
        List<Customer> customers = new ArrayList<>();
        int offset = ((pageNumber - 1) * limit);
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGED)){
            if(limit < 1){
                limit = 10;
            }
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstname(resultSet.getString("first_name"));
                customer.setLastname(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customers;
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
