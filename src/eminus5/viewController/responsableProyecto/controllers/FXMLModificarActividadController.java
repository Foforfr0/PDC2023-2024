/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.DAO.ProyectoDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.POJO.Proyecto;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ConvertData.convertStringToLocalDate;
import eminus5.utils.ShowMessage;
import static eminus5.viewController.responsableProyecto.controllers.FXMLCrearActividadController.idResponsable;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class FXMLModificarActividadController implements Initializable {
    @FXML
    private AnchorPane stageCrearActividad;
    @FXML
    private Pane pnPrincipal;
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
    
    
    public static Actividad currentActividad = null;
    public static int idProyecto = 0;
    public static int idResponsable = 0;
    private String fechaInicio = "";
    private String fechaFin = "";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    
    
    private void initializeData() {
        this.tfNombre.setText(currentActividad.getNombre());
        this.tfDescripcion.setText(currentActividad.getDescripcion());
        this.cbTipo.setValue(currentActividad.getTipo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(currentActividad.getFechaInicio(), formatter);
            this.dpFechaInicio.setValue(localDate);
            localDate = LocalDate.parse(currentActividad.getFechaFin(), formatter);
            this.dpFechaFin.setValue(localDate);
        this.cbTipo.setValue(currentActividad.getTipo());
        
        try {
            this.cbTipo.getItems().setAll(                      //addOptionsCbTipo();
                (ObservableList<String>) ActividadDAO.getTiposActividad().getData()
            );
            Proyecto currentProyecto = (Proyecto) ProyectoDAO.getProyectoUsuario(idResponsable).getData();
            fechaInicio = String.valueOf(currentProyecto.getFechaInicio());
            fechaFin = String.valueOf(currentProyecto.getFechaFin());
        } catch (SQLException sqlex) {
            System.err.println("Error de \"SQLException\" en archivo \"FXMLCrearActividadController\" en método \"initializaData\"");
            sqlex.printStackTrace();  
        }
        dpFechaInicio.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                /**
                 * FECHA DE INICIO:
                 * -No puede ser antes de la fecha actual.
                 * -No puede ser después de la fecha de termino.
                 * -No puede estar antes ni después del perido.
                */
                setDisable(
                    date.isAfter(dpFechaFin.getValue() == null ? convertStringToLocalDate(fechaFin) : dpFechaFin.getValue() ) || 
                    date.isAfter(convertStringToLocalDate(fechaFin)) || 
                    date.isBefore(LocalDate.now()) ||
                    date.isBefore(convertStringToLocalDate(fechaInicio))
                );
            }
        });
        dpFechaFin.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                /**
                 * FECHA DE TERMINO:
                 * -No puede estar antes de la fecha de inicio.
                 * -No puede estar antes ni después del perido.
                */
                setDisable(
                    date.isAfter(convertStringToLocalDate(fechaFin)) ||
                    date.isBefore(dpFechaInicio.getValue() == null ? LocalDate.now() : dpFechaInicio.getValue())
                );
            }
        });
    }
    
    private void initializeStage(){        
        stageCrearActividad.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow((Stage) this.tfNombre.getScene().getWindow());
            }
        }); 
    }
    
    private boolean validateFullFields() {      //faltanDatos ? return true : return false;
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (tfDescripcion.getText().length() <= 0) {
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
    private void clicModifyActividad(ActionEvent event) {
        if (validateFullFields() == true) {
            ShowMessage.showMessage(
                "ERROR", 
                "Campos incompletos", 
                "Faltan campos por ingresar", 
                "Favor de ingresar datos faltantes e intente de nuevo"
            );
        } else {
            try {
                Actividad newActividad = new Actividad();
                    newActividad.setIdActividad(currentActividad.getIdActividad());
                    newActividad.setNombre(this.tfNombre.getText());
                    newActividad.setDescripcion(this.tfDescripcion.getText());
                    newActividad.setTipo(this.cbTipo.getValue());
                    newActividad.setFechaInicio(this.dpFechaInicio.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    newActividad.setFechaFin(this.dpFechaFin.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                ResultOperation resultModify = ActividadDAO.modifyActividad(newActividad);
                if (resultModify.getIsError() == true) {
                    ShowMessage.showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        resultModify.getMessage(), 
                        "Intente más tarde"
                    );
                } else {
                    ShowMessage.showMessage(
                            "INFORMATION", 
                            "Se ha creado correctamente", 
                            "Se modificó con éxito la actividad", 
                            ""
                    );
                    Stage currentStage = (Stage) this.tfNombre.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLCrearActividadController\" en método \"createActividad\"");
                sqlex.printStackTrace();
            }
        }
    }
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("¿Ésta acción no se podrá revertir?");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close(); 
            }
        });
    }

    @FXML
    private void clicCancelAdd(ActionEvent event) {
        closeWindow((Stage) this.tfNombre.getScene().getWindow());
    }   
}
