package eminus5.viewController.desarrollador.controllers;

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

/**
 * FXML Controller class
 *
 * @author abrah
 */
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

    public static int idUser = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
    }    

    private void initializeStage() {
        this.cbEstadoCambio.getItems().setAll(
                "Asignado",
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
