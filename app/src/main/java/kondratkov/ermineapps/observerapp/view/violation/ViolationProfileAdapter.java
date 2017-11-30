package kondratkov.ermineapps.observerapp.view.violation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.AllComments;
import kondratkov.ermineapps.observerapp.model.Message;
import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.view.references.ReferencesListHolder;

/**
 * Created by kondratkov on 28.11.2017.
 */

public class ViolationProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<AllComments> mMessages;
    ViewGroup mParent;
    int mViewType;

    ViolationProfileAdapter(Context context, List<AllComments>messages){
        mContext = context;
        mMessages = messages;
    }
    @Override
    public int getItemViewType(int position) {
        if(mMessages.get(position).isTypeMessage()){
            return 0;
        }else{
            return 1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder pvh=null;
        switch (viewType){
            case 0:
                mParent = parent;
                mViewType = viewType;
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_violation_message, parent, false);
                pvh = new ViolationProfileHolder(v);
                break;
            case 1:
                mParent = parent;
                mViewType = viewType;
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_violation_comment, parent, false);
                pvh = new ViolationProfileHolderComment(v1);
                break;
        }
        return pvh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViolationProfileHolder violationProfileHolder = (ViolationProfileHolder)holder;
                violationProfileHolder.bind(mMessages.get(position));
                break;
            case 1:
                ViolationProfileHolderComment violationProfileHolderComment = (ViolationProfileHolderComment)holder;
                violationProfileHolderComment.bind(mMessages.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        int d = 0;
        try{
            d =mMessages.size();
        }catch (Exception e){}
        return d;
    }
}
