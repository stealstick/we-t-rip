package teamapex.kr.we_t_rip.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import teamapex.kr.we_t_rip.Activity.CourseViewActivity;
import teamapex.kr.we_t_rip.Activity.MainActivity;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;


public class PreviewCourseRecyclerViewAdapter extends RecyclerView.Adapter<PreviewCourseRecyclerViewAdapter.ViewHolder> {

    private final List<PreviewCourse> mPreviewCourse;
    private Fragment mFragment;
    private int mFragmentType;

    public PreviewCourseRecyclerViewAdapter(List<PreviewCourse> items, Fragment fragment, int fragmentType) {
        mPreviewCourse = items;
        mFragment = fragment;
        mFragmentType = fragmentType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_previewcourse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.previewCourse = mPreviewCourse.get(position);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("  ###,###,###,###", symbols);
        String cost = decimalFormat.format(mPreviewCourse.get(position).getCost()) + (char) 0xFFE6;
        String title = mPreviewCourse.get(position).getTitle();
        holder.mCostView.setText(cost);
        holder.mTitleView.setText(title);
        Glide.with(mFragment).load(mPreviewCourse.get(position).getImageURL()).centerCrop().thumbnail(0.2f).into(holder.mImageview);
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
}
