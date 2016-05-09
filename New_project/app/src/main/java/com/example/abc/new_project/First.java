package com.example.abc.new_project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class First extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Player me = new Player();
    public Socket socket;
    TextView textResponse;
    TextView nikName;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    final String TAG = "Navi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextAddress = (EditText)findViewById(R.id.address);
        editTextPort = (EditText)findViewById(R.id.port);
        buttonConnect = (Button)findViewById(R.id.connect);
        buttonClear = (Button)findViewById(R.id.clear);
        textResponse = (TextView)findViewById(R.id.response);
        nikName = (EditText)findViewById(R.id.nik);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        //Очищаэ поле результату
        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
                Reader_socket temp = new Reader_socket(me);
                temp.execute();
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Log.e("Navi", navigationView.toString());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.e("Navi","true");
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Log.e("Navi","false");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.connecting) {
            // Handle the camera action
        } else if (id == R.id.rol_view) {
            Intent view_rol = new Intent(this,Rol_activity.class);
            view_rol.putExtra("Keygen",me.primary_rol.rols_player.definition);
            startActivity(view_rol);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener buttonConnectOnClickListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {

                    MyClientTask myClientTask = new MyClientTask(
                            editTextAddress.getText().toString(),
                            Integer.parseInt(editTextPort.getText().toString()));
                    myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response, nik;

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected void onPreExecute() {
            response = textResponse.getText().toString();
            nik = nikName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {


            //socket = null;

            try {
                if(socket==null) {
                    socket = new Socket(dstAddress, dstPort);
                    me = new Player(socket);
                }
                byte [] message =("Nik:"+ nik+";").getBytes();

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(message);


                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();


    /*
     * notice:
     * inputStream.read() will block if no data return
     */
                DataInputStream asaa = new DataInputStream(inputStream);

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        if (buffer[bytesRead-1] == ';') {
                            byteArrayOutputStream.write(buffer, 0, bytesRead);
                            response = byteArrayOutputStream.toString("UTF-8");
                            parser(response, me);
                             byteArrayOutputStream.reset();
                        }
                    }






                /*while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }*/


            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if(socket != null){
                    /*try {
                      //  socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
     //       buttonConnect.setClickable(false);


            super.onPostExecute(result);
        }

    }
    private class Reader_socket extends AsyncTask<Void, Void, Void> {
        Player players;
        InputStream inputStream;
        String response = "";
        public Reader_socket (Player a){
            players = a;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            if(players!=null)
                try {
                    inputStream = players.socket_player.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream(1024);
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            if (buffer[bytesRead] == 0) {
                                byteArrayOutputStream.write(buffer, 0, bytesRead);
                                response += byteArrayOutputStream.toString("UTF-8");
                                parser(response, players);
                                byteArrayOutputStream.reset();
                            }
                        }




                } catch (Exception e) {
                    e.printStackTrace();
                }
            return null;
        }
    }
    void parser(final String a, Player player){

        First.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textResponse.setText(textResponse.getText().toString()+a);
            }
        });

        String mas_str[] = a.split(":");
        switch (mas_str[0]){
            case "Rol":{
                switch (mas_str[1]){
                    case "Civilians;":
                        player.addPrimaryPols(new Rols(Rol_name.Civilians));
                    break;
                    case "Don;":
                        player.addPrimaryPols(new Rols(Rol_name.Don));
                        break;
                    case "Sheriff;":
                        player.addPrimaryPols(new Rols(Rol_name.Sheriff));
                        break;
                    case "Doctor;":
                        player.addPrimaryPols(new Rols(Rol_name.Doctor));
                        break;
                    case "Mafia;":
                        player.addPrimaryPols(new Rols(Rol_name.Mafia));
                        break;

                }
            }
            break;
        }

    }
}
