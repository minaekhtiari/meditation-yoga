package adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import apps.hillavas.com.yoga.ArticleTextView;
import apps.hillavas.com.yoga.R;
import classes.models.ResultJson;
import classes.models.YogaContent;
import classes.tools.helpers.RetrofitFactory;
import fragments.Fragment_ArticleTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class RecyclerView_Adapter_FirstContentActivity_category1 extends RecyclerView.Adapter<RecyclerView_Adapter_FirstContentActivity_category1.MVHolder> {

    Context context;
    LayoutInflater inflater;
    List<YogaContent> yogaContents;

    public RecyclerView_Adapter_FirstContentActivity_category1(Context context , List<YogaContent> yogaContents){
        this.context= context;
        inflater = LayoutInflater.from(context);
        this.yogaContents = yogaContents;
    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MVHolder(inflater.inflate(R.layout.card_fragment_home_page_sexual_helath_recycler_content , parent , false));
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        YogaContent yogaContent = yogaContents.get(position);
        holder.setData(yogaContent , position);
    }

    @Override
    public int getItemCount() {
        return yogaContents.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RelativeLayout relativeLayout;

        public MVHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.card_fragment_relative);
            relativeLayout.setOnClickListener(this);
        }

        public void setData(YogaContent yogaContent , int position){

        }

        @Override
        public void onClick(View v) {

//            Bundle bundle = new Bundle();
//            bundle.putParcelable(CONTENT, (Parcelable) homePagePagerList.get(getAdapterPosition()));
//            bundle.putString(TOKEN, token);
            Fragment_ArticleTextView articleTextView = new Fragment_ArticleTextView();
//            articleTextView.setArguments(bundle);
            Intent intent = new Intent(context, ArticleTextView.class);
//            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
