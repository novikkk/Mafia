package com.example.abc.client_4_2;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Config_Windows extends Activity implements SeekBar.OnSeekBarChangeListener  {
    static LinkedList<Rols> setRols = new LinkedList<Rols>();
    public TextView textprogres;
    public TextView header;
    public SeekBar seekBar;
    final String ATRIBUTE_FOR_TEXT ="text";
    final String ATRIBUTE_FOR_SWITCH = "switch";
    final String ATRIBUTE_FOR_IMAGES = "images";
    boolean Komisar = false;
    boolean Doctor = false;
    public  Map<String,Object> temp;
    public ArrayList<Map<String,Object>> data;
    public SimpleAdapter my_simple_adapter;
    public int []to = {R.id.queues_item_switch,R.id.queues_item_text,R.id.queues_item_image};
    public  String[] text = {"Комісар","Доктор"};
    public boolean []switch_atribute = {true,false};
    public  int[] images_my = {R.drawable.cowboyicon,R.drawable.doctoricon };
    public  String[] from = {
            ATRIBUTE_FOR_SWITCH,
            ATRIBUTE_FOR_TEXT,
            ATRIBUTE_FOR_IMAGES
    };
    public ListView my_list_view;
    int coutn_player;
    public LinearLayout linerrols;

    public ImageView my_image_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config__windows);


        my_image_view = (ImageView) findViewById(R.id.imageView);



        linerrols = (LinearLayout) findViewById(R.id.linerrols);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        header = (TextView) findViewById(R.id.textView4);
        coutn_player=MainActivity.mas_socket.size();
        header.setText("Загальна кількість гравців: " + coutn_player);

        seekBar.setMax(coutn_player / 3 + 1);
        seekBar.setOnSeekBarChangeListener(this);
        textprogres = (TextView) findViewById(R.id.textView2);
        setRols.add(new Rols(Rol_name.Civilians));
        my_list_view = (ListView) findViewById(R.id.listView);
        data = new ArrayList<Map<String,Object>>(text.length);
        for(int i=0;i<text.length;i++){
            temp = new HashMap<String,Object>();
            temp.put(ATRIBUTE_FOR_SWITCH,switch_atribute[i]);
            temp.put(ATRIBUTE_FOR_TEXT,text[i]);
            temp.put(ATRIBUTE_FOR_IMAGES,images_my[i]);
            data.add(temp);
        }


        my_simple_adapter = new SimpleAdapter(this,data,R.layout.item,from,to);

        my_list_view.setAdapter(my_simple_adapter);

        linerrols.removeAllViews();


    }

    public void send_rols(View asd){
        if(setRols.size()==coutn_player){
            for(int i=0;i<MainActivity.mas_socket.size();i++) {
                Random random = new Random();
                if (MainActivity.mas_socket.get(i).socket_player != null) {
                    SocketServerStringThread nnnn = new SocketServerStringThread(MainActivity.mas_socket.get(i).socket_player, "Rol:"+setRols.remove(random.nextInt(setRols.size())).rols_player.definition+";");
                    nnnn.run();
                }
            }
        }
    }

    public void Generate_rols(View a){

        linerrols.removeAllViews();
        setRols.clear();
        for(int i=0;i<my_list_view.getChildCount();i++){
            LinearLayout temp1 = (LinearLayout)my_list_view.getChildAt(i);
            RelativeLayout temp2 = (RelativeLayout)temp1.getChildAt(0);
            Switch switch1 =(Switch) temp2.findViewById(R.id.queues_item_switch);
            TextView textView = (TextView)temp2.findViewById(R.id.queues_item_text);
            switch (textView.getText().toString()){
                case "Комісар":
                    if(switch1.isChecked()){
                        Komisar = true;
                    }
                    break;
                case "Доктор":
                    if(switch1.isChecked()){
                        Doctor = true;
                    }
                    break;

            }

        }

        int velue = seekBar.getProgress();
        for(int i=0;i<velue;i++) {
            if (i == 0) {
                setRols.add(0,new Rols(Rol_name.Don));
            }
            else
                setRols.add(0,new Rols(Rol_name.Mafia));
        }

        if(Komisar)
            setRols.add(0,new Rols(Rol_name.Sheriff));
        if(Doctor)
            setRols.add(0,new Rols(Rol_name.Doctor));

        for(int i=setRols.size();i<coutn_player;i++){
            setRols.add(0,new Rols(Rol_name.Civilians));
        }


        for(int i=setRols.size()-1;i>=0;i--) {

            ImageView neww = new ImageView(this);
            neww.setLayoutParams(my_image_view.getLayoutParams());
            Rol_name aaa = setRols.get(i).rols_player;
            switch (aaa) {
                case NullRols:
                    break;
                case Don:
                    neww.setImageResource(R.drawable.don);
                    break;
                case Mafia:
                    neww.setImageResource(R.drawable.mafia);
                    break;
                case Sheriff:
                    neww.setImageResource(R.drawable.detective);
                    break;
                case Civilians:
                    neww.setImageResource(R.drawable.citizen);
                    break;
                case Doctor:
                    neww.setImageResource(R.drawable.doctor);
                    break;
            }
            linerrols.addView(neww);

        }
    }

    public void asddddd(View a) throws IOException {
        for(int i=0;i<MainActivity.mas_socket.size();i++)
            if (MainActivity.mas_socket.get(i).socket_player != null) {
                SocketServerStringThread nnnn = new SocketServerStringThread(MainActivity.mas_socket.get(i).socket_player,"Rol:Don;");
                nnnn.run();
            }
    }

    /**
     * Notification that the progress level has changed. Clients can use the fromUser parameter
     * to distinguish user-initiated changes from those that occurred programmatically.
     *
     * @param seekBar  The SeekBar whose progress has changed
     * @param progress The current progress level. This will be in the range 0..max where max
     *                 was set by {@link ProgressBar#setMax(int)}. (The default value for max is 100.)
     * @param fromUser True if the progress change was initiated by the user.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String aaaaa = progress+"";
        textprogres.setText(aaaaa);


    }

    /**
     * Notification that the user has started a touch gesture. Clients may want to use this
     * to disable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     * Notification that the user has finished a touch gesture. Clients may want to use this
     * to re-enable advancing the seekbar.
     *
     * @param seekBar The SeekBar in which the touch gesture began
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
