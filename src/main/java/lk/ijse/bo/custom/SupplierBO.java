package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.SupplierDTO;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {

    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException;

    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    public String generateNewSupplierID() throws SQLException, ClassNotFoundException;

}
