package teamapex.kr.we_t_rip.Activity.Submit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamapex.kr.we_t_rip.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Submit2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Submit2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Submit2Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SubmitActivityRecyclerViewAdapter adapter;


    public Submit2Fragment() {
    }


    public static Submit2Fragment newInstance(String param1, String param2) {
        Submit2Fragment fragment = new Submit2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit2, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new SubmitActivityRecyclerViewAdapter();
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
