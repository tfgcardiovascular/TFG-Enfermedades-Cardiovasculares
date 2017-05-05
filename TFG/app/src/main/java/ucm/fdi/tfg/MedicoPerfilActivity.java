package ucm.fdi.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MedicoPerfilActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    private Button Guardar;

    private EditText Colegiado;
    private EditText Nombre;
    private EditText Apellidos;
    private EditText Mail;
    private EditText Telefono;
    private EditText Contraseña;
    private EditText RepeatContraseña;

    private Medico medic;

    private String repeatPassword;

    /*private String name;
    private String surname;
    private String number;
    private String telephone;
    private String password;

    private String mail;*/

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private Medico argumentMedic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_perfil);

        Guardar = (Button) findViewById(R.id.button_guardar);

        Colegiado = (EditText) findViewById(R.id.colegiado);
        Nombre = (EditText) findViewById(R.id.nombre);
        Apellidos = (EditText) findViewById(R.id.apellidos);
        Mail = (EditText) findViewById(R.id.mail);
        Telefono = (EditText) findViewById(R.id.telefono);
        Contraseña = (EditText) findViewById(R.id.pass);
        RepeatContraseña = (EditText) findViewById(R.id.repeatPass);

        medic = DAOCardiovascular.getInstance().getLoggedUser();

        Colegiado.setText( medic.getColegiado( ) );
        Nombre.setText( medic.getNombre() );
        Apellidos.setText( medic.getApellidos() );
        Mail.setText( medic.getMail() );
        Telefono.setText( medic.getTelefono() );




        // Get in data
       // Bundle bundle = getIntent().getExtras();
        //System.out.println( "freeze phoenix" );
       // System.out.println( (Medico ) getIntent().getSerializableExtra( "medic" ) );

      //  argumentMedic =  ( Medico ) getIntent().getSerializableExtra( "medic" );

        /*Colegiado.setText( argumentMedic.getColegiado() );
        Nombre.setText( argumentMedic.getNombre() );
        Apellidos.setText( argumentMedic.getApellidos() );
        Mail.setText( argumentMedic.getMail() );
        Telefono.setText( argumentMedic.getTelefono() );*/

        Colegiado.setEnabled(false);

       // Colegiado.setText( bundle.getString( "colegiado" ) );

        Guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //logIn(v);
                //
                signUp(v);




               /* System.out.println( "gaia phoenix" );
                System.out.println( argumentMedic );

                System.out.println( "sedna phoenix" );*/
                /*new ValidateMedic().execute( argumentMedic.getNombre(), argumentMedic.getApellidos(),
                        argumentMedic.getColegiado(), argumentMedic.getTelefono(),
                        argumentMedic.getPassword(), argumentMedic.getMail(),
                        argumentMedic.getId(), "false");*/
            }
        });


    }

    public void signUp (View view){
        //Button SignUp_Button = (Button) findViewById(R.id.button_signup);


        medic.setNombre( Nombre.getText().toString() );
        medic.setApellidos( Apellidos.getText().toString() );
        medic.setColegiado ( Colegiado.getText().toString() );
        medic.setTelefono(  Telefono.getText().toString() ) ;
        medic.setMail( Mail.getText().toString() );
        medic.setPassword( Contraseña.getText().toString() );
        repeatPassword = RepeatContraseña.getText().toString();


        // Validate
        if (!validate()) {
            onSignUpFailed();
            System.out.println( "phoenix icetear");
            return;
        }

        System.out.println( "Phoenix volcano" );

        // SignUp_Button.setEnabled(false);

        // final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
        //      R.style.AppTheme);
        //PENDIENTE!!!
        // progressDialog.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        //progressDialog.setMessage("Creating account...");
        //progressDialog.setIndeterminate(false);
        //progressDialog.setMax(100);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //progressDialog.setCancelable(true);
        // progressDialog.show();

        // Para que el dialog este un tiempo
        /*new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
*/

        new SaveProfile().execute( medic.getNombre(), medic.getApellidos(), medic.getColegiado(), medic.getTelefono(), medic.getPassword(), medic.getMail() );
    }

    public void onSignUpFailed(){
        Toast.makeText(getBaseContext(), "Registro incorrecto", Toast.LENGTH_LONG).show();
        //SignUp_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
       /* EditText user_text = (EditText) findViewById(R.id.edit_message_User);
        EditText password_text = (EditText) findViewById(R.id.edit_message_Password);

        //String user = user_text.getText().toString();
        String password = password_text.getText().toString();*/

        // Validate mail
        if ( medic.getMail().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher( medic.getMail() ).matches()) {
            Mail.setError("Introduce una dirección e-mail válida");
            valid = false;
        } else {
            Mail.setError(null);
        }

        // Validate name
        if ( medic.getNombre().isEmpty() ){
            Nombre.setError("Introduce un nombre");
            valid = false;
        } else {
            Nombre.setError(null);
        }

        // Validate surname
        if ( medic.getApellidos().isEmpty() ){
            Apellidos.setError("Introduce un apellido");
            valid = false;
        } else {
            Apellidos.setError(null);
        }

        // Validate telephone
        if ( medic.getTelefono().isEmpty() || medic.getTelefono().length() != 9 ){
            Telefono.setError("Introduce un Nº teléfono correcto (9 cifras numéricas)");
            valid = false;
        } else {
            Telefono.setError(null);
        }


        // Validate password
        if ( ( medic.getPassword().length() > 0 && medic.getPassword().length() < 4 ) || medic.getPassword().length() > 16) {
            Contraseña.setError("Introduce entre 4 y 16 carácteres alfanumericos");
            valid = false;

        }else if ( !medic.getPassword().equals( repeatPassword ) )
        {

            Contraseña.setError("Las contraseñas no coinciden");
            valid = false;


        } else {
            Contraseña.setError(null);
        }

        System.out.println( "Phoenix giaeaes" );
        System.out.println( valid );

        return valid;
    }






    private class SaveProfile extends AsyncTask<String,Void,String> {

        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl("saveProfile.php");


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
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("surname", params[1])
                        .appendQueryParameter("number", params[2])
                        .appendQueryParameter("telephone", params[3])
                        .appendQueryParameter("password", params[4])
                        .appendQueryParameter("mail", params[5]);
                String query = builder.build().getEncodedQuery();


                System.out.println("phoenix orange");

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
                    System.out.println( finalResult );

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

            System.out.println("phoenix dungeon");
            System.out.println(result);
            System.out.println( "phoenix computer" );

            if (result.equalsIgnoreCase("Usuario modificado con exito")) {

                // On Sign Up Failed
                Toast.makeText(getBaseContext(), "Usuario modificado con éxito", Toast.LENGTH_LONG).show();

                DAOCardiovascular.getInstance().setLoggedUser( medic );
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase("exception")) {
                onSignUpFailed();
                // If username and password does not match display a error message
                // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            } else if (result.equalsIgnoreCase("No hay modificaciones nuevas") ) {

                Toast.makeText(getBaseContext(), "No hay modificaciones nuevas", Toast.LENGTH_LONG).show();
                // If username and password does not match display a error message
                // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            }
        }


    }





}
