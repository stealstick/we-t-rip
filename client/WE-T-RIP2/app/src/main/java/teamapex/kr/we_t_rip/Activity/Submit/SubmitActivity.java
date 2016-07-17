package teamapex.kr.we_t_rip.Activity.Submit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teamapex.kr.we_t_rip.Activity.MainActivity;
import teamapex.kr.we_t_rip.Activity.Submit.Data.Course;
import teamapex.kr.we_t_rip.R;

public class SubmitActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int GPS_RESULT_OK = 18;
    public static final int GPS_REQUEST = 19;
    private ViewPager mViewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_submit_save) {
            Toast.makeText(SubmitActivity.this, "저장", Toast.LENGTH_SHORT).show();
            //TODO 저장
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(SubmitActivity.this.getCurrentFocus().getWindowToken(), 0);
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(1);
                    SubmitFragmentPagerAdapter adapter = (SubmitFragmentPagerAdapter) mViewPager.getAdapter();
                    Submit2Fragment submit2Fragment = (Submit2Fragment) adapter.getItem(1);
                    submit2Fragment.notifyAdapter(true);
                } else {
                    SubmitFragmentPagerAdapter adapter = (SubmitFragmentPagerAdapter) mViewPager.getAdapter();
                    Submit2Fragment submit2Fragment = (Submit2Fragment) adapter.getItem(1);
                    List<Course> courseList = submit2Fragment.courseList;
                    courseList.add(new Course(courseList.get(courseList.size() - 1).getCourseDay() + 1));
                    submit2Fragment.notifyAdapter(false);

                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SubmitFragmentPagerAdapter(getSupportFragmentManager()));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mViewPager.getCurrentItem() != 0) {
            mViewPager.setCurrentItem(0);
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
            startActivity(new Intent(SubmitActivity.this, MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SubmitFragmentPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragmentList;

        public SubmitFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
            fragmentList.add(new SubmitActivityFragment());
            fragmentList.add(new Submit2Fragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
