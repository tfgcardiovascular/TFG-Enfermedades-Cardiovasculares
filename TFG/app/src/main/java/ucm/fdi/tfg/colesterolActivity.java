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

public class colesterolActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "colesterolActivity";

    private Button colButton;

    // Text
    private TextView identif;

    private EditText identificacion;
    private EditText HDL;
    private EditText Total;
    private EditText trigliceridos;
    private EditText LDL;
    //private TextView clasifData;

    private double hdlValue;
    private double totalValue;
    private double trigliceridosValue;
    private double ldlValue;

    // Argument
    private Paciente argumentPaciente;

    public void onResume(Bundle savedInstanceState){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colesterol_paciente);

        // TextView
        identificacion = (EditText) findViewById(R.id.id);

        // Text
        identif = (TextView) findViewById(R.id.identificacion);

        HDL = (EditText) findViewById(R.id.hdlData);
        Total = (EditText) findViewById(R.id.totalData);
        LDL = (EditText) findViewById(R.id.resultData);
        trigliceridos = (EditText) findViewById(R.id.trigliceridosData);
        //clasifData = (TextView) findViewById(R.id.clasifData);

        // Buttons
        colButton = (Button) findViewById( R.id.button_calcular_col);

        // Get in data
       /* Bundle bundle = getIntent().getExtras();

        argumentPaciente =  ( Paciente ) getIntent().getSerializableExtra( "paciente" );*/
        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        // Set info data
        identificacion.setText( argumentPaciente.getId() );
        HDL.setText( argumentPaciente.getHdl() );
        Total.setText( argumentPaciente.getColesterolTotal() );
        trigliceridos.setText( argumentPaciente.getTrigliceridos() );
        LDL.setText( argumentPaciente.getLdl() );

        identificacion.setEnabled( false );
        LDL.setEnabled( false );
        //clasifData.setEnabled( false );

        // Get Data
        //new getImc().execute( argumentPaciente.getId() );

        colButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(HDL.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow(Total.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                imm.hideSoftInputFromWindow( trigliceridos.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                // Get data
                try
                {

                    hdlValue = Double.parseDouble( HDL.getText().toString() );
                    totalValue = Double.parseDouble( Total.getText().toString() );
                    trigliceridosValue = Double.parseDouble( trigliceridos.getText().toString() );

                    if ( hdlValue >= totalValue )
                    {
                        HDL.setError( "HDL no puede ser mayor que Colesterol Total" );
                    }

                    // Calculate
                    ldlValue = ( double ) ( ( totalValue - hdlValue - ( trigliceridosValue ) / 5 ) );

                    // Set Data
                    LDL.setText( String.valueOf( ldlValue ) );

                    new saveColesterol().execute( argumentPaciente.getId(), HDL.getText().toString(), Total.getText().toString(), trigliceridos.getText().toString(), LDL.getText().toString() );


                }catch(NumberFormatException e)
                {


                }

            }

        });
    }



    // Save Colesterol
    public class saveColesterol extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get url
            url = DAOCardiovascular.getInstance().getUrl("saveColesterol.php");

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
                        .appendQueryParameter("hdl", params[1])
                        .appendQueryParameter("total", params[2])
                        .appendQueryParameter("trigliceridos", params[3])
                        .appendQueryParameter("ldl", params[4]);
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

            } else if (result.equalsIgnoreCase("Colesterol Data actualizado")) {


                Toast.makeText(getBaseContext(), "Colesterol Data actualizado", Toast.LENGTH_LONG).show();

                argumentPaciente.setHdl( HDL.getText().toString() );
                argumentPaciente.setColesterolTotal( Total.getText().toString() );
                argumentPaciente.setTrigliceridos( trigliceridos.getText().toString() );
                argumentPaciente.setLdl( LDL.getText().toString( ) );

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );


            } else if (result.equalsIgnoreCase("Error al guardar Colesterol Data")) {


                Toast.makeText(getBaseContext(), "Error al guardar Colesterol Data", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Registrado con exito")) {

            }
        }
    }

}




