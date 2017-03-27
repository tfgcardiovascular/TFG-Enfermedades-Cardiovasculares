package ucm.fdi.tfg;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import java.net.URL;
import java.util.ArrayList;

public class tabacoActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "tabacoActivity";

    private Button tabacoButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText años;
    private EditText cantidad;
    private TextView resultData;
    private TextView clasifData;

    private double añosValue;
    private double cantidadValue;
    private double tabacoValue;

    // Argument
    private Paciente argumentPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabaco_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        años = (EditText) findViewById(R.id.diastolicaData);
        cantidad = (EditText) findViewById(R.id.cantidadData);
        resultData = (TextView) findViewById(R.id.resultData);
        clasifData = (TextView) findViewById(R.id.clasifData);

        // Buttons

        tabacoButton = (Button) findViewById( R.id.button_calcular_hta);

        // Get in data
        Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );

        // Set info data
        identificacion.setText( argumentPaciente.getId() );

        identificacion.setEnabled( false );

        // Get Data
        new getTabaco().execute( argumentPaciente.getId() );

        // Buttons Listener
        tabacoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(años.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(cantidad.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                // Check correct number


                // Get data
                try
                {

                    añosValue = Double.parseDouble( años.getText().toString() );
                    cantidadValue = Double.parseDouble( cantidad.getText().toString() );

                    if ( añosValue <= 0 )
                    {
                        años.setError( "Años no puede ser menor que 0" );
                    }

                    if ( cantidadValue <= 0 )
                    {
                        cantidad.setError( "Cantidad no puede ser menor que 0" );
                    }

                    if (  añosValue >= 0 && cantidadValue >= 0 ){



                        // Calculate
                        tabacoValue = ( double ) ( ( cantidadValue * añosValue ) / 20 );

                        System.out.println( "drugged phoenix" );
                        System.out.println( añosValue );
                        System.out.println( cantidadValue );
                        System.out.println( tabacoValue );
                        System.out.println( "fin drugged phoenix" );

                        // Set Data
                        resultData.setText( String.valueOf( tabacoValue ) );

                        // Set clasif data
                        updateClasif( tabacoValue );

                        // Save Data
                        new tabacoActivity.saveTabaco().execute( argumentPaciente.getId(), años.getText().toString(), cantidad.getText().toString(), String.valueOf( tabacoValue ) );

                    }

                }catch(NumberFormatException e)
                {


                }








            }
        });

    }

    private void updateClasif( double ipa )
    {
        String clasif = "";

        if ( ipa < 10)
        {
            clasif = "SIN RIESGO";
        }
        else if ( ipa >= 10 && ipa <= 20 )
        {

            clasif = "RIESGO MODERADO";
        }
        else if ( ipa >= 21 && ipa <= 40 )
        {
            clasif = "RIESGO INTENSO";
        }
        else if ( ipa >= 41 )
        {

            clasif = "ALTO RIESGO";

        }

        clasifData.setText( clasif );
    }


    // Get all the medics to validate on the database
    private class getTabaco extends AsyncTask<   String, Void, ArrayList<String>> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "getTabaco.php" );


            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(DAOCardiovascular.getInstance().getReadTimeout());
                conn.setConnectTimeout(DAOCardiovascular.getInstance().getConnectTimeout());
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pacienteId", params[0])
                        ;
                String query = builder.build().getEncodedQuery();

                // Append parameters to URL
                /*Uri.Builder builder = new Uri.Builder()
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
                os.close();*/

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



                    System.out.println( "phoenix lion" );
                    System.out.println( reader );
                    System.out.println( "phoenix falcon" );
                    StringBuilder result = new StringBuilder();
                    String line;

                    // New Paciente Object
                    Paciente medic = new Paciente();

                    ArrayList< String > linePhp = new ArrayList <  > ();

                    // ArrayList < Paciente > medicPhp = new ArrayList<>();

                    while ((line = reader.readLine()) != null) {
                        System.out.println( "phoenix fox" );
                        System.out.println( line  );
                        System.out.println( "phoenix wolf" );

                        linePhp.add( line );

                        /*if ( linePhp.size() == 3 )
                        {

                            // Create medic Object
                            medic = new Paciente();

                            // Update Medic Object
                            medic.setId(linePhp.get(0));
                            medic.setSexo(linePhp.get(1));
                            medic.setEdad(linePhp.get(2));

                            // Update Medic ArrayList
                            medicPhp.add( medic );

                            // Reset linePhp
                            linePhp.clear();



                        }*/


                        //result.append(line);
                    }

                    System.out.println( "simorgh fire" );
                    // System.out.println( medicPhp );

                    // Pass data to onPostExecute method
                    return linePhp;
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
        protected void onPostExecute(ArrayList<String> result) {

            // Check obtained result

            //this method will be running on UI thread
            System.out.println( "Phoenix blue wave");
            System.out.println( result );
            System.out.println( "Phoenix alter ego");


            //pdLoading.dismiss();

            if ( result.size() > 0 ) {

                años.setText( result.get( 0 ) );
                cantidad.setText( result.get( 1 ) );
                resultData.setText( result.get( 2 ) );



                //resultData.setText( result.get( 2 ) );

                try {

                    // imcResult =  Double.parseDouble( resultData.getText().toString() );
                    añosValue = Integer.parseInt( años.getText().toString() );
                    cantidadValue = Integer.parseInt( cantidad.getText().toString() );
                    tabacoValue = Double.parseDouble( resultData.getText().toString() );
                    updateClasif( tabacoValue );

                }
                catch(NumberFormatException e)
                {


                }








                // Class<? extends Activity> activityClass;
                // activityClass = ValidateActivity.class;

                //  Intent intent = new Intent(DAOCardiovascular.this, activityClass);
                // startActivity(intent);





                //if(result.equalsIgnoreCase("true")){
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                // Clear the fields
                /*user_text.getText().clear();
                password_text.getText().clear();

                // Restaurar cursor
                user_text.requestFocus();*/

                // Change frame
               /* Class<? extends Activity> activityClass;
                activityClass = InicioActivity.class;

                // Paciente -> Menu Inicio
                String i = result.getRol();
                System.out.println("Llega aqui");
                System.out.println(i);
                if (i.equals( "1") ) {
                    activityClass = InicioActivity.class;

                    // Admin -> Menú admin
                } else if (i.equals( "0")){
                    activityClass = ValidateActivity.class;

                }*/

                // Start new frame
               /* Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);*/

                //EditText editText = (EditText) findViewById(R.id.edit_message_User);
                //String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);

               /* Intent intent = new Intent(MainActivity.this,SuccessActivity.class);
                startActivity(intent);
                MainActivity.this.finish();*/

            }else{
                //if (result.equalsIgnoreCase("false")){
                //onLoginFailed();
                // If username and password does not match display a error message
                // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            }/* else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }*/
        }
    }


    // Save The Imc Data
    public class saveTabaco extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl("saveTabaco.php");

            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( DAOCardiovascular.getInstance().getReadTimeout() );
                conn.setConnectTimeout( DAOCardiovascular.getInstance().getConnectTimeout() );
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pacienteId", params[0])
                        .appendQueryParameter("years", params[1])
                        .appendQueryParameter("cantidad", params[2])
                        .appendQueryParameter("ipa", params[3]);
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

            // Final result
            String finalResult = "";

            try {

                int response_code = conn.getResponseCode();

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
                    //return(result.toString());

                } else {
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

            return finalResult;
        }


        // @Override
        protected void onPostExecute(String result) {

            //pdLoading.dismiss();

            System.out.println("phoenix dungeon");
            System.out.println(result);

            if (result.equalsIgnoreCase("error")) {

                // Clear the fields

            } else if (result.equalsIgnoreCase("Tobacco Data actualizado")) {


                Toast.makeText(getBaseContext(), "Tobacco Data Actualizado", Toast.LENGTH_LONG).show();


            } else if (result.equalsIgnoreCase("Error al guardar Tobacco Data")) {


                Toast.makeText(getBaseContext(), "Error al guardar Tobacco Data", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {


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
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
