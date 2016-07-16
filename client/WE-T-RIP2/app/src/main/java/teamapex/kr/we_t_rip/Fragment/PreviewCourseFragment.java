package teamapex.kr.we_t_rip.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;


public class PreviewCourseFragment extends Fragment {
    List<PreviewCourse> mPreviewCourse;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mFragmentType = 1;

    public PreviewCourseFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PreviewCourseFragment newInstance(int columnCount) {
        PreviewCourseFragment fragment = new PreviewCourseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setRetainInstance(true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragmentType = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_previewcourse_list, container, false);
        mPreviewCourse = new ArrayList<PreviewCourse>();
        for (int i = 0; i < 10; i++) {
            mPreviewCourse.add(new PreviewCourse("중국 여행 코스", "http://cfile7.uf.tistory.com/image/244A174A51D0D55219D213", 20000));
        }
        mPreviewCourse.add(new PreviewCourse("애니 여행 코스", "http://wjdtmddnr24.dyndns.org/%EC%95%A0%EB%8B%88/Akame%20ga%20Kill!/poster.jpg", 1000));
        mPreviewCourse.add(new PreviewCourse("애니 여행 코스", "http://wjdtmddnr24.dyndns.org/%EC%95%A0%EB%8B%88/Date%20A%20Live%20S2/poster.jpg", 1000));

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PreviewCourseRecyclerViewAdapter adapter = new PreviewCourseRecyclerViewAdapter(mPreviewCourse, this, mFragmentType);
        recyclerView.setAdapter(adapter);

        return rootView;
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
