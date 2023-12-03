package eminus5.viewController.desarrollador.controllers;

import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLCambiosDController implements Initializable {

    @FXML
    private TableView<?> tvCambios;
    @FXML
    private TableColumn<?, ?> tcNombreCambio;
    @FXML
    private TableColumn<?, ?> tcDescripcionCambio;
    @FXML
    private Button btCambio;

    public static int idUser = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnRegistrarCambio(ActionEvent event) {
        try{
            Stage clicRegistrarCambio = new Stage();
            FXMLFormularioCambioController.idUser = idUser;
            clicRegistrarCambio.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioCambio.fxml"));
            clicRegistrarCambio.setTitle("Formulario cambio");
            clicRegistrarCambio.initModality(Modality.WINDOW_MODAL);
            clicRegistrarCambio.initOwner(
                    (Stage) this.tvCambios.getScene().getWindow()
            );
            clicRegistrarCambio.initStyle(StageStyle.UTILITY);
            clicRegistrarCambio.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("¿Ésta acción no se podrá revertir?");
                
                alert.showAndWait().ifPresent(response ->{
                   String responseMessage = response.getText();
                   if (responseMessage.equals("Aceptar")) {
                       clicRegistrarCambio.close();
                   }
                });
            });
            clicRegistrarCambio.showAndWait();
            
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\""
                    + " en método \"clicRegistrarCambio\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void btnModificarCambio(ActionEvent event) {
    }
    
}
