package com.example.user.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnimagen;
    ImageView imgimagen;
    private static final String IMAGEN_DESCARGAR = "http://vignette4.wikia.nocookie.net/distos/images/e/e6/Vete_a_la_versh_EP29.png/revision/latest?cb=20140309040159&path-prefix=es";
    //private static final String IMAGEN_DESCARGAR = "http://vetealaversh.com/wp-content/uploads/2016/01/005-820x250.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnimagen = (Button) findViewById(R.id.btncargarImagen);
        imgimagen = (ImageView) findViewById(R.id.imgimagen);

        btnimagen.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        cargarImagen tarea1 = new cargarImagen();
        tarea1.execute(IMAGEN_DESCARGAR);
    }

    private class cargarImagen extends AsyncTask<String,Void,Bitmap>{

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Descargando imagen...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imagenDescargar = params[0];
            Bitmap imagenDescargada = descargarImagen(imagenDescargar);
            return imagenDescargada;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            imgimagen.setImageBitmap(result);

            pDialog.dismiss();
        }
    }

    private Bitmap descargarImagen (String direccion){
        URL imageUrl = null;
        Bitmap imagen = null;
        try{
            imageUrl = new URL(direccion);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return imagen;
    }
}
