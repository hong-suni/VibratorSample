package kr.co.njin.vibratorsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mPlayer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        Button b4 = (Button)findViewById(R.id.button4);
        Button b5 = (Button)findViewById(R.id.button5);
        Button b6 = (Button)findViewById(R.id.button6);

        mPlayer = new MediaPlayer();         // 객체생성
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);



        try {

            // 이렇게 URI 객체를 그대로 삽입해줘야한다.

            //인터넷에서 url.toString() 으로 하는것이 보이는데 해보니까 안된다 -_-;

            mPlayer.setDataSource(this, alert);



            // 출력방식(재생시 사용할 방식)을 설정한다. STREAM_RING 은 외장 스피커로,

            // STREAM_VOICE_CALL 은 전화-수신 스피커를 사용한다.

            mPlayer.setAudioStreamType(AudioManager.STREAM_RING);



            mPlayer.setLooping(true);  // 반복여부 지정



        } catch (IOException e) {

            e.printStackTrace();

        }

        //진동
        // 1. 진동 권한을 획득해야한다. AndroidManifest.xml
        // 2. Virator 객체를 얻어서 진동시킨다.
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        b1.setText("1초진동");
        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                vibrator.vibrate(1000);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[] pattern = {100,400,100,400,100,600,100,400,100,400};
                //           대기,진동,대기,진동,....
                // 짝수 인덱스 : 대기시간
                // 홀수 인덱스 : 진동시간
                vibrator.vibrate(pattern, // 진동 패턴을 배열로
                        3);     // 반복 인덱스
                // 0 : 무한반복, -1: 반복없음,
                // 양의정수 : 진동패턴배열의 해당 인덱스부터 진동 무한반복



            }
        });

        b3.setText("무한반복으로 진동");
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrator.vibrate(
                        new long[]{100,1000,100,500,100,500,100,1000}
                        , 0);
            }
        });

        b4.setText("진동 취소");
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vibrator.cancel(); // 진동취소
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                try {
                    mPlayer.prepare();    // 실행전 준비
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();   // 실행 시작

            }
        });

        b6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPlayer.stop();
               //mPlayer.release();
            }
        });
    }
}