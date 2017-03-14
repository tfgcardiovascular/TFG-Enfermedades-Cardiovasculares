package ucm.fdi.tfg;

import android.content.Intent;
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

/**
 * Created by PC on 02/03/2017.
 */

public class PacienteValidarActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    // Text
    private TextView identif;
    private TextView eda;
    private TextView sexo;

    private EditText identificacion;
    private EditText edad;

    private RadioButton Masculino;
    private RadioButton Femenino;

    // Buttons
    private Button saveButton;
    private Button removeButton;
    private Button diabetesButton;
    private Button colesterolButton;
    private Button cardiovascularButton;

    // String
    private String identifier;
    private String sex;
    private String age;



    private Button Rechazar;
    private TextView Colegiado;
    private TextView Nombre;
    private TextView Apellidos;
    private TextView Mail;
    private TextView Telefono;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private Paciente argumentPaciente;

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

    private boolean validatePatient( String id, String age, String sex )
    {

        boolean valid = true;


        if ( age.isEmpty() || Integer.parseInt( age ) < 0 ){

            edad.setError( "Valor entre 0 y 99" );
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
            }

        }

        return valid;
    }

    private boolean checkData()
    {

        // Get data
        identifier = identificacion.getText().toString();
        age = edad.getText().toString();

        sex = "N";

        if ( Masculino.isChecked() )
        {

            sex = "M";

        }else
        {

            sex = "F";

        }

        if (!validatePatient( identifier, age, sex )) {
            //onLoginFailed();
            return false;
        }else
        {

            return true;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_info);


        // Text
        identif = (TextView) findViewById(R.id.identificacion);
        eda = (TextView) findViewById(R.id.weight);
        sexo = (TextView) findViewById(R.id.height);

        identificacion = (EditText) findViewById(R.id.id);
        edad = (EditText) findViewById(R.id.añosData);

        Masculino = (RadioButton) findViewById(R.id.RB_genero_hombre);
        Femenino = (RadioButton) findViewById(R.id.RB_genero_mujer);

        // Buttons
        saveButton = (Button) findViewById( R.id.button_guardar );
        removeButton = ( Button ) findViewById( R.id.button_eliminar );

        diabetesButton = ( Button ) findViewById( R.id.button_diabetes );
        colesterolButton = ( Button ) findViewById( R.id.button_colesterol );
        cardiovascularButton = ( Button ) findViewById ( R.id.button_calcular );

        // Get in data
        Bundle bundle = getIntent().getExtras();
        System.out.println( "freeze phoenix" );
        System.out.println( ( Paciente ) getIntent().getSerializableExtra( "paciente" ) );

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        System.out.println( "phoenix of hunger");
        System.out.println( identificacion );
        System.out.println( argumentPaciente.getId() );
        System.out.println( edad );
        System.out.println( argumentPaciente.getEdad() );


        // Set info data
        identificacion.setText( argumentPaciente.getId() );
        edad.setText( argumentPaciente.getEdad() );

        String sexValue =  argumentPaciente.getSexo();

        if ( sexValue.equals ( "M" ) )
        {
            Masculino.setChecked( true );

        }else
        {
            Femenino.setChecked( true );
        }




       // Colegiado.setText( bundle.getString( "colegiado" ) );

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( checkData() )
                {
                    new updatePatient().execute( argumentPaciente.getId(), identifier, sex,
                            age, DAOCardiovascular.getInstance().getLoggedUser().getColegiado(), "false");
                }

                //logIn(v);


               /* System.out.println( "gaia phoenix" );
                System.out.println( argumentPaciente );

                System.out.println( "sedna phoenix" );*/

            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ( checkData() )
                {
                    new updatePatient().execute( argumentPaciente.getId(), identifier, sex,
                            age, DAOCardiovascular.getInstance().getLoggedUser().getColegiado(), "true");
                }

            }
        });


        cardiovascularButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the App activity
                Intent intent = new Intent(getApplicationContext(), CardiovascularActivity.class);
                intent.putExtra( "paciente", argumentPaciente);
                startActivity(intent);
            }
        });

    }


























    private class updatePatient extends AsyncTask<String,Void,String> {

       // ProgressDialog pdLoading = new ProgressDialog(SignupActivity.this);
        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
           /* pdLoading.setMessage("\tProcessing Sign Up...");
            pdLoading.setCancelable(false);
            pdLoading.show();*/

        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl( "updatePatient.php" );


            try {

                System.out.println("phoenix white");
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                System.out.println("phoenix grey");

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("oldIdentifier", params[0])
                        .appendQueryParameter("identifier", params[1])
                        .appendQueryParameter("sexo", params[2])
                        .appendQueryParameter("edad", params[3])
                        .appendQueryParameter("colegiado", params[4])
                        .appendQueryParameter("refuse", params[5])
                        ;
                String query = builder.build().getEncodedQuery();


                System.out.println("phoenix orange");
                for ( int i = 0; i < params.length; i++ )
                {
                    System.out.println( params[i]);
                }
                System.out.println("phoenix apple");


                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                System.out.println("phoenix yellow");
                conn.connect();

                System.out.println("phoenix stone");

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

                System.out.println("phoenix diablo");

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    System.out.println("phoenix gaia");

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    System.out.println("phoenix altmile");

                    finalResult = result.toString();

                    // Pass data to onPostExecute method
                    //return(result.toString());

                } else {

                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
                //return "exception";
                //return "exception";
            } finally {

                System.out.println("phoenix tear");
                conn.disconnect();
            }

            return finalResult;

            // return null;//DAOCuentaCuentas.getInstance().login(strings[0],strings[1]);
        }

        //@Override
        //protected void onPostExecute(Medico usu1) {
        protected void onPostExecute(String result) {

            // Remove loading
            //pdLoading.dismiss();

            System.out.println( "phoenix dungeon" );
            System.out.println( result );

            if (result.equalsIgnoreCase("error")) {

                // Clear the fields
               /* name_text.getText().clear();
                surname_text.getText().clear();
                number_text.getText().clear();
                telephone_text.getText().clear();
                mail_text.getText().clear();
                password_text.getText().clear();*/

                // On Sign Up Failed
                Toast.makeText(getBaseContext(), "Error en el proceso", Toast.LENGTH_LONG).show();
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase( "exception" )) {


            }else if (  result.equalsIgnoreCase("Paciente ya asignado a un medico")    ){


                Toast.makeText(getBaseContext(), "Paciente ya asignado a un médico", Toast.LENGTH_LONG).show();



            }else if (  result.equalsIgnoreCase("Error actualizando")    ){


                Toast.makeText(getBaseContext(), "Error actualizando", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Actualizado con exito")) {

                Toast.makeText(getBaseContext(), "Actualizado con exito", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Eliminado con exito")) {

                Toast.makeText(getBaseContext(), "Eliminado con exito", Toast.LENGTH_LONG).show();


                Intent intent = new Intent( getApplicationContext(), MisPacientesActivity.class);
                // intent.putExtra("colegiado", currentLead.getColegiado());
                startActivity(intent);
                //identificacion.getText().clear();
               // edad.getText().clear();
                //Masculino.setChecked( true );
                //Femenino.setChecked( false );

                //recreate();

                // Prepare next window
                //Intent intent = new Intent(MedicoValidarActivity.this, ValidateActivity.class);
                //startActivity(intent);


                //intent.setFlags( FLAG_ACTIVITY_CLEAR_TASK );
                // finish();
                /*EditText editText = (EditText) findViewById(R.id.edit_message_Name);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);*/
                //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK );
                //intent.setAction("com.package.ACTION_LOGOUT");



                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }
        }


        // if (usu1==null) {
                /*if(primero){
                    //inicializarVentana();
                }else {*/
      /*          Toast toast =
                        Toast.makeText(getApplicationContext(),
                                "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
                toast.show();
                //}
            } else {
                Medico medico;
                medico = Medico.getIntsance();
                medico.setId(usu1.getId());
                medico.setColegiado(usu1.getColegiado());
                medico.setNombre(usu1.getNombre());
                medico.setApellidos(usu1.getApellidos());
                medico.setTelefono(usu1.getTelefono());
                medico.setPassword(usu1.getPassword());

                //usu.setImagen(usu1.getImagen());

                //IniciarAplicacion(usu);
            }
*/
    }
}
