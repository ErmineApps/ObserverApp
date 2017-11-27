package kondratkov.ermineapps.observerapp.view.references;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.R;

/**
 * Created by kondratkov on 25.11.2017.
 */

public class ReferencesListAdapter extends RecyclerView.Adapter<ReferencesListHolder> {

    private Context mContext;
    List<Violation> mViolations;

    ReferencesListAdapter(Context context, List<Violation> violations) {
        this.mContext = context;
        this.mViolations = violations;
    }

    @Override
    public ReferencesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_references_list, parent, false);
        ReferencesListHolder pvh = new ReferencesListHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ReferencesListHolder holder, int position) {
        holder.bind(mViolations.get(position));
        holder.setClickListener(new ReferencesListClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(mContext, "# (Long click)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        int d = 0;
        try{
            d =mViolations.size();
        }catch (Exception e){}
        return d;
    }
}

