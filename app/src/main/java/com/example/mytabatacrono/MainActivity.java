package com.example.mytabatacrono;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//variables globales para facil acceso
    private TextView series;
    private TextView workT;
    private TextView restT;
    private TextView timer;
    private TextView actual;
    private ConstraintLayout background;
    private TextView state;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variables locales y asignacion de valores para las variables globales
        Button btn = findViewById(R.id.btn);
        series = findViewById(R.id.series);
        workT = findViewById(R.id.work);
        restT = findViewById(R.id.rest);
        timer = findViewById(R.id.timer);
        actual = findViewById(R.id.actual_series);
        background = findViewById(R.id.constraintLayout);
        state = findViewById(R.id.state);


        //Listener que desencadena el inicio del tabata con una cuenta atras desde 3
        btn.setOnClickListener(start ->{
            num = Integer.parseInt(series.getText().toString());
            actual.setText("Series restantes: " + num);
            state.setText("Ready?");
            //cuenta atras desde 3
            new CountDownTimer(4000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setText(""+(millisUntilFinished / 1000));
                }
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.gong);
                    mp.start();
                    work(Integer.parseInt(workT.getText().toString()), background, timer, state);
                }
            }.start();
        });




    }

    //Metodo para Correr el tabata en periodo de trabajo (parametros introducido en work)
    public void work(int work, ConstraintLayout background, TextView timer, TextView state) {
        background.setBackground(getDrawable(R.drawable.d_verde));
        state.setText("Work");
        timer.setText(""+work);
        //cuenta atras del periodo de trabajo
        new CountDownTimer(work*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(""+(millisUntilFinished / 1000));
            }
            public void onFinish() {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.gong);
                mp.start();
                rest(Integer.parseInt(restT.getText().toString()), background, timer, state);
            }
        }.start();

    }

    //Metodo para Correr el tabata en periodo de descanso (parametros introducido en rest)
    public void rest(int rest, ConstraintLayout background, TextView timer ,TextView state) {
        background.setBackground(getDrawable(R.drawable.d_rojo));
        state.setText("Rest");
        timer.setText(""+rest);
        //cuenta atras del periodo de descanso
        new CountDownTimer(rest*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(""+millisUntilFinished / 1000);
            }
            public void onFinish() {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.gong);
                mp.start();
                num--;
                if(num != 0){
                    work(Integer.parseInt(workT.getText().toString()), background, timer, state);
                    actual.setText("Series restantes: " + num);
                }else{
                    actual.setText("Series restantes: 0");
                    timer.setText("END");
                }
            }

        }.start();
    }
}