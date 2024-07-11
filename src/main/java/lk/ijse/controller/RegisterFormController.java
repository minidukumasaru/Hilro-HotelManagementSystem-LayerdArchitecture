package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.model.UserDTO;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegisterFormController {
    public Button btnBACK;
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        generateNextUserId();

    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    @FXML
    void btnREGISTEROnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String UserId = txtUserId.getText();
        String UserName = txtUserName.getText();
        String Contact = txtUserName.getText();
        String Position = txtUserName.getText();
        String Password = txtPassword.getText();
        Date date = Date.valueOf(txtDate.getText());


            if(userBO.addUser(new UserDTO(UserId, UserName,Contact,Position,Password,date))) {
                new Alert(Alert.AlertType.CONFIRMATION, "Register successfully!!!").show();

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                Stage stage = (Stage) rootNode.getScene().getWindow();

                stage.setScene(new Scene(anchorPane));
                stage.setTitle("Dashboard Form");
                stage.centerOnScreen();

            }else{
                new Alert(Alert.AlertType.ERROR, "Can't register this user").show();
        }
        generateNextUserId();


    }



    private void generateNextUserId() throws ClassNotFoundException {
        try {
            String nextId = userBO.generateNewUserID();
            txtUserId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnBACKOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    public void txtUSERIDOnAction(ActionEvent actionEvent) {
        txtUserName.requestFocus();
    }

    public void txtUSERNAMEOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtPosition.requestFocus();
    }

    public void txtPOSITIONOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPASSWORDOnAction(ActionEvent actionEvent) {
        txtDate.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        btnREGISTEROnAction(actionEvent);
    }


    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.ID, txtUserId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtPosition)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD, txtPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDate)) return false;


        return true;
    }
    public void txtUSERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ID, txtUserId);
    }

    public void txtUSERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtUserName);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtContact);
    }
    public void txtPOSITIONOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtPosition);
    }

    public void txtPASSWORDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD, txtPassword);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDate);
    }
}
