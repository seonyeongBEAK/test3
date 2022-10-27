package com.adu.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.test3.R;


public class CarActivity extends AppCompatActivity {
    Handler mHandler;

    ImageButton  btn_go, btn_back, btn_left, btn_right;
    Button btn_Z, btn_X, trafficOff;
    TextView control, toast, stop_toast, white;
    private final String TAG = "TEST+Main_activity";    // 디버깅을 위한 Log 태그

    public static final int MODE_REQUEST = 1 ;
    public static final int MESSAGE_WRITE = 2;
    private static final int STATE_SENDING = 1;
    private static final int STATE_NO_SENDING = 2;

    private int mSendingState ;
    public static BluetoothService bluetoothServiceMain = null;
    StringBuffer mOutStringBuffer = new StringBuffer("");
    String[] sensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        bluetoothServiceMain = InitialActivity.btService;

        control = findViewById(R.id.control);
        toast = findViewById(R.id.toast);
        stop_toast = findViewById(R.id.stop_toast);
        white = findViewById(R.id.white);
        white.setVisibility(View.VISIBLE);
        toast.setVisibility(View.INVISIBLE);
        stop_toast.setVisibility(View.INVISIBLE);
        trafficOff = findViewById(R.id.trafficOff);
        trafficOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                white.setVisibility(View.VISIBLE);
                toast.setVisibility(View.INVISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_left = findViewById(R.id.btn_left);
        btn_right =  findViewById(R.id.btn_right);
        btn_Z = findViewById(R.id.btn_Z);
        btn_X = findViewById(R.id.btn_X);
        btn_go = findViewById(R.id.btn_go);

        btn_go.setOnTouchListener(new TouchListener());
        btn_back.setOnTouchListener(new TouchListener());
        btn_left.setOnTouchListener(new TouchListener());
        btn_right.setOnTouchListener(new TouchListener());
        btn_X.setOnTouchListener(new TouchListener());
        btn_Z.setOnTouchListener(new TouchListener());

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
    public class TouchListener implements View.OnTouchListener {
        private Handler mHandler;

        @Override public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mHandler != null) return true;
                    mHandler = new Handler();
                    if (v.getId() == R.id.btn_go) {
                        mHandler.postDelayed(goAction, 100);
                    }  else if (v.getId() == R.id.btn_back) {
                        mHandler.postDelayed(backAction, 100);
                    } else if (v.getId() == R.id.btn_left) {
                        mHandler.postDelayed(leftAction, 100);
                    } else if (v.getId() == R.id.btn_right) {
                        mHandler.postDelayed(rightAction, 100);
                    } else if (v.getId() == R.id.btn_X) {
                        mHandler.postDelayed(xAction, 100);
                    } else if (v.getId() == R.id.btn_Z) {
                        mHandler.postDelayed(zAction, 100);
                    }break;
                case MotionEvent.ACTION_UP:
                    if (mHandler == null) return true;
                    if (v.getId() == R.id.btn_go) {
                        mHandler.removeCallbacks(goAction);
                        mHandler.post(stopAction);
                        stop_toast.setVisibility(View.VISIBLE);
                        toast.setVisibility(View.INVISIBLE);
                    }else if (v.getId() == R.id.btn_back) {
                        mHandler.removeCallbacks(backAction);
                        mHandler.post(stopAction);
                    }else if (v.getId() == R.id.btn_left) {
                        mHandler.removeCallbacks(leftAction);
                        mHandler.post(stopAction);
                    }else if (v.getId() == R.id.btn_right) {
                        mHandler.removeCallbacks(rightAction);
                        mHandler.post(stopAction);
                    }else if (v.getId() == R.id.btn_X) {
                        mHandler.removeCallbacks(xAction);
                        mHandler.post(stopAction);
                    }else if (v.getId() == R.id.btn_Z) {
                        mHandler.removeCallbacks(zAction);
                        mHandler.post(stopAction);
                    }
                    mHandler = null;
                    break;
            }
            return false;
        }
        Runnable stopAction = new Runnable() {
            @Override public void run() {
                stop_toast.setVisibility(View.VISIBLE);
                toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                stop_toast.setText("STOP");
                sendMessage("s",MESSAGE_WRITE);
//                ClickHandlerStop();
            }
        };
        Runnable goAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("GOING");
                sendMessage("g",MESSAGE_WRITE);
//                ClickHandlerGo();
            }
        };
        Runnable backAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("BACK");
                sendMessage("k",MESSAGE_WRITE);
//                ClickHandlerBack();
            }
        };
        Runnable leftAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("LEFT");
                sendMessage("l",MESSAGE_WRITE);
//                ClickHandlerLeft();
            }
        };
        Runnable rightAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
//                ClickHandlerRight();
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("RIGHT");
                sendMessage("r",MESSAGE_WRITE);
            }
        };
        Runnable xAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("X");
                sendMessage("x",MESSAGE_WRITE);
