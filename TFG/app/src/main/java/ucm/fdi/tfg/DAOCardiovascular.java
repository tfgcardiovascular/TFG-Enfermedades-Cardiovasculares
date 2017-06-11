package ucm.fdi.tfg;

import java.net.MalformedURLException;
import java.net.URL;

public class DAOCardiovascular {

    private static DAOCardiovascular dao = new DAOCardiovascular();
    private String IP = "192.168.1.199:3306"; //10.0.2.2
    private String baseDatos = "/cardiovascular";
    private String usbd = "root";
    private String contbd = "";

    private Medico loggedUser;
    private Paciente currentPatient;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private DAOCardiovascular() {

    }

    public static DAOCardiovascular getInstance() {

        return dao;
    }

    public void setLoggedUser(Medico medic) {

        loggedUser = medic;
    }

    public Medico getLoggedUser() {

        return loggedUser;
    }

    public boolean isAdmin()
    {
        return loggedUser.getRol().equals( "0" );
    }

    public void setCurrentPatient( Paciente patient )
    {
        currentPatient = patient;
    }

    public Paciente getCurrentPatient()
    {
        return currentPatient;
    }

    public boolean isNumber(String string) {

        if (string == null || string.isEmpty()) {
            return false;
        }

        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }

        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public URL getUrl(String phpFile) {

        URL url = null;
        //String dir = "http://147.96.114.219:8888/php/" + phpFile;

        String dir = "http://educacion.dacya.ucm.es/cardiovascular/php/" + phpFile;


        try {

            url = new URL(dir);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        return url;
    }

    public int getReadTimeout(){

        return READ_TIMEOUT;
    }

    public int getConnectTimeout() {

        return CONNECTION_TIMEOUT;
    }


    public String prueba() {
        return "PHOENIX";
    }

    public String infoTratamientoCardiovascular()
    {
        Paciente argumentPaciente = getCurrentPatient();

        String cardiovascularString = "";

        String sexo = "Masculino";
        String diabetes = "No";
        String tabaquismo = "No";

        //Genero
        if (argumentPaciente.getSexo().equals("M")) {
            sexo = "Masculino";

        } else {
            sexo = "Femenino";
        }

        // Sistolica
        // Diastolica
        String sistolica = "0";

        if ( argumentPaciente.getSistolica().length() > 0 )
        {
            sistolica = argumentPaciente.getSistolica();
        }

        String diastolica = "0";

        if ( argumentPaciente.getDiastolica().length() > 0 )
        {
            diastolica = argumentPaciente.getDiastolica();
        }


        String colesterol = "0";

        if ( argumentPaciente.getColesterolTotal().length() > 0 )
        {
            colesterol = argumentPaciente.getColesterolTotal();
        }

        // Get important factors
        // Diabetes
        try
        {
            if ( Double.parseDouble(argumentPaciente.getYearEnfermo() ) > 0 )
            {
                diabetes = "Si";
            }
        }catch( NumberFormatException e )
        {
        }

        // Tabaquismo
        try
        {
            if (Double.parseDouble(argumentPaciente.getIpa()) > 0)
            {
                tabaquismo = "Si";
            }
        }catch( NumberFormatException e )
        {
        }

        // Cardiovascular
        try
        {

            double cardiovascular = Double.parseDouble(argumentPaciente.getCardiovascular());

            if (cardiovascular > 39) {
                cardiovascularString = "muy alto";

            } else if (cardiovascular >= 20 && cardiovascular <= 39) {

                cardiovascularString = "alto";
            } else if (cardiovascular >= 10 && cardiovascular <= 19) {
                cardiovascularString = "moderado";

            } else if (cardiovascular >= 5 && cardiovascular <= 9) {

                cardiovascularString = "ligero";

            } else if (cardiovascular < 5) {
                cardiovascularString = "bajo";

            }

            cardiovascularString = "El paciente " + argumentPaciente.getId() + " de edad " + argumentPaciente.getEdad() +
                    " tiene riesgo coronario " + cardiovascularString + " (" + argumentPaciente.getCardiovascular()
                    + "%).\n" +
                    "Debido a los factores: tabaquismo (" + tabaquismo + "), " +
                    "hipertensión arterial (Sistólica " + sistolica + " y Diastólica " + diastolica + "), " +
                    "colesterol (Total " + colesterol + "), " +
                    "género (" + sexo + ") y " +
                    "diabetes (" + diabetes + ").\n" +
                    "*Puede consultar las tablas de REGICOR en Tratamiento dentro de Algortimos.\n";



        }catch( NumberFormatException e )
        {

            cardiovascularString = "El paciente " + argumentPaciente.getId() + " de edad " + argumentPaciente.getEdad() + "\n" +
                    "Factores: tabaquismo (" + tabaquismo + "), " +
                    "hipertensión arterial (Sistólica " + sistolica + " y Diastólica " + diastolica + "), " +
                    "colesterol (Total " + colesterol + "), " +
                    "género (" + sexo + ") y " +
                    "diabetes (" + diabetes + ").\n";
        }

        return cardiovascularString;

    }
}

