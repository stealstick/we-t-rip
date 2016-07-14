package teamapex.kr.we_t_rip.Fragment;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import teamapex.kr.we_t_rip.Fragment.PreviewCourseFragment.OnListFragmentInteractionListener;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PreviewCourse} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PreviewCourseRecyclerViewAdapter extends RecyclerView.Adapter<PreviewCourseRecyclerViewAdapter.ViewHolder> {

    private final List<PreviewCourse> mPreviewCourse;
    private final OnListFragmentInteractionListener mListener;
    private Fragment mFragment;
    private int mFragmentType;

    public PreviewCourseRecyclerViewAdapter(List<PreviewCourse> items, OnListFragmentInteractionListener listener, Fragment fragment, int fragmentType) {
        mPreviewCourse = items;
        mListener = listener;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.previewCourse = mPreviewCourse.get(position);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("  ###,###,###,###", symbols);
        String cost = decimalFormat.format(mPreviewCourse.get(position).getCost()) + (char) 0xFFE6;
        String title = mPreviewCourse.get(position).getTitle();
        holder.mCostView.setText(cost);
        holder.mTitleView.setText(title);
        Glide.with(mFragment).load(mPreviewCourse.get(position).getImageURL()).centerCrop().thumbnail(0.2f).into(holder.mImageview);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.previewCourse);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPreviewCourse.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mCostView;
        public final AppCompatImageView mImageview;
        public final Toolbar mControlToolbar;
        public final Toolbar mStatusToolbar;

        public PreviewCourse previewCourse;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.courseTitle);
            mCostView = (TextView) view.findViewById(R.id.courseCost);
            mImageview = (AppCompatImageView) view.findViewById(R.id.courseImage);
            mControlToolbar = (Toolbar) view.findViewById(R.id.toolbar);
            mStatusToolbar = (Toolbar) view.findViewById(R.id.toolbar_course_status);
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
