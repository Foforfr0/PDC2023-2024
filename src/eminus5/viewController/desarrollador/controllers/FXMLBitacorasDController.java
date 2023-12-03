package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.BitacoraDAO;
import eminus5.databaseManagment.model.POJO.Bitacora;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLBitacorasDController implements Initializable {
    
    @FXML
    private TableView<Bitacora> tvBitacoras;
    @FXML
    private TableColumn<Bitacora, String> tcTituloBitacora;
    @FXML
    private TableColumn<Bitacora, String> tcDescripcion;
    @FXML
    private Button btCancelarSeleccion;
    
    public static int idUser = 0;
    private ObservableList<Bitacora> bitacoras = FXCollections.observableArrayList();
    private Bitacora bitacoraSeleccionada = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
        cargarBitacoras();
    }    
    
    public void inicializarTabla() {
        this.tcTituloBitacora.setCellValueFactory(new PropertyValueFactory("Titulo"));
        this.tcDescripcion.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        tvBitacoras.setRowFactory(tv -> {
            TableRow<Bitacora> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()){
                    bitacoraSeleccionada = row.getItem();
                    try {
                        Stage stageBitacora = new Stage();
                        FXMLVerBitacoraController.currentBitacora = bitacoraSeleccionada;
                        stageBitacora.setScene(loadScene("viewController/desarrollador/views/FXMLVerBitacora.fxml"));
                        stageBitacora.setTitle("Visualizar bitacora");
                        stageBitacora.initModality(Modality.WINDOW_MODAL);
                        stageBitacora.initOwner(
                                (Stage) this.tvBitacoras.getScene().getWindow()
                        );
                        
                        stageBitacora.initStyle(StageStyle.UTILITY);
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
    
    public void cargarBitacoras(){
        try{
            ResultOperation resultGetBitacoras = BitacoraDAO.getBitacoras(idUser);
            
            if(resultGetBitacoras.getIsError() == true && resultGetBitacoras.getData() == null ||
               resultGetBitacoras.getNumberRowsAffected() <= 0){
                showMessage(
                        "ERROR",
                        "Error inesperado",
                        resultGetBitacoras.getMessage(),
                        "Intente mas tarde"
                );
            } else {
            this.bitacoras = FXCollections.observableArrayList(
                    (ObservableList) BitacoraDAO.getBitacoras(idUser).getData()
            );
            this.tvBitacoras.setItems(this.bitacoras);
            }
        } catch (SQLException sqlex) {
            showMessageFailureConnection();
           System.out.println("\"Error de \"SQLException\" en archivo \"FXMLBitacorasDcontroller\"");
           sqlex.printStackTrace();
        }
    }

    @FXML
    private void btnCancelarSeleccion(ActionEvent event) {
    }

    @FXML
    private void btnVerBitacora(ActionEvent event) {
    }
    
}
