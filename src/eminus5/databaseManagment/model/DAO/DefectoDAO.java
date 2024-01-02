/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DefectoDAO {
    public static ResultOperation getDefectosProyecto(int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT D.IdDefecto, D.Nombre, D.Descripcion, E.Nombre AS Estado, D.Esfuerzo, " +
                                  "DATE_FORMAT(D.FechaEncontrado, '%d-%m-%Y') AS FechaEncontrado, " +
                                  "DATE_FORMAT(D.FechaSolucionado, '%d-%m-%Y') AS FechaSolucionado, " +
                                  "TA.Nombre AS Tipo " +
                                  "FROM Defecto D RIGHT JOIN Proyecto P ON D.IdProyecto = P.IdProyecto " +
                                  "LEFT JOIN Estado E ON D.IdEstado = E.IdEstado " +
                                  "LEFT JOIN TipoActividad TA ON TA.IdTipoActividad = D.IdTipo " +
                                  "WHERE P.IdProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList <Defecto> listDefectos = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Defecto newDefecto = new Defecto();
                        newDefecto.setIdDefecto(resultQuery.getInt("IdDefecto"));
                        newDefecto.setNombre(resultQuery.getString("Nombre"));
                        newDefecto.setDescripcion(resultQuery.getString("Descripcion"));
                        newDefecto.setEstado(resultQuery.getString("Estado"));
                        newDefecto.setEsfurzoMin(resultQuery.getInt("Esfuerzo"));
                        newDefecto.setFechaEncontrado(resultQuery.getString("FechaEncontrado"));
                        newDefecto.setFechaSolucionado(resultQuery.getString("FechaSolucionado"));
                        newDefecto.setTipo(resultQuery.getString("Tipo"));
                    listDefectos.add(newDefecto);
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontraron defectos", 
                        listDefectos.size(),
                        listDefectos
                    );
                    System.out.println("DefectoDAO//DEFECTOS ENCONTRADOS: "+listDefectos.size()+" DEL PROYECTO ID "+idProyecto);
                }
                
                if (listDefectos.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontraron defectos", 
                        0, 
                        null
                    );
                    System.out.println("DefectoDAO//NO SE ENCONTRARON DEFECTOS DEL PROYECTO ID "+idProyecto);
                }

            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DefectoDAO\" en método \"getDefectosProyecto\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(                 //Could not connect to database
                true, 
                "Falló conexión con la base de datos", 
                -1, 
                null
            );
            showMessageFailureConnection();
        }  
        
        return resultOperation;
    }
}
