package com.mbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class GamePadActivity extends AppCompatActivity implements View.OnClickListener {


    //-----------------bluetooth------------
    private final String DEVICE_ADDRESS = "00:21:13:01:A8:02";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket = null;
    private OutputStream outputStream;
    private InputStream inputStream;

    BluetoothAdapter myBluetooth = null;

    Button mConnectBtn;
    TextView textView;
    EditText editText;
    boolean deviceConnected = false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    private ProgressDialog progress;
    //------bluetooth end------------

    private Button left, right, up, down;
    private ImageView light1, light2, light3, light4;
    private ImageView sound1, sound2, sound3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_pad);

        init();


        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate();

                onConnect();
                if (deviceConnected) {


                    mConnectBtn.setText("Connected");
                }
            }
        });


    }


    private void init() {
        left = findViewById(R.id.left_btn);
        right = findViewById(R.id.right_btn);
        up = findViewById(R.id.up_btn);
        down = findViewById(R.id.down_btn);

        mConnectBtn = findViewById(R.id.connect);


        light1 = findViewById(R.id.light1);
        light2 = findViewById(R.id.light2);
        light3 = findViewById(R.id.light3);
        light4 = findViewById(R.id.light4);

        sound1 = findViewById(R.id.sound1);
        sound2 = findViewById(R.id.sound2);
        sound3 = findViewById(R.id.sound3);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);

        light1.setOnClickListener(this);
        light2.setOnClickListener(this);
        light3.setOnClickListener(this);
        light4.setOnClickListener(this);

        sound1.setOnClickListener(this);
        sound2.setOnClickListener(this);
        sound3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        //leff,right,up,down Button works
        if (id == R.id.left_btn) {
            vibrate();

            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            } else {

                String lockClose = "L";
                try {
                    outputStream.write(lockClose.getBytes());
                    Log.i("BLU", "LOCK -- send value:" + lockClose);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (id == R.id.right_btn) {
            vibrate();

            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            } else {

                String lockClose = "R";
                try {
                    outputStream.write(lockClose.getBytes());
                    Log.i("BLU", "LOCK -- send value:" + lockClose);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (id == R.id.up_btn) {
            vibrate();

            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            } else {

                String lockClose = "U";
                try {
                    outputStream.write(lockClose.getBytes());
                    Log.i("BLU", "LOCK -- send value:" + lockClose);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (id == R.id.down_btn) {
            vibrate();

            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            } else {

                String lockClose = "D";
                try {
                    outputStream.write(lockClose.getBytes());
                    Log.i("BLU", "LOCK -- send value:" + lockClose);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        //light works
        else if (id==R.id.light1){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }else if (id==R.id.light2){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }else if (id==R.id.light3){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }else if (id==R.id.light4){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }

        //Sounds works
        else if (id==R.id.sound1){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }else if (id==R.id.sound2){
            vibrate();

            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }
        }else if (id==R.id.sound3){
            vibrate();
            if (!deviceConnected) {
                Toast.makeText(getApplicationContext(), "Not Connected!", Toast.LENGTH_SHORT).show();
                return;

            }

        }



    }


    //-------------------------bluetooth work--------------------
    public boolean BTinit() {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }


            }
        }

        return found;
    }

    public boolean BTconnect() {
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if (connected) {
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return connected;
    }

    public void onConnect() {
        if (BTinit()) {
            if (BTconnect()) {

                deviceConnected = true;
                beginListenForData();
                Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
//                mConnectBtn.setVisibility(View.GONE);
//                mLock.setVisibility(View.VISIBLE);
//                mUnlock.setVisibility(View.GONE);
            } else {

                Toast.makeText(this, "Not Connected!Try Again!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopThread) {
                    try {
                        int byteCount = inputStream.available();
                        if (byteCount > 0) {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string = new String(rawBytes, "UTF-8");
                            handler.post(new Runnable() {
                                public void run() {
                                    textView.append(string);
                                }
                            });

                        }
                    } catch (IOException ex) {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }

    public void onClickStop() throws IOException {
        stopThread = true;
        outputStream.close();
        inputStream.close();
        socket.close();
        deviceConnected = false;
//        textView.append("\nConnection Closed!\n");
    }

    //--------------------------------bluetooth method finished----------------

    void vibrate(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(60);
    }
}
