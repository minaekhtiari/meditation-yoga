package adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import apps.hillavas.com.meditation.R;
import classes.models.AttachedFile;
import classes.models.CaloryAdd;
import classes.models.Content;
import classes.models.FileGiver;
import classes.models.FileResult;
import classes.models.Record;
import classes.models.ResultJsonForBuy;
import classes.models.ResultJsonInteger;
import classes.tools.helpers.RetrofitFactory;
import classes.tools.helpers.RetrofitFactoryForFileManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class RecyclerView_record_adapter extends RecyclerView.Adapter<RecyclerView_record_adapter.MVHolder> {


    private static final String CONTENT = "CONTENT";
    private static final String TOKEN = "TOKEN";

    Context context;
    List<Record> records = new ArrayList<>();
    LayoutInflater inflater;
    String token;
    boolean free = false;
    int contentId = 0;
    ImageView imageViewLock;
    TextView tvPrice;


    public RecyclerView_record_adapter(Context context, List<Record> records , String token) {
        this.context = context;
        this.records = records;
        inflater = LayoutInflater.from(context);
        this.token = token;
    }

    @Override
    public MVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_content_for_vertical_list , parent , false);
        return new MVHolder(view);
    }

    @Override
    public void onBindViewHolder(MVHolder holder, int position) {
        Record record = records.get(position);
        holder.setData(record,position);
    }


    public void clear() {
        int size = this.records.size();
        if (size > 0) {
//            for (int i = 0; i < size; i++) {
//                this.records.remove(0);
//            }
            records.removeAll(null);
            this.notifyItemRangeRemoved(0, size);
        }
    }


    @Override
    public int getItemCount() {
        return records.size();
    }

    class MVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView tvTitr;
        TextView tvDescription;
        TextView tvScore;
        TextView tvCallories;
        TextView tvTime;
        ImageView ivTopBorder;



        public MVHolder(View itemView) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iransans.ttf");
            imageView = (ImageView) itemView.findViewById(R.id.card_content_vertical_image);
            imageViewLock = (ImageView) itemView.findViewById(R.id.card_content_vertical_image_lock);
            tvTitr = (TextView) itemView.findViewById(R.id.card_content_vertical_textTitr);
            tvDescription = (TextView) itemView.findViewById(R.id.card_content_vertical_text);
            tvScore = (TextView) itemView.findViewById(R.id.card_content_textScore);
            tvCallories = (TextView) itemView.findViewById(R.id.card_content_textCallory);
            tvTime = (TextView) itemView.findViewById(R.id.card_content_textTime);
            tvPrice = (TextView) itemView.findViewById(R.id.card_content_textPrice);
            ivTopBorder = (ImageView) itemView.findViewById(R.id.card_content_vertical_imageTopBorder);
            ivTopBorder.setAlpha(0.8f);
            tvDescription.setTypeface(typeface);
            tvTitr.setTypeface(typeface);
            tvScore.setTypeface(typeface);
            tvCallories.setTypeface(typeface);
            tvTime.setTypeface(typeface);
            tvPrice.setTypeface(typeface);
            imageView.setOnClickListener(this);
        }

        private void setData(Record record , int position){
            if(record.getThumbnailImageId() != null && record.getThumbnailImageId().length() > 0){
                RetrofitFactoryForFileManager.getRetrofitClient().getFiles(record.getThumbnailImageId() , 1).enqueue(new Callback<FileGiver>() {
                    @Override
                    public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {

                        if(response.body() != null &&  response.body().isIsSuccessfull()){
                            FileResult result = response.body().getFileResult();
                            if(result  != null){
                                try {
                                    Glide.with(context)
                                            .load(result.getFileAddress())
                                            .asBitmap()
                                            .override(424, 240)
                                            .centerCrop()
                                            .into(imageView);
                                }catch (Exception e){}
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<FileGiver> call, Throwable t) {

                    }
                });
            }

            String description = null;
            description = Html.fromHtml(record.getBody()).toString();
            tvTitr.setText(record.getSubject());
            tvDescription.setText(description);
            tvPrice.setText(record.getPrice()+"");
            tvScore.setText(record.getPoint()+"");
            tvCallories.setText(record.getCalory()+"");

            if(record.isIsFree()){
                free = true;
                imageViewLock.setVisibility(View.INVISIBLE);
                tvPrice.setText(R.string.hint_bouth);
            }else {
                imageViewLock.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onClick(View v) {
                if(v.getId() == R.id.card_content_vertical_image){
                    if(records.get(getAdapterPosition()).isIsFree()) {
                        contentId = records.get(getAdapterPosition()).getContentId();
                        new TaskLoadContent().execute(contentId);
                    }else {
                        final Dialog dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.setContentView(R.layout.dialog_buy);
                        Button btnAccept = (Button) dialog.findViewById(R.id.dialog_buy_btn_accept);
                        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_buy_btn_cancel);

                        btnAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TaskBuyAContent().execute(records.get(getAdapterPosition()).getContentId() , records.get(getAdapterPosition()).getPrice());
                                records.get(getAdapterPosition()).setFree(true);
                                notifyDataSetChanged();
                                dialog.hide();
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.hide();
                            }
                        });
                        dialog.show();

                    }
                }
            }
        }


    class TaskLoadContent extends AsyncTask<Integer, Void, List<AttachedFile>> {

        @Override
        protected List<AttachedFile> doInBackground(Integer... params) {
            Content content = null;
            List<AttachedFile> attaches = null;
            try {
                if (RetrofitFactory.getRetrofitClient().getContentById(token, params[0]).execute().body().isIsSuccessfull()) {
                    attaches = RetrofitFactory.getRetrofitClient().getContentById(token, params[0]).execute().body().getResult().getAttachedFiles();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return attaches;
        }

        @Override
        protected void onPostExecute(List<AttachedFile> attachedFiles) {
            super.onPostExecute(attachedFiles);
            if(attachedFiles != null && attachedFiles.size() > 0) {
                for (AttachedFile attachedFile : attachedFiles) {
                    if (attachedFile.getFileType() == 5) {
                        String videoUrlStr = attachedFile.getFileID();
                        if (videoUrlStr != null && videoUrlStr.length() > 0) {
//                        if(1 ==1) {
                            RetrofitFactoryForFileManager.getRetrofitClient().getFiles(videoUrlStr, 5).enqueue(new Callback<FileGiver>() {
                                @Override
                                public void onResponse(Call<FileGiver> call, Response<FileGiver> response) {
                                    if (response.body().isIsSuccessfull()) {
                                        String videoUrl = null;
                                        FileResult result = response.body().getFileResult();
                                        if (result != null) {
                                            videoUrl = result.getFileAddress();
                                            if (videoUrl.length() > 0) {
                                                if (videoUrl.length() > 0) {
                                                    Intent intentVideo = new Intent(Intent.ACTION_VIEW);
                                                    intentVideo.setDataAndType(Uri.parse(videoUrl), "video/*");
                                                    intentVideo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    context.startActivity(intentVideo);
                                                    new TaskAddToCaloryHistory().execute(contentId);
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<FileGiver> call, Throwable t) {

                                }
                            });
                        }
                    }

                }
            }
        }
    }

    class TaskBuyAContent extends AsyncTask<Integer, Void,String> {
        @Override
        protected String doInBackground(Integer... params) {
            ResultJsonForBuy resultJsonForBuy = null;
            String strMessage = null;
            try {
                resultJsonForBuy = RetrofitFactory.getRetrofitClient().buy(token,params[0],params[1]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(resultJsonForBuy != null) {
                if (resultJsonForBuy.isIsSuccessfull()) {
                    if (resultJsonForBuy.isResult())
                        strMessage = "YES";
                } else
                    strMessage = resultJsonForBuy.getMessage();
            }
            return strMessage;
        }

        @Override
        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            String message = null;

            if(str != null && str.length() > 0) {
                if (str.equals("YES")) {
                    //yes
//                    sharedPreferencesHome.edit().putInt(LAST_CATEGORYID_SELECTED , categoryId).commit();
//                    sharedPreferencesHome.edit().putBoolean("BUY" , true).commit();
//                    sharedPreferencesHome.edit().putInt("BUY_POSITION" , position).commit();
//                    sharedPreferencesHome.edit().putInt("CREDIT" , sharedPreferencesHome.getInt("CREDIT" , 0) - content.getPrice()).commit();

                    message = "خرید شما با موفقیت انجام شد";
                    new TaskLoadContent().execute(contentId);

                } else {
                    //error
                    message = str;
                }
            }else
                message = "error";
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    class TaskAddToCaloryHistory extends AsyncTask<Integer, Void,Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            ResultJsonInteger resultJsonInteger = null;
            String strMessage = null;
            try {
                resultJsonInteger = RetrofitFactory.getRetrofitClient().addToCaloryHistory(new CaloryAdd(token , params[0])).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}






