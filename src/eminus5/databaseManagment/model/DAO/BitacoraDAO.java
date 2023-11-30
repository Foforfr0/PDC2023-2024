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
                String sqlQuery = "SELECT * FROM Bitacora " +
                                  "JOIN Usuario ON Bitacora.IDDesarrollador = Usuario.IDUsuario " +
                                  "WHERE Usuario.IDUsuario = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Bitacora> listBitacoras = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Bitacora newBitacora = new Bitacora();
                    newBitacora.setIdBitacora(resultQuery.getInt("IDBitacora"));
                    newBitacora.setNombreCambio(resultQuery.getString("[Nombre del cambio]"));
                    newBitacora.setDescripcion(resultQuery.getString("Descripcion"));
                    newBitacora.setIdEstado(resultQuery.getInt("IDEstado"));
                    listBitacoras.add(newBitacora);
                resultOperation = new ResultOperation(
                        false,
                        "Se encontraron bitacoras",
                        listBitacoras.size(),
                        listBitacoras
                );
                System.out.println("BitacoraDAO//BITACORAS ENCONTRADAS: " + listBitacoras.size() + " DEL DESARROLLADOR ID" + idUser);
                }
                if(listBitacoras.size() <= 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "No se encontraron bitacoras",
                            0,
                            null
                    );
                    System.out.println("BitacoraDAO//NO SE ENCONTRARON BITACORAS DEL DESARROLLADOR ID" + idUser);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.out.println("Error de \"SQLException\" en archivo\"BitacoraDAO\" en metodo \"getBitacoras\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(
                    true,
                    "Fallo la conexion con la base de datos",
                    -1,
                    null
            );
            showMessageFailureConnection();
        }
        return resultOperation;
    }
}
