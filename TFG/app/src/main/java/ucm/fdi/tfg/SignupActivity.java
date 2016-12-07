package ucm.fdi.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

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
        Button SignUp_Button = (Button) findViewById(R.id.button_signup);

        /*if (!validate()) {
            onLoginFailed();
            return;
        }*/

        SignUp_Button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        //PENDIENTE!!!
        // progressDialog.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        progressDialog.setMessage("Creating account...");
        progressDialog.setIndeterminate(false);
        //progressDialog.setMax(100);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //progressDialog.setCancelable(true);
        progressDialog.show();

        EditText name_text = (EditText) findViewById(R.id.edit_message_User);
        String name = name_text.getText().toString();
        EditText surname_text = (EditText) findViewById(R.id.edit_message_Surname);
        String surname = surname_text.getText().toString();
        EditText number_text = (EditText) findViewById(R.id.edit_message_Number);
        String number = number_text.getText().toString();
        EditText telephone_text = (EditText) findViewById(R.id.edit_message_Telephone);
        String telephone = telephone_text.getText().toString();
        EditText mail_text = (EditText) findViewById(R.id.edit_message_Email);
        String mail = mail_text.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

        //new SignupActivity().SelectInicio().execute(user, password);

    }

    public void onLoginSuccess() {
        Button LogIn_Button = (Button) findViewById(R.id.button_login);
        LogIn_Button.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Button LogIn_Button = (Button) findViewById(R.id.button_login);
        Toast.makeText(getBaseContext(), "Login incorrecto", Toast.LENGTH_LONG).show();

        LogIn_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        EditText user_text = (EditText) findViewById(R.id.edit_message_User);
        EditText password_text = (EditText) findViewById(R.id.edit_message_Password);

        String user = user_text.getText().toString();
        String password = password_text.getText().toString();

        if (user.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            user_text.setError("Introduce un usuario correcto");
            valid = false;
        } else {
            user_text.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_text.setError("Introduce entre 4 y 10 carácteres alfanumericos");
            valid = false;
        } else {
            password_text.setError(null);
        }

        return valid;
    }

    private class SelectInicio extends AsyncTask<String,Void,Medico> {

        @Override
        protected Medico doInBackground(String... strings) {


            return null;//DAOCuentaCuentas.getInstance().login(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(Medico usu1) {

            if (usu1==null) {
                /*if(primero){
                    //inicializarVentana();
                }else {*/
                Toast toast =
                        Toast.makeText(getApplicationContext(),
                                "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
                toast.show();
                //}
            } else {
                Medico medico;
                medico = Medico.getIntsance();
                medico.setId(usu1.getId());
                medico.setNickname(usu1.getNickname());
                medico.setNombre(usu1.getNombre());
                medico.setApellidos(usu1.getApellidos());
                medico.setTelefono(usu1.getTelefono());
                medico.setPassword(usu1.getPassword());

                //usu.setImagen(usu1.getImagen());

                //IniciarAplicacion(usu);
            }

        }
    }
}

