package kondratkov.ermineapps.observerapp.view.references;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import kondratkov.ermineapps.observerapp.R;
import kondratkov.ermineapps.observerapp.model.Violation;
import kondratkov.ermineapps.observerapp.representation.DataTimePepresentation;
import kondratkov.ermineapps.observerapp.representation.TypeViolationToString;

/**
 * Created by kondratkov on 25.11.2017.
 */

public class ReferencesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    @BindView(R.id.textView_item_references_type)TextView textView_item_references_type;
    @BindView(R.id.textView_item_references_body)TextView textView_item_references_body;
    @BindView(R.id.textView_item_references_comments)TextView textView_item_references_comments;
    @BindView(R.id.textView_item_references_date)TextView textView_item_references_date;
    @BindView(R.id.textView_item_references_city)TextView textView_item_references_city;
    @BindView(R.id.imageView_item_references_foto)ImageView imageView_item_references_foto;

    ReferencesListClickListener mReferencesListClickListener;


    public ReferencesListHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setTag(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bind(@NonNull Violation violation) {

        try {
            textView_item_references_type.setText(
                    TypeViolationToString.typeToString(violation.getType_violation(),
                            itemView.getResources().getStringArray(R.array.array_violations_enum),
                            itemView.getResources().getStringArray(R.array.array_violations)));
            textView_item_references_date.setText(DataTimePepresentation.dateDisplayFormat("2017-11-27T02:11:25", itemView.getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(violation.getPhotoViolations()[0].getPhoto()), null, options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView_item_references_foto.setImageBitmap(bitmap);
        } catch (Exception e){

        }
        try {
            textView_item_references_body.setText(violation.getBody_observation());
            textView_item_references_comments.setText(String.valueOf(violation.getMessages().length));
            textView_item_references_city.setText(violation.getLabelsMaps()[0].getCities().getName());
        } catch (Exception e) {
        }
    }

    public void setClickListener(ReferencesListClickListener referencesListClickListener){
        this.mReferencesListClickListener = referencesListClickListener;
    }

    @Override
    public void onClick(View view) {
        mReferencesListClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        mReferencesListClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}