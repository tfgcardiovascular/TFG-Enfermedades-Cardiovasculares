package ucm.fdi.tfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
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

import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class FragmentPaciente extends Fragment {

    private ListView mLeadsList;
    private AdapterPaciente mLeadsAdapter;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    private String m_Text = "";

    private String identifier ="";
    private String genre = "";
    private String edad = "";



    public FragmentPaciente() {
        // Required empty public constructor
    }

    public static FragmentPaciente newInstance(/*parámetros*/) {
        FragmentPaciente fragment = new FragmentPaciente();
        // Setup parámetros
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Gets parámetros
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // Get medic
        String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();
        String rol = DAOCardiovascular.getInstance().getLoggedUser().getRol();

        if ( rol.equals( "0" ) )
        {
            colegiado = "admin";
        }

        new AsyncPacienteValidate().execute( colegiado );

        update();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_fragment_paciente, container, false);

        //System.out.println( "prueba onCreateView" );
        //System.out.println( inflater );
        //System.out.println( container );
        //System.out.println( savedInstanceState );
        //System.out.println( R.id.leads_list );
        //System.out.println( root );

        // Instancia del ListView.
        mLeadsList = (ListView) root.findViewById(R.id.leads_list);

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.search);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getActivity().getBaseContext(), "Busqueda", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Buscar Paciente");
                builder.setIcon(R.mipmap.search_icon);

                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.activity_search_patient, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);

                final EditText input2 = (EditText) viewInflated.findViewById(R.id.input2);

                final RadioButton Masculino = (RadioButton) viewInflated.findViewById(R.id.RB_genero_hombre);
                final RadioButton Femenino = (RadioButton) viewInflated.findViewById(R.id.RB_genero_mujer);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Dismiss Dialog
                        dialog.dismiss();

                        // Get fields
                        identifier = input.getText().toString();
                        edad = input2.getText().toString();

                        genre = "N";

                        if ( Masculino.isChecked() )
                        {

                            genre = "M";

                        }else if ( Femenino.isChecked() )
                        {
                            genre = "F";
                        }

                        // Get medic
                        String colegiado = DAOCardiovascular.getInstance().getLoggedUser().getColegiado();
                        String rol = DAOCardiovascular.getInstance().getLoggedUser().getRol();

                        if ( rol.equals( "0" ) )
                        {
                            colegiado = "admin";
                        }

                        // Get result
                        new AsyncPacienteSearch().execute( colegiado, identifier, edad, genre );

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        mLeadsAdapter = new AdapterPaciente(getActivity(),
                PacienteRepository.getInstance().getCategorys());

        //System.out.println( getActivity() );
        //System.out.println( PacienteRepository.getInstance().getCategorys() );

        //System.out.println( mLeadsAdapter );
        //System.out.println( mLeadsList );

        //Relacionando la lista con el adaptador
        mLeadsList.setAdapter(mLeadsAdapter);

        // Long Pressed
        mLeadsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l)
            {

                final Paciente currentLead = mLeadsAdapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Compartir Paciente");
                builder.setIcon(R.mipmap.share_icon);

                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.activity_share_patient, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Dismiss Dialog
                        dialog.dismiss();

                        // Share Patient

                        new AsyncPacienteShare().execute( currentLead.getId(), input.getText().toString() );

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }

        });


        // Eventos
        mLeadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Paciente currentLead = mLeadsAdapter.getItem(position);
                Toast.makeText(getActivity(),
                        "Vista detalle para: " + currentLead.getId(),
                        Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(getActivity(), PacienteValidarActivity.class);

                // Register
                DAOCardiovascular.getInstance().setCurrentPatient( currentLead );


                intent.putExtra( "paciente", currentLead);
                // intent.putExtra("colegiado", currentLead.getColegiado());
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_menu_category_list, menu);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_menu_context, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.share:
                // Tareas a realizar
                return true;
            //case R.id.delete:
                // Tareas a realizar
                //return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_delete_all) {
            // Eliminar todos los leads
            mLeadsAdapter.clear();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void update()
    {
       // mLeadsAdapter.clear();
        mLeadsAdapter.notifyDataSetChanged();
    }

    // Get all the medics to validate on the database
    private class AsyncPacienteValidate extends AsyncTask<   String, Void,  ArrayList<Paciente> > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Paciente> doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl( "getListPacientes.php" );

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
                        .appendQueryParameter("colegiado", params[0])
                        ;
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

                    // New Paciente Object
                    Paciente medic = new Paciente();

                    ArrayList< String > linePhp = new ArrayList <  > ();

                    ArrayList < Paciente > medicPhp = new ArrayList<>();

                    while ((line = reader.readLine()) != null) {
                        //System.out.println( line  );

                        linePhp.add( line );

                        if ( linePhp.size() == 26 )
                        {

                            // Create medic Object
                            medic = new Paciente();

                            // Update Medic Object
                            medic.setId(linePhp.get(0));
                            medic.setSexo(linePhp.get(1));
                            medic.setEdad(linePhp.get(2));

                            // Imc
                            medic.setHeight( linePhp.get( 3 ) );
                            medic.setWeight( linePhp.get( 4 ) );
                            medic.setImc( linePhp.get( 5 ) );

                            medic.setSistolica( linePhp.get( 6 ) );
                            medic.setDiastolica( linePhp.get( 7 ) );

                            medic.setCantidad( linePhp.get( 8 ) );
                            medic.setAdiccion( linePhp.get( 9 ) );
                            medic.setIpa( linePhp.get( 10 ) );

                            medic.setHdl( linePhp.get( 11 ) );
                            medic.setColesterolTotal( linePhp.get( 12 ) );
                            medic.setTrigliceridos( linePhp.get( 13 ) );
                            medic.setLdl( linePhp.get( 14 ) );

                            medic.setTipo( linePhp.get( 15 ) );
                            medic.setTratamiento( linePhp.get( 16 ) );
                            medic.setHbac( linePhp.get( 17 ) );
                            medic.setMonitorizacion( linePhp.get( 18 ) );

                            medic.setYearEnfermo( linePhp.get( 19 ) );

                            medic.setLastEyes( linePhp.get( 20 ) );

                            medic.setCreatinina( linePhp.get( 21 ) );

                            medic.setMicroalbuminuria( linePhp.get( 22 ) );
                            medic.setRaza( linePhp.get( 23 ) );

                            medic.setUrea( linePhp.get( 24 ) );

                            medic.setCardiovascular( linePhp.get( 25 ) );
                           // medic.setFinalTratamiento( linePhp.get( 26 ) );

                            // Update Medic ArrayList
                            medicPhp.add( medic );

                            // Reset linePhp
                            linePhp.clear();
                        }

                        //System.out.println( medicPhp );
                    }

                    //System.out.println( medicPhp );

                    // Pass data to onPostExecute method
                    return medicPhp;
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
        protected void onPostExecute(ArrayList<Paciente> result) {
            // Check obtained result
            //System.out.println( result );

            if ( result == null )
            {
                //System.out.println( "Conexión sin exito" );
                Toast.makeText( getActivity().getBaseContext(), "Conexión sin éxito", Toast.LENGTH_LONG).show();
            /*}

            else if ( result.size() > 0 ) {
                // Let PacienteRepository know the result
                PacienteRepository.getInstance().saveMedicList( result );

                //System.out.println( PacienteRepository.getInstance().getCategorys() );
                update();*/

            }else{

                // Let PacienteRepository know the result
                PacienteRepository.getInstance().saveMedicList( result );

                //System.out.println( PacienteRepository.getInstance().getCategorys() );
                update();

            }
        }
    }

    // Get all the medics to validate on the database
    private class AsyncPacienteShare extends AsyncTask<   String, Void,  String > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "sharePatient.php" );


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
                        .appendQueryParameter("paciente", params[0])
                        .appendQueryParameter("colegiado", params[1])
                        ;
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

                    // Pass data to onPostExecute method
                    //return(result.toString());

                } else {

                    //return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                finalResult = "exception";
            } finally {
                conn.disconnect();
            }

            return finalResult;
        }



        // @Override
        protected void onPostExecute( String result) {
            // Check obtained result

            //System.out.println( result );

            if (result.equalsIgnoreCase("error")) {

                // On Sign Up Failed
                Toast.makeText( getActivity().getBaseContext(), "Error en el proceso", Toast.LENGTH_LONG).show();

                //SignUp_Button.setEnabled(true);

            }else if (  result.equalsIgnoreCase("No existe el medico")    ){

                Toast.makeText( getActivity().getBaseContext(), "No existe el médico", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("Paciente ya asignado al medico")    ){

                Toast.makeText( getActivity().getBaseContext(), "Paciente ya asignado al médico", Toast.LENGTH_LONG).show();

            }else if (  result.equalsIgnoreCase("Error al compartir paciente")    ){

                Toast.makeText( getActivity().getBaseContext(), "Error al compartir paciente", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("Compartido con exito")) {

                Toast.makeText( getActivity().getBaseContext(), "Compartido con éxito", Toast.LENGTH_LONG).show();

            }
        }
    }

    // Get all the medics to validate on the database
    private class AsyncPacienteSearch extends AsyncTask<   String, Void,  ArrayList<Paciente> > {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Paciente> doInBackground(String... params) {


            url = DAOCardiovascular.getInstance().getUrl( "searchPatient.php" );


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
                        .appendQueryParameter("colegiado", params[0])
                        .appendQueryParameter("pacienteId", params[1])
                        .appendQueryParameter("age", params[2])
                        .appendQueryParameter("genre", params[3])
                        ;
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

                    // New Paciente Object
                    Paciente medic = new Paciente();

                    ArrayList< String > linePhp = new ArrayList <  > ();

                    ArrayList < Paciente > medicPhp = new ArrayList<>();

                    while ((line = reader.readLine()) != null) {
                        //System.out.println( line  );

                        linePhp.add( line );

                        if ( linePhp.size() == 3 )
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



                        }


                        //result.append(line);
                    }

                    //System.out.println( medicPhp );

                    // Pass data to onPostExecute method
                    return medicPhp;

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
        protected void onPostExecute(ArrayList<Paciente> result) {
            // Check obtained result
            //System.out.println( result );

            if ( result.size() > 0 ) {

                // Let PacienteRepository know the result
                PacienteRepository.getInstance().saveMedicList( result );

                //System.out.println( PacienteRepository.getInstance().getCategorys() );
                update();

                // Messages
                String message = "";
                boolean extra = false;

                if ( !identifier.equals( "" ) )
                {
                    message = message + " identificador: " + identifier;
                    extra = true;
                }

                if ( !edad.equals( "" ) )
                {
                    if ( extra )
                    {
                        message = message + ",";
                    }
                    message = message + " edad: " + edad;
                    extra = true;
                }

                if ( !genre.equals( "N" ) )
                {
                    if ( extra )
                    {
                        message = message + " y";
                    }
                    message = message + " género: " + genre;
                }

                if ( !message.equals( "" ) )
                {

                    message = "Paciente encontrado con" + message;

                }else
                {
                    message = "Pacientes encontrados";
                }

                Toast.makeText( getActivity().getBaseContext(), message, Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText( getActivity().getBaseContext(), "No hay pacientes encontrados", Toast.LENGTH_LONG).show();

            }
        }
    }
}
