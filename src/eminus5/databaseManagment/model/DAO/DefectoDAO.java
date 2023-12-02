/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DefectoDAO {
    public static ResultOperation getDefectos(int idUser) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            String sqlQuery = "SELECT ";
            PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
            prepareQuery.setInt(1, idUser);
            ResultSet resultQuery = prepareQuery.executeQuery();
        }
        return resultOperation;
    }
}
