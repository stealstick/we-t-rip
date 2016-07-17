package teamapex.kr.we_t_rip.Activity.Submit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.AccessibleTextView;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teamapex.kr.we_t_rip.Activity.Submit.Data.Course;
import teamapex.kr.we_t_rip.R;

/**
 * Created by 정 on 2016-07-17.
 */
public class SubmitActivityRecyclerViewAdapter extends RecyclerView.Adapter<SubmitActivityRecyclerViewAdapter.ViewHolder> {
    private Fragment mFragment;
    private List<Course> courseList;
    private RecyclerView mRecyclerView;

    public SubmitActivityRecyclerViewAdapter(Fragment fragment, List<Course> courseList, RecyclerView recyclerView) {
        mFragment = fragment;
        this.courseList = courseList;
        mRecyclerView = recyclerView;
    }

    @Override
    public SubmitActivityRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submitview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubmitActivityRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mTextViewDay.setText(ordinal(courseList.get(position).getCourseDay()) + " DAY");
        holder.mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holder.mEditTextTitle.getText().toString().isEmpty()) {
                    holder.mTextViewTitle.setText("일정을 입력해주세요");
                } else {
                    holder.mTextViewTitle.setText(holder.mEditTextTitle.getText());
                }
            }
        });
        holder.mEditTextCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (holder.mEditTextCost.getText().toString().isEmpty() || holder.mEditTextCost.getText().toString().equals(".")) {
                    holder.mTextViewCost.setText("0원");
                } else {
                    try {
                        holder.mTextViewCost.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(holder.mEditTextCost.getText().toString())));
                    } catch (Exception e) {
                        holder.mTextViewCost.setText("0원");
                    }
                }
            }
        });
        holder.mViewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 저장
                courseList.add(position + 1, new Course(courseList.get(position).getCourseDay()));
                notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(position + 1);

                Toast.makeText(mFragment.getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = new SimpleDateFormat("a hh:mm").format(new Date(0, 0, 0, hourOfDay, minute));
                        holder.mTextViewStartTime.setText(time);
                        holder.mStart.setText(time);
                        holder.mStart.setVisibility(View.VISIBLE);
                        holder.mStartImage.setVisibility(View.INVISIBLE);
                        holder.mStartText.setVisibility(View.INVISIBLE);

                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);
                timePickerDialog.setAccentColor("#039BE5");
                timePickerDialog.show(mFragment.getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        holder.mViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = new SimpleDateFormat("a hh:mm").format(new Date(0, 0, 0, hourOfDay, minute));
                        holder.mTextViewEndTime.setText("~" + time);
                        holder.mEnd.setText(time);
                        holder.mEnd.setVisibility(View.VISIBLE);
                        holder.mEndImage.setVisibility(View.INVISIBLE);
                        holder.mEndText.setVisibility(View.INVISIBLE);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);
                timePickerDialog.setAccentColor("#039BE5");
                timePickerDialog.show(mFragment.getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        holder.mImageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.startActivityForResult(new Intent(mFragment.getContext(), SubmitLocationActivity.class).putExtra("position", position), SubmitActivity.GPS_REQUEST);
            }
        });

    }

    public String ordinal(int i) {
        int mod100 = i % 100;
        int mod10 = i % 10;
        if (mod10 == 1 && mod100 != 11) {
            return i + "st";
        } else if (mod10 == 2 && mod100 != 12) {
            return i + "nd";
        } else if (mod10 == 3 && mod100 != 13) {
            return i + "rd";
        } else {
            return i + "th";
        }
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final AppCompatTextView mTextViewDay;
        public final AppCompatTextView mTextViewTitle;
        public final AppCompatTextView mTextViewCost;
        public final AppCompatTextView mTextViewStartTime;
        public final AppCompatTextView mTextViewEndTime;
        public final AppCompatEditText mEditTextTitle;
        public final AppCompatEditText mEditTextCost;
        public final AppCompatImageView mImageViewLocation;
        public final View mViewStartTime;
        public final View mViewEndTime;
        public final View mViewSubmit;
        public final View mEndImage;
        public final View mStartImage;
        public final View mStartText;
        public final View mEndText;
        public final AppCompatTextView mStart;
        public final AppCompatTextView mEnd;

        public ViewHolder(View view) {
            super(view);
            mTextViewTitle = (AppCompatTextView) view.findViewById(R.id.submit_course_title);
            mTextViewCost = (AppCompatTextView) view.findViewById(R.id.submit_course_cost);
            mTextViewDay = (AppCompatTextView) view.findViewById(R.id.textview_submit_day);
            mTextViewStartTime = (AppCompatTextView) view.findViewById(R.id.submit_course_start_time);
            mTextViewEndTime = (AppCompatTextView) view.findViewById(R.id.submit_course_end_time);
            mEditTextTitle = (AppCompatEditText) view.findViewById(R.id.edittext_submit_title);
            mEditTextCost = (AppCompatEditText) view.findViewById(R.id.edittext_submit_cost);
            mImageViewLocation = (AppCompatImageView) view.findViewById(R.id.imageview_submit_location);
            mViewSubmit = view.findViewById(R.id.view_submit_course);
            mViewStartTime = view.findViewById(R.id.view_submit_start_time);
            mViewEndTime = view.findViewById(R.id.view_submit_end_time);
            mStart = (AppCompatTextView) view.findViewById(R.id.textview_submit_start_timeview);
            mEnd = (AppCompatTextView) view.findViewById(R.id.textview_submit_end_timeview);
            mStartImage = view.findViewById(R.id.imageview_submit_time);
            mEndImage = view.findViewById(R.id.imageview_submit_time2);
            mStartText = view.findViewById(R.id.textview_submit_time);
            mEndText = view.findViewById(R.id.textview_submit_time2);

        }
    }
}
