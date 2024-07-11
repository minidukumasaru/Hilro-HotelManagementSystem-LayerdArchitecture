package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.db.dbConnection;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.EmployeeDTO;
import lk.ijse.model.UserDTO;
import lk.ijse.util.Regex;
import lk.ijse.view.tdm.EmployeeTM;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    public Button btnDETAILS;
    @FXML
    private TableColumn<?, ?> colADDRESS;

    @FXML
    private TableColumn<?, ?> colATTENDENCE;

    @FXML
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colPOSITION;

    @FXML
    private TableColumn<?, ?> colSALARY;

    @FXML
    private TableColumn<?, ?> colUSERID;

    @FXML
    private TableColumn<?, ?> colWORKHOURS;

    @FXML
    private ComboBox<String> combUSERID;
    @FXML
    private TableView<EmployeeTM> tblEMPLOYEE;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtATTENDENCE;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtEMPLOYEEID;

    @FXML
    private TextField txtEMPLOYEENAME;

    @FXML
    private TextField txtPOSITION;

    @FXML
    private TextField txtSALARY;

    @FXML
    private TextField txtWORKHOURS;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    ObservableList<EmployeeTM> observableList;


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setCellValueFactory();
        loadAllEmployee();
        getUserIds();
        generateNextEmployeeId();
    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colWORKHOURS.setCellValueFactory(new PropertyValueFactory<>("WorkHours"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colSALARY.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colPOSITION.setCellValueFactory(new PropertyValueFactory<>("Position"));
        colATTENDENCE.setCellValueFactory(new PropertyValueFactory<>("Attendence"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colUSERID.setCellValueFactory(new PropertyValueFactory<>("UserId"));

    }
    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<EmployeeDTO> allEmployee = employeeBO.getAllEmployee();

        for (EmployeeDTO e : allEmployee) {
            observableList.add(new EmployeeTM(e.getEmployeeId(), e.getEmployeeName(),e.getDate(),e.getWorkHours(),e.getContact(),e.getSalary(),e.getPosition(),e.getAttendence(),e.getAddress(),e.getUserId()));
        }
        tblEMPLOYEE.setItems(observableList);
    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEMPLOYEEID.setText("");
        txtEMPLOYEENAME.setText("");
        txtDATE.setText("");
        txtWORKHOURS.setText("");
        txtCONTACT.setText("");
        txtSALARY.setText("");
        txtPOSITION.setText("");
        txtATTENDENCE.setText("");
        txtADDRESS.setText("");
        combUSERID.setValue("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes){
            if(employeeBO.deleteEmployee(txtEMPLOYEEID.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete Successfully!!").show();
            }
            clearFields();
            generateNextEmployeeId();
            loadAllEmployee();

        }
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        Date date = Date.valueOf(LocalDate.now());
        String WorkHours = txtWORKHOURS.getText();
        String Contact = txtCONTACT.getText();
        double Salary = Double.parseDouble(txtSALARY.getText());
        String Position = txtPOSITION.getText();
        String Attendence = txtATTENDENCE.getText();
        String Address = txtADDRESS.getText();
        String UserId = combUSERID.getValue();


        if (employeeBO.addEmployee(new EmployeeDTO(EmployeeId, EmployeeName, date,WorkHours ,Contact, Salary, Position,Attendence,Address,UserId))) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
        clearFields();
        generateNextEmployeeId();
        loadAllEmployee();
    }





    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String WorkHours = txtWORKHOURS.getText();
        String Contact = txtCONTACT.getText();
        double Salary = Double.parseDouble(txtSALARY.getText());
        String Position = txtPOSITION.getText();
        String Attendence = txtATTENDENCE.getText();
        String Address = txtADDRESS.getText();
        String UserId = combUSERID.getValue();



        if(employeeBO.updateEmployee(new EmployeeDTO(EmployeeId, EmployeeName, date,WorkHours ,Contact, Salary, Position,Attendence,Address,UserId))){
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        clearFields();
        loadAllEmployee();
        generateNextEmployeeId();
    }
    private void getUserIds() {
        try {
            ArrayList<UserDTO> allUsers =employeeBO.getAllUsers();
            for (UserDTO c : allUsers) {
                combUSERID.getItems().add(c.getUserId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load User ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void generateNextEmployeeId() throws SQLException, ClassNotFoundException {
        String nextId = employeeBO.generateNewEmployeeID();
        txtEMPLOYEEID.setText(nextId);
    }

    public void tblEMPLOYEEOnAction(MouseEvent mouseEvent) {
        Integer index = tblEMPLOYEE.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtEMPLOYEEID.setText(colID.getCellData(index).toString());
        txtEMPLOYEENAME.setText(colNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtWORKHOURS.setText(colWORKHOURS.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtSALARY.setText(colSALARY.getCellData(index).toString());
        txtPOSITION.setText(colPOSITION.getCellData(index).toString());
        txtATTENDENCE.setText(colATTENDENCE.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        combUSERID.setValue(colUSERID.getCellData(index).toString());



    }

    public void txtEMPLOYEEIDOnAction(ActionEvent actionEvent) {
        txtEMPLOYEENAME.requestFocus();
    }

    public void txtEMPLOYEENAMEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtWORKHOURS.requestFocus();
    }

    public void txtWORKHOURSOnAction(ActionEvent actionEvent) {
        txtSALARY.requestFocus();
    }

    public void txtSALARYOnAction(ActionEvent actionEvent) {
        txtPOSITION.requestFocus();
    }

    public void txtPOSITIONOnAction(ActionEvent actionEvent) {
        txtATTENDENCE.requestFocus();
    }

    public void txtATTENDENCEOnAction(ActionEvent actionEvent) {
        combUSERID.requestFocus();
    }

    public void combUSERIDOnAction(ActionEvent actionEvent) {

    }


    public void txtEMPLOYEEIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtEMPLOYEEID);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtEMPLOYEEID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtEMPLOYEENAME)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtADDRESS)) return false;


        return true;
    }

    public void txtEMPLOYEENAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtEMPLOYEENAME);
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

    public void btnSDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/EmployeeReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }



}
