package com.adu.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test3.R;

public class TestActivity extends AppCompatActivity {
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
    int dust;
    //    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        bluetoothServiceMain = InitialActivity.btService;
        bluetoothServiceMain.set(this, handler);

        init();
    }

    // 메세지를 받는 핸들러
    private final Handler handler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.d(TAG, "handle");
            switch (message.what) {
                case MESSAGE_WRITE:
                    String tmp = message.obj.toString();
                    sensor = tmp.split(",");
                    txt_Result.setText(sensor[0]);
//            int readBufferPosition = 0;
//                    if(message.what == MESSAGE_WRITE){
//                        String readMessage = null;
//                        try {
//                            readMessage = new String((byte[]) message.obj, "UTF-8");
////                            byte[] encodedBytes = new byte[readBufferPosition];
////                            readMessage = new String(encodedBytes, "UTF-8");
////                            readBufferPosition = 0;
//                        } catch (UnsupportedEncodingException e) {
//                            e.printStackTrace();
//                            txt_Result.setText(readMessage);

//                        }
            }
        }
    };

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

    public  void  send(View view){
        String sendData = editText.getText().toString();
        sendMessage(sendData,MESSAGE_WRITE);
    }

    public void init() {
//        btn_Connect = (Button) findViewById(R.id.btn_connect);
        btn_Send = (Button) findViewById(R.id.btn_send);
        txt_Result = (TextView) findViewById(R.id.txt_result);
        editText = (EditText)findViewById(R.id.edit);
    }

}