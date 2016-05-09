package com.example.abc.client_4_2;

/**
 * Created by Богдан on 17.12.2015.
 */
enum Position{
    Bleak,
    White,
    NullStorona
}

enum Rol_name{
    NullRols("Порожня роль",Position.NullStorona),
    Don("Don",Position.Bleak),
    Mafia("Mafia",Position.Bleak),
    Sheriff("Sheriff",Position.White),
    Civilians("Civilians",Position.White),
    Doctor("Doctor",Position.White);
    Position position;
    String definition;
    Rol_name(String s,Position a) {
        position = a;
        definition = s;
    }
}
public class Rols {
    Rol_name rols_player = Rol_name.NullRols;
    public Rols(Rol_name a){
        rols_player = a;
    }
}
