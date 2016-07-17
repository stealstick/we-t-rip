package teamapex.kr.we_t_rip.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import teamapex.kr.we_t_rip.Account.LoginActivity;
import teamapex.kr.we_t_rip.R;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
                    if (pref.getString("id", null) == null || pref.getString("id", null).isEmpty()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                                finish();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                finish();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
