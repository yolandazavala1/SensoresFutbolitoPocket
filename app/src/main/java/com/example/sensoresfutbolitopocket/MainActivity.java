package com.example.sensoresfutbolitopocket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;

    ImageView mDrawable;
    public static int x = 0;
    public static int y = 0;
    TextView txtX;
    TextView txtY ;
    TextView txtGolArriba ;
    TextView txtGolAbajo ;
    int contadorGolesA=0;
    int contadorGolesB=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        txtX = (TextView) findViewById(R.id.txtX);
        txtY= (TextView)findViewById(R.id.txtY);
        txtGolAbajo= (TextView)findViewById(R.id.txtGolAbajo);
        txtGolArriba= (TextView)findViewById(R.id.txtGolArriba);

        txtX.setTextColor(Color.WHITE);
        txtY.setTextColor(Color.WHITE);
        txtGolAbajo.setTextColor(Color.YELLOW);
        txtGolArriba.setTextColor(Color.YELLOW);

        mDrawable = (ImageView) findViewById(R.id.balon);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        x=460;
        y=684;
        mDrawable.setX(x);
        mDrawable.setY(y);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    int xCambio=0;
    public static int xMasCambio=0;
    int yCambio=0;
    public static int yMasCambio=0;
    boolean masGolesA=false;
    boolean masGolesB=false;
    int velocidad=4;
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xCambio=((int) event.values[0])*velocidad;
            yCambio=((int) event.values[1])*velocidad;

            xMasCambio =x-xCambio;
            yMasCambio =y+ yCambio;

            if(xMasCambio>=0 && xMasCambio<=880){//para modificar x si esta dentro del rango
                x-=xCambio;
                txtX.setText("X: "+ x);
                mDrawable.setX(x);
            }

            if(yMasCambio>=0 && yMasCambio<=1355){//para modificar y si esta dentro del rango
                y+=yCambio;
                txtY.setText("Y: "+ y);
                mDrawable.setY(y);
            }
            if (yMasCambio<=0 && (xMasCambio>410 && xMasCambio<512) && masGolesA==true){//para marcar gol arriba
                masGolesA=false;
                contadorGolesA++;
                txtGolArriba.setText("Goles: "+ contadorGolesA);
            }
            if (yMasCambio>96){//para resetear el poder meter gol arriba
                masGolesA=true;
            }

            if (yMasCambio>=1350 && (xMasCambio>410 && xMasCambio<512) && masGolesB==true){//para marcar gol abajo
                masGolesB=false;
                contadorGolesB++;
                txtGolAbajo.setText("Goles: "+ contadorGolesB);
            }
            if (yMasCambio<1273){//para resetear el poder meter gol abajo
                masGolesB=true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
