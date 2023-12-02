package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.Bitacora;
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLBitacorasDController implements Initializable {

    @FXML
    private TableView<Bitacora> tvBitacoras;
    @FXML
    private TableColumn<?, ?> tcBitacoras;

    public static int idUser = 0;
    private ObservableList<Bitacora> bitacoras = FXCollections.observableArrayList();
    private Bitacora bitacoraSeleccionada = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
    }    
    
    public void inicializarTabla() {
        this.tcBitacoras.setCellValueFactory(new PropertyValueFactory("Bitacoras"));
        
        tvBitacoras.setRowFactory(tv -> {
            TableRow<Bitacora> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    Bitacora objetoSeleccionado = row.getItem();
                    try {
                        Stage stageBitacora = new Stage();
                        stageBitacora.setScene(loadScene("viewController/desarrollador/views/FXMLVerBitacora.fxml"));
                        stageBitacora.setTitle("Visualizar bitacora");
                        stageBitacora.initModality(Modality.WINDOW_MODAL);
                        stageBitacora.initOwner((Stage) this.tvBitacoras.getScene().getWindow());
                        
                        /*stageBitacora.initStyle(StageStyle.UTILITY);
                        stageBitacora.setOnCloseRequest(eventStage -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("¿Está seguro?");
                            alert.setHeaderText("¿Está seguro de cancelar?");
                            alert.setContentText("¿Esta accion no se podra revertir?");
                            alert.showAndWait().ifPresent(response -> {
                                if(response == ButtonType.OK) {
                                    stageBitacora.close();
                                }
                            });
                        });
                        */
                        stageBitacora.showAndWait();
                    } catch (IOException e) {
                        System.out.println("Error de \"IOException\" en archivo \"FXMLBitacorasDController\" en metodo \"clicVerBitacora\"");
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void btnCancelarSeleccion(ActionEvent event) {
    }

    @FXML
    private void btnVerBitacora(ActionEvent event) {
    }
    
}
