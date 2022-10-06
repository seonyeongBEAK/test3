package com.adu.test3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test3.R;

public class InitialActivity extends AppCompatActivity {
    Button bluetoothOnButton;
    Button bluetoothListButton;
    Button test;
    // Debugging
    private static final String TAG = "TEST+ITactivity";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;


    @SuppressLint("StaticFieldLeak")
    public static BluetoothService btService = null;

    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);

            switch (message.what) {
                case MESSAGE_STATE_CHANGE:
                    Log.i(TAG, "MESSAGE_STATE_CHANGE" + message.arg1);
                    switch (message.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), "블루투스 연결에 성공했습니다.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case BluetoothService.STATE_FAIL:
                            Toast.makeText(getApplicationContext(), "블루투스 연결에 실패했습니다.", Toast.LENGTH_LONG).show();
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_initial);

//        bluetoothOnButton = (Button)findViewById(R.id.bluetoothOn);
//        bluetoothListButton = (Button) findViewById(R.id.bluetoothList);
//        test = (Button)findViewById(R.id.test);
        if (btService == null) {
            btService = new BluetoothService(this, handler);
        }
//        bluetoothOnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(btService.getDeviceState()) {
//                    // 블루투스가 지원 가능한 기기일 때
//                    btService.enableBluetooth();
//                } else {
//                    // finish();
//                }
//            }
//        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_SCAN,
                            Manifest.permission.BLUETOOTH_ADVERTISE,
                            Manifest.permission.BLUETOOTH_CONNECT
                    },
                    1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{
                            Manifest.permission.BLUETOOTH
                    },
                    1);
        }

        btService.scanDevice();

//        Button setting = (Button)findViewById(R.id.bt_setting);
//        setting.setOnClickListener(v -> {
////                Intent setting= new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
////                startActivity(setting);   //startActivityForResult() 는 호출한 Activity로 부터 결과를 받을 경우 사용.
//            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
//        });

//        bluetoothListButton.setOnClickListener(v -> btService.scanDevice());

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode != Activity.RESULT_OK) {
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    btService.getDeviceInfo(data);
                }
                break;
        }
    }
}
//