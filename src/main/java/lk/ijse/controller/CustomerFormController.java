package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.db.dbConnection;
import lk.ijse.model.CustomerDTO;
import lk.ijse.util.Regex;
import lk.ijse.view.tdm.CustomerTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {

    public Button btnDETAILS;
    @FXML
    private TableColumn<?, ?> colADDRESS;

    @FXML
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableView<CustomerTM> tblCUSTOMER;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtCUSTOMERID;

    @FXML
    private TextField txtCUSTOMERNAME;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtNIC;

    ObservableList<CustomerTM> observableList;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        setCellValueFactory();
        loadAllCustomers();
        generateNextCustomerId();

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("Nic"));


    }

    private void generateNextCustomerId() throws SQLException, ClassNotFoundException {
       String nextId = customerBO.generateNewCustomerID();
       txtCUSTOMERID.setText(nextId);
    }
    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<CustomerDTO> allCustomer = customerBO.getAllCustomer();


            for (CustomerDTO c : allCustomer) {
                observableList.add(new CustomerTM(c.getCustomerId(), c.getCustomerName(), c.getContact(), c.getAddress(), c.getDate(), c.getNic()));
            }

            tblCUSTOMER.setItems(observableList);

    }


    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCUSTOMERID.setText("");
        txtCUSTOMERNAME.setText("");
        txtCONTACT.setText("");
        txtADDRESS.setText("");
        txtDATE.setText("");
        txtNIC.setText("");

    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


        if(result.orElse(no) == yes){
            if(!customerBO.deleteCustomer(txtCUSTOMERID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Customer Delete Succesfully").show();
            }
            clearFields();
        }
        loadAllCustomers();
        generateNextCustomerId();
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        String Contact = txtCONTACT.getText();
        String Address = txtADDRESS.getText();
        Date date = Date.valueOf(LocalDate.now());
        String Nic = txtNIC.getText();

        if(customerBO.addCustomer(new CustomerDTO(CustomerId,CustomerName,Contact,Address,date,Nic))){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextCustomerId();
        loadAllCustomers();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event)  {

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(!isValied()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields!").show();
            return;
        }
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        String Contact = txtCONTACT.getText();
        String Address = txtADDRESS.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String Nic = txtNIC.getText();



        if(customerBO.updateCustomer(new CustomerDTO(CustomerId,CustomerName,Contact,Address,date,Nic))){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextCustomerId();
        loadAllCustomers();
    }

    public void tblCUSTOMEROnAction(MouseEvent mouseEvent) {
        Integer index = tblCUSTOMER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtCUSTOMERID.setText(colID.getCellData(index).toString());
        txtCUSTOMERNAME.setText(colNAME.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtNIC.setText(colNIC.getCellData(index).toString());

    }


    public void txtCUSTOMERIDOnAction(ActionEvent actionEvent) {
        txtCUSTOMERNAME.requestFocus();
    }

    public void txtCUSTOMERNAMEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtNIC.requestFocus();
    }

    public void txtNICOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSAVEOnAction(actionEvent);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtCUSTOMERID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtCUSTOMERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NIC, txtNIC)) return false;


        return true;
    }
    public void txtCUSTOMERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtCUSTOMERID);
    }

    public void txtCUSTOMERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtCUSTOMERNAME);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtCONTACT);
    }

    public void txtNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NIC, txtNIC);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }


}
