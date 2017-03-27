package ucm.fdi.tfg;

import java.net.MalformedURLException;
import java.net.URL;

public class DAOCardiovascular {

    private static DAOCardiovascular dao = new DAOCardiovascular();
    private String IP = "192.168.1.199:3306"; //10.0.2.2
    private String baseDatos = "/cardiovascular";
    private String usbd = "root";
    private String contbd = "";


    private Medico loggedUser;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private DAOCardiovascular() {


    }


    public static DAOCardiovascular getInstance() {
        return dao;
    }


    public void setLoggedUser(Medico medic) {

        loggedUser = medic;

    }

    public Medico getLoggedUser() {

        return loggedUser;

    }


    public boolean isNumber(String string) {

        if (string == null || string.isEmpty()) {
            return false;

        }

        int i = 0;
        if (string.charAt(0) == '-') {
            if (string.length() > 1) {
                i++;
            } else {
                return false;
            }
        }


        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public URL getUrl(String phpFile) {

        URL url = null;
        String dir = "http://147.96.123.148:8888/php/" + phpFile;

        try {

            url = new URL(dir);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        return url;
    }

    public int getReadTimeout()
    {
        return READ_TIMEOUT;
    }

    public int getConnectTimeout() {
        return CONNECTION_TIMEOUT;
    }


    public String prueba() {
        return "PHOENIX";
    }

}

    /*

    public void beginSaveImc(String pacienteId, String altura, String peso, String imc) {
        new saveImc().execute(pacienteId, altura, peso, imc);
    }





        // Get all the medics to validate on the database
        public class AsyncMedicValidate extends AsyncTask<String, String, ArrayList<Medico>> {

            HttpURLConnection conn;
            URL url = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<Medico> doInBackground(String... params) {
                try {
                    // Enter URL address where your php file resides
                    url = new URL("http://147.96.164.247:8888/php/getMedicValidate.php");//192.168.1.199:8888

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // return "exception";
                }
                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");

                    // setDoInput and setDoOutput method depict handling of both send and receive
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

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

                   /* conn.connect();

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


                        System.out.println("phoenix lion");
                        System.out.println(reader);
                        System.out.println("phoenix falcon");
                        StringBuilder result = new StringBuilder();
                        String line;

                        // New Medico Object
                        Medico medic = new Medico();

                        ArrayList<String> linePhp = new ArrayList<>();

                        ArrayList<Medico> medicPhp = new ArrayList<>();

                        while ((line = reader.readLine()) != null) {
                            System.out.println("phoenix fox");
                            System.out.println(line);
                            System.out.println("phoenix wolf");

                            linePhp.add(line);

                            if (linePhp.size() == 6) {

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
                                medicPhp.add(medic);

                                // Reset linePhp
                                linePhp.clear();


                            }


                            //result.append(line);
                        }

                        System.out.println("simorgh fire");
                        System.out.println(medicPhp);

                        // Pass data to onPostExecute method
                        return medicPhp;
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
            }


            // @Override
            protected void onPostExecute(ArrayList<Medico> result) {

                // Check obtained result

                //this method will be running on UI thread
                System.out.println("Phoenix blue wave");
                System.out.println(result);
                System.out.println("Phoenix alter ego");


                //pdLoading.dismiss();

                if (result != null) {

                    // Let LeadsRepository know the result
                    LeadsRepository.getInstance().saveMedicList(result);

                    System.out.println("phoenix de hielo y storm");
                    System.out.println(LeadsRepository.getInstance().getCategorys());

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

                // Medico -> Menu Inicio
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

                /*} else {
                    //if (result.equalsIgnoreCase("false")){
                    //onLoginFailed();
                    // If username and password does not match display a error message
                    // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

                }/* else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                //  Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).Show();
            }*/
                /*
            }

        }
    }

}*/
    //}





    /*

    public Medico login(String usun, String cont){
        Medico usu = null;
        try {

            Connection conn;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+IP+baseDatos,  usbd, contbd);

            //conn.setConnectTimeout(50);

            String peticion = "select * from medico WHERE nombre= ? AND password = ?";
            PreparedStatement consulta = conn.prepareStatement(peticion);
            System.out.println("Conexion establecida");
            consulta.setString(1,usun);
            consulta.setString(2,cont);
            ResultSet result = consulta.executeQuery();

            if (result != null) {
                if (!result.next()) {
                    return null;

                } else {
                    usu= new Medico();
                    usu.setId(result.getString("id"));
                    usu.setColegiado(result.getString("colegiado"));
                    usu.setNombre(result.getString("nombre"));
                    usu.setApellidos(result.getString("apellidos"));
                    usu.setTelefono(result.getString("telefono"));
                    usu.setPassword(result.getString("password"));

                    Blob bl=result.getBlob("imagen");
                    if(bl!=null){
                        usu.setImagen(bl.getBytes(1, (int)bl.length()));
                    }

                    return usu;
                }

            }else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }*/
