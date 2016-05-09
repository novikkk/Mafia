package com.example.abc.client_4_2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.json.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;


final public class MainActivity extends Activity {
    static ArrayList<Player> mas_socket = new ArrayList<Player>();
    TextView info, infoip, msg;
    String message = "";
    static ServerSocket serverSocket;

    public void asdfg(View a) throws IOException {

        /*SocketServerStringThread socketServerString = new SocketServerStringThread(
                mas_socket.get(0).socket_player,"Rol:Civilian;");x
        socketServerString.run();*/
        Reader_all_Thread readerall = new Reader_all_Thread();
        readerall.run();

        Intent config_windows = new Intent(this,Config_Windows.class);
        startActivity(config_windows);

/*
        if(mas_socket.get(0).socket_player!=null){
            MyWriterServerTask ms = new MyWriterServerTask(mas_socket.get(0),"Rol:Cuvilian;");
            ms.execute();
        }*/

    }


    /*public class MyWriterServerTask extends AsyncTask<Void, Void, Void> {

        Player player;
        DataOutputStream outputStream;
        String mesage;
        MyWriterServerTask(Player a, String mes){
           player = a;
            mesage = mes;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                outputStream = new DataOutputStream(player.socket_player.getOutputStream());
                outputStream.writeUTF(message);
                //outputStream.write(0);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        infoip.setText(getIpAddress());

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverSocket != null) {
            /*try {
                //serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
    }
    private class Reader_socket_one extends AsyncTask<Void, Void, Void> {
        Player players;
        InputStream inputStream;
        String response = "";
        public Reader_socket_one(Player a){
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
                            if (buffer[bytesRead] == ';') {
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
    static void parser(String a,Player player){

        String mas_str[] = a.split(":");
        switch (mas_str[0]){
            case "Nik":{
                player.setNikname(mas_str[1]);
            }
            break;
        }

    }
    private class Reader_all_Thread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<mas_socket.size();i++){
                Reader_socket_one a = new Reader_socket_one(mas_socket.get(i));
                a.execute();
            }
        }
    }

    public class SocketServerThread extends Thread {
        Socket socket;
        static final int SocketServerPORT = 6660;
        int count = 0;
        String [] inetaddr = new String[10] ;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        info.setText("Чекаю на підплючення: "
                                + serverSocket.getLocalPort());
                    }
                });

                while (true) {
                    socket = serverSocket.accept();


                    //Додаю нового клієнта
                    mas_socket.add(new Player(socket));

                    //Отримую адресу
                    String temp = socket.getInetAddress().toString() ;

                    //Відкидаю порт
                    String []temp1 = temp.split(":");

                    //Перевыряю чи вже э подыбне підключення
                    Boolean f = false;
                    for(int i=0;i<count;i++){
                       if(inetaddr[i]!=null){
                           if(inetaddr[i].equals(temp)){
                               f=true;
                           }
                       }
                    }

                    //Якщо нема то запамятовую його
                    if(!f){
                        inetaddr[count] = temp1[0];
                        count++;
                        message += "#" + count + " Від " + socket.getInetAddress()
                                + ":" + socket.getPort() + "\n";
                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                msg.setText(message);
                            }
                        });
                        SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                                socket, count);
                        socketServerReplyThread.run();
                    }








                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        int cnt;

        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            String msgReply = "Привіт, ви #" + cnt+";";

            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);

               //

                //printStream.close();

                message += "Гравець №" + cnt + " підключений\n\n";

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg.setText(message);
                    }
                });

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Виникла помилка! " + e.toString() + "\n";
            }

            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    msg.setText(message);
                }
            });
        }

    }
    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "SiteLocalAddress: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }
}