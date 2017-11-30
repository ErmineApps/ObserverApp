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

public class ViolationProfileHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.textView_item_message_body)TextView textView_item_message_body;
    @BindView(R.id.textView_item_message_dislike)TextView textView_item_message_dislike;
    @BindView(R.id.textView_item_message_like)TextView textView_item_message_like;
    @BindView(R.id.textView_item_message_date)TextView textView_item_message_date;
    @BindView(R.id.imageView_item_message_icon_user)ImageView imageView_item_message_icon_user;

    public ViolationProfileHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setTag(itemView);

    }

    public void bind(@NonNull AllComments message){

        textView_item_message_body.setText(message.getBody());
        textView_item_message_like.setText(String.valueOf(message.getLike()));
        textView_item_message_dislike.setText(String.valueOf(message.getDislike()));
        textView_item_message_date.setText(DateTimePepresentation.dateDisplayFormat(message.getDate(), itemView.getContext()));
    }


}
