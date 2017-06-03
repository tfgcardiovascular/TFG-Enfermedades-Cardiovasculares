package ucm.fdi.tfg;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class NuevoPacienteActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "NuevoPacienteActivity";

    private Button Validar;

    private TextView identif;
    private TextView eda;
    private TextView sexo;

    private EditText identificacion;
    //private EditText edad;

    private RadioButton Masculino;
    private RadioButton Femenino;

    int ageValue;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public static boolean isNumber( String string )
    {

        if ( string == null || string.isEmpty() )
        {
            return false;
        }

        int i = 0;
        if ( string.charAt( 0 ) == '-')
        {
            if ( string.length() > 1 )
            {
                i++;
            }else{
                return false;
            }
        }


        for ( ; i < string.length(); i++ )
        {
            if ( !Character.isDigit( string.charAt( i ) ) )
            {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_paciente);

        Validar = (Button) findViewById(R.id.button_add);

        identif = (TextView) findViewById(R.id.identificacion);
        //eda = (TextView) findViewById(R.id.weight);
        sexo = (TextView) findViewById(R.id.height);

        identificacion = (EditText) findViewById(R.id.id);
       // edad = (EditText) findViewById(R.id.diastolicaData);

        Masculino = (RadioButton) findViewById(R.id.RB_genero_hombre);
        Femenino = (RadioButton) findViewById(R.id.RB_genero_mujer);

        Masculino.setChecked( true );

        Validar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerPatient(v);
            }
        });
    }

    private boolean dayMonthValid( int day, int month )
    {

        boolean valid = true;
        int limit = 0;

        if ( month < 1 || month > 12 )
        {
            valid = false;
        }else {

            if ( month == 2 )
            {
                limit = 28;

            }else if ( month == 11 || month == 4 || month == 6 || month == 9 )
            {
                limit = 30;
            }else{

                limit = 31;
            }

            if ( day < 1 || day > limit )
            {
                valid = false;
            }


        }

        return valid;
    }

    private boolean validatePatient( String id, int edad, String sex )
    {

        boolean valid = true;

        if ( sex.equals( "N" ) )
        {
            Femenino.setError( "El campo sexo es obligatorio" );
            valid = false;
        }

        else if ( edad < 0 ){

            identificacion.setError( "La fecha introducida es posterior a la actual" );
            valid = false;
        }

        else if ( id.isEmpty() || id.length() != 9 || isNumber( id.substring( 0, 3 ) ) == true || isNumber( id.substring( 3, 9 ) ) == false )
        {
            identificacion.setError( "id debe ser 3 letras iniciales y ddmmaa" );
            valid = false;

        // Check birthday
        }else
        {

            // Get day
            int day = Integer.parseInt( id.substring( 3, 5 ) );

            // Get Month
            int month = Integer.parseInt( id.substring( 5, 7 ) );

            // Get year
            int year = Integer.parseInt( id.substring( 7, 9 ) );

            // Check day and month
            if ( !dayMonthValid( day, month ) )
            {
                identificacion.setError( "el día y el mes no son válidos" );
                valid = false;
            }else
            {
                Calendar c1 = Calendar.getInstance();

                // Get info current date Happy Birthday!!!
                int dia = c1.get( Calendar.DATE );
                int mes = c1.get( Calendar.MONTH ) + 1;
                int annio = c1.get( Calendar.YEAR );

                //System.out.println( dia );
                //System.out.println( mes );
                //System.out.println( annio );

                //System.out.println( day );
                //System.out.println( month );
                //System.out.println( year );

                // Transform annio
                annio = annio % 100;

                // Get year
                if ( annio < year )
                {
                    annio = annio + 100;
                }

                ageValue = annio - year;

                if ( mes < month ){

                    ageValue = ageValue -1;
                }
                else if ( dia < day && mes == month )
                {
                    ageValue = ageValue -1;
                }
            }

        }

        return valid;
    }

    private void registerPatient (View view){

        // Get data
        String identifier = identificacion.getText().toString();
        ageValue = 0;

        String sex = "N";

        if ( Masculino.isChecked() )
        {
            sex = "M";

        }else
        {

            sex = "F";

        }

        if (!validatePatient( identifier, ageValue, sex )) {
            //onLoginFailed();
            return;
        }

        String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();

        // AsyncTask
        new NuevoPacienteActivity.RegisterPatient().execute( identifier, Integer.toString( ageValue ), sex, colegiado );

    }

    private class RegisterPatient extends AsyncTask<String,Void,String> {

        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl( "registerPatient.php" );

            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("identifier", params[0])
                        .appendQueryParameter("edad", params[1])
                        .appendQueryParameter("sexo", params[2])
                        .appendQueryParameter("colegiado", params[3])
                        ;
                String query = builder.build().getEncodedQuery();

                for ( int i = 0; i < params.length; i++ )
                {
                    System.out.println( params[i]);
                }

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                //return "exception";
            }

            // Final result
            String finalResult = "";

            // Tercer try
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    finalResult = result.toString();

                } else {

                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
            } finally {
                conn.disconnect();
            }

            return finalResult;
        }

        //@Override
        protected void onPostExecute(String result) {
            //System.out.println( result );

            if (result.equalsIgnoreCase("error")) {

                // On Sign Up Failed
                Toast.makeText(getBaseContext(), "Error en el proceso", Toast.LENGTH_LONG).show();
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase( "exception" )) {

            }else if (  result.equalsIgnoreCase("Paciente ya asignado a un medico")    ){

                Toast.makeText(getBaseContext(), "Paciente ya asignado a un médico", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("Error al registrar paciente")    ){

                Toast.makeText(getBaseContext(), "Error al registrar paciente", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

                Toast.makeText(getBaseContext(), "Registrado con éxito", Toast.LENGTH_LONG).show();

                identificacion.getText().clear();
               // edad.getText().clear();
                Masculino.setChecked( true );
                Femenino.setChecked( false );
            }
        }
    }
}
