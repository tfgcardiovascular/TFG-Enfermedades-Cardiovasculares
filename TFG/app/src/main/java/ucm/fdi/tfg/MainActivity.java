package ucm.fdi.tfg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    private Button LogIn_Button;
    private Button SignUp_Button;
    private EditText user_text;
    private EditText password_text;
    private CheckBox opcionMostrar;

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private String url_usuario = "http://localhost:8888/android_connect/Usuario.php";

    @Override
    public void onBackPressed() {
        /*Intent intent;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);*/
        moveTaskToBack(true);
        /*android.os.Process.killProcess(android.os.Process.myPid());

        System.exit(1);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogIn_Button = (Button) findViewById(R.id.button_login);
        SignUp_Button = (Button) findViewById(R.id.button_signup);
        user_text = (EditText) findViewById(R.id.edit_message_User);
        password_text = (EditText) findViewById(R.id.edit_message_Password);
        opcionMostrar = (CheckBox)findViewById(R.id.edit_check_contraseña);

        LogIn_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logIn(v);
            }
        });

        SignUp_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

      public void logIn (View view){

        if (!validate()) {
            onLoginFailed();
            return;
        }

        // Get info
        String user = user_text.getText().toString();
        String password = password_text.getText().toString();

        // Function to do the login
        new AsyncLogin().execute( user,password);
    }

    private class AsyncLogin extends AsyncTask<String, String, Medico>{
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tCargando...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected Medico doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl( "login.php" );

            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

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
                return null;
                //return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    //System.out.println( reader );

                    StringBuilder result = new StringBuilder();
                    String line;

                    // New Medico Object
                    Medico medic = new Medico();

                    ArrayList< String > linePhp = new ArrayList < String > ();

                    while ((line = reader.readLine()) != null) {
                        //System.out.println( line  );

                        linePhp.add( line );
                        //result.append(line);
                    }

                    if ( linePhp.size() <= 1 )
                    {

                        medic = null;

                    }else {

                        // Update medic
                        medic.setNombre(linePhp.get(0));
                        medic.setApellidos(linePhp.get(1));
                        medic.setTelefono(linePhp.get(2));
                        medic.setPassword(linePhp.get(3));
                        medic.setColegiado(linePhp.get(4));
                        medic.setMail(linePhp.get(5));
                        medic.setRol(linePhp.get(6));
                    }

                    // Pass data to onPostExecute method
                    return medic;
                    //return(result.toString());

                }else{
                    return null;
                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
               // return "exception";
            } finally {
                conn.disconnect();
            }
        }

       // @Override
        protected void onPostExecute(Medico result) {
            // Check obtained result
            //System.out.println( result );

            pdLoading.dismiss();

            if ( result != null ) {

                // Clear the fields
                user_text.getText().clear();
                password_text.getText().clear();

                // Restaurar cursor
                user_text.requestFocus();

                // Change frame
                Class<? extends Activity> activityClass;
                activityClass = InicioActivity.class;

                // Medico -> Menu Inicio
                String i = result.getRol();
                //System.out.println(i);

                // Set logged user
                DAOCardiovascular.getInstance().setLoggedUser( result );

                if (i.equals( "1") ) {
                    activityClass = InicioActivity.class;

                    // Admin -> Menú admin
                } else if (i.equals( "0")){
                    activityClass = adminActivity.class;

                }

                // Start new frame
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);

            }else{

                onLoginFailed();

            }
        }
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Login incorrecto", Toast.LENGTH_LONG).show();
        LogIn_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String user = user_text.getText().toString();
        String password = password_text.getText().toString();

        // Validate user
        if ( user.isEmpty() || user.length() != 9 ){
            user_text.setError("Introduce un Nº Colegiado correcto (9 cifras numéricas)");
            valid = false;
        } else {
            user_text.setError(null);
        }

        // Validate Password
        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            password_text.setError("Introduce entre 4 y 16 carácteres alfanumericos");
            valid = false;
        } else {
            password_text.setError(null);
        }

        return valid;
    }

    public void mostrarContraseña(View v){
        // Salvar cursor
        int cursor = password_text.getSelectionEnd();

        if(opcionMostrar.isChecked()){
            password_text.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            password_text.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // Restaurar cursor
        password_text.setSelection(cursor);
    }
}