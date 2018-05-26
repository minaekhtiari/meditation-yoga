package apps.hillavas.com.meditation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import classes.Home_Pager_Page;
import classes.models.Record;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {

    SharedPreferences sharedPreferencesHome;
    private ImageView imageView,imageViewLock;
    private TextView titr1Text,titr2Text,textViewCount,textLikeCount , textDateDay,textDateMounth,textDateYear;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4;
    private String imageUrl;
    private Home_Pager_Page page;
    private Record record;
    private int categoryId;
    private int position;
    private AspectRatioCardView aspectRatioCardView;
    LayoutInflater inflater = null;
    DragLayout dragLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        CardView cardDetails = (CardView) rootView.findViewById(R.id.fragment_common_cardDetails);
        aspectRatioCardView = (AspectRatioCardView) rootView.findViewById(R.id.aspectRatio);
        imageViewLock = (ImageView) dragLayout.findViewById(R.id.image_lock);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        titr1Text = (TextView) dragLayout.findViewById(R.id.common_text_titr1);
        titr2Text = (TextView) dragLayout.findViewById(R.id.common_text_titr2);
        textViewCount = (TextView) dragLayout.findViewById(R.id.fragment_common_textViewCount);
        textLikeCount = (TextView) dragLayout.findViewById(R.id.fragment_common_textLikeCount);
        textDateDay = (TextView) dragLayout.findViewById(R.id.fragment_common_txtDateDay);
        textDateMounth = (TextView) dragLayout.findViewById(R.id.fragment_common_txtDateMounth);
        textDateYear = (TextView) dragLayout.findViewById(R.id.fragment_common_txtDateYear);

        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);
        titr1Text.setText(record.getSubject());
        String titr2Str = Html.fromHtml(record.getBody().toString()).toString();
        textLikeCount.setText(record.getLikeCount()+"");
        textViewCount.setText(record.getViewCount()+"");
        textDateMounth.setText(record.getInsertDateSh());
        if(titr2Str.length() > 50){
            titr2Str = titr2Str.substring(0 , 50 ) + " ... ";
        }
        titr2Text.setText(titr2Str);

        if(!record.isIsFree()) {
            imageViewLock.setVisibility(View.VISIBLE);
            imageView.setAlpha(0.5f);
        }else {
            imageViewLock.setVisibility(View.INVISIBLE);
            imageView.setAlpha(1f);
        }
        dragLayout.setGotoDetailListener(this);
        cardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleTextView.class);
                intent.putExtra("CONTENT_ID", record.getContentId());
                intent.putExtra("CATEGORY_ID", categoryId);
                ActivityCompat.startActivity(getActivity(), intent,null);
            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();


    }
    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        Intent intent = new Intent(activity, ArticleTextView.class);
        intent.putExtra("CONTENT_ID", record.getContentId());
        intent.putExtra("CATEGORY_ID", categoryId);
        intent.putExtra("POSITION", position);
        ActivityCompat.startActivity(activity, intent,null);
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void bindData(Record record)  {
        this.record = record;
    }
    public void bindData(int categoryId)  {
        this.categoryId = categoryId;
    }
    public void bindPosition(int position)  {
        this.position = position;
    }

}
