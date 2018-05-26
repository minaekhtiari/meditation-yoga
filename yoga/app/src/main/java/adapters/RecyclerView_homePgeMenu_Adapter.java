package adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.yoga.MenuActivity;
import apps.hillavas.com.yoga.R;
import classes.Home_Menu_Page;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_homePgeMenu_Adapter extends RecyclerView.Adapter<RecyclerView_homePgeMenu_Adapter.MVHolder> {

    private static final String LEVEL_ID = "LEVEL_ID";
    private static final String CATEGORYID_ID = "CATEGORYID_ID";
    private static final String HAS_CHILD = "HAS_CHILD";


    private static final String CONTENT = "CONTENT";
    private static final String TOKEN = "TOKEN";

    List<Home_Menu_Page> menuPages = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    String token;
    LinearLayout linearLayoutCategories;
    TextView tvCategory;

    public RecyclerView_homePgeMenu_Adapter(Context context, List<Home_Menu_Page> home_menu_pages , String token) {
        this.context = context;
        this.menuPages = home_menu_pages;
        if(context != null)
            inflater = LayoutInflater.from(context);
        this.token = token;
//        linearLayoutCategories = (LinearLayout) ((AppCompatActivity)context).findViewById(R.id.activity_menu_scroll_linear_categories);
//        tvCategory = new TextView(context);
//        tvCategory.setText(menuPages.get(0).getCategoryId() + "");
//        tvCategory.setTextSize(30);
//        linearLayoutCategories.addView(tvCategory);
    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_fragment_home_page_menu_rectangle , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        Home_Menu_Page page = menuPages.get(position);
        holder.setData(page,position);
    }

    @Override
    public int getItemCount() {
        return menuPages.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitr;
        RelativeLayout relativeLayoutBack;

        public MVHolder(View itemView) {
            super(itemView);
            tvTitr = (TextView) itemView.findViewById(R.id.fragment_home_page_menu_text);
            relativeLayoutBack = (RelativeLayout) itemView.findViewById(R.id.fragment_home_page_menu_relative);
            relativeLayoutBack.setOnClickListener(this);

        }

        private void setData(Home_Menu_Page home_menu_page , int position){
            tvTitr.setText(home_menu_page.getTitr());
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("LEVEL_ID" , menuPages.get(getAdapterPosition()).getLevelId());
            bundle.putInt("CATEGORYID_ID" , menuPages.get(getAdapterPosition()).getCategoryId());
            bundle.putBoolean("HAS_CHILD" , menuPages.get(getAdapterPosition()).isHasChild());
            Intent intent = new Intent(context , MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

}






