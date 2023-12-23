package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.CambioDAO;
import eminus5.databaseManagment.model.POJO.Cambio;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLFormularioCambioController implements Initializable {

    @FXML
    private TextField tfTituloCambio;
    @FXML
    private TextField tfDescCambio;
    @FXML
    private ComboBox<String> cbEstadoCambio;
    @FXML
    private ComboBox<String> cbTipoCambio;
    @FXML
    private DatePicker dpFechaInicioCambio;
    @FXML
    private DatePicker dpFechaFinCambio;
    @FXML
    private TextField tfEsfuerzo;

    public static Cambio currentCambio = null;
    public static int idUser = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
    }    

    private void initializeStage() {
        this.cbEstadoCambio.getItems().setAll(
                "Iniciado",
                "Entregado"
        );
        
        this.cbTipoCambio.getItems().setAll(
                "Frontend",
                "Backend",
                "Controladores",
                "Base de datos",
                "JavaScript"
        );
        
        this.dpFechaFinCambio.setDisable(true);
        this.tfEsfuerzo.setDisable(true);
        this.dpFechaInicioCambio.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
        
        this.dpFechaFinCambio.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(dpFechaInicioCambio.getValue()));
            }
        });
    }
    
    private boolean validateFields() {
        if (tfTituloCambio.getText().length() <= 0 || tfDescCambio.getText().length() <= 0) {
            return true;
        }
        if (cbEstadoCambio.getValue() == null) {
            return true;
        }
        if (cbTipoCambio.getValue() == null) {
            return true;
        }
        if (dpFechaInicioCambio.getValue() == null) {
            return true;
        }
        return false;
    }
    
    @FXML
    private void btnCancelarCambio(ActionEvent event) {
        closeWindow((Stage) this.tfTituloCambio.getScene().getWindow());
    }

    @FXML
    private void btnGuardarCambio(ActionEvent event) {
        if (validateFields() == true) {
            ShowMessage.showMessage(
                    "ERROR",
                    "Campos incompletos",
                    "Faltan datos por ingresar",
                    "Por favor ingrese los datos faltantes"
            );
        } else {
            try {
                Cambio newCambio = new Cambio();
                newCambio.setNombre(this.tfTituloCambio.getText());
                newCambio.setDescripcion(this.tfDescCambio.getText());
                newCambio.setFechaInicio(this.dpFechaInicioCambio.getValue().format
                (DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                newCambio.setEstado(this.cbEstadoCambio.getValue());
                newCambio.setTipo(this.cbTipoCambio.getValue());
                
                ResultOperation  resultCreate = CambioDAO.registrarCambio(idUser, newCambio);
                if (resultCreate.getIsError() == true) {
                    ShowMessage.showMessage(
                            "ERROR",
                            "Error inesperado",
                            resultCreate.getMessage(),
                            "Intente mas tarde"
                    );
                } else {
                    ShowMessage.showMessage(
                            "INFORMATION",
                            "Se ha registrado con exito",
                            "Se registro el cambio",
                            ""
                    );
                    Stage currentStage = (Stage) this.tfTituloCambio.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLFormularioCambioController"
                        + " en metodo \"registrarCambio\"");
                sqlex.printStackTrace();
            }
        }
    }
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("Ésta acción no se podrá revertir");
        
        alert.showAndWait().ifPresent(response -> {
           if (response == ButtonType.OK) {
               currentStage.close();
           } 
        });
    }
}
