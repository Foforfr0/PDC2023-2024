/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.ResultOperation;
import eminus5.databaseManagment.model.POJO.Actividad;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ActividadDAO {
    public static ResultOperation getActividadesProyecto(int idResponsable) throws SQLException{
        ObservableList<Actividad> actividades = FXCollections.observableArrayList();
        ResultOperation resultOperation = new ResultOperation(false, "", 0, actividades);
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
