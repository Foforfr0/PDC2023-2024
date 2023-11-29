/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Proyecto;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProyectoDAO {
    public static ResultOperation getProyectoUsuario(int idUser) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT P.IDProyecto, P.Nombre FROM Proyecto P " +
                                  "RIGHT JOIN Usuario U ON U.IDProyecto = P.IDProyecto " +
                                  "WHERE U.IDUsuario = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();

                if(resultQuery.next()){
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "El usuario si pertenece a un proyecto", 
                        resultQuery.getInt("IDProyecto"), 
                        new Proyecto(
                            resultQuery.getInt("IDProyecto"), 
                            resultQuery.getString("Nombre")
                        )
                    );
                    System.out.println("ProyectoDAO//ID DE PROYECTO DE USUARIO ES: " + resultQuery.getInt("IDProyecto"));
                }else{
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "El usuario no pertenece a un proyecto", 
                        0, 
                        null
                    );
                    System.out.println("ProyectoDAO//NO SE ENCONTRÓ PROYECTO RELACIONADO CON EL USUARIO "+idUser);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"ProyectoDAO\" en método \"getProyectoUsuario\"");
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
