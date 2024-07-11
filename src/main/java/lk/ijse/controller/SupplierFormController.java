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
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.db.dbConnection;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.SupplierDTO;
import lk.ijse.util.Regex;
import lk.ijse.view.tdm.CustomerTM;
import lk.ijse.view.tdm.SupplierTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SupplierFormController {
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
    private TableColumn<?, ?> colPRODUCTNAME;

    @FXML
    private TableColumn<?, ?> colQUANTITY;
    @FXML
    private TableView<SupplierTM> tblSUPPLIER;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtPRODUCTNAME;

    @FXML
    private TextField txtQUANTITY;

    @FXML
    private TextField txtSUPPLIERID;

    @FXML
    private TextField txtSUPPLIERNAME;
    ObservableList<SupplierTM> observableList;
    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        setCellValueFactory();
        loadAllSupplier();
        generateNextSupplierId();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colQUANTITY.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colPRODUCTNAME.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }
    private void loadAllSupplier() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<SupplierDTO> allSupplier = supplierBO.getAllSupplier();


        for (SupplierDTO c : allSupplier) {
            observableList.add(new SupplierTM(c.getSupplierId(), c.getSupplierName(), c.getAddress(), c.getQuantity(), c.getContact(), c.getProductName(), c.getDate()));
        }

        tblSUPPLIER.setItems(observableList);

    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSUPPLIERID.setText("");
        txtSUPPLIERNAME.setText("");
        txtADDRESS.setText("");
        txtQUANTITY.setText("");
        txtCONTACT.setText("");
        txtPRODUCTNAME.setText("");
        txtDATE.setText("");



    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


        if(result.orElse(no) == yes){
            if(!supplierBO.deleteSupplier(txtSUPPLIERID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Delete Succesfully").show();
            }
            clearFields();
        }
        loadAllSupplier();
        generateNextSupplierId();
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        String Quantity = txtQUANTITY.getText();
        String Contact = txtCONTACT.getText();
        String ProductName = txtPRODUCTNAME.getText();
        Date date = Date.valueOf(LocalDate.now());


        if(supplierBO.addSupplier(new SupplierDTO(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextSupplierId();
        loadAllSupplier();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(!isValied()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields!").show();
            return;
        }
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        String Quantity = txtQUANTITY.getText();
        String Contact = txtCONTACT.getText();
        String ProductName = txtPRODUCTNAME.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());


        if(supplierBO.updateSupplier(new SupplierDTO(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextSupplierId();
        loadAllSupplier();
    }

    private void generateNextSupplierId() throws SQLException, ClassNotFoundException {
        String nextId = supplierBO.generateNewSupplierID();
        txtSUPPLIERID.setText(nextId);
    }

    public void tblSUPPLIEROnAction(MouseEvent mouseEvent) {
        Integer index = tblSUPPLIER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtSUPPLIERID.setText(colID.getCellData(index).toString());
        txtSUPPLIERNAME.setText(colNAME.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtQUANTITY.setText(colQUANTITY.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtPRODUCTNAME.setText(colPRODUCTNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());


    }

    public void txtSUPPLIERIDOnAction(ActionEvent actionEvent) {
        txtSUPPLIERNAME.requestFocus();
    }

    public void txtSUPPLIERNAMEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtQUANTITY.requestFocus();
    }

    public void txtQUANTITYOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtPRODUCTNAME.requestFocus();
    }

    public void txtPRODUCTNAMEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSAVEOnAction(actionEvent);
    }



    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtSUPPLIERID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtSUPPLIERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.QUANTITY, txtQUANTITY)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtPRODUCTNAME)) return false;


        return true;
    }
    public void txtSUPPLIERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtSUPPLIERID);
    }

    public void txtSUPPLIERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtSUPPLIERNAME);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE);
    }
    public void txtQUANTITYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.QUANTITY, txtQUANTITY);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtCONTACT);
    }

    public void txtPRODUCTNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtPRODUCTNAME);
    }


    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/SupplierReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
