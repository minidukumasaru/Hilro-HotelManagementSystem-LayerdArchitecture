package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Supplier;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> arrayList = new ArrayList<>();
        List<Supplier> suppliers = supplierDAO.getAll();
        for (Supplier c : suppliers){
            arrayList.add(new SupplierDTO(c.getSupplierId(),c.getSupplierName(),c.getAddress(),c.getQuantity(),c.getContact(),c.getProductName(),c.getDate()));
        }
        return arrayList;

    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }


    @Override
    public String generateNewSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(dto.getSupplierId(),dto.getSupplierName(), dto.getAddress(), dto.getQuantity(),  dto.getContact(),dto.getProductName(),dto.getDate()));
    }

    @Override
    public boolean addSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(dto.getSupplierId(), dto.getSupplierName(), dto.getAddress(), dto.getQuantity(), dto.getContact(), dto.getProductName(),dto.getDate()));
    }
}
