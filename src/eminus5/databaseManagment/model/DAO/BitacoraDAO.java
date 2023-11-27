/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.databaseManagment.model.POJO.Bitacora;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BitacoraDAO {
    public static ResultOperation getBitacoras(int idUser) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if(connectionDB != null){
            try{
                String sqlQuery = "SELECT * FROM Bitacora WHERE idUser = ?";
                PreparedStatement prepareQuery= connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();
                ObservableList<Bitacora> listBitacoras = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Bitacora newBitacora = new Bitacora(
                            resultQuery.getInt("idBitacora"),
                            resultQuery.getInt("numBitacora"),
                            resultQuery.getString("nombreCambio"),
                            resultQuery.getString("descripcion"),
                            resultQuery.getInt("idEstado"),
                            resultQuery.getInt("idDesarrollador"),
                            resultQuery.getInt("idActividad"),
                            resultQuery.getInt("idCambio")
                    );
                    listBitacoras.add(newBitacora);
                }
                
                resultOperation = new ResultOperation(
                        false,
                        "Se encontraron bitacoras",
                        listBitacoras.size(),
                        listBitacoras
                );
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(true, "Falló conexión con la base de datos", -1, null);
                System.out.println("Error de \"SQLException\" en archivo \"BitacoraDAO\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(true, "Falló conexión con la base de datos", -1, null);
            showMessageFailureConnection();
        }
        return resultOperation;
    }
}
