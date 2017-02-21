package ucm.fdi.tfg;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCardiovascular {

    private static final DAOCardiovascular dao = new DAOCardiovascular();
    private String IP = "192.168.1.199:3306"; //10.0.2.2
    private String baseDatos = "/cardiovascular";
    private String usbd = "root";
    private String contbd = "";

    public DAOCardiovascular() {


    }

    public final static DAOCardiovascular getInstance() {
        return dao;
    }

    public Medico login(String usun, String cont){
        Medico usu = null;
        try {

            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);

            //conn.setConnectTimeout(50);

            String peticion = "select * from medico WHERE nombre= ? AND password = ?";
            PreparedStatement consulta = conn.prepareStatement(peticion);
            System.out.println("Conexion establecida");
            consulta.setString(1,usun);
            consulta.setString(2,cont);
            ResultSet result = consulta.executeQuery();

            if (result != null) {
                if (!result.next()) {
                    return null;

                } else {
                    usu= new Medico();
                    usu.setId(result.getInt("id"));
                    usu.setColegiado(result.getString("colegiado"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    usu.setPassword(result.getString("password"));

                    /*Blob bl=result.getBlob("imagen");
                    if(bl!=null){
                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }*/

                    return usu;
                }

            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}