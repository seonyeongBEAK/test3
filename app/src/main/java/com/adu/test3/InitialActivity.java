package com.adu.test3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.test3.R;

public class InitialActivity extends AppCompatActivity {
    //    Button bluetoothOnButton;
    Button bluetoothListButton, goMain;
    Button test;
    // Debugging
    private static final String TAG = "TEST+ITactivity";

    // Intent request code
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    Dialog dialog;

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
        bluetoothListButton = (Button) findViewById(R.id.bluetoothList);
        goMain = (Button) findViewById(R.id.goMain);
//        test = (Button)findViewById(R.id.test);
        if (btService == null) {
            btService = new BluetoothService(this, handler);
        }


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            requestPermissions(
//                    new String[]{
//                            Manifest.permission.BLUETOOTH,
//                            Manifest.permission.BLUETOOTH_SCAN,
//                            Manifest.permission.BLUETOOTH_ADVERTISE,
//                            Manifest.permission.BLUETOOTH_CONNECT,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                    },
//                    1);
//        } else {
//            requestPermissions(
//                    new String[]{
//                            Manifest.permission.BLUETOOTH,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION
//                    },
//                    1);
//        }
//        final LinearLayout layout = (LinearLayout)View.inflate(getApplicationContext(),R.layout.permisson_dialog,null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(InitialActivity.this);
        dialog.setTitle("위치정보에 대한 액세스가 필요합니다.").setMessage("아두정복 앱이 블루투스를 감지할 수 있도록 위치 정보 액세스 권한을 허용해주세요.")
                .setIcon(R.drawable.playstore)
//                .setView(layout)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            requestPermissions(
                                    new String[]{
                                            Manifest.permission.BLUETOOTH,
                                            Manifest.permission.BLUETOOTH_SCAN,
                                            Manifest.permission.BLUETOOTH_ADVERTISE,
                                            Manifest.permission.BLUETOOTH_CONNECT,
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                    },
                                    PERMISSION_REQUEST_COARSE_LOCATION);
                        } else {
                            requestPermissions(
                                    new String[]{
                                            Manifest.permission.BLUETOOTH,
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                    },
                                    PERMISSION_REQUEST_COARSE_LOCATION);
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"위치 정보 및 액세스 권한이 허용되지 않으면 블루투스 검색 및 연결할 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();




//        btService.scanDevice();


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

//        Button setting = (Button)findViewById(R.id.bt_setting);
//        setting.setOnClickListener(v -> {
////                Intent setting= new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
////                startActivity(setting);   //startActivityForResult() 는 호출한 Activity로 부터 결과를 받을 경우 사용.
//            startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
//        });

        bluetoothListButton.setOnClickListener(v -> btService.scanDevice());
        goMain.setOnClickListener(v -> startActivity(new Intent(InitialActivity.this, MainActivity.class)));

//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                if (resultCode == PackageManager.PERMISSION_GRANTED) {
                    Log.d("디버깅", "대략적인 위치 권한이 부여됨");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 제한");
                    builder.setMessage("위치 정보 및 액세스 권한이 허용되지 않아서 블루투스 검색 및 연결할 수 없습니다");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });
                    builder.show();
                }
                break;
        }
    }
}
//