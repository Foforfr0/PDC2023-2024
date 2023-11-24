/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ShowMessage.showMessageFailureConnection; 
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLActividadesProyectoController implements Initializable {
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn<Actividad, String> tcNombre;
    @FXML
    private TableColumn<Actividad, String> tvAsignado;
    @FXML
    private TableColumn<Actividad, String> tcEstado;
    @FXML
    private TableColumn<Actividad, String> tcTipo;
    @FXML
    private TableColumn<Actividad, String> tcFechaInicio;
    @FXML
    private TableColumn<Actividad, String> tcFechaFin;
    
    
    public static int idResponsable = 0;
    private ObservableList<Actividad> actividades = FXCollections.observableArrayList();
    private Actividad selectedActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        initializeData();
    }    
    
    public void initializeTable() {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tvAsignado.setCellValueFactory(new PropertyValueFactory("isAsignado"));
        this.tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.tcFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        this.tcFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        
        tvActividades.setRowFactory(tv -> {
            TableRow<Actividad> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedActividad = row.getItem();
                    try {
                        Stage stageAddPaciente = new Stage();
                        
                        FXMLDetallesActividadController.currentActividad = selectedActividad;
                        stageAddPaciente.setScene(loadScene("viewController/responsableProyecto/views/FXMLDetallesActividad.fxml"));
                        stageAddPaciente.setTitle("Detalles de actividad");
                        stageAddPaciente.initModality(Modality.WINDOW_MODAL);
                        stageAddPaciente.initOwner(
                            (Stage) this.tvActividades.getScene().getWindow()
                        );
                        stageAddPaciente.initStyle(StageStyle.UTILITY);
                        stageAddPaciente.showAndWait();
                    } catch (IOException ioex) {
                        System.out.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicModifyActividad\"");
                        ioex.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void initializeData() {
        try{            
            this.actividades = FXCollections.observableArrayList(
                (ObservableList) ActividadDAO.getActividadesProyecto(idResponsable).getData()
            );
            this.tvActividades.setItems(this.actividades);
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.out.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
        
        this.tvActividades.getSortOrder().add(this.tcNombre);
    }

    @FXML
    private void clicAddActividad(MouseEvent event) {
        try {
            Stage stageAddPaciente = new Stage();
            stageAddPaciente.setScene(loadScene("viewController/responsableProyecto/views/FXMLCrearActividad.fxml"));
            stageAddPaciente.setTitle("Crear actividad");
            stageAddPaciente.initModality(Modality.WINDOW_MODAL);
            stageAddPaciente.initOwner(
                (Stage) this.tvActividades.getScene().getWindow()
            );
            stageAddPaciente.initStyle(StageStyle.UTILITY);
            stageAddPaciente.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("¿Ésta acción no se podrá revertir?");


                alert.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        stageAddPaciente.close(); 
                    }
                });
            });
            stageAddPaciente.showAndWait();
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicAddActividad\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void clicModifyActividad(MouseEvent event) {
        if (verifyActividadSelected() != null) {
            try {
                Stage stageAddPaciente = new Stage();
                FXMLModificarActividadController.currentActividad = verifyActividadSelected();
                //FXMLModificarActividadController.idActividad = verifyActividadSelected().getIdActividad();
                stageAddPaciente.setScene(loadScene("viewController/responsableProyecto/views/FXMLModificarActividad.fxml"));
                stageAddPaciente.setTitle("Modificar actividad");
                stageAddPaciente.initModality(Modality.WINDOW_MODAL);
                stageAddPaciente.initOwner(
                    (Stage) this.tvActividades.getScene().getWindow()
                );
                stageAddPaciente.initStyle(StageStyle.UTILITY);
                stageAddPaciente.setOnCloseRequest(eventStage -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Está seguro?");
                    alert.setHeaderText("¿Está seguro de cancelar?");
                    alert.setContentText("¿Ésta acción no se podrá revertir?");
                        
                    alert.showAndWait().ifPresent(response -> {
                        String responseMessage = response.getText();
                        if (responseMessage.equals("Aceptar")) {
                            stageAddPaciente.close(); 
                        }
                    });
                });
                stageAddPaciente.showAndWait();
            } catch (IOException ioex) {
                System.out.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicModifyActividad\"");
                ioex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING", 
                "Selección requerida", 
                "Primero selecciona una actividad", 
                "Eliga una actividad para poder modificarla"
            );
        }
    }
    
    private Actividad verifyActividadSelected(){
        int selectedRow = this.tvActividades.getSelectionModel().getSelectedIndex();
        this.selectedActividad = (selectedRow >= 0) ? this.actividades.get(selectedRow) : null ;
        return selectedActividad;
    }
    
    @FXML
    private void clicDeleteActividad(MouseEvent event) {
        if (verifyActividadSelected() != null) {
            try {
                ResultOperation resultDelete = ActividadDAO.deleteActividad(
                        verifyActividadSelected().getIdActividad()
                );
                
                if (resultDelete.getIsError() == false && resultDelete.getNumberRowsAffected() > 0) {
                    
                } else if (resultDelete.getIsError() == true) {
                    showMessage(
                        "ERROR", 
                        "Falló de eliminación", 
                        "Falló la eliminación de la actividad", 
                        "Intente más tarde"
                    );
                }
            } catch (SQLException sqlex) {
                System.out.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"ActividadDAO.deleteActividad\"");
                sqlex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING", 
                "Selección requerida", 
                "Primero selecciona una actividad", 
                "Eliga una actividad para poder eliminarla"
            );
        }
    }
}
