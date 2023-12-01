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


public class FXMLDefectosDController implements Initializable {

    @FXML
    private TableView<?> tvDefectos;
    @FXML
    private TableColumn<?, ?> tcNombreDefecto;
    @FXML
    private TableColumn<?, ?> tcDescripcionDefecto;
    @FXML
    private Button btModificarDefecto;

    public static int idUser = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }
    
}
