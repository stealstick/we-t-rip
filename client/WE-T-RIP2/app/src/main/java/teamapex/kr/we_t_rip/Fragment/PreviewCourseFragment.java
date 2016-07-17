package teamapex.kr.we_t_rip.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import teamapex.kr.we_t_rip.Activity.MainActivity;
import teamapex.kr.we_t_rip.Fragment.data.PreviewCourse;
import teamapex.kr.we_t_rip.R;


public class PreviewCourseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    List<PreviewCourse> mPreviewCourse;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mFragmentType = 1;
    private CourseReceiveTask mAuthTask = null;
    private SharedPreferences pref;
    private PreviewCourseRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

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
   /*     for (int i = 0; i < 10; i++) {
            mPreviewCourse.add(new PreviewCourse("중국 여행 코스", "http://cfile7.uf.tistory.com/image/244A174A51D0D55219D213", 20000));
        }
        mPreviewCourse.add(new PreviewCourse("애니 여행 코스", "http://wjdtmddnr24.dyndns.org/%EC%95%A0%EB%8B%88/Akame%20ga%20Kill!/poster.jpg", 1000));
        mPreviewCourse.add(new PreviewCourse("애니 여행 코스", "http://wjdtmddnr24.dyndns.org/%EC%95%A0%EB%8B%88/Date%20A%20Live%20S2/poster.jpg", 1000));
*/
        pref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        mAuthTask = new CourseReceiveTask(this, pref.getString("id", null), true);
        mAuthTask.execute((Void) null);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#039BE5"));
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PreviewCourseRecyclerViewAdapter(mPreviewCourse, this, mFragmentType, pref.getString("id", null));
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

    public void refresh(boolean progress) {
        mPreviewCourse.clear();
        adapter.notifyDataSetChanged();
        mAuthTask = new CourseReceiveTask(this, pref.getString("id", null), progress);
        mAuthTask.execute((Void) null);
    }

    @Override
    public void onRefresh() {
        mPreviewCourse.clear();
        adapter.notifyDataSetChanged();
        mAuthTask = new CourseReceiveTask(this, pref.getString("id", null), true);
        mAuthTask.execute((Void) null);
    }

    public class CourseReceiveTask extends AsyncTask<Void, Void, String> {
        private boolean progress;
        private final String mId;
        private ProgressDialog dialog;
        private Fragment mFragment;

        CourseReceiveTask(Fragment fragment, String id, boolean progress) {
            mFragment = fragment;
            mId = id;
            this.progress = progress;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            try {
                String httpurl = mFragmentType == 0 ? "http://metrip.sunrin.io/u/" + mId + "/course" : "http://metrip.sunrin.io/u/" + mId + "/mycourse";
                Log.d("wetripURL", httpurl);

                URL url = new URL(httpurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);

                urlConnection.connect();
                int response = urlConnection.getResponseCode();
                Log.d("wetrip", "The response is: " + response);

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "";
                }

                /*
                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(param.toString());
                out.flush();
                */
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
            mAuthTask = null;
            if (dialog != null) {
                dialog.dismiss();
            }
            if (!result.isEmpty()) {
                try {
                    JSONObject jsonres = new JSONObject(result);
                    JSONArray course = jsonres.getJSONArray(mFragmentType == 0 ? "course" : "mycourse");
                    for (int i = 0; i < course.length(); i++) {
                        JSONObject object = (JSONObject) course.get(i);
                        mPreviewCourse.add(new PreviewCourse(object.getString("title"),
                                "http://metrip.sunrin.io" + object.getString("image"), object.getInt("price"),
                                object.getInt("id"), object.getBoolean("like"),
                                object.getString("intro"), object.getString("schedule")));
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("JSONARR", course.toString());
                    Log.d("JSONObject", result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(mFragment.getContext(), "코스를 받아오는데 오류가 발생하였습니다. 잠시후에 다시 시도해주세요", Toast.LENGTH_SHORT).
                        show();
                mFragment.getActivity().finish();
            }
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        protected void onPreExecute() {
            if (progress == true) {
                dialog = ProgressDialog.show(mFragment.getContext(), "잠시만 기다려주세요", "코스를 받아오는 중입니다");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            if (dialog != null) {
                dialog.dismiss();
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
