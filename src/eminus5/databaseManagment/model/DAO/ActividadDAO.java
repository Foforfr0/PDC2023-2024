/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ActividadDAO {
    public static ResultOperation getActividadesProyecto(int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT A.IdActividad, A.Nombre, A.Descripcion, E.Nombre AS 'Estado', " +
                                  "TA.Nombre AS 'TipoActividad', DATE_FORMAT(A.FechaInicio, '%d-%m-%Y') AS FechaInicio, " +
                                  "DATE_FORMAT(A.FechaTermino, '%d-%m-%Y') AS FechaTermino, A.IdProyecto, A.IdDesarrollador " +
                                  "FROM Estado E RIGHT JOIN Actividad A ON E.IdEstado = A.IdEstado " +
                                  "LEFT JOIN TipoActividad TA ON TA.IdTipoActividad = A.IdTipo " +
                                  "RIGHT JOIN Proyecto ON A.IdProyecto = Proyecto.IdProyecto " +
                                  "WHERE Proyecto.IdProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Actividad> listActividades= FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Actividad newActividad = new Actividad();
                        newActividad.setIdActividad(resultQuery.getInt("IdActividad"));
                        newActividad.setNombre(resultQuery.getString("Nombre"));
                        newActividad.setDescripcion(resultQuery.getString("Descripcion"));
                        if (resultQuery.getInt("IdDesarrollador") > 0) {
                            newActividad.setIsAsignado("Sí");
                        } else if (resultQuery.getInt("IdDesarrollador") <= 0) {
                            newActividad.setIsAsignado("No");
                        }
                        newActividad.setEstado(resultQuery.getString("Estado"));
                        newActividad.setTipo(resultQuery.getString("TipoActividad"));
                        newActividad.setFechaInicio(resultQuery.getString("FechaInicio"));
                        newActividad.setFechaFin(resultQuery.getString("FechaTermino"));
                        newActividad.setIdProyecto(resultQuery.getInt("IdProyecto"));
                        newActividad.setIdDesarrollador(resultQuery.getInt("IdDesarrollador"));
                    listActividades.add(newActividad);
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontraron actividades", 
                        listActividades.size(),
                        listActividades
                    );
                    System.out.println("ActividadDAO//ACTIVIDADES ENCONTRADAS: "+listActividades.size()+" DEL PROYECTO ID"+idProyecto);
                }
                if (listActividades.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontraron actividades", 
                        0, 
                        null
                    );
                    System.out.println("ActividadDAO//NO SE ENCONTRARON ACTIVIDADES DEL PROYECTO ID"+idProyecto);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"getActividadesProyecto\"");
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
    
    public static ResultOperation getDesarrolladorActividad(int idActividad) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT U.IdUsuario FROM " +
                                  "Actividad A LEFT JOIN Usuario U " +
                                  "ON A.IdDesarrollador = U.IdUsuario " +
                                  "WHERE A.IdActividad = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idActividad);
                ResultSet resultQuery = prepareQuery.executeQuery();
                int idDesarrolllador = 0;
                
                if (resultQuery.next()) {
                    idDesarrolllador = resultQuery.getInt("IdUsuario");
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontró el desarrollador", 
                        idDesarrolllador,
                        idDesarrolllador
                    );
                    System.out.println("ActividadDAO//DESARROLLADOR ENCONTRADO "+idDesarrolllador);
                }
                if (idDesarrolllador <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "La actividad no está asignada a un desarrollador", 
                        0, 
                        null
                    );
                    System.out.println("ActividadDAO//NO SE ENCONTRÓ DESARROLLADOR");
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"getDesarrolladoActividad\"");
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
    
    private static int getTipoActividadToInt (String idTipoAtividad) {
        switch (idTipoAtividad) {
            case "Frontend":
                return 1;
            case "Backend":
                return 2;
            case "Base de datos":
                return 3;
            case "Controlador":
                return 4;
            case "JavaScript":
                return 5;
            default:
                return 0;
        }
    }
    
    public static ResultOperation createActividad(int idProyecto, Actividad newActividad) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) { 
            try {            
                String sqlQuery = "INSERT INTO Actividad (Nombre, Descripcion, IdEstado, IdTipo, " +
                                  "FechaInicio, FechaTermino, IdProyecto, IdDesarrollador) VALUES " +
                                  "(?,?,?,?,(STR_TO_DATE(?, '%d-%m-%Y')),(STR_TO_DATE(?, '%d-%m-%Y')),?,NULL);";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setString(1, newActividad.getNombre());
                    prepareQuery.setString(2, newActividad.getDescripcion());
                    prepareQuery.setInt(3, 1);
                    prepareQuery.setInt(4, getTipoActividadToInt(newActividad.getTipo()));
                    prepareQuery.setString(5, newActividad.getFechaInicio().replace("/}", "-"));
                    prepareQuery.setString(6, newActividad.getFechaFin().replace("/}", "-"));
                    prepareQuery.setInt(7, idProyecto);
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                        false, 
                        "Se ha registrado la actividad", 
                        numberAffectedRows, 
                        newActividad
                    );
                    System.out.println("ActividadDAO//SE HA REGISTRADO LA ACTIVIDAD: "+newActividad.getNombre());
                } else {
                    resultOperation = new ResultOperation(
                        true, 
                        "No se ha registrado la actividad", 
                        numberAffectedRows, 
                        newActividad
                    );
                    System.out.println("ActividadDAO//NO SE REGISTRÓ LA ACTIVIDAD: "+newActividad.getNombre());
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"createActividad\"");
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
    
    public static ResultOperation modifyActividad(Actividad newActividad) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) { 
            try {            
                String sqlQuery = "UPDATE Actividad " +
                                  "SET Nombre = ?, Descripcion = ?, IdTipo = ?, " +
                                  "FechaInicio = (STR_TO_DATE(?, '%d-%m-%Y')), FechaTermino = (STR_TO_DATE(?, '%d-%m-%Y')) " +
                                  "WHERE IdActividad = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setString(1, newActividad.getNombre());
                    prepareQuery.setString(2, newActividad.getDescripcion());
                    prepareQuery.setInt(3, getTipoActividadToInt(newActividad.getTipo()));
                    prepareQuery.setString(4, newActividad.getFechaInicio().replace("/}", "-"));
                    prepareQuery.setString(5, newActividad.getFechaFin().replace("/}", "-"));
                    prepareQuery.setInt(6, newActividad.getIdActividad());
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                        false, 
                        "Se ha modificado la actividad", 
                        numberAffectedRows, 
                        newActividad
                    );
                    System.out.println("ActividadDAO//SE HA MODIFICADO LA ACTIVIDAD: "+newActividad.getNombre());
                } else {
                    resultOperation = new ResultOperation(
                        true, 
                        "No se ha modificado la actividad", 
                        numberAffectedRows, 
                        newActividad
                    );
                    System.out.println("ActividadDAO//SE SE MODIFICÓ LA ACTIVIDAD: "+newActividad.getNombre());
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"modifyActividad\"");
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
    
    public static ResultOperation deleteActividad(int idActividad) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) { 
            try {            
                String sqlQuery = "DELETE FROM Actividad WHERE IdActividad = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idActividad);
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                        false, 
                        "Se ha eliminado la actividad", 
                        1, 
                        null
                    );
                    System.out.println("ActividadDAO//SE HA ELIMINADO LA ACTIVIDAD: "+idActividad);
                } else {
                    resultOperation = new ResultOperation(
                        true, 
                        "No se ha eliminado la actividad", 
                        -1, 
                        null
                    );
                    System.out.println("ActividadDAO//NO SE ELIMINÓ LA ACTIVIDAD: "+idActividad);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.out.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"deleteActividad\"");
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
    
    public static ResultOperation setAsignacionActividad(int idActividad, int idDesarrollador) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                PreparedStatement prepareQuery = null;
                if (idDesarrollador <= 0) {
                    String sqlQuery = "UPDATE Actividad SET IdDesarrollador = NULL, IdEstado = NULL " +
                                      "WHERE Actividad.IdActividad = ?";
                    prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idActividad);
                } else {
                    String sqlQuery = "UPDATE Actividad SET IdDesarrollador = ?, IdEstado = 1 " +
                                      "WHERE Actividad.IdActividad = ?";
                    prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idDesarrollador);
                    prepareQuery.setInt(2, idActividad);
                }
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                        false, 
                        "Se ha asignado la actividad", 
                        1, 
                        null
                    );
                    System.out.println("ActividadDAO//SE HA ASIGNADO LA ACTIVIDAD: "+idActividad);
                } else {
                    resultOperation = new ResultOperation(
                        true, 
                        "No se ha asignado la actividad", 
                        -1, 
                        null
                    );
                    System.out.println("ActividadDAO//NO SE ASIGNÓ LA ACTIVIDAD: "+idActividad);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ActividadDAO\" en método \"setAsignacionActividad\"");
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
