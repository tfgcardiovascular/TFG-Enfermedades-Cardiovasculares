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

        resultData.setEnabled(false);

        // Buttons
        tabacoButton = (Button) findViewById( R.id.button_calcular_hta);

        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        // Set info data
        identificacion.setText( argumentPaciente.getId() );

        identificacion.setEnabled( false );

        años.setText( argumentPaciente.getAdiccion() );
        cantidad.setText( argumentPaciente.getCantidad() );
        resultData.setText( argumentPaciente.getIpa( ) );

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

                    if ( añosValue < 0 )
                    {
                        años.setError( "Años no puede ser menor que 0" );
                    }

                    if ( cantidadValue < 0 )
                    {
                        cantidad.setError( "Cantidad no puede ser menor que 0" );
                    }

                    if (  añosValue >= 0 && cantidadValue >= 0 ){

                        // Calculate
                        tabacoValue = ( double ) ( ( cantidadValue * añosValue ) / 20 );

                        //System.out.println( añosValue );
                        //System.out.println( cantidadValue );
                        //System.out.println( tabacoValue );

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
            clasif = "NULO";
        }
        else if ( ipa >= 10 && ipa < 21 )
        {

            clasif = "MODERADO";
        }
        else if ( ipa >= 21 && ipa < 41 )
        {
            clasif = "INTENSO";
        }
        else if ( ipa >= 41 )
        {

            clasif = "ALTO";

        }

        clasifData.setText( clasif );
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

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

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
            //System.out.println(result);

            if (result.equalsIgnoreCase("error")) {

                // Clear the fields

            } else if (result.equalsIgnoreCase("Tabaco Data actualizado")) {

                Toast.makeText(getBaseContext(), "Tabaco Data actualizado", Toast.LENGTH_LONG).show();

                argumentPaciente.setAdiccion( años.getText().toString() );
                argumentPaciente.setCantidad( cantidad.getText().toString() );
                argumentPaciente.setIpa( resultData.getText().toString( ) );

                //System.out.println( argumentPaciente );

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );

                //System.out.println( "tabaco argumentPaciente DAO" );
                //System.out.println( DAOCardiovascular.getInstance().getCurrentPatient() );

            } else if (result.equalsIgnoreCase("Error al guardar Tabaco Data")) {

                Toast.makeText(getBaseContext(), "Error al guardar Tabaco Data", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

            }
        }
    }
}
