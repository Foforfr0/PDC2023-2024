package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Cambio;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CambioDAO {
    public static ResultOperation getCambios(int idUser) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "SELECT C.Nombre, C.Descripcion FROM Cambio C " + 
                                  "JOIN Usuario U ON C.IdDesarrollador = U.IDUsuario " +
                                  "WHERE U.IDUsuario = ?; ";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Cambio> listCambios = FXCollections.observableArrayList();
                while(resultQuery.next()) {
                    Cambio newCambio = new Cambio();
                    newCambio.setNombre(resultQuery.getString("Nombre"));
                    newCambio.setDescripcion(resultQuery.getString("Descripcion"));
                    listCambios.add(newCambio);
                    resultOperation = new ResultOperation(
                            false,
                            "Se encontraron cambios",
                            listCambios.size(),
                            listCambios
                    );
                    System.out.println("CambioDAO//CAMBIOS ENCONTRADOS: " + listCambios.size() + " DEL DESARROLLADOR ID" + idUser);
                }
                
                if (listCambios.size() <= 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Sin registros",
                            0,
                            null
                    );
                    System.out.println("CambioDAO//NO SE ENCONTRARON CAMBIOS DEL DESARROLLADOR ID " + idUser);
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
    
    private static int getEstadoCambioToInt(String idEstadoCambio) {
        switch(idEstadoCambio) {
            case "Iniciado":
                return 1;
            case "Entregado":
                return 2;
            default:
                return 0;
        }
    }
    
    private static int getTipoCambioToInt (String idTipoCambio) {
        switch (idTipoCambio) {
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
    
    public static ResultOperation registrarCambio(int idUser, Cambio newCambio) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, " +
                                  "IdDesarrollador, Estado, Tipo) " +
                                  "VALUES (?,?,(STR_TO_DATE(?, '%d-%m-%Y')),?,?,?);";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setString(1, newCambio.getNombre());
                prepareQuery.setString(2, newCambio.getDescripcion());
                prepareQuery.setString(3, newCambio.getFechaInicio().replace("/", "-"));
                prepareQuery.setInt(4, idUser);
                prepareQuery.setInt(5, getEstadoCambioToInt(newCambio.getEstado()));
                prepareQuery.setInt(6, getTipoCambioToInt(newCambio.getTipo()));
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if(numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Se ha registrado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                    System.out.println("CambioDAO//SE HA REGISTRADO EL CAMBIO: " + newCambio.getNombre());
                } else {
                    resultOperation = new ResultOperation(
                            true,
                            "No se ha registrado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                    System.out.println("CambioDAO//NO SE REGISTRO EL CAMBIO" + newCambio.getNombre());
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
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
