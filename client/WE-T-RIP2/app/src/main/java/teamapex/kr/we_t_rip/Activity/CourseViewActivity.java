package teamapex.kr.we_t_rip.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
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

import teamapex.kr.we_t_rip.R;

public class CourseViewActivity extends AppCompatActivity {
    private ImageView imageView;
    private Intent intent;
    private String courseTitle, courseImageURL;
    private int courseNo;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
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
}
