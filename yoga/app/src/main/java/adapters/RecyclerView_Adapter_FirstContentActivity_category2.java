package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import apps.hillavas.com.yoga.R;
import classes.models.YogaContent;

/**
 * Created by A.Mohammadi on 11/14/2017.
 */

public class RecyclerView_Adapter_FirstContentActivity_category2 extends RecyclerView.Adapter<RecyclerView_Adapter_FirstContentActivity_category2.MVHolder> {

    Context context;
    LayoutInflater inflater;
    List<YogaContent> yogaContents;

    public RecyclerView_Adapter_FirstContentActivity_category2(Context context , List<YogaContent> yogaContents){
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

    class MVHolder extends RecyclerView.ViewHolder{

        public MVHolder(View itemView) {
            super(itemView);
        }

        public void setData(YogaContent yogaContent , int position){

        }
    }
}
