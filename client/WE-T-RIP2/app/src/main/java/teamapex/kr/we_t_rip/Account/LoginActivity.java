package teamapex.kr.we_t_rip.Account;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import teamapex.kr.we_t_rip.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView mLoginTextView, mpasswordTextView;
    private UserLoginTask mAuthTask = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_card_button:
                attemptLogin();
                break;
            case R.id.login_sign_up_button:
                //TODO 회원가입 창
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mLoginTextView = (AutoCompleteTextView) findViewById(R.id.login_input_login);
        mpasswordTextView = (AutoCompleteTextView) findViewById(R.id.login_input_password);
        findViewById(R.id.login_card_button).setOnClickListener(this);
        findViewById(R.id.login_sign_up_button).setOnClickListener(this);

    }


    private void attemptLogin() {
        String id = mLoginTextView.getText().toString();
        String password = mpasswordTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;


   /*비밀번호 유효성 검사*/
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            if (TextUtils.isEmpty(password)) {
                mpasswordTextView.setError(getString(R.string.error_invalid_password));
            } else {
                mpasswordTextView.setError(getString(R.string.error_incorrect_password));
            }
            focusView = mpasswordTextView;
            cancel = true;
        }

        /*아이디 유효성 검사*/
        if (TextUtils.isEmpty(id) || !isIdValid(id)) {
            if (TextUtils.isEmpty(id)) {
                mLoginTextView.setError(getString(R.string.error_invalid_id));

            } else {
                mLoginTextView.setError(getString(R.string.error_incorrect_id));
            }
            focusView = mLoginTextView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Toast.makeText(LoginActivity.this, "로그인 작동" + id + password, Toast.LENGTH_SHORT).show();
            mAuthTask = new UserLoginTask(id, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isIdValid(String id) {
        Log.d("WET_login_id", String.valueOf(id.contains(" ")));
        return !id.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        Log.d("WET_login_password", String.valueOf(password.length() > 7));
        return password.length() > 7;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mId;
        private final String mPassword;
        private ProgressDialog dialog;

        UserLoginTask(String id, String password) {
            mId = id;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.d("WETTETd", "ASDF");
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://teamapex.kr/user/signin.php").openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                StringBuilder param = new StringBuilder();
                param.append("&id=" + mId).
                        append("&passwd=" + mPassword);
                urlConnection.setRequestMethod("POST");

                urlConnection.connect();
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(param.toString());
                out.flush();

                BufferedReader bis = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                int responseCode = urlConnection.getResponseCode();
                Log.d("WETTTTd", String.valueOf(responseCode));
                Log.d("WETTETd", "ASDF" + bis.readLine());
                out.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            dialog.dismiss();
            if (success) {
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "로그인 도중 오류가 발생하였습니다. 잠시후에 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(LoginActivity.this, "잠시만 기다려주세요", "로그인 중입니다");
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            dialog.dismiss();
        }
    }

}

