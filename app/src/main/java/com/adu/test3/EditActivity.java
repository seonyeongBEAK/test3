package com.adu.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.test3.R;

public class EditActivity extends AppCompatActivity {
    Context context;

    private Button btn_Connect, btn_Send;
    private TextView txt_Result;
    private EditText editText;
    // View 객체

    private final String TAG = "TEST+Main_activity";    // 디버깅을 위한 Log 태그

    public static final int MODE_REQUEST = 1 ;
    public static final int MESSAGE_WRITE = 2;
    private static final int STATE_SENDING = 1;
    private static final int STATE_NO_SENDING = 2;

    private int mSendingState ;
    public static BluetoothService bluetoothServiceMain = null;
    StringBuffer mOutStringBuffer = new StringBuffer("");
    String[] sensor;
    Switch led_swt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        bluetoothServiceMain = InitialActivity.btService;
//        bluetoothServiceMain.set(this, handler);

        led_swt = findViewById(R.id.led_swt);
        led_swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // switchButton이 체크된 경우
                    sendMessage("on",MESSAGE_WRITE);
                } else {
                    // switchButton이 체크되지 않은 경우
                    sendMessage("off",MESSAGE_WRITE);
                }
            }
        });

    }
//    private final Handler handler = new Handler() {
//        public void handleMessage(Message message) {
//            super.handleMessage(message);
//            Log.d(TAG, "handle");
//            switch (message.what) {
//                case MESSAGE_WRITE:
//                    String tmp = message.obj.toString();
//                    sensor = tmp.split(",");
//                    txt_Result.setText(sensor[0]);
//            }
//        }
//    };

    // 블루투스 통신을 이용해 메세지를 보내는 함수
    private synchronized void sendMessage( String message, int mode ) {
        if ( mSendingState == STATE_SENDING ) {
            try {
                wait() ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mSendingState = STATE_SENDING ;
        if ( bluetoothServiceMain.getState() != BluetoothService.STATE_CONNECTED ) {
            mSendingState = STATE_NO_SENDING ;
            return ;
        }
        if ( message.length() > 0 ) {
            byte[] send = message.getBytes() ;
//            bluetoothServiceMain.write(send, mode) ;
            bluetoothServiceMain.write(message);
            mOutStringBuffer.setLength(0) ;
            Log.d(TAG,"send");
        }
        mSendingState = STATE_NO_SENDING ;
//        notify() ;
    }


}