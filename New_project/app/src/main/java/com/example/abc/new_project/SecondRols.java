package com.example.abc.new_project;

/**
 * Created by Богдан on 17.12.2015.
 */
enum time{
    Police_Round,
    Throughout_the_game,
    Beginning_of_the_game
}

enum Second_rols_name{
    Neutral_card("Ця карта ні на що не впливає. Не відкривайте її до кінця гри.",time.Throughout_the_game),
    Spy("Використовуй цю карту в кінці раунду Поліції і подивися на карту одного з гравців - дізнайся, хто він, - мафіозі або поліцейський.",time.Police_Round),
    The_vest("Якщо тебе вб'ють , не відкриєш свою карту і не показуй , хто ти. Просто покажи цю карту і залишайся в грі. Карту можна використовувати тільки один раз.",time.Throughout_the_game),
    Ringleader("Покажи цю карту всім на початку гри. Якщо під час голосування проти когось буде рівну кількість голосів - твій голос буде вирішальним.",time.Beginning_of_the_game),
    Unexpected_shot("Покажи цю карту і вкажи на того, кого ти хочеш вбити . Гравець , на якого ти вказав , відкриває свою карту і виходить з гри.",time.Police_Round),
    Help("Покажи цю карту, коли когось із членів твоєї команди вб'ють , і спаси його від смерті . Карту можна використовувати тільки один раз.",time.Police_Round),
    The_last_shoot("Якщо під час гри тебе вбили, ти маєш право останнього пострілу - можеш убити кого-небудь з гравців. Він теж вважається вбитим і виходить з гри.",time.Throughout_the_game),
    Changing_roles("Якщо ти отримав цю карту, можеш помінятися ролями з будь-яким гравцем ( помінятися картами) , але не знаючи, хто він.",time.Beginning_of_the_game);

    String defin;
    time when;

    Second_rols_name(String s, time when_1) {
        defin = s;
        when = when_1;
    }
}

public class SecondRols {
    Position position = Position.NullStorona;
    Second_rols_name secondRols = Second_rols_name.Neutral_card;
    public SecondRols(Second_rols_name a){
        secondRols = a;
    }
}
