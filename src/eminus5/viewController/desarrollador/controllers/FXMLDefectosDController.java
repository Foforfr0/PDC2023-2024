package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.DefectoDAO;
import eminus5.databaseManagment.model.POJO.Defecto;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLDefectosDController implements Initializable {

    @FXML
    private TableView<Defecto> tvDefectos;
    @FXML
    private TableColumn<Defecto, String> tcNombreDefecto;
    @FXML
    private TableColumn<Defecto, String> tcDescripcionDefecto;
    @FXML
    private Button btModificarDefecto;
    @FXML
    private Button btSolicitudCambio;

    public static int idUser = 0;
    private ObservableList<Defecto> defectos = FXCollections.observableArrayList();
    private Defecto defectoSeleccionado = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    public void inicializarTabla() {
        this.tcNombreDefecto.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.tcDescripcionDefecto.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        tvDefectos.setRowFactory(tv -> {
            TableRow<Defecto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()) {
                    defectoSeleccionado = row.getItem();
                    try{
                        Stage stageDefecto = new Stage();
                        FXMLFormularioDefectoController.currentDefecto = defectoSeleccionado;
                        stageDefecto.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioDefecto.fxml"));
                        stageDefecto.setTitle("Formulario defecto");
                        stageDefecto.initModality(Modality.WINDOW_MODAL);
                        stageDefecto.initOwner(
                                (Stage) this.tvDefectos.getScene().getWindow()
                        );
                        stageDefecto.initStyle(StageStyle.UTILITY);
                        stageDefecto.showAndWait();
                    } catch (IOException e) {
                        System.out.println("Error de \"IOException\" en archivo \"FXMLDefectosDController\" en metodo \"modificarDefecto\"");
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void cargarDefectos() {
        try{
            ResultOperation resultGetDefectos = DefectoDAO.getDefectosDesarrollador(idUser);
            
            if(resultGetDefectos.getIsError() == true && resultGetDefectos.getData() == null ||
               resultGetDefectos.getNumberRowsAffected() <= 0){
                showMessage(
                        "ERROR",
                        "Error inesperado",
                        resultGetDefectos.getMessage(),
                        "Intente mas tarde"
                );
            } else {
            this.defectos = FXCollections.observableArrayList(
                    (ObservableList) DefectoDAO.getDefectosDesarrollador(idUser).getData()
            );
            this.tvDefectos.setItems(this.defectos);
            }
        } catch (SQLException sqlex) {
            showMessageFailureConnection();
           System.out.println("\"Error de \"SQLException\" en archivo \"FXMLBitacorasDcontroller\"");
           sqlex.printStackTrace();
        }
    } 
    
    @FXML
    private void btnRegistrarDefecto(ActionEvent event) {
        try {
        Stage clicRegistrarDefecto = new Stage();
        FXMLFormularioDefectoController.idUser = idUser;
        clicRegistrarDefecto.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioDefecto.fxml"));
        clicRegistrarDefecto.setTitle("Formulario defecto");
        clicRegistrarDefecto.initModality(Modality.WINDOW_MODAL);
        clicRegistrarDefecto.initOwner(
                (Stage) this.tvDefectos.getScene().getWindow()
        );
        clicRegistrarDefecto.initStyle(StageStyle.UTILITY);
        clicRegistrarDefecto.setOnCloseRequest(eventStage -> {
            eventStage.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("¿Está seguro?");
            alert.setHeaderText("¿Está seguro de cancelar?");
            alert.setContentText("¿Ésta acción no se podrá revertir?");
            
            alert.showAndWait().ifPresent(response -> {
                String responseMessage = response.getText();
                if (responseMessage.equals("Aceptar")) {
                    clicRegistrarDefecto.close();
                }
            });
        });
        clicRegistrarDefecto.showAndWait();
        
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\""
                    + " en método \"clicRegistrarCambio\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void btnModificarDefecto(ActionEvent event) {
        if (verifySelectedDefecto() != null) {
            //TODO
        } else {
            showMessage(
                    "WARNING",
                    "Seleccion requerida",
                    "Primero selecciona un defecto",
                    "Elije un defecto para modificarlo"
                    );
        }
    }
    
    private Defecto verifySelectedDefecto(){
        int selectedRow = this.tvDefectos.getSelectionModel().getSelectedIndex();
        this.defectoSeleccionado = (selectedRow >= 0) ? this.defectos.get(selectedRow) : null;
        return defectoSeleccionado;
    }

    @FXML
    private void btnSolicitudCambio(ActionEvent event) {
    }
}
