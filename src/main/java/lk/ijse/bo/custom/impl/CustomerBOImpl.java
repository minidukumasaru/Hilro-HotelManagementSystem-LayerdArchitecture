package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> arrayList = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll();
        for (Customer c : customers){
            arrayList.add(new CustomerDTO(c.getCustomerId(),c.getCustomerName(),c.getContact(),c.getAddress(),c.getDate(),c.getNic()));
        }
        return arrayList;

    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getCustomerId(), dto.getCustomerName(), dto.getContact(), dto.getAddress(), dto.getDate(), dto.getNic()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustomerId(),dto.getCustomerName(), dto.getContact(), dto.getAddress(),  dto.getDate(),dto.getNic()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public List<String> getCustomerNIC() throws SQLException {
        return customerDAO.getCustomerNIC();
    }
}
