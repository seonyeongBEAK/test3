package com.adu.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test3.R;
//
//public class MainActivity extends  Activity implements View.OnClickListener {
//    // Debugging
//    private static final String TAG = "Main";
//
//    // Intent request code 인텐트 요청 코드
//    private static final int REQUEST_CONNECT_DEVICE = 1;
//    private static final int REQUEST_ENABLE_BT = 2;
//
//    // Layout
//    private Button btn_Connect, btn_Send;
//    private TextView txt_Result;
//    private EditText editText;
//
//    private BluetoothService btService = null;
//
//
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.e(TAG, "onCreate");
//        setContentView(R.layout.activity_main);
//
//        //
//        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
//
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
//        //
//
//        /** Main Layout **/
//        btn_Connect = (Button) findViewById(R.id.btn_connect);
//        btn_Send = (Button) findViewById(R.id.btn_send);
//        txt_Result = (TextView) findViewById(R.id.txt_result);
//        editText = (EditText)findViewById(R.id.edit);
//
//        btn_Send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String editData = editText.getText().toString();
//                btService.write(editData.getBytes());
//                Toast.makeText(getApplicationContext(), editData, Toast.LENGTH_SHORT).show();
//            }
//        });
//        btn_Connect.setOnClickListener(this);
//
//        // BluetoothService Ŭ���� ����
//        if(btService == null) {
//            btService = new BluetoothService(this, mHandler);
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(TAG, "onActivityResult " + resultCode);
//
//        switch (requestCode) {
//            case REQUEST_CONNECT_DEVICE:
//                // DeviceListActivity가 연결할 장치와 함께 반환될 때
//                if (resultCode == Activity.RESULT_OK) {
//                    btService.getDeviceInfo(data);
//                    // getDeviceInfo()메소드는 그 정보를 이용하여 블루투스연결을 시도
//                }
//                break;
//            case REQUEST_ENABLE_BT:
//                // 블루투스 활성화 요청이 반환되면
//                if (resultCode == Activity.RESULT_OK) {// 확인 눌렀을 때
//                    // Next Step
//                    btService.scanDevice();
//                } else { // 취소 눌렀을 때
//                    Log.d(TAG, "Bluetooth is not enabled");
//                    Toast.makeText(this.getApplicationContext(),"블루투스가 활성화되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        if(btService.getDeviceState()) {
//            // 블루투스가 지원 가능한 기기일 때
//            btService.enableBluetooth();
//        } else {
//            finish();
//        }
//    }
//}

public class MainActivity extends AppCompatActivity {

    //숨겨진 페이지가 열렸는지 확인하는 변수
    boolean isPageOpen = false;
    LinearLayout page;
    FrameLayout main_logo;
    ImageButton instagram, youtube, btn_shop;
    Animation fadeIn;
    Animation fadeOut;

    Button btn_edit, btn_led, btn_write, btn_car, button_visible;
    //    private Button btn_Connect, btn_led, btn_Send;
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
        setContentView(R.layout.activity_main);
//        bluetoothServiceMain = InitialActivity.btService;
//        bluetoothServiceMain.set(this, handler);

//        btn_shop = findViewById(R.id.btn_shop);
//        btn_shop.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xn--hu1bz7nj6gp2c.com/"))));
        instagram = findViewById(R.id.instagram);
        instagram.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/a_du_jung_bok/?hl=ko"))));
        youtube = findViewById(R.id.youtube);
        youtube.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtube.com/channel/UCIK0pD42mcReMDo5HZLJgtA"))));


//        btn_edit = findViewById(R.id.btn_edit);
//        btn_edit.setOnClickListener(v -> {
//            startActivity(new Intent(MainActivity.this, TestActivity.class));
//        });
        btn_led = findViewById(R.id.btn_led);
        btn_led.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EditActivity.class));
        });
        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WriteActivity.class));
        });
        btn_car = findViewById(R.id.btn_car);
        btn_car.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CarActivity.class));
        });

        page = findViewById(R.id.page);
        main_logo = findViewById(R.id.main_logo);

       // anim 폴더의 애니메이션을 가져와서 준비
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);

        //페이지 슬라이딩 이벤트가 발생했을때 애니메이션이 시작 됐는지 종료 됐는지 감지할 수 있다.
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();

        fadeIn.setAnimationListener(animListener);
        fadeOut.setAnimationListener(animListener);

        button_visible = findViewById(R.id.button_visible);
        button_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPageOpen){
                    page.startAnimation(fadeIn);
                }else{
                    main_logo.setVisibility(View.INVISIBLE);
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(fadeOut);
                }
            }
        });

//        init();
    }


    // 블루투스 사용

    // 메세지를 받는 핸들러

//    private final Handler handler = new Handler() {
//        public void handleMessage(Message message) {
//            super.handleMessage(message);
//            Log.d(TAG, "handle");
//            switch (message.what) {
//                case MESSAGE_WRITE:
//                    String tmp = message.obj.toString();
//                        sensor = tmp.split(",");
//                    txt_Result.setText(sensor[0]);
////            int readBufferPosition = 0;
////                    if(message.what == MESSAGE_WRITE){
////                        String readMessage = null;
////                        try {
////                            readMessage = new String((byte[]) message.obj, "UTF-8");
//////                            byte[] encodedBytes = new byte[readBufferPosition];
//////                            readMessage = new String(encodedBytes, "UTF-8");
//////                            readBufferPosition = 0;
////                        } catch (UnsupportedEncodingException e) {
////                            e.printStackTrace();
////                            txt_Result.setText(readMessage);
//
////                        }
//            }
//        }
//    };
//
//    // 블루투스 통신을 이용해 메세지를 보내는 함수
//    private synchronized void sendMessage( String message, int mode ) {
//        if ( mSendingState == STATE_SENDING ) {
//            try {
//                wait() ;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        mSendingState = STATE_SENDING ;
//        if ( bluetoothServiceMain.getState() != BluetoothService.STATE_CONNECTED ) {
//            mSendingState = STATE_NO_SENDING ;
//            return ;
//        }
//        if ( message.length() > 0 ) {
//            byte[] send = message.getBytes() ;
////            bluetoothServiceMain.write(send, mode) ;
//            bluetoothServiceMain.write(message);
//            mOutStringBuffer.setLength(0) ;
//            Log.d(TAG,"send");
//        }
//        mSendingState = STATE_NO_SENDING ;
////        notify() ;
//    }
//
//    public  void  send(View view){
//        String sendData = editText.getText().toString();
//        sendMessage(sendData,MESSAGE_WRITE);
//    }
//
//    public void init() {
////        btn_Connect = (Button) findViewById(R.id.btn_connect);
////        btn_Send = (Button) findViewById(R.id.btn_send);
//        txt_Result = (TextView) findViewById(R.id.txt_result);
//        editText = (EditText)findViewById(R.id.edit);
//
//
//
//    }
    //페이지 열고 닫음
    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation){
            if(isPageOpen){
                page.setVisibility(View.INVISIBLE);
                main_logo.setVisibility(View.VISIBLE);
                button_visible.setText("OPEN");
                isPageOpen = false;
            }else{
                button_visible.setText("CLOSE");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}