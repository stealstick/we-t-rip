package teamapex.kr.we_t_rip.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import teamapex.kr.we_t_rip.Fragment.PreviewCourseFragment;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;

public class CourseViewActivity extends AppCompatActivity {
    private ImageView imageView;
    private Intent intent;
    private String courseTitle, courseImageURL;
    private int courseNo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_download:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setEnterTransition(new Fade());
        getWindow().setReturnTransition(new Fade());
        intent = getIntent();
        if (intent == null) {
            Toast.makeText(CourseViewActivity.this, "에러", Toast.LENGTH_SHORT).show();
            finish();
        }
        courseTitle = intent.getStringExtra("courseTitle");
        courseImageURL = intent.getStringExtra("courseImageUrl");
        courseNo = intent.getIntExtra("courseNo", -1);
        if (courseTitle == null || courseImageURL == null || courseNo == -1) {
            Toast.makeText(CourseViewActivity.this, "에러", Toast.LENGTH_SHORT).show();
            finish();
        }
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(courseTitle);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        imageView = (ImageView) findViewById(R.id.course_view_imageView);
        Glide.with(this).load(courseImageURL).thumbnail(0.2f).into(imageView);

    }

    public class LikeCourseTask extends AsyncTask<Void, Void, String> {

        private final String mId;
        private Fragment mFragment;
        private int courseId;
        private PreviewCourse previewCourse;
        private int type;

        LikeCourseTask(Fragment fragment, String id, int courseId, PreviewCourse course, int type) {
            mFragment = fragment;
            this.courseId = courseId;
            this.previewCourse = course;
            mId = id;
            this.type = type;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                String httpurl = "http://metrip.sunrin.io/u/" + mId + (type == 0 ? "/like/" : "/mycourse/");
                Log.d("wetripURL", httpurl);

                URL url = new URL(httpurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                DataOutputStream out = new DataOutputStream(os);
                StringBuilder param = new StringBuilder();
                param.append("&course=" + courseId);
                out.writeBytes(param.toString());

                urlConnection.connect();
                int response = urlConnection.getResponseCode();
                Log.d("wetrip", "The response is: " + response);

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    //   return "";
                }


                out.flush();

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
            if (result != null && !result.isEmpty()) {
                previewCourse.setIslike(true);
            }
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(CourseViewActivity.this, "다운로드를 완료하였습니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
        }
    }

}
