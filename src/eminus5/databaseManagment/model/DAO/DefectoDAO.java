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
    public static ResultOperation getDefectosDesarrollador(int idDefecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "SELECT * FROM Defecto " +
                        "JOIN Proyecto ON D.IdProyecto = Proyecto.IdProyecto " +
                        "WHERE Proyecto.IdProyecto = ?";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idDefecto);
                ResultSet resultQuery = prepareQuery.executeQuery();

                ObservableList<Defecto> listDefectos = FXCollections.observableArrayList();
                while(resultQuery.next()) {
                    Defecto newDefecto = new Defecto();
                        newDefecto.setNombre(resultQuery.getString("Nombre"));
                        newDefecto.setDescripcion(resultQuery.getString("Descripcion"));
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
