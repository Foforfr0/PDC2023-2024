/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DefectoDAO {
    public static ResultOperation getDefectosDesarrollador(int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "SELECT DE.IdDefecto, DE.Nombre, DE.Descripcion, E.Nombre AS 'Estado', " +
                                  "DE.Esfuerzo, DATE_FORMAT(DE.FechaEncontrado, '%d-%m-%Y') AS FechaEncontrado, " +
                                  "DATE_FORMAT(DE.FechaSolucionado, '%d-%m-%Y') AS FechaSolucionado, TA.Nombre AS 'Tipo' \n" +
                                  "FROM Defecto DE \n" +
                                  "JOIN Estado E ON E.IdEstado = DE.IdEstado \n" +
                                  "JOIN TipoActividad TA ON TA.IdTipoActividad = DE.IdTipo \n" + 
                                  "JOIN Proyecto P ON DE.IdProyecto = P.IdProyecto \n" +
                                  "WHERE P.IdProyecto = ? AND DE.IdEstado = 1";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();

                ObservableList<Defecto> listDefectos = FXCollections.observableArrayList();
                while(resultQuery.next()) {
                    Defecto newDefecto = new Defecto();
                        newDefecto.setIdDefecto(resultQuery.getInt("IdDefecto"));
                        newDefecto.setNombre(resultQuery.getString("Nombre"));
                        newDefecto.setDescripcion(resultQuery.getString("Descripcion"));
                        newDefecto.setEstado(resultQuery.getString("Estado"));
                        newDefecto.setEsfuerzo(resultQuery.getInt("Esfuerzo"));
                        newDefecto.setFechaEncontrado(resultQuery.getString("FechaEncontrado"));
                        newDefecto.setFechaSolucionado(resultQuery.getString("FechaSolucionado"));
                        newDefecto.setTipo(resultQuery.getString("Tipo"));
                        listDefectos.add(newDefecto);
                        resultOperation = new ResultOperation(
                                false,
                                "Se encontraron defectos",
                                listDefectos.size(),
                                listDefectos
                        );
                        System.out.println("DefectoDAO//DEFECTOS ENCONTRADOS :" + listDefectos.size());
                }
                if (listDefectos.size() <=  0) {
                    resultOperation = new ResultOperation(
                            false,
                            "No se encontraron defectos",
                            0,
                            null
                    );
                    System.out.println("DefectoDAO//NO SE ENCONTRARON DEFECTOS ");
            }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true, 
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DefectoDAO\" en metodo \"getDefectos\"");
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
