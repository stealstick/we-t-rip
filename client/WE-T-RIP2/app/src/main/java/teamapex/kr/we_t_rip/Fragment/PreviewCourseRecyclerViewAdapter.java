package teamapex.kr.we_t_rip.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import teamapex.kr.we_t_rip.Activity.CourseViewActivity;
import teamapex.kr.we_t_rip.Activity.MainActivity;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;


public class PreviewCourseRecyclerViewAdapter extends RecyclerView.Adapter<PreviewCourseRecyclerViewAdapter.ViewHolder> {

    private final List<PreviewCourse> mPreviewCourse;
    private Fragment mFragment;
    private int mFragmentType;
    private String id;

    public PreviewCourseRecyclerViewAdapter(List<PreviewCourse> items, Fragment fragment, int fragmentType, String id) {
        mPreviewCourse = items;
        mFragment = fragment;
        mFragmentType = fragmentType;
        this.id = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_previewcourse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
//        holder.mStatusToolbar.getMenu().getItem(R.id.course_favorite).setIcon(R.drawable.ic_favorite_black_24dp);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.previewCourse = mPreviewCourse.get(position);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("  ###,###,###,###", symbols);
        String cost = decimalFormat.format(mPreviewCourse.get(position).getCost()) + (char) 0xFFE6;
        String title = mPreviewCourse.get(position).getTitle();
        holder.mControlToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete) {
                    mPreviewCourse.remove(position);
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        if (mPreviewCourse.get(position).islike()) {
            holder.mStatusToolbar.getMenu().findItem(R.id.course_favorite).setIcon(R.drawable.ic_favorite_red_24dp);
        }
        if (mFragmentType == 1) {
            holder.mStatusToolbar.getMenu().findItem(R.id.course_bookmark).setVisible(false);
        }
        holder.mStatusToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.course_favorite:
                        Toast.makeText(mFragment.getContext(), "좋아요", Toast.LENGTH_SHORT).show();
                        item.setIcon(R.drawable.ic_favorite_red_24dp);
                        new StatusCourseTask(mFragment, id, mPreviewCourse.get(position).getId(), mPreviewCourse.get(position), 0).execute((Void) null);
                        break;
                    case R.id.course_bookmark:
                        if (mFragmentType == 1) {
                            break;
                        }
                        Toast.makeText(mFragment.getContext(), mPreviewCourse.get(position).getTitle() + "를 다운로드 하였습니다.", Toast.LENGTH_SHORT).show();
                        new StatusCourseTask(mFragment, id, mPreviewCourse.get(position).getId(), mPreviewCourse.get(position), 1).execute((Void) null);
                        break;
                }
                return false;
            }
        });
        holder.mCostView.setText(cost);
        holder.mTitleView.setText(title);
        Glide.with(mFragment).load(mPreviewCourse.get(position).getImageURL()).centerCrop().thumbnail(0.2f).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mImageview);
        holder.mImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getContext(), CourseViewActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mFragment.getActivity(), null);
                intent.putExtra("courseImageUrl", mPreviewCourse.get(position).getImageURL());
//                intent.putExtra("courseNo", mPreviewCourse.get(position).getId());
                intent.putExtra("courseNo", 1);
                intent.putExtra("courseTitle", mPreviewCourse.get(position).getTitle());
                mFragment.startActivity(intent, optionsCompat.toBundle());

            }
        });
        holder.mControlToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragment.getContext(), CourseViewActivity.class);
                intent.putExtra("courseImageUrl", mPreviewCourse.get(position).getImageURL());
//                intent.putExtra("courseNo", mPreviewCourse.get(position).getId());
                intent.putExtra("courseNo", 1);
                intent.putExtra("courseTitle", mPreviewCourse.get(position).getTitle());
                mFragment.startActivity(intent);
            }
        });
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getContext(), CourseViewActivity.class);
                intent.putExtra("courseImageUrl", mPreviewCourse.get(position).getImageURL());
//                intent.putExtra("courseNo", mPreviewCourse.get(position).getId());
                intent.putExtra("courseNo", 1);
                intent.putExtra("courseTitle", mPreviewCourse.get(position).getTitle());
                mFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPreviewCourse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mTitleView;
        public TextView mCostView;
        public ImageView mImageview;
        public Toolbar mControlToolbar;
        public Toolbar mStatusToolbar;
        public CardView mCardView;
        public RelativeLayout mRelativeLayout;

        public PreviewCourse previewCourse;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.courseTitle);
            mCostView = (TextView) view.findViewById(R.id.courseCost);
            mImageview = (ImageView) view.findViewById(R.id.courseImage);
            mCardView = (CardView) view.findViewById(R.id.preview_cardview);
            mControlToolbar = (Toolbar) view.findViewById(R.id.toolbar);
            mStatusToolbar = (Toolbar) view.findViewById(R.id.toolbar_course_status);
            mRelativeLayout = (RelativeLayout) view.findViewById(R.id.preview_statusbar);
            mControlToolbar.inflateMenu(R.menu.main);
            mStatusToolbar.inflateMenu(R.menu.preview_course_status);
            if (mFragmentType == 1) {
                mCostView.setBackgroundColor(Color.parseColor("#F44336"));
            }
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }

    public class StatusCourseTask extends AsyncTask<Void, Void, String> {

        private final String mId;
        private ProgressDialog dialog;
        private Fragment mFragment;
        private int courseId;
        private PreviewCourse previewCourse;
        private int type;

        StatusCourseTask(Fragment fragment, String id, int courseId, PreviewCourse course, int type) {
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
            if (dialog != null) {
                dialog.dismiss();
            }
            if (result != null && !result.isEmpty()) {
                previewCourse.setIslike(true);
            }
            ((PreviewCourseFragment) mFragment.getFragmentManager().getFragments().get(1)).refresh(false);
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(mFragment.getContext(), "잠시만 기다려주세요", "코스를 받아오는 중입니다");
        }

        @Override
        protected void onCancelled() {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

}
