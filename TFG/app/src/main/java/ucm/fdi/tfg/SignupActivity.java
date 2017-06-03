package ucm.fdi.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignupActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";
    private static final String TAG = "SignupActivity";

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    // Buttons
    private Button LogIn_Button;
    private Button SignUp_Button;

    // Define important variables
    private EditText name_text;
    private EditText surname_text;
    private EditText number_text;
    private EditText telephone_text;
    private EditText password_text;
    private EditText mail_text;
    private EditText repeatPasswordText;

    private String name;
    private String surname;
    private String number;
    private String telephone;
    private String password;
    private String mail;
    private String repeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button LogIn_Button = (Button) findViewById(R.id.button_create);
        LogIn_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                signUp(v);
            }
        });

        Button SignUp_Button = (Button) findViewById(R.id.button_login2);
        SignUp_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    public void signUp (View view){

        // Get field values
        name_text = (EditText) findViewById(R.id.edit_message_Name);
        name = name_text.getText().toString();
        surname_text = (EditText) findViewById(R.id.edit_message_Surname);
        surname = surname_text.getText().toString();
        number_text = (EditText) findViewById(R.id.edit_message_Number);
        number = number_text.getText().toString();
        telephone_text = (EditText) findViewById(R.id.edit_message_Telephone);
        telephone = telephone_text.getText().toString();
        mail_text = (EditText) findViewById(R.id.edit_message_Email);
        mail = mail_text.getText().toString();
        password_text = (EditText) findViewById(R.id.edit_message_Password);
        password = password_text.getText().toString();
        repeatPasswordText = (EditText) findViewById(R.id.edit_message_repeatPass);
        repeatPassword = repeatPasswordText.getText().toString();

        // Validate
        if (!validate()) {
            onSignUpFailed();
            return;
        }

        new SelectInicio().execute(name, surname, number, telephone, password, mail);
    }

    public void onSignUpFailed(){
        Toast.makeText(getBaseContext(), "Registro incorrecto", Toast.LENGTH_LONG).show();
        //SignUp_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        // Validate mail
        if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mail_text.setError("Introduce una dirección e-mail válida");
            valid = false;
        } else {
            mail_text.setError(null);
        }

        // Validate name
        if ( name.isEmpty() ){
            name_text.setError("Introduce un nombre");
            valid = false;
        } else {
            name_text.setError(null);
        }

        // Validate surname
        if ( surname.isEmpty() ){
            surname_text.setError("Introduce un apellido");
            valid = false;
        } else {
            surname_text.setError(null);
        }

        // Validate telephone
        if ( telephone.isEmpty() || telephone.length() != 9 ){
            telephone_text.setError("Introduce un Nº teléfono correcto (9 cifras numéricas)");
            valid = false;
        } else {
            telephone_text.setError(null);
        }

        // Validate user
        if ( number.isEmpty() || number.length() != 9 ){
            number_text.setError("Introduce un Nº Colegiado correcto (9 cifras numéricas)");
            valid = false;
        } else {
            number_text.setError(null);
        }

        // Validate password
        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            password_text.setError("Introduce entre 4 y 16 carácteres alfanumericos");
            valid = false;

        }else if ( !password.equals( repeatPassword ) )
        {

            password_text.setError("Las contraseñas no coinciden");
            valid = false;

        } else {
            password_text.setError(null);
        }

        //System.out.println( valid );

        return valid;
    }

    private class SelectInicio extends AsyncTask<String,Void,String> {

        ProgressDialog pdLoading = new ProgressDialog(SignupActivity.this);
        HttpURLConnection conn;
        URL url = null;

        // Message of Sign Up activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tProcesando registro...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            url = DAOCardiovascular.getInstance().getUrl( "SignUp.php" );

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
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("surname", params[1])
                        .appendQueryParameter("number", params[2])
                        .appendQueryParameter("telephone", params[3])
                        .appendQueryParameter("password", params[4])
                        .appendQueryParameter("mail", params[5]);
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

        //@Override
        protected void onPostExecute(String result) {
            // Remove loading
            pdLoading.dismiss();
            //System.out.println( result );

            if (result.equalsIgnoreCase("User already exists")) {

                // Clear the fields
                name_text.getText().clear();
                surname_text.getText().clear();
                number_text.getText().clear();
                telephone_text.getText().clear();
                mail_text.getText().clear();
                password_text.getText().clear();
                repeatPasswordText.getText().clear();

                // On Sign Up Failed
                Toast.makeText(getBaseContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();
                //SignUp_Button.setEnabled(true);

            } else if (result.equalsIgnoreCase("false") || result.equalsIgnoreCase( "exception" )) {
                onSignUpFailed();
                // If username and password does not match display a error message
                // Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).Show();

            } else if (result.equalsIgnoreCase("true")) {

                // Prepare next window
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message_Name);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        }
    }
}

