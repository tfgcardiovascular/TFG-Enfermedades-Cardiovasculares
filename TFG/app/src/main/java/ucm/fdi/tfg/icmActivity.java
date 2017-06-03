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

import static java.lang.Math.pow;

public class icmActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "icmActivity";

    private Button imcButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText heightData;
    private EditText weightData;
    private EditText resultData;
    private TextView clasifData;
    private EditText edad;

    private double weight;
    private double height;

    private double imcResult;

    // Argument
    private Paciente argumentPaciente;


    public void onResume(Bundle savedInstanceState)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icm_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        heightData = (EditText) findViewById(R.id.hdlData);
        weightData = (EditText) findViewById(R.id.diastolicaData);
        resultData = (EditText) findViewById(R.id.resultData);
        clasifData = (TextView) findViewById(R.id.clasifData);

        // Buttons
        imcButton = (Button) findViewById( R.id.button_calcular_hta);

        // Set
        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        //System.out.println( argumentPaciente );


        // Set info data
        identificacion.setText( argumentPaciente.getId() );

        identificacion.setEnabled( false );
        resultData.setEnabled( false );
        clasifData.setEnabled( false );

        // Get Data
        heightData.setText( argumentPaciente.getHeight() );
        weightData.setText( argumentPaciente.getWeight() );
        resultData.setText( argumentPaciente.getImc() );

        try {

            imcResult =  Double.parseDouble( resultData.getText().toString() );
            updateClasif( imcResult );

        }
        catch(NumberFormatException e)
        {

        }

        imcButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(heightData.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(weightData.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                // Check correct number

                // Get data
                try
                {

                    height = Double.parseDouble( heightData.getText().toString() );
                    weight = Double.parseDouble( weightData.getText().toString() );

                    if ( height <= 0 )
                    {

                        heightData.setError( "Altura debe ser mayor que 0" );

                    }

                    if ( weight <= 0 )
                    {

                        weightData.setError( "Peso debe ser mayor que 0" );

                    }

                    if (  height > 0 && weight > 0 ){

                        // Calculate
                        imcResult = weight / pow( height, 2 );

                        // Set Data
                        resultData.setText( String.valueOf( imcResult ) );

                        // Set clasif data
                        updateClasif( imcResult );

                        // Save Data
                        new saveImc().execute( argumentPaciente.getId(), heightData.getText().toString(), weightData.getText().toString(), resultData.getText().toString() );

                    }

                }catch(NumberFormatException e)
                {

                }
            }
        });
    }

    private void updateClasif( double imc )
    {
        String clasif = "";

        if ( imc < 16 )
        {

            clasif = "INFRAPESO: DELGADEZ SEVERA";

        }
        else if ( imc >= 16 && imc <= 16.99 )
        {

            clasif = "INFRAPESO: DELGADEZ MODERADA";

        }
        else if ( imc >= 17 && imc <= 18.49 )
        {

            clasif = "INFRAPESO: DELGADEZ ACEPTABLE";

        }
        else if ( imc >= 18.5 && imc <= 24.99 )
        {

            clasif = "PESO NORMAL";

        }
        else if ( imc >= 25 && imc <= 29.99 )
        {

            clasif = "SOBREPESO";

        }
        else if ( imc >= 30 && imc <= 34.99 )
        {

            clasif = "OBESO: TIPO I";

        }
        else if ( imc >= 35 && imc <= 40 )
        {

            clasif = "OBESO: TIPO II";

        }
        else if ( imc > 40 )
        {

            clasif = "OBESO: TIPO III";

        }

        clasifData.setText( clasif );
    }

    // Save The Imc Data
    public class saveImc extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl("saveImc.php");

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
                        .appendQueryParameter("altura", params[1])
                        .appendQueryParameter("peso", params[2])
                        .appendQueryParameter("imc", params[3]);
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

            } else if (result.equalsIgnoreCase("IMC Data actualizado")) {


                Toast.makeText(getBaseContext(), "IMC Data Actualizado", Toast.LENGTH_LONG).show();

                // Update
                argumentPaciente.setHeight( heightData.getText().toString() );
                argumentPaciente.setWeight( weightData.getText().toString() );
                argumentPaciente.setImc( resultData.getText().toString() );

                // Save
                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );


            } else if (result.equalsIgnoreCase("Error al guardar IMC Data")) {

                Toast.makeText(getBaseContext(), "Error al guardar IMC Data", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

            }
        }
    }
}
