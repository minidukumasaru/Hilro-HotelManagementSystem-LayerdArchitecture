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
import lk.ijse.bo.custom.MealBO;
import lk.ijse.db.dbConnection;
import lk.ijse.model.CustomerDTO;
import lk.ijse.model.MealDTO;
import lk.ijse.util.Regex;
import lk.ijse.view.tdm.CustomerTM;
import lk.ijse.view.tdm.MealTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MealFormController {

    public Button btnDETAILS;
    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colPRICE;
    @FXML
    private TableView<MealTM> tblMEAL;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtMEALID;

    @FXML
    private TextField txtMEALNAME;

    @FXML
    private TextField txtPRICE;

    ObservableList<MealTM> observableList;
    MealBO mealBO = (MealBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MEAL);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        setCellValueFactory();
        loadAllMeal();
        generateNextMealId();
    }



    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("MealId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("MealName"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }
    private void loadAllMeal() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<MealDTO> allMeal = mealBO.getAllMeal();


        for (MealDTO c : allMeal) {
            observableList.add(new MealTM(c.getMealId(), c.getMealName(), c.getPrice(), c.getDate()));
        }

        tblMEAL.setItems(observableList);

    }



    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtMEALID.setText("");
        txtMEALNAME.setText("");
        txtPRICE.setText("");
        txtDATE.setText("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


        if(result.orElse(no) == yes){
            if(!mealBO.deleteMeal(txtMEALID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Meal Delete Succesfully").show();
            }
            clearFields();
        }
        loadAllMeal();
        generateNextMealId();
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String MealId = txtMEALID.getText();
        String MealName = txtMEALNAME.getText();
        double Price = Double.parseDouble(txtPRICE.getText());
        Date date = Date.valueOf(LocalDate.now());



        if(mealBO.addMeal(new MealDTO(MealId,MealName,Price,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Meal Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextMealId();
        loadAllMeal();
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
        String MealId = txtMEALID.getText();
        String MealName = txtMEALNAME.getText();
        double Price = Double.valueOf(txtPRICE.getText());
        Date date = java.sql.Date.valueOf(LocalDate.now());



        if(mealBO.updateMeal(new MealDTO(MealId,MealName,Price,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Meal Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextMealId();
        loadAllMeal();
    }

    private void generateNextMealId() throws SQLException, ClassNotFoundException {
        String nextId = mealBO.generateNewMealID();
        txtMEALID.setText(nextId);
    }

    public void tblMEALOnAction(MouseEvent mouseEvent) {
        Integer index = tblMEAL.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtMEALID.setText(colID.getCellData(index).toString());
        txtMEALNAME.setText(colNAME.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());



    }

    public void txtMEALIDOnAction(ActionEvent actionEvent) {
        txtMEALNAME.requestFocus();
    }

    public void txtMEALNAMEOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSAVEOnAction(actionEvent);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtMEALID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtMEALNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE)) return false;



        return true;
    }
    public void txtMEALIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtMEALID);
    }

    public void txtMEALNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtMEALNAME);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtPRICE);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/MealReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
