package teamapex.kr.we_t_rip.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import teamapex.kr.we_t_rip.Fragment.PreviewCourseFragment.OnListFragmentInteractionListener;
import teamapex.kr.we_t_rip.Fragment.dummy.PreviewCourse;
import teamapex.kr.we_t_rip.R;

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

    public PreviewCourseRecyclerViewAdapter(List<PreviewCourse> items, OnListFragmentInteractionListener listener, Fragment fragment) {
        mPreviewCourse = items;
        mListener = listener;
        mFragment = fragment;
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
        String cost = String.valueOf(mPreviewCourse.get(position).getCost());
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

        public PreviewCourse previewCourse;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.courseTitle);
            mCostView = (TextView) view.findViewById(R.id.courseCost);
            mImageview = (AppCompatImageView) view.findViewById(R.id.courseImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
