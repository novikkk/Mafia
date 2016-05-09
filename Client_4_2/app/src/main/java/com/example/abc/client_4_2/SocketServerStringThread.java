package com.example.abc.client_4_2;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Богдан on 18.12.2015.
 */
final public class  SocketServerStringThread extends Thread {

    public Socket hostThreadSocket;
    public String cnt;

    public SocketServerStringThread(Socket socket, String c) {
        hostThreadSocket = socket;
        cnt = c;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        String msgReply = cnt;

        try {

            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(msgReply);

            //printStream.close();
            // printStream.flush();*/

            //  outputStream =new DataOutputStream( hostThreadSocket.getOutputStream());

            //  outputStream.writeUTF(msgReply);
            //    byte[] endSequence = new byte[1];
            //  outputStream.writeByte(';');


/*
                message += "replayed: " + msgReply + "\n";

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg.setText(message);
                    }
                });*/

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //message += "Виникла помилка! " + e.toString() + "\n";
        }
/*
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    msg.setText(message);
                }
            });*/
    }

}
