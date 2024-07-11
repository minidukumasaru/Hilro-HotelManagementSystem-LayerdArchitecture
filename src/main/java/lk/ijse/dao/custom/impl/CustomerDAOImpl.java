package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO{
    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> customers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("CustomerId"), rst.getString("CustomerName"), rst.getString("Contact"), rst.getString("Address"),  rst.getDate("Date"),rst.getString("Nic"));
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Customer(CustomerId, CustomerName, Contact, Address, Date , Nic) VALUES (?,?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getCustomerName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getDate(),
                entity.getNic());

    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Customer SET CustomerName = ?, Contact = ? , Address = ?, Date = ?, Nic = ?  WHERE CustomerId = ?",
                entity.getCustomerName(),
                entity.getContact(),
                entity.getAddress(),
                entity.getDate(),
                entity.getNic(),
                entity.getCustomerId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "DELETE FROM Customer WHERE CustomerId = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT CustomerId FROM Customer ORDER BY CustomerId DESC LIMIT 1");
        if(rst.next()){
            String string = rst.getString(1);
            String[] strings = string.split("C0");
            int id =Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if(length < 2){
                return "C00"+id;
            }else {
                if(length < 3){
                    return "C0"+id;
                }else {
                    return "C"+id;
                }
            }
        }else {
            return "C001";
        }
    }

    @Override
    public Customer search(String nic) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE Nic = ? ",nic);
        if (rst.next()) {
            return new Customer(
                    rst.getString("CustomerId"),
                    rst.getString("CustomerName"),
                    rst.getString("Contact"),
                    rst.getString("Address"),
                    rst.getDate("Date"),
                    rst.getString("Nic")
            );
        }else {
            return null;
        }
    }

    @Override
    public List<String> getCustomerNIC() throws SQLException {
        List<String> cusNicList = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT Nic FROM Customer");
        while (rst.next()) {
            cusNicList.add(rst.getString(1));
        }
        return cusNicList;
    }


}