//                ClickHandlerX();
            }
        };
        Runnable zAction = new Runnable() {
            @Override public void run() {
                mHandler.postDelayed(this, 100);
                toast.setVisibility(View.VISIBLE);
                stop_toast.setVisibility(View.INVISIBLE);
                white.setVisibility(View.INVISIBLE);
                toast.setText("Z");
                sendMessage("z",MESSAGE_WRITE);
//                ClickHandlerZ();
            }
        };
    }

    private void ClickHandlerStop() {
//// Toast 객체 생성
//        Toast toast = Toast.makeText(this, "  STOP  ", Toast.LENGTH_SHORT);
//// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
//        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
//        View toastView = toast.getView();
//            toastView.setBackgroundResource(R.drawable.stop_toast_background);
//        // Toast 보여주기
//        toast.show();
//        sendMessage("s",MESSAGE_WRITE);

//        토스트 모양을 정의하고 있는 레이아웃을 인플레이션 한다.
        // 기존 토스트 모양을 작성한 toastborder.xml로 인플레이션 한다.
//         LayoutInflater inflater = getLayoutInflater();
//         View layout = inflater.inflate(R.layout.layout_stop_toast,
//         (ViewGroup)findViewById(R.id.toast_layout_root));
//        // 레이아웃의 텍스트뷰에 보여줄 문자열을 설정한다.
//         TextView stop = (TextView)layout.findViewById(R.id.stop);
//         stop.setText("STOP");
//        // 토스트 객체 만들기
//        Toast toast = Toast.makeText(this, " STOP ", Toast.LENGTH_SHORT);
////         Toast toast = new Toast(MainActivity.this);
//        // 토스트가 출력될 위치를 지정
//        // x,y 좌표는 CENTER 의 위치에서 시작되는 좌표임
//         toast.setGravity(Gravity.CENTER, 0, -670);
//        // 토스트에 뷰를 설정
//         toast.setView(layout);
//        // 토스트 부여주기
//         toast.show();
//        sendMessage("s",MESSAGE_WRITE);
    }
    private void ClickHandlerGo() {
//// Toast 객체 생성
//        Toast toast = Toast.makeText(this, " GOING ", Toast.LENGTH_SHORT);
//// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
//        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
//        View toastView = toast.getView();
//            toastView.setBackgroundResource(R.drawable.toast_background);
//
//        // Toast 보여주기
//        toast.show();

//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.layout_toast,
//                (ViewGroup)findViewById(R.id.toast_layout_root));
//        // 레이아웃의 텍스트뷰에 보여줄 문자열을 설정한다.
//        TextView stop = (TextView)layout.findViewById(R.id.toast);
//        stop.setText("GOING");
//        // 토스트 객체 만들기
//        Toast toast = Toast.makeText(this, " GOING ", Toast.LENGTH_SHORT);
////         Toast toast = new Toast(MainActivity.this);
//        // 토스트가 출력될 위치를 지정
//        // x,y 좌표는 CENTER 의 위치에서 시작되는 좌표임
//        toast.setGravity(Gravity.CENTER, 0, -670);
//        // 토스트에 뷰를 설정
//        toast.setView(layout);
//        // 토스트 부여주기
//        toast.show();
//        sendMessage("g",MESSAGE_WRITE);
    }
    private void ClickHandlerBack() {
// Toast 객체 생성
        Toast toast = Toast.makeText(this, " BACK ", Toast.LENGTH_SHORT);
// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast_background);
        // Toast 보여주기
        toast.show();
        sendMessage("k",MESSAGE_WRITE);

    }
    private void ClickHandlerLeft() {
// Toast 객체 생성
        Toast toast = Toast.makeText(this, "   LEFT   ", Toast.LENGTH_SHORT);
// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast_background);
        // Toast 보여주기
        toast.show();
        sendMessage("l",MESSAGE_WRITE);

    }
    private void ClickHandlerRight() {
// Toast 객체 생성
        Toast toast = Toast.makeText(this, " RIGHT ", Toast.LENGTH_SHORT);
// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast_background);
        // Toast 보여주기
        toast.show();
        sendMessage("r",MESSAGE_WRITE);

    }
    private void ClickHandlerX() {
// Toast 객체 생성
        Toast toast = Toast.makeText(this, "      X       ", Toast.LENGTH_SHORT);
// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast_background);
        // Toast 보여주기
        toast.show();
        sendMessage("x",MESSAGE_WRITE);

    }
    private void ClickHandlerZ() {
// Toast 객체 생성
        Toast toast = Toast.makeText(this, "      Z      ", Toast.LENGTH_SHORT);
// 위치설정, Gravity - 기준지정(상단,왼쪽 기준 0,0) / xOffset, yOffset - Gravity기준으로 위치 설정
        toast.setGravity( Gravity.CENTER_HORIZONTAL,0 ,-10);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.toast_background);
        // Toast 보여주기
        toast.show();
        sendMessage("z",MESSAGE_WRITE);

    }
}