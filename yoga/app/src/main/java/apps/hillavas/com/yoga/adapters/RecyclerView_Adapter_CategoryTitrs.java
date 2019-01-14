package apps.hillavas.com.yoga.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.R;
import apps.hillavas.com.yoga.data.models.CategoryWithParentChild;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_Adapter_CategoryTitrs extends RecyclerView.Adapter<RecyclerView_Adapter_CategoryTitrs.MVHolder> {

    Context context;
    List<CategoryWithParentChild> list = new ArrayList<>();
    LayoutInflater inflater;
    int selectedPosition ;

    public RecyclerView_Adapter_CategoryTitrs(Context context, List<CategoryWithParentChild> list , int selectedPosition) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.selectedPosition =selectedPosition-1;
    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_category_titr , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(final MVHolder holder, final int position) {
        final CategoryWithParentChild child = list.get(position);

        holder.tv.setText(child.getName());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.setFromTitr(true);
                child.setIndexForColor(position);
                EventBus.getDefault().post(child);
            }
        });

        if(position == selectedPosition) {
            holder.tv.setBackgroundResource(R.drawable.test);

        }
        else {
            holder.tv.setBackgroundResource(R.drawable.test2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MVHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MVHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.card_category_text_titr);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");
            tv.setTypeface(typeface);
        }
    }
}






