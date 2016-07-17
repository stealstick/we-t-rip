package teamapex.kr.we_t_rip.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import teamapex.kr.we_t_rip.Activity.MainActivity;
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
            case R.id.login_button:
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
        findViewById(R.id.login_sign_up_button).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);

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

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mId;
        private final String mPassword;
        private ProgressDialog dialog;

        UserLoginTask(String id, String password) {
            mId = id;
            mPassword = password;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                Log.d("wetripURL", "http://metrip.sunrin.io/u/" + mId + "/");
                URL url = new URL("http://metrip.sunrin.io/u/" + mId + "/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);

                urlConnection.connect();
                int response = urlConnection.getResponseCode();
                Log.d("wetrip", "The response is: " + response);

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "";
                }

                /*
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(param.toString());
                out.flush();
                */
                BufferedReader bis = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                result = bis.readLine();
                Log.d("wetriplogin", "login:" + bis.readLine());
                //out.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            mAuthTask = null;
            dialog.dismiss();
            if (!result.isEmpty()) {
                try {
                    result = result.substring("login :".length() + 1);
                    JSONObject login = new JSONObject(result);
                    String id = login.getString("username");
                    String uniqueId = login.getString("id");
                    String password = login.getString("passwd");
                    String location = login.getString("location");
                    int age = login.getInt("age");
                    String mycourse = login.getJSONArray("mycourse").toString();
                    String username = login.getString("username");
                    String like = login.getJSONArray("like").toString();
                    String email = login.getString("email");
                    String name = login.getString("name");

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.putString("id", id);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putInt("age", age);
                    editor.putString("mycourse", mycourse);
                    editor.putString("like", like);
                    editor.putString("email", email);
                    editor.putString("name", name);
                    editor.putString("uniqueId", uniqueId);
                    editor.putString("location", location);
                    editor.apply();
                    Log.d("JSONObject", "json:" + id);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
//                SharedPreferences.Editor editor = pref.edit();
//                finish();
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

