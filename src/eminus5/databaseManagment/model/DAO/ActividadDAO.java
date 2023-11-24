/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.POJO.Proyecto;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ActividadDAO {
    public static ResultOperation getActividadesProyecto(int idResponsable) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        ResultOperation resultGetProyecto = ProyectoDAO.getProyectoUsuario(idResponsable);
        
        if (resultGetProyecto.getIsError() == true && resultGetProyecto.getData() == null) {
            resultOperation = new ResultOperation(
                resultGetProyecto.getIsError(),
                resultGetProyecto.getMessage(),
                0,
                null
            );
        } else if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT A.IDActividad, A.Nombre, A.Descripcion, A.Asignado, E.Nombre AS 'Estado', " +
                                  "TA.Nombre AS 'TipoActividad', DATE_FORMAT(A.FechaInicio, '%d-%m-%Y') AS FechaInicio, " +
                                  "DATE_FORMAT(A.FechaFin, '%d-%m-%Y') AS FechaFin " +
                                  "FROM Estado E RIGHT JOIN Actividad A ON E.IDEstado = A.Estado " +
                                  "LEFT JOIN TipoActividad TA ON TA.IDTipoActividad = A.Tipo " +
                                  "RIGHT JOIN Proyecto ON A.IDProyecto = Proyecto.IDProyecto " +
                                  "WHERE Proyecto.IDProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                Proyecto currentProyecto = (Proyecto) resultGetProyecto.getData();
                    prepareQuery.setInt(1, currentProyecto.getIdProyecto());
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Actividad> listActividades= FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Actividad newActividad = new Actividad();
                        newActividad.setIdActividad(resultQuery.getInt("IDActividad"));
                        newActividad.setNombre(resultQuery.getString("Nombre"));
                        newActividad.setDescripcion(resultQuery.getString("Descripcion"));
                        if (resultQuery.getInt("Asignado") == 1) {
                            newActividad.setIsAsignado("Sí");
                        } else if (resultQuery.getInt("Asignado") == 2) {
                            newActividad.setIsAsignado("No");
                        }
                        newActividad.setEstado(resultQuery.getString("Estado"));
                        newActividad.setTipo(resultQuery.getString("TipoActividad"));
                        newActividad.setFechaInicio(resultQuery.getString("FechaInicio"));
                        newActividad.setFechaFin(resultQuery.getString("FechaFin"));
                    listActividades.add(newActividad);
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontraron actividades", 
                        listActividades.size(),
                        listActividades
                    );
                    System.out.println("ActividadDAO//ACTIVIDADES ENCONTRADAS "+listActividades.size()+" DEL PROYECTO "+currentProyecto.getIdProyecto());
                }
                if (listActividades.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontraron actividades", 
                        0, 
                        null
                    );
                    System.out.println("NO SE ENCONTRARON ACTIVIDADES");
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.out.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"getActividadesProyecto\"");
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
    
    public static ResultOperation createActividad(int idProyecto) throws SQLException{
        return new ResultOperation();
    }
    
    public static ResultOperation modifyActividad(int idActividad) throws SQLException{
        return new ResultOperation();
    }
    
    public static ResultOperation deleteActividad(int idActividad) throws SQLException{
        return new ResultOperation();
    }
}
