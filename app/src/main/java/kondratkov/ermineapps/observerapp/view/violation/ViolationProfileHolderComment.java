package kondratkov.ermineapps.observerapp.view.violation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.AllComments;
import kondratkov.ermineapps.observerapp.model.Comment;
import kondratkov.ermineapps.observerapp.model.Message;
import kondratkov.ermineapps.observerapp.representation.DateTimePepresentation;

/**
 * Created by kondratkov on 28.11.2017.
 */

public class ViolationProfileHolderComment extends RecyclerView.ViewHolder{

    @BindView(R.id.textView_item_comment_body)TextView textView_item_comment_body;
    @BindView(R.id.textView_item_comment_like)TextView textView_item_comment_like;
    @BindView(R.id.textView_item_comment_dislike)TextView textView_item_comment_dislike;
    @BindView(R.id.textView_item_comment_date)TextView textView_item_comment_date;
    @BindView(R.id.imageView_item_comment_icon_user)ImageView imageView_item_comment_icon_user;

    public ViolationProfileHolderComment(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(itemView);
    }

    public void bind(@NonNull AllComments comment){

        textView_item_comment_body.setText(comment.getBody());
        textView_item_comment_like.setText(String.valueOf(comment.getLike()));
        textView_item_comment_dislike.setText(String.valueOf(comment.getDislike()));
        textView_item_comment_date.setText(DateTimePepresentation.dateDisplayFormat(comment.getDate(), itemView.getContext()));
    }
}
