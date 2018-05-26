package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.meditation.R;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_maghta_Adapter extends RecyclerView.Adapter<RecyclerView_maghta_Adapter.MVHolder> {

    Context context;
    List<String> strLst= new ArrayList<>();
    LayoutInflater inflater;


    public RecyclerView_maghta_Adapter(Context context, List<String> strLst) {
        this.context = context;
        this.strLst = strLst;
        if(context != null)
            inflater = LayoutInflater.from(context);

    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_fragment_home_page_sexual_helath_recycler_content , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        String str = strLst.get(position);
        holder.setData(str,position);
    }

    @Override
    public int getItemCount() {
        return strLst.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvText;
        ImageView ivCircle;

        public MVHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.card_maghta_text);
            ivCircle = (ImageView) itemView.findViewById(R.id.card_maghta_image);
        }

        private void setData(String str , int position){
            tvText.setText(str);
        }

        @Override
        public void onClick(View v) {

        }
    }

}






