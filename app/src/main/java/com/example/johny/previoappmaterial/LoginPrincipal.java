package com.example.johny.previoappmaterial;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class LoginPrincipal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_principal);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void consultar (View view){
        EditText var1 = (EditText) findViewById(R.id.user);
        EditText var2 = (EditText) findViewById(R.id.pass);

        String nombre = var1.getText().toString();
        String contra = var2.getText().toString();

        try {
            HttpURLConnection conn;
            URL url;
            //DECLARACION DE VARIABLES
            url = new URL("http://192.168.0.103/Android/conectar.php");
            String param = URLEncoder.encode("nombre", "UTF-8")+ "=" + URLEncoder.encode(nombre, "UTF-8");
            param += "&" + URLEncoder.encode("contra", "UTF-8")+ "=" + URLEncoder.encode(contra, "UTF-8");

            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true); //INICIA Y VALIDA EL PROCESO
            conn.setRequestMethod("POST"); //SELECCIONAMOS EL METODO DE ENVIO : EN ESTE CASO POST
            conn.setFixedLengthStreamingMode(param.getBytes().length); //CONCATENA LOS PARAMETROS Y LOS INSERTA EN EL PHP
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //PROPIEDADES DE CODIFICACION
            PrintWriter out = new PrintWriter(conn.getOutputStream());

            out.print(param);
            out.close();

            //CONSTRUYE LA CADENA OBTENIDA DEL SERVIDOR
            String response= "";

            //ESCANEA LA LINEA POR DECODIFICACION
            Scanner inStream = new Scanner(conn.getInputStream());

            //PROCESA EL RESULTADO EN UNA CADENA PARA SER LEIDA
            while(inStream.hasNextLine())
                response+=(inStream.nextLine());

            //ENVIAMOS EL MENSAJE EN UNA CAJA TOAST
            conn.disconnect();
            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}
