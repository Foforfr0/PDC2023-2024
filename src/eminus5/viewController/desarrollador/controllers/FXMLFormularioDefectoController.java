package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.utils.ShowMessage;
import java.net.URL;
import java.time.LocalDate;
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


public class FXMLFormularioDefectoController implements Initializable {

    @FXML
    private TextField tfTituloDefecto;
    @FXML
    private TextField tfDescDefecto;
    @FXML
    private ComboBox<String> cbTipoDefecto;
    @FXML
    private TextField tfEsfuerzo;
    @FXML
    private DatePicker dpFechaReporteDefecto;

    public static int idUser = 0;
    public static Defecto currentDefecto = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
    }    

    private void initializeStage() {
        this.cbTipoDefecto.getItems().setAll(
                "Base de datos",
                "JavaScript",
                "Controlador",
                "Vista"
        );
        
        this.tfEsfuerzo.setDisable(true);
        this.dpFechaReporteDefecto.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
    }
    
    private boolean validateFields() {
        if (tfTituloDefecto.getText().length() <= 0 || tfDescDefecto.getText().length() <= 0) {
            return true;
        }
        if (cbTipoDefecto.getValue() == null) {
            return true;
        }
        if (dpFechaReporteDefecto.getValue() == null) {
           return true; 
        }
        return false;
    }
    
    @FXML
    private void btnCancelarDefecto(ActionEvent event) {
        closeWindow((Stage) this.tfTituloDefecto.getScene().getWindow());
        
    }

    @FXML
    private void btnGuardarDefecto(ActionEvent event) {
        if (validateFields() == true) {
            ShowMessage.showMessage(
                    "ERROR",
                    "Campos incompletos",
                    "Faltan datos por ingresar",
                    "Por favor ingrese los datos faltantes"
            );
        } else {
            //TODO crear metodo en CambioDAO
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
