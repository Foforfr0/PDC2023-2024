/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.ResultOperation;
import eminus5.databaseManagment.model.POJO.Bitacora;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BitacoraDAO {
    public static ResultOperation getBitacoras(int idUser) throws SQLException{
        ObservableList<Bitacora> bitacoras = FXCollections.observableArrayList();
        ResultOperation resultOperation = new ResultOperation(false, "", 0, bitacoras);
        return resultOperation;
    }
}
