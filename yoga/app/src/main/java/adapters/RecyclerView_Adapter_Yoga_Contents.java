package adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import classes.models.CategoryWithParentChild;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_Adapter_Yoga_Contents extends RecyclerView.Adapter<RecyclerView_Adapter_Yoga_Contents.MVHolder> {

    Context context;
    List<CategoryWithParentChild> list = new ArrayList<>();
    LayoutInflater inflater;

    public RecyclerView_Adapter_Yoga_Contents(Context context, List<CategoryWithParentChild> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_category , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(final MVHolder holder, final int position) {

        final CategoryWithParentChild child = list.get(position);
        if(child.isSelected())
            holder.iv.setVisibility(View.VISIBLE);
        holder.tv.setText(child.getName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(child);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MVHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public MVHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.card_category_text);
            iv = (ImageView) itemView.findViewById(R.id.card_category_image_tick);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");
            tv.setTypeface(typeface);
        }
    }
}






