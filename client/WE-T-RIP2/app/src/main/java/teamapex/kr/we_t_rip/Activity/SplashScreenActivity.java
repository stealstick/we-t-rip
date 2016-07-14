package teamapex.kr.we_t_rip.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import teamapex.kr.we_t_rip.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        progressDialog = ProgressDialog.show(this, "잠시만 기려주세요", "로딩중입니다...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
