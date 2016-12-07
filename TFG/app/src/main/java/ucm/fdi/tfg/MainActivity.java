package ucm.fdi.tfg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "ucm.fdi.tfg .MESSAGE";

    private Button LogIn_Button;
    private Button SignUp_Button;
    private EditText user_text;
    private EditText password_text;
    private CheckBox opcionMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogIn_Button = (Button) findViewById(R.id.button_login);
        SignUp_Button = (Button) findViewById(R.id.button_signup);
        user_text = (EditText) findViewById(R.id.edit_message_User);
        password_text = (EditText) findViewById(R.id.edit_message_Password);
        opcionMostrar = (CheckBox)findViewById(R.id.edit_check_contraseña);

        LogIn_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logIn(v);
            }
        });

        SignUp_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Called when the user clicks the Send button */
    /*public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message_User);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/

    public void logIn (View view){

        if (!validate()) {
            onLoginFailed();
            return;
        }

        LogIn_Button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this,
                R.style.AppTheme);
        //PENDIENTE!!!
        progressDialog.setMessage("Autenticando...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        String user = user_text.getText().toString();
        String password = password_text.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);

        new SelectInicio().execute(user, password);

    }

    public void onLoginSuccess() {
        LogIn_Button.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login incorrecto", Toast.LENGTH_LONG).show();
        LogIn_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String user = user_text.getText().toString();
        String password = password_text.getText().toString();

       // if (user.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
        if (user.isEmpty()){
            user_text.setError("Introduce un usuario correcto");
            valid = false;
        } else {
            user_text.setError(null);
        }

        if (password.isEmpty() || password.length() < 0 || password.length() > 10) {
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

            System.out.println("phoenix dark");


            return DAOCardiovascular.getInstance().login(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(Medico usu1) {

            System.out.println("phoenix light");

            if (usu1==null) {


                System.out.println("phoenix rainbow");
                /*if(primero){
                    //inicializarVentana();
                }else {*/
                Toast toast =
                        Toast.makeText(getApplicationContext(),
                                "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
                toast.show();

               /* Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message_User);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/
                //}
            } else {

                System.out.println("phoenix fire");
                Medico medico;
                medico = Medico.getIntsance();
                System.out.println( medico.getNombre());
                System.out.println("phoenix");

                Intent intent = new Intent(MainActivity.this, DisplayMessageActivity.class);
                EditText editText = (EditText) findViewById(R.id.edit_message_User);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

                System.out.println("phoenix ice");

                /*Medico medico;
                medico = Medico.getIntsance();
                medico.setId(usu1.getId());
                medico.setNickname(usu1.getNickname());
                medico.setNombre(usu1.getNombre());
                medico.setApellidos(usu1.getApellidos());
                medico.setTelefono(usu1.getTelefono());
                medico.setPassword(usu1.getPassword());*/

                //usu.setImagen(usu1.getImagen());

                //IniciarAplicacion(usu);


            }
        }
    }

    public void mostrarContraseña(View v){
        // Salvar cursor
        int cursor = password_text.getSelectionEnd();

        if(opcionMostrar.isChecked()){
            password_text.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            password_text.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        // Restaurar cursor
        password_text.setSelection(cursor);
    }
}