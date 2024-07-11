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
import lk.ijse.bo.custom.MealBO;
import lk.ijse.bo.custom.RoomBO;
import lk.ijse.db.dbConnection;
import lk.ijse.model.MealDTO;
import lk.ijse.model.RoomDTO;
import lk.ijse.util.Regex;
import lk.ijse.view.tdm.MealTM;
import lk.ijse.view.tdm.RoomTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RoomFormController {
    public Button btnDETAILS;
    @FXML
    private TableColumn<?, ?> colAVALIABILITY;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colDESCRIPTION;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colType;
    @FXML
    private TableView<RoomTM> tblROOM;

    @FXML
    private TextField txtAVALIABILITY;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtDESCRIPTION;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtROOMID;

    @FXML
    private TextField txtType;

    ObservableList<RoomTM> observableList;
    RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        setCellValueFactory();
        loadAllRoom();
        generateNextRoomId();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAVALIABILITY.setCellValueFactory(new PropertyValueFactory<>("Avaliability"));
        colDESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }

    private void loadAllRoom() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<RoomDTO> allRoom = roomBO.getAllRoom();


        for (RoomDTO c : allRoom) {
            observableList.add(new RoomTM(c.getRoomId(), c.getPrice(), c.getDate(),c.getAvaliability(),c.getDescription(),c.getType()));
        }

        tblROOM.setItems(observableList);

    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtROOMID.setText("");
        txtPRICE.setText("");
        txtDATE.setText("");
        txtAVALIABILITY.setText("");
        txtDESCRIPTION.setText("");
        txtType.setText("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();


        if(result.orElse(no) == yes){
            if(!roomBO.deleteRoom(txtROOMID.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION,"Room Delete Succesfully").show();
            }
            clearFields();
        }
        loadAllRoom();
        generateNextRoomId();
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String RoomId = txtROOMID.getText();
        double Price = Double.parseDouble(txtPRICE.getText());
        Date date = Date.valueOf(LocalDate.now());
        String Avaliability = txtAVALIABILITY.getText();
        String Description = txtDESCRIPTION.getText();
        String Type = txtType.getText();




        if(roomBO.addRoom(new RoomDTO(RoomId,Price,date,Avaliability,Description,Type))){
            new Alert(Alert.AlertType.CONFIRMATION, "Meal Added!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextRoomId();
        loadAllRoom();
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
        String RoomId = txtROOMID.getText();
        double Price = Double.parseDouble(txtPRICE.getText());
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String Avaliability = txtAVALIABILITY.getText();
        String Description = txtDESCRIPTION.getText();
        String Type = txtType.getText();



        if(roomBO.updateRoom(new RoomDTO(RoomId,Price,date,Avaliability,Description,Type))){
            new Alert(Alert.AlertType.CONFIRMATION, "Meal Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextRoomId();
        loadAllRoom();
    }


    private void generateNextRoomId() throws SQLException, ClassNotFoundException {
        String nextId = roomBO.generateNewRoomID();
        txtROOMID.setText(nextId);
    }

    public void tblROOMOnAction(MouseEvent mouseEvent) {
        Integer index = tblROOM.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtROOMID.setText(colID.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtAVALIABILITY.setText(colAVALIABILITY.getCellData(index).toString());
        txtDESCRIPTION.setText(colDESCRIPTION.getCellData(index).toString());
        txtType.setText(colType.getCellData(index).toString());


    }

    public void txtROOMIDOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtAVALIABILITY.requestFocus();
    }

    public void txtAVALIABILITYOnAction(ActionEvent actionEvent) {
        txtDESCRIPTION.requestFocus();
    }

    public void txtDESCRIPTIONOnAction(ActionEvent actionEvent) {
        txtType.requestFocus();
    }

    public void txtTYPEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSAVEOnAction(actionEvent);
    }


    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtROOMID)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE)) return false;


        return true;
    }
    public void txtROOMIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtROOMID);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtPRICE);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDATE);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/RoomReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
}
