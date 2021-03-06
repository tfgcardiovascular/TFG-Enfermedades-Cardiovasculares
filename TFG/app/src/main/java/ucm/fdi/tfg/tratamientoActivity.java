package ucm.fdi.tfg;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class tratamientoActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "tratamientoActivity";

    private TextView tratamiento_inicial;
    private EditText tratamiento;

    // Argument
    private Paciente argumentPaciente;

    private Button saveButton;

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), PacienteValidarActivity.class);
        startActivity(intent);

    }

    public void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tratamiento);

        // EditText
        tratamiento_inicial = ( TextView ) findViewById( R.id.editText );
        tratamiento = (EditText) findViewById( R.id.editText2 );
        saveButton = (Button ) findViewById(R.id.button_guardar);

        // Set
        argumentPaciente = DAOCardiovascular.getInstance().getCurrentPatient();

        //System.out.println( "argumentPaciente" );
        //System.out.println( argumentPaciente );

        String infoTratamiento = DAOCardiovascular.getInstance().infoTratamientoCardiovascular();
        tratamiento_inicial.setText( infoTratamiento );

        // Tratamiento médico
        tratamiento.setText( argumentPaciente.getFinalTratamiento() );

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                argumentPaciente.setFinalTratamiento( tratamiento.getText().toString() );

                // Calculate cardiovascular
                new saveTratamiento().execute( argumentPaciente.getId(), argumentPaciente.getFinalTratamiento() );

            }
        });
    }

    private class saveTratamiento extends AsyncTask<String,Void,String> {

        // ProgressDialog pdLoading = new ProgressDialog(SignupActivity.this);
        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl("saveTratamiento.php");

            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(DAOCardiovascular.getInstance().getReadTimeout());
                conn.setConnectTimeout(DAOCardiovascular.getInstance().getConnectTimeout());
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pacienteId", params[0])
                        .appendQueryParameter("tratamiento", params[1]);
                String query = builder.build().getEncodedQuery();

                for (int i = 0; i < params.length; i++) {
                    System.out.println(params[i]);
                }

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
                //return "exception";
            }

            // Final result
            String finalResult = "";

            // Tercer try
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


                } else {

                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
            } finally {
                conn.disconnect();
            }

            return finalResult;
        }

        //@Override
        protected void onPostExecute(String result) {
            //System.out.println(result);

            if (result.equalsIgnoreCase("Paciente no encontrado")) {

                Toast.makeText(getBaseContext(), "Paciente no encontrado", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase("exception")) {

            } else if (result.equalsIgnoreCase("Tratamiento guardado con exito")) {

                DAOCardiovascular.getInstance().setCurrentPatient( argumentPaciente );

                Toast.makeText(getBaseContext(), "Tratamiento guardado con éxito", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("No hay cambios en el tratamiento")) {

                Toast.makeText(getBaseContext(), "No hay cambios en el tratamiento", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Error al guardar el tratamiento")) {

                Toast.makeText(getBaseContext(), "Error al guardar el tratamiento", Toast.LENGTH_LONG).show();

            }
        }
    }
}
