package ucm.fdi.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ValidateActivity extends AppCompatActivity{
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "ValidateActivity";
    private ListView mListView;
    private Button Validar_Button;
    private Button Rechazar_Button;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), adminActivity.class);
        startActivity(intent);

    }

    protected void onResume(){
        super.onResume();
    }

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //Crear lista
        setContentView(R.layout.activity_validar);

        FragmentCategory leadsFragment = (FragmentCategory)
                getSupportFragmentManager().findFragmentById(R.id.leads_container);


        if (leadsFragment == null) {
            leadsFragment = FragmentCategory.newInstance();
            //leadsFragment.onCreateView( R.id.category_container, android.R.layout.simple_list_item_1, false );
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.leads_container, leadsFragment)
                    .commit();
        }
    }

    private void getMedicsValidateData()
    {

        // Initialize
        // New Medico Object
        Medico medic = new Medico();

        ArrayList< String > linePhp = new ArrayList <  > ();

        ArrayList < Medico > medicPhp = new ArrayList<>();

        HttpURLConnection conn;
        URL url = null;

        url = DAOCardiovascular.getInstance().getUrl( "getMedicValidate.php" );

        try {
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                //System.out.println( reader );
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    //System.out.println( line  );

                    linePhp.add( line );

                    if ( linePhp.size() == 6 )
                    {
                        // Create medic Object
                        medic = new Medico();

                        // Update Medic Object
                        medic.setNombre(linePhp.get(0));
                        medic.setApellidos(linePhp.get(1));
                        medic.setTelefono(linePhp.get(2));
                        medic.setPassword(linePhp.get(3));
                        medic.setColegiado(linePhp.get(4));
                        medic.setMail(linePhp.get(5));

                        // Update Medic ArrayList
                        medicPhp.add( medic );

                        // Reset linePhp
                        linePhp.clear();
                    }

                }
                //System.out.println( medicPhp );

                conn.disconnect();

            }else{

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        // Save the obtained data
        //this method will be running on UI thread
        //System.out.println( medicPhp );

        if ( medicPhp != null ) {

            // Let LeadsRepository know the result
            LeadsRepository.getInstance().saveMedicList( medicPhp );

        }else{

        }
    }
}

