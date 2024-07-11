package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        List<Supplier> suppliers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute(
                "SELECT * FROM Supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("SupplierId"), rst.getString("SupplierName"), rst.getString("Address"), rst.getString("Quantity"),  rst.getString("Contact"),rst.getString("ProductName"),rst.getDate("Date"));
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Supplier(SupplierId,SupplierName,Address,Quantity,Contact,productName,Date) VALUES (?,?,?,?,?,?,?)",
                entity.getSupplierId(),
                entity.getSupplierName(),
                entity.getAddress(),
                entity.getQuantity(),
                entity.getContact(),
                entity.getProductName(),
                entity.getDate());

    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Supplier SET SupplierName = ?, Address = ? , Quantity = ?, Contact = ?,ProductName = ?, Date = ?  WHERE SupplierId = ?",
                entity.getSupplierName(),
                entity.getAddress(),
                entity.getQuantity(),
                entity.getContact(),
                entity.getProductName(),
                entity.getDate(),
                entity.getSupplierId());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "DELETE FROM Supplier WHERE SupplierId = ?", id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT SupplierId FROM Supplier ORDER BY SupplierId DESC LIMIT 1");
        if(rst.next()){
            String string = rst.getString(1);
            String[] strings = string.split("S0");
            int id =Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if(length < 2){
                return "S00"+id;
            }else {
                if(length < 3){
                    return "S0"+id;
                }else {
                    return "S"+id;
                }
            }
        }else {
            return "S001";
        }
    }

    @Override
    public Supplier search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
