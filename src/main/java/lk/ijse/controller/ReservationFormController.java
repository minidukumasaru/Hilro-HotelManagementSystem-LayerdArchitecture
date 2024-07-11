package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.*;
import lk.ijse.bo.custom.impl.ReservationBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.MealDAO;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.MealDAOImpl;
import lk.ijse.dao.custom.impl.RoomDAOImpl;
import lk.ijse.db.dbConnection;
import lk.ijse.entity.*;
import lk.ijse.model.*;
import lk.ijse.view.tdm.CartTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ReservationFormController {
    public ComboBox<String> combMEALID;
    public TextField txtMEALNAME;
    public TextField txtPRICE;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public Button btnAddToCart;
    public TableColumn colAction;
    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colDURATION;

    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private ComboBox<String> combCUSTOMERNIC;

    @FXML
    private ComboBox<String> combROOMID;

    @FXML
    private ComboBox<String> combUSERID;
    @FXML
    private TableView<CartTM> tblRESERVATION;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;
    @FXML
    private TextField txtCUSTOMERNAME;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtDURATION;
    @FXML
    private TextField txtNETBALANCE;

    @FXML
    private TextField txtRESERVATIONID;

    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    ReservationBOImpl reservationBO = (ReservationBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    CustomerBO customerDAO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    RoomBO roomDAO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);

    UserBO userDAO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    MealBO mealDAO =(MealBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.MEAL);

    public void initialize() throws SQLException, ClassNotFoundException {
        setDate();
        setCellValueFactory();
        getUserIds();
        getRoomIds();
        getCustomerNic();
        generateNextReservationId();
        getMealIds();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
        txtDURATION.setText(String.valueOf(now));
    }

    private void getUserIds() throws ClassNotFoundException {
        try {
            ArrayList<UserDTO> allUsers =userDAO.getAllUsers();
            for (UserDTO c : allUsers) {
                combUSERID.getItems().add(c.getUserId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load User ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void getRoomIds() throws ClassNotFoundException {
       // ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RoomDTO> roomList = roomDAO.getAllRoom();
            for (RoomDTO id : roomList) {
                combROOMID.getItems().add(id.getRoomId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getMealIds() throws ClassNotFoundException {
       // ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<MealDTO> mealList = mealDAO.getAllMeal();
            for (MealDTO id : mealList) {
               combMEALID.getItems().add(id.getMealId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomerNic()   {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> customerList = customerDAO.getCustomerNIC();
            for (String id : customerList) {
                obList.add(String.valueOf(id));
            }
            combCUSTOMERNIC.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void combCUSTOMERNICOnAction(ActionEvent actionEvent) {
        String nic =  combCUSTOMERNIC.getValue();

        try {
            CustomerDTO customerDTO = reservationBO.SEARCHbyNic(nic);
            txtADDRESS.setText(customerDTO.getAddress());
            txtCONTACT.setText(customerDTO.getContact());
            txtCUSTOMERNAME.setText(customerDTO.getCustomerName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void combROOMIDOnAction(ActionEvent actionEvent) throws ClassNotFoundException, RuntimeException {
        String roomid =  combROOMID.getValue();

        try {
            RoomDAOImpl roomDAO1 = new RoomDAOImpl();
            Room roomDTO = roomDAO1.search(roomid);


            if (roomDTO!= null){
                txtPRICE.setText(String.valueOf(roomDTO.getPrice()));
                txtMEALNAME.setText(String.valueOf(roomDTO.getType()));
                combMEALID.getSelectionModel().clearSelection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void combMEALNAMEOnAction(ActionEvent actionEvent) {
        String MealId =  combMEALID.getValue();
        MealDAO mealDTO1 = new MealDAOImpl();

        try {
            Meal mealDTO = mealDTO1.search(MealId);


            if (mealDTO!= null){
                txtPRICE.setText(String.valueOf(mealDTO.getPrice()));
                txtMEALNAME.setText(String.valueOf(mealDTO.getMealName()));
                combROOMID.getSelectionModel().clearSelection();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void generateNextReservationId() throws SQLException, ClassNotFoundException {
        String nextId = reservationBO.generateNewOrderID();
        txtRESERVATIONID.setText(nextId);
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("InDate"));
        colDURATION.setCellValueFactory(new PropertyValueFactory<>("OutDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblRESERVATION.getItems().size(); i++) {
            netTotal += (double) colAmount.getCellData(i);
        }
        txtNETBALANCE.setText(String.valueOf(netTotal));
    }


    @FXML
    void btnPLACEORDEROnAction(ActionEvent event) throws JRException, SQLException {
        String ReCode=txtRESERVATIONID.getText();
        Date date = Date.valueOf(txtDATE.getText());
        String userId =combUSERID.getValue();
        String Nic =combCUSTOMERNIC.getValue();
        String roomId= null;
        String mealId= null;
        char rId='R';

        Reservation reservation = new Reservation(ReCode, date, Nic, userId);
        ArrayList<ReservationDetails> odList = new ArrayList<>();

        for (int i = 0; i < tblRESERVATION.getItems().size(); i++){
            CartTM tm = obList.get(i);

            boolean isEqual = checkId(tm.getId(),rId);

            if (isEqual){
                roomId = tm.getId();
                mealId = null;

            }else {
                mealId= tm.getId();
                roomId = null;
            }

            ReservationDetails rd = new ReservationDetails(
                    ReCode,
                    userId,
                    Nic,
                    roomId,
                    mealId,
                    tm.getInDate(),
                    tm.getOutDate()

            );

            odList.add(rd);
            System.out.println("rd = " + rd);
        }
        ReservationPlacedDTO rl = new ReservationPlacedDTO(reservation,odList);


        try {
            System.out.println( "chhhh");
            boolean isPlaced =reservationBO.placereservation(rl);
            System.out.println("reseverstin"+ isPlaced);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                makeBill();

            }else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void makeBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/BillDetail.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("ReservationId1",txtRESERVATIONID.getText());
        data.put("NetTotal",txtNETBALANCE.getText());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);



    }
    private boolean checkId(String id, char rId) {
        for (int i = 0; i < id.length(); i++) {
            char ch = id.charAt(i);

            if (rId == (ch)) {
                System.out.println("chhhhhhh");
                return true;
            } else {
//                System.out.println("false");
                return false;
            }
        }
        return false;
    }








    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String ReCode=txtRESERVATIONID.getText();
        Date Indate = Date.valueOf(txtDATE.getText());
        String time =txtDURATION.getText();
        String Nic =combCUSTOMERNIC.getValue();
        String cusName=txtCUSTOMERNAME.getText();
        String Address =txtADDRESS.getText();
        String contact=txtCONTACT.getText();
        String userId =combUSERID.getValue();
        Date OutDate = Date.valueOf(txtDURATION.getText());

        String id;
        String name=txtMEALNAME.getText();
        double price= Double.parseDouble(txtPRICE.getText());

        if (combMEALID.getValue() == null){
            id=combROOMID.getValue();
            System.out.println("name"+name);

        }else {
            id=combMEALID.getValue();
            System.out.println("name       "+id);
        }
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblRESERVATION.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblRESERVATION.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblRESERVATION.getItems().size(); i++) {
            if(ReCode.equals(colID.getCellData(i))) {

                CartTM tm = obList.get(i);

                tblRESERVATION.refresh();

                calculateNetTotal();
                return;
            }
        }

        CartTM tm = new CartTM(userId,ReCode,Indate,OutDate,id,name,price,Address,contact, btnRemove);
        obList.add(tm);

        tblRESERVATION.setItems(obList);
        calculateNetTotal();


    }

}
