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

import static ucm.fdi.tfg.R.mipmap.new_patient;

public class htaActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "htaActivity";

    private Button htaButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText sistolica;
    private EditText diastolica;
    private TextView resultData;

    private int diastolicaValue;
    private int sistolicaValue;

  //  private double imcResult;


    // Argument
    private Paciente argumentPaciente;

    private void updateClasif( int sistolicaInt, int diastolicaInt )
    {
        String clasif = "";

        if ( sistolicaInt < 130 )
        {
            clasif = "PRESIÓN ARTERIAL NORMAL";

            if ( diastolicaInt >= 85 )
            {
                diastolica.setError( "Diastolica suele estar por debajo de 85", getResources().getDrawable(R.mipmap.warning)   );

            }
        }
        else if ( sistolicaInt >= 130 && sistolicaInt <= 139 )
        {

            clasif = "PRESIÓN ARTERIAL ELEVADA NORMAL";

            if ( diastolicaInt < 85 || diastolicaInt > 89 )
            {
                diastolica.setError( "Diastolica suele estar entre 85 y 89", getResources().getDrawable(R.mipmap.warning)   );

            }
        }
        else if ( sistolicaInt >= 140 && sistolicaInt <= 159 )
        {
            clasif = "HIPERTENSIÓN (LEVE) FASE 1";

            if ( diastolicaInt < 90 || diastolicaInt > 99 )
            {
                diastolica.setError( "Diastolica suele estar entre 90 y 99", getResources().getDrawable(R.mipmap.warning)   );

            }


        }
        else if ( sistolicaInt >= 160 && sistolicaInt <= 179 )
        {

            clasif = "HIPERTENSIÓN (MODERADA) FASE 2";

            if ( diastolicaInt < 100 || diastolicaInt > 109 )
            {
                diastolica.setError( "Diastolica suele estar entre 100 y 109", getResources().getDrawable(R.mipmap.warning)   );

            }

        }
        else if ( sistolicaInt >= 180 && sistolicaInt <= 209  )
        {
            clasif = "HIPERTENSIÓN (GRAVE) FASE 3";

            if ( diastolicaInt < 110 || diastolicaInt > 119 )
            {
                diastolica.setError( "Diastolica suele estar entre 90 y 99", getResources().getDrawable(R.mipmap.warning)   );

            }

        }
        else if ( sistolicaInt >= 210  )
        {
            clasif = "HIPERTENSIÓN (MUY GRAVE) FASE 4";

            if ( diastolicaInt < 120 )
            {
                diastolica.setError( "Diastolica suele ser mayor o igual que 120", getResources().getDrawable(R.mipmap.warning)   );

            }

        }


        resultData.setText( clasif );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hta_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        sistolica = (EditText) findViewById(R.id.sistolicaData);
        diastolica = (EditText) findViewById(R.id.diastolicaData);
        resultData = (TextView) findViewById(R.id.resultData);

        // Buttons
        htaButton = (Button) findViewById( R.id.button_calcular_hta);

        // Get in data
        /*Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );*/

        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        // Set info data
        identificacion.setText( argumentPaciente.getId() );

        identificacion.setEnabled( false );
        resultData.setEnabled( false );


        sistolica.setText( argumentPaciente.getSistolica() );
        diastolica.setText( argumentPaciente.getDiastolica() );

        try {

            // imcResult =  Double.parseDouble( resultData.getText().toString() );
            sistolicaValue = Integer.parseInt( sistolica.getText().toString() );
            diastolicaValue = Integer.parseInt( diastolica.getText().toString() );
            updateClasif( sistolicaValue, diastolicaValue );

        }
        catch(NumberFormatException e)
        {


        }


        // Buttons Listener
        // Get Data
       // new getHta().execute( argumentPaciente.getId() );

        htaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sistolica.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(diastolica.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                // Check correct number


                // Get data
                try
                {

                    sistolicaValue = Integer.parseInt( sistolica.getText().toString() );
                    diastolicaValue = Integer.parseInt( diastolica.getText().toString() );

                    if ( sistolicaValue <= 90 )
                    {
                        sistolica.setError( "Sistólica debe ser mayor que 90" );
                    }

                    if ( diastolicaValue <= 60 )
                    {
                        diastolica.setError( "Diastólica debe ser mayor que 60" );
                    }

                    if (  sistolicaValue >= 90 && diastolicaValue >= 60 ){

                        // Calculate
                       // htaResult = diastolicaValue / pow( sistolicaValue, 2 );

                        // Set Data
                       // resultData.setText( String.valueOf( htaResult ) );

                        // Set clasif data
                        updateClasif( sistolicaValue, diastolicaValue );

                        // Save Data
                        new saveHta().execute( argumentPaciente.getId(), sistolica.getText().toString(), diastolica.getText().toString() );

                    }

                }catch(NumberFormatException e)
                {


                }








            }
        });




    }




    // Save The Imc Data
    public class saveHta extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl("saveHta.php");

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
                        .appendQueryParameter("sistolica", params[1])
                        .appendQueryParameter("diastolica", params[2]);
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

            } else if (result.equalsIgnoreCase("HTA Data actualizado")) {


                Toast.makeText(getBaseContext(), "HTA Data Actualizado", Toast.LENGTH_LONG).show();

                argumentPaciente.setSistolica( sistolica.getText().toString() );
                argumentPaciente.setDiastolica( diastolica.getText().toString() );

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );



            } else if (result.equalsIgnoreCase("Error al guardar HTA Data")) {


                Toast.makeText(getBaseContext(), "Error al guardar HTA Data", Toast.LENGTH_LONG).show();

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
