package fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hillavas.messaging.classes.Question;
import com.hillavas.messaging.helpers.QuestionHelper;

import apps.hillavas.com.yoga.MessagingActivity;
import apps.hillavas.com.yoga.R;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_messaging_text_new extends Fragment {

    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;

    EditText editSubject;
    EditText editBody;
    Button btnSend;
    TextView tvTextLength;

    String token=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messaging_text_new , container , false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");

        editSubject = (EditText) getActivity().findViewById(R.id.fragment_messaging_voice_new_edit_subject);
        editBody = (EditText) getActivity().findViewById(R.id.fragment_messaging_text_new_edit_body);
        btnSend  = (Button) getActivity().findViewById(R.id.fragment_messaging_text_new_btn_send);
        tvTextLength = (TextView) getActivity().findViewById(R.id.fragment_messaging_text_new_text_textLength);

        editBody.addTextChangedListener(new AbsListView(getActivity()) {
            @Override
            public ListAdapter getAdapter() {
                return null;
            }

            @Override
            public void setSelection(int position) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                tvTextLength.setText(s.length()+"");
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageError = null;

                if(editBody.getText().length() > 0 && editSubject.getText().length()>0) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setContentView(R.layout.dialog_send_message);
                    ImageView ivBtnAccept = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_accept);
                    ImageView ivBtnCancel = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_cancel);

                    ivBtnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Question question = new Question(editSubject.getText().toString() , editBody.getText().toString());
                            new TaskSendQuestion().execute(question);
                            dialog.hide();
                        }
                    });
                    ivBtnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                        }
                    });
                    dialog.show();
                }else{
                    if(editBody.getText().length() <= 0 && editSubject.getText().length()<=0){
                        messageError = getString(R.string.emptyTitrAndBody);
                    }else
                        if(editBody.getText().length() <=0){
                            messageError = getString(R.string.emptyBody);
                        }else if(editSubject.getText().length() <=0){
                            messageError = getString(R.string.emptyTitr);
                        }
                    Toast.makeText(getActivity(), messageError, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    class  TaskSendQuestion extends AsyncTask<Question,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Question... params) {
            return QuestionHelper.sendQuestion(token , params[0] , 1);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(getActivity(), "ارسال شد", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity() , MessagingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}


