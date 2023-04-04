package com.havrulyk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "admin");
        try {
            Connection connection = dcm.getConnection();
            CustomerDao customerDao = new CustomerDao(connection);
            customerDao.findAllSorted(20).forEach(System.out::println);
            System.out.println("Paged");
            for(int i = 1; i < 3; i++){
                System.out.println("Page number: " + i);
                customerDao.findAllPaged(10, i).forEach(System.out::println);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
