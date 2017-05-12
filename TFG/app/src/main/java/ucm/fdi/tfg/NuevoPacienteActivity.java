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


        //edad.setEnabled( false );

        /*identificacion.addTextChangedListener();*/

        Validar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                registerPatient(v);


                //logIn(v);
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

       // else if ( age.isEmpty() || Integer.parseInt( age ) < 0 ){
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

                System.out.println( "date phoenix" );
                System.out.println( dia );
                System.out.println( mes );
                System.out.println( annio );

                System.out.println( "birth phoenix" );
                System.out.println( day );
                System.out.println( month );
                System.out.println( year );

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

        //String age = edad.getText().toString();


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


        //LogIn_Button.setEnabled(false);

        // final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
        //     R.style.AppTheme);

        // Get info
        /*String user = user_text.getText().toString();
        String password = password_text.getText().toString();

        // Function to do the login
        new MainActivity.AsyncLogin().execute( user,password);
*/
        /*

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

        new SelectInicio().execute(user, password);

        */
    }


    private class RegisterPatient extends AsyncTask<String,Void,String> {

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

            url = DAOCardiovascular.getInstance().getUrl( "registerPatient.php" );


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
                        .appendQueryParameter("identifier", params[0])
                        .appendQueryParameter("edad", params[1])
                        .appendQueryParameter("sexo", params[2])
                        .appendQueryParameter("colegiado", params[3])
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



            }else if (  result.equalsIgnoreCase("Error al registrar paciente")    ){


                Toast.makeText(getBaseContext(), "Error al registrar paciente", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

                Toast.makeText(getBaseContext(), "Registrado con éxito", Toast.LENGTH_LONG).show();

                identificacion.getText().clear();
               // edad.getText().clear();
                Masculino.setChecked( true );
                Femenino.setChecked( false );

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