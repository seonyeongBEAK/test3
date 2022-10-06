package com.adu.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.test3.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    TextView mTvBluetoothStatus;
    TextView mTvReceiveData;
    EditText mTvSendData;
    TextView txt_compile;
    TextView send_list;
    TextView count;


    private final String TAG = "TEST+Main_activity";    // 디버깅을 위한 Log 태그

    public static final int MODE_REQUEST = 1 ;
    public static final int MESSAGE_WRITE = 2;
    private static final int STATE_SENDING = 1;
    private static final int STATE_NO_SENDING = 2;
    private static final int List = 3;

    private int mSendingState ;
    public static BluetoothService bluetoothServiceMain = null;
    StringBuffer mOutStringBuffer = new StringBuffer("");
    String[] sensor;

    ImageButton btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        bluetoothServiceMain = InitialActivity.btService;
        bluetoothServiceMain.set(this, handler);

        txt_compile = findViewById(R.id.txt_compile);
        send_list = findViewById(R.id.send_list);
//        count = findViewById(R.id.count);

        mTvBluetoothStatus = findViewById(R.id.tvBluetoothStatus);
        mTvReceiveData = findViewById(R.id.tvReceiveData);
        mTvSendData =  findViewById(R.id.tvSendData);
//        init();



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
                    mTvReceiveData.setText(sensor[0]);
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

    public  void Onsend(View view){
        Date day = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String toDay = date.format(day);

        ArrayList<String> list = new ArrayList<>();
        String sendData = mTvSendData.getText().toString();
//        if (sendtxt.getBytes().length <= 0) {//빈값이 넘어올때의 처리
        if (sendData.equals("")){
            mTvReceiveData.setText("Error: value is null");
            txt_compile.setText("업로드 에러");
        } else {
            sendMessage(sendData, MESSAGE_WRITE);
            txt_compile.setText("업로드 성공");
            list.add(toDay + " : " + sendData);



//                mTvReceiveData.setText("결과 데이터 없음");
//            send_list.setText(toDay + sendData + "\n");
//            for (int i = list.size(); i >= 0; i--) {
//                //            send_list.setText(toDay + sendData + "\n");
//
//            }

        }
//        for (int i = list.size(); i >= 0; i--) {
//            send_list.setText(list.get(i)+"\n");  //배열리스트 이용
//        }
//        count.setText(list.size());

        for (String test : list){
            System.out.println(test);
            send_list.setText(test);
        }
    }

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

    //컴파일 데이터 검사
    public  void onCompile(View view){
        String sendData = mTvSendData.getText().toString();

        if(sendData.equals("")){//빈값이 넘어올때의 처리
            mTvReceiveData.setText("Error: value is null");
            txt_compile.setText("컴파일 에러");
        }else {
            if (sendData.equals("for(pos=160; pos>=10; pos-=6)")){
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("for(pos=10; pos<=160; pos+=6)")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("myservo.write(0);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("myservo.write(45);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("myservo.write(90);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("myservo.write(135);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("myservo.write(180);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("digitalWrite(LG,150);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("digitalWrite(RG,150);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("digitalWrite(LB,150);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("digitalWrite(RB,150);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("BT.println(distance);")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("allcode")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else if (sendData.equals("clean")) {
                txt_compile.setText("컴파일 완료");
                mTvReceiveData.setText("true");
            }else {
                mTvReceiveData.setText("Error: value is false");
                txt_compile.setText("컴파일 에러");
                mTvSendData.setText(null);
            }
        }
    }
    public void onList(View view){
//        String output = myAdapter.toString();
//        send_list.setText(output);
//        list.clear();
//        send_list.setText("");
//        count.setText(list.size());
//        finish();//인텐트 종료
//        overridePendingTransition(0, 0);//인텐트 효과 없애기
//        Intent intent = getIntent(); //인텐트
//        startActivity(intent); //액티비티 열기
//        overridePendingTransition(0, 0);//인텐트 효과 없애기

    }

//    public void init() {
//        txt_compile = findViewById(R.id.txt_compile);
//        send_list = findViewById(R.id.send_list);
//        count = findViewById(R.id.count);
//
//        mTvBluetoothStatus = findViewById(R.id.tvBluetoothStatus);
//        mTvReceiveData = findViewById(R.id.tvReceiveData);
//        mTvSendData =  findViewById(R.id.tvSendData);
//    }


}