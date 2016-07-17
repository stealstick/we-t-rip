package teamapex.kr.we_t_rip.Activity.Submit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teamapex.kr.we_t_rip.Activity.Submit.Data.Course;
import teamapex.kr.we_t_rip.R;

public class Submit2Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SubmitActivityRecyclerViewAdapter adapter;
    public List<Course> courseList;

    public Submit2Fragment() {
    }

    public void notifyAdapter(boolean position) {
        adapter.notifyDataSetChanged();
        if (position) {
            mRecyclerView.scrollToPosition(0);

        } else {
            mRecyclerView.smoothScrollToPosition(courseList.size());
        }
    }

    public static Submit2Fragment newInstance(String param1, String param2) {
        Submit2Fragment fragment = new Submit2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SubmitActivity.GPS_REQUEST && resultCode == SubmitActivity.GPS_RESULT_OK) {

            Log.d("wetripcode", String.valueOf(data.getIntExtra("position", -1)));
        } else {
            Toast.makeText(getContext(), "GPS정보를 얻어오는데 실패하였습니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit2, container, false);
        courseList = new ArrayList<>();
        courseList.add(new Course(1));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new SubmitActivityRecyclerViewAdapter(this, courseList, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
