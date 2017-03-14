package ucm.fdi.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

/**
 * Created by PC on 02/03/2017.
 */

public class MedicoValidarActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    private Button Validar;
    private Button Rechazar;

    private EditText Colegiado;
    private EditText Nombre;
    private EditText Apellidos;
    private EditText Mail;
    private EditText Telefono;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private Medico argumentMedic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_validar_info);

        Validar = (Button) findViewById(R.id.button_validar);
        Rechazar = (Button) findViewById(R.id.button_rechazar);

        Colegiado = (EditText) findViewById(R.id.colegiado);
        Nombre = (EditText) findViewById(R.id.nombre);
        Apellidos = (EditText) findViewById(R.id.apellidos);
        Mail = (EditText) findViewById(R.id.mail);
        Telefono = (EditText) findViewById(R.id.telefono);

        // Get in data
        Bundle bundle = getIntent().getExtras();
        System.out.println( "freeze phoenix" );
        System.out.println( (Medico ) getIntent().getSerializableExtra( "medic" ) );

        argumentMedic =  ( Medico ) getIntent().getSerializableExtra( "medic" );

        Colegiado.setText( argumentMedic.getColegiado() );
        Nombre.setText( argumentMedic.getNombre() );
        Apellidos.setText( argumentMedic.getApellidos() );
        Mail.setText( argumentMedic.getMail() );
        Telefono.setText( argumentMedic.getTelefono() );

        Colegiado.setEnabled(false);
        Nombre.setEnabled(false);
        Apellidos.setEnabled(false);
        Mail.setEnabled(false);
        Telefono.setEnabled(false);

       // Colegiado.setText( bundle.getString( "colegiado" ) );

        Validar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //logIn(v);


               /* System.out.println( "gaia phoenix" );
                System.out.println( argumentMedic );

                System.out.println( "sedna phoenix" );*/
                new ValidateMedic().execute( argumentMedic.getNombre(), argumentMedic.getApellidos(),
                        argumentMedic.getColegiado(), argumentMedic.getTelefono(),
                        argumentMedic.getPassword(), argumentMedic.getMail(),
                        argumentMedic.getId(), "false");
            }
        });

        Rechazar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //logIn(v);
                new ValidateMedic().execute( argumentMedic.getNombre(), argumentMedic.getApellidos(),
                        argumentMedic.getColegiado(), argumentMedic.getTelefono(),
                        argumentMedic.getPassword(), argumentMedic.getMail(),
                        argumentMedic.getId(), "true");

            }
        });

    }


























    private class ValidateMedic extends AsyncTask<String,Void,String> {

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

            url = DAOCardiovascular.getInstance().getUrl( "confirmMedicValidate.php" );

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
                        .appendQueryParameter("mail", params[5])
                        .appendQueryParameter("id", params[6])
                        .appendQueryParameter("refuse", params[7])
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
                Toast.makeText(getBaseContext(), "Error validando médico", Toast.LENGTH_LONG).show();
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase( "exception" )) {



            }else if (  result.equalsIgnoreCase("No se pudo borrar de la lista a validar")    ){


                Toast.makeText(getBaseContext(), "No se pudo borrar de la lista a validar", Toast.LENGTH_LONG).show();


            }else if (  result.equalsIgnoreCase("El colegiado ya existe")    ){


                Toast.makeText(getBaseContext(), "El colegiado ya existe", Toast.LENGTH_LONG).show();


            }else if (  result.equalsIgnoreCase("Error al asignar rol")    ) {


                Toast.makeText(getBaseContext(), "Error al asignar rol", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("No se pudo insertar en médicos")    ){


                    Toast.makeText(getBaseContext(), "No se pudo insertar en médicos", Toast.LENGTH_LONG).show();


            } else if (result.equalsIgnoreCase("success")) {

                Toast.makeText(getBaseContext(), "Validado/Rechazado correctamente", Toast.LENGTH_LONG).show();

                // Prepare next window
                Intent intent = new Intent(MedicoValidarActivity.this, ValidateActivity.class);
                //intent.setFlags( FLAG_ACTIVITY_CLEAR_TASK );
               // finish();
                /*EditText editText = (EditText) findViewById(R.id.edit_message_Name);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);*/
                //intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK );
                //intent.setAction("com.package.ACTION_LOGOUT");
                startActivity(intent);


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
