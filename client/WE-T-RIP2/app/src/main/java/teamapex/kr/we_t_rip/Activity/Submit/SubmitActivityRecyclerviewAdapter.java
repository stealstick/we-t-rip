package teamapex.kr.we_t_rip.Activity.Submit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamapex.kr.we_t_rip.R;

/**
 * Created by 정 on 2016-07-17.
 */
public class SubmitActivityRecyclerViewAdapter extends RecyclerView.Adapter<SubmitActivityRecyclerViewAdapter.ViewHolder> {

    @Override
    public SubmitActivityRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_submitview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubmitActivityRecyclerViewAdapter.ViewHolder holder, int position) {

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
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
