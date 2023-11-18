/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.ResultOperation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import eminus5.utils.ShowMessage;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLCrearActividadController implements Initializable {
    @FXML
    private TextField tfNombre;    
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Button btAddActividad;
    @FXML
    private Button btCancelAddActividad;

    
    public static int idResponsable = 0;
    @FXML
    private AnchorPane stageCrearActividad;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addOptionsCbTipo();
        initializaDatePickers();
        //addActionToStage((Stage) this.tfNombre.getScene().getWindow());
    }    
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("¿Ésta acción no se podrá revertir?");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close(); 
            }
        });
    }
    
    private void addActionToStage(Stage currentStage) {
        //Stage currentStage = (Stage) this.tfNombre.getScene().getWindow();
        currentStage.setOnCloseRequest(event -> {
            event.consume(); 
            closeWindow(currentStage);
        });
    }
            
    private void addOptionsCbTipo() {
        this.cbTipo.getItems().setAll(
            "Frontend", 
            "Backend", 
            "Controladoes", 
            "Base de datos", 
            "JavaScript"
        );
    }
    
    private void initializaDatePickers() {
        
    }
    
    private boolean validateFullFields() {      //faltanDatos ? return true : return false;
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (tfDescripcion.getText().length() <= 0) {
            return true;
        }
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (cbTipo.getValue() == null) {
            return true;
        }
        if (dpFechaInicio.getValue() == null) {
            return true;
        }
        if (dpFechaFin.getValue() == null) {
            return true;
        }
        return false;
    }
    
    @FXML
    private void clicAddActividad(ActionEvent event) {
        if (validateFullFields() == true) {
            ShowMessage.showMessage(
                "ERROR", 
                "Campos incompletos", 
                "Faltan campos por ingresar", 
                "Favor de ingresar datos faltantes e intente de nuevo"
            );
        } else {
            try {
                ResultOperation resultCreate = ActividadDAO.createActividad(idResponsable);
                if (resultCreate.getIsError() == true) {
                    ShowMessage.showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        resultCreate.getMessage(), 
                        "Intente más tarde"
                    );
                }
            } catch (SQLException sqlex) {
                System.out.println("\"Error de \"SQLException\" en archivo \"FXMLCrearActividadController\" en método \"createActividad\"");
                sqlex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicCancelAdd(ActionEvent event) {
        closeWindow((Stage) this.tfNombre.getScene().getWindow());
    }
}
