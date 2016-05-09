package com.example.abc.client_4_2;

import java.net.Socket;

/**
 * Created by Богдан on 17.12.2015.
 */
public class Player {
    Rols primary_rol = new Rols(Rol_name.NullRols);
    public String nikname;
    public int count_fol = 0;
    public Socket socket_player;
    public Player(Socket my){
        socket_player = my;
    }
    public void setNikname(String nik){
        nikname = nik;
    }
    public void fol_add(){
        count_fol++;
    }
    public void fol_minus(){
        count_fol--;
    }
    public void addPrimaryPols(Rols a){
        primary_rol = a;
    }
}
