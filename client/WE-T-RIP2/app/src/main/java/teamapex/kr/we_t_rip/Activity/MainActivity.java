package teamapex.kr.we_t_rip.Activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import teamapex.kr.we_t_rip.Account.LoginActivity;
import teamapex.kr.we_t_rip.Activity.Submit.SubmitActivity;
import teamapex.kr.we_t_rip.Fragment.PreviewCourseFragment;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onResume() {
        super.onResume();
        if (getSupportFragmentManager().getFragments() != null) {
            ((PreviewCourseFragment) getSupportFragmentManager().getFragments().get(0)).refresh(false);
            ((PreviewCourseFragment) getSupportFragmentManager().getFragments().get(1)).refresh(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Fade());


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SubmitActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_main).setChecked(true);
        setNavigationViewClick(navigationView.getHeaderView(0));


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        CoursePreviewViewPagerAdapter adapter = new CoursePreviewViewPagerAdapter(getSupportFragmentManager());
        PreviewCourseFragment fragment1, fragment2;
        fragment1 = PreviewCourseFragment.newInstance(0);
        fragment2 = PreviewCourseFragment.newInstance(1);
        fragment1.setSharedElementReturnTransition(new ChangeImageTransform());
        fragment1.setExitTransition(new Explode());

        fragment2.setSharedElementEnterTransition(new ChangeImageTransform());
        fragment2.setEnterTransition(new Explode());

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 1) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setNavigationViewClick(View headerview) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        AppCompatTextView userName = (AppCompatTextView) headerview.findViewById(R.id.nav_id);
        userName.setText(pref.getString("username", null));
        AppCompatTextView email = (AppCompatTextView) headerview.findViewById(R.id.nav_email);
        email.setText(pref.getString("email", null));


        View view, view2;
        view = headerview.findViewById(R.id.nav_logout);
        if (view != null) {
            view.setOnClickListener(this);
        }
        view2 = headerview.findViewById(R.id.nav_mypage);
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_main) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.nav_logout:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(MainActivity.this, "로그아웃하였습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.nav_mypage:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.create();
                break;
        }
    }

    class CoursePreviewViewPagerAdapter extends FragmentPagerAdapter {

        public CoursePreviewViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return PreviewCourseFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "오늘의 여행";
                case 1:
                    return "내 여행함";
            }
            return null;
        }
    }


}
