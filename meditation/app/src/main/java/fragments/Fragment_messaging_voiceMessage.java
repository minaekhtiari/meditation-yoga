package fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hillavas.filemanaging.classes.FileForUpload;
import com.hillavas.filemanaging.helpers.FileManagerHelper;
import com.hillavas.messaging.classes.AttachedFile;
import com.hillavas.messaging.classes.Question;
import com.hillavas.messaging.helpers.QuestionHelper;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import apps.hillavas.com.meditation.MessagingActivity;
import apps.hillavas.com.meditation.R;
import classes.models.ResultUploadFileResponse;

/**
 * Created by A.Mohammadi on 7/9/2017.
 */

public class Fragment_messaging_voiceMessage extends Fragment {

    public static final String SENT_MESSAGE="SENT_MESSAGE";
    public static final String GUID="GUID";
    SharedPreferences sharedPreferencesHome;
    ImageView ivRecording;
    ImageView ivPlay;
    ProgressBar progressBar;
    TextView tvTimer;
    TextView tvTimerRecoorded;
    boolean recording = false;
    MediaRecorder recorder;
    private File outfile = null;
    int MAX_DURATION=60000;
    Timer timer = new Timer();
    boolean stopRecording = true;
    CountDownTimer coTimerRecording;
    CountDownTimer coTimerPlaying;
    int counter = 0;
    int timerCounter = 60;
    int timeRecorded = 0;
    boolean playing = false;
    MediaPlayer player;
    ImageView btnSend;
    String token=null;
    EditText editSubject;
    String subject;
    ImageView btnRestart;
    RelativeLayout relativeLayoutSendBox;
    FloatingActionButton fabRecording;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messaging_voice_message, container , false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferencesHome.getString(GUID, "");

        ivPlay = (ImageView) getActivity().findViewById(R.id.fragment_messaging_voice_image_playRecording);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_messaging_voice_progress);
        tvTimer = (TextView) getActivity().findViewById(R.id.fragment_messaging_voice_text_progressTime);
        tvTimerRecoorded = (TextView) getActivity().findViewById(R.id.fragment_messaging_voice_text_recordedTime);
        btnSend = (ImageView) getActivity().findViewById(R.id.fragment_messaging_voice_new_btn_send);
        editSubject = (EditText) getActivity().findViewById(R.id.fragment_messaging_voice_new_edit_subject);
        btnRestart = (ImageView) getActivity().findViewById(R.id.fragment_messaging_voice_new_btn_restart);
        relativeLayoutSendBox= (RelativeLayout) getActivity().findViewById(R.id.fragment_messaging_voice_relative_sendBox);
        fabRecording = (FloatingActionButton) getActivity().findViewById(R.id.fragment_messaging_voice_fab_recording);
        ivPlay.setEnabled(false);
        progressBar.setMax(MAX_DURATION);
        progressBar.setProgress(0);
        progressBar.getProgressDrawable().setColorFilter(
                getActivity().getResources().getColor(R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
        fabRecording.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getActivity().getResources().getColor(R.color.green)}));


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outfile = null;
                recorder = new MediaRecorder();
                coTimerRecording.cancel();
                tvTimerRecoorded.setText("00");
                progressBar.setProgress(0);
                relativeLayoutSendBox.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.to_down_50));
                relativeLayoutSendBox.setVisibility(View.INVISIBLE);
            }
        });


        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(outfile != null && outfile.getPath() != null) {
                    if(!playing) {
                        ivPlay.startAnimation(AnimationUtils.loadAnimation(getActivity() , R.anim.from_middle_slow));
                        playing = true;
//                        ivPlay.setImageResource(R.drawable.stop_white);
                        try {
                            player = new MediaPlayer();
                            player.setDataSource(outfile.getPath());
                            player.prepare();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            System.out.println("Exception of type : " + e.toString());
                            e.printStackTrace();
                        }
                        player.start();
                        counter = 0;
                        timerCounter = 60;
                        progressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_messaging_voice_progress);
                        progressBar.setMax(timeRecorded);
                        coTimerPlaying = new CountDownTimer(MAX_DURATION , 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if(counter == MAX_DURATION)
//                                    ivPlay.setImageResource(R.drawable.play_white);
                                counter +=1000;
                                progressBar.setProgress(counter);
                                if(timerCounter > 0)
                                    timerCounter--;
                                tvTimer.setText(timerCounter+"");
                                if(!player.isPlaying()){
//                                    ivPlay.setImageResource(R.drawable.play_white);
                                    player.stop();
                                    playing = false;
                                    coTimerPlaying.cancel();
                                }
                            }
                            @Override
                            public void onFinish() {

                            }
                        };
                        coTimerPlaying.start();

                    }else {
                        player.stop();
//                        ivPlay.setImageResource(R.drawable.play_white);
                        playing = false;
                        coTimerPlaying.cancel();
                    }
                }
            }
        });


        fabRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(relativeLayoutSendBox.getVisibility() == View.VISIBLE){
//                    relativeLayoutSendBox.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.to_middle_slow));
//                    relativeLayoutSendBox.setVisibility(View.INVISIBLE);
//                }
                ivPlay.setEnabled(false);
                progressBar.setMax(MAX_DURATION);
                timeRecorded = 0;
                player = new MediaPlayer();
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
                }

                int permissionCheckAudio = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO);
                if (permissionCheckAudio != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.RECORD_AUDIO},1001);
                } else {
                    if(!recording) {
                        if(outfile != null){
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.setContentView(R.layout.dialog_send_message);
                            TextView tvTitr = (TextView) dialog.findViewById(R.id.titr);
                            ImageView ivBtnAccept = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_accept);
                            ImageView ivBtnCancel = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_cancel);
                            tvTitr.setText(R.string.areYouSureYouWantRecordNewSound);
                            ivBtnAccept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    relativeLayoutSendBox.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.to_down_50));
                                    relativeLayoutSendBox.setVisibility(View.INVISIBLE);
                                    fabRecording.startAnimation(AnimationUtils.loadAnimation(getActivity() , R.anim.from_middle_slow));
//                                    fabRecording.setImageResource(R.drawable.stop_icon);
                                    fabRecording.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getActivity().getResources().getColor(R.color.backColor)}));
                                    tvTimerRecoorded.setText("00");
                                    String state = android.os.Environment.getExternalStorageState();
                                    if(!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
                                        Toast.makeText(getActivity(), "SD Card is not mounted.  It is " + state + ".", Toast.LENGTH_SHORT).show();
                                    }
                                    try{
                                        File storageDir = new File(Environment
                                                .getExternalStorageDirectory() , "SH");
                                        storageDir.mkdir();
                                        outfile = File.createTempFile(getString(R.string.titrEnglish)+"-",".wav",storageDir);
                                        recorder = new MediaRecorder();
                                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                                        recorder.setMaxDuration(MAX_DURATION);
                                        recorder.setOutputFile(outfile.getAbsolutePath());
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    try {
                                        recorder.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    progressBar.setVisibility(View.VISIBLE);
                                    recorder.start();
                                    recording = true;
                                    counter = 0;
                                    timerCounter = 60;
                                    coTimerRecording = new CountDownTimer(MAX_DURATION , 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            counter +=1000;
                                            progressBar.setProgress(counter);
                                            if(timerCounter > 0)
                                                timerCounter--;
                                            tvTimer.setText(timerCounter+"");
                                            if(timerCounter > 50)
                                                tvTimerRecoorded.setText("0"+(60 - timerCounter));
                                            else
                                                tvTimerRecoorded.setText(60 - timerCounter+"");
                                        }
                                        @Override
                                        public void onFinish() {
                                            ivRecording.callOnClick();
                                        }
                                    };
                                    coTimerRecording.start();
                                    dialog.hide();
                                }
                            });
                            ivBtnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.hide();
                                    if(outfile != null){

                                    }
                                    return;
                                }
                            });
                            dialog.show();


                        }else {
                            fabRecording.startAnimation(AnimationUtils.loadAnimation(getActivity() , R.anim.from_middle_slow));
//                            fabRecording.setImageResource(R.drawable.stop_icon);
                            fabRecording.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getActivity().getResources().getColor(R.color.backColor)}));
                            tvTimerRecoorded.setText("00");
                            String state = android.os.Environment.getExternalStorageState();
                            if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
                                Toast.makeText(getActivity(), "SD Card is not mounted.  It is " + state + ".", Toast.LENGTH_SHORT).show();
                            }
                            try {
                                File storageDir = new File(Environment
                                        .getExternalStorageDirectory(), "SH");
                                storageDir.mkdir();
                                outfile = File.createTempFile(getString(R.string.titrEnglish) + "-", ".wav", storageDir);
                                recorder = new MediaRecorder();
                                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                                recorder.setMaxDuration(MAX_DURATION);
                                recorder.setOutputFile(outfile.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                recorder.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            progressBar.setVisibility(View.VISIBLE);
                            recorder.start();
                            recording = true;
                            counter = 0;
                            timerCounter = 60;
                            coTimerRecording = new CountDownTimer(MAX_DURATION, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    counter += 1000;
                                    progressBar.setProgress(counter);
                                    if (timerCounter > 0)
                                        timerCounter--;
                                    tvTimer.setText(timerCounter + "");
                                    if (timerCounter > 50)
                                        tvTimerRecoorded.setText("0" + (60 - timerCounter));
                                    else
                                        tvTimerRecoorded.setText(60 - timerCounter + "");
                                }

                                @Override
                                public void onFinish() {

                                }
                            };
                            coTimerRecording.start();
                        }
                    }else {
                        relativeLayoutSendBox.setVisibility(View.VISIBLE);
                        relativeLayoutSendBox.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.to_up_50));
                        fabRecording.startAnimation(AnimationUtils.loadAnimation(getActivity() , R.anim.from_middle_slow));
//                        fabRecording.setImageResource(R.drawable.mic_logo);
                        fabRecording.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{getActivity().getResources().getColor(R.color.green)}));
                        if(recorder != null) {
                            recorder.stop();
                            recorder.release();
                            ivPlay.setAlpha(1.0f);
                            recording = false;
                            stopRecording = true;
                            coTimerRecording.cancel();
                            player = new MediaPlayer();
                            ivPlay.setEnabled(true);
                            if (timerCounter > 50)
                                tvTimerRecoorded.setText("0" + (60 - timerCounter));
                            else
                                tvTimerRecoorded.setText(60 - timerCounter + "");
                            timeRecorded = (60 - timerCounter) * 1000;
                        }
                    }
                }
            }

        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editSubject.getText().length() >0){
                    if(outfile == null){
                        Toast.makeText(getActivity(), R.string.notSoundRecorded, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setContentView(R.layout.dialog_send_message);
                    ImageView ivBtnAccept = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_accept);
                    ImageView ivBtnCancel = (ImageView) dialog.findViewById(R.id.dialog_sendMessage_image_cancel);

                    ivBtnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(outfile != null){
                                byte[] audioBytes = null;
                                try {
                                    File audioFile = new File(outfile.getPath());
                                    long fileSize = audioFile.length();
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    FileInputStream fis = new FileInputStream(audioFile);
                                    byte[] buf = new byte[(int)fileSize];
                                    int n;
                                    while (-1 != (n = fis.read(buf)))
                                        baos.write(buf, 0, n);
                                    audioBytes = baos.toByteArray();
                                } catch (Exception e) {
                                    String fc = e.getMessage().toString();
                                }
                                String valueBase64 = Base64.encodeToString(audioBytes, Base64.DEFAULT);

//                    String url = "data:audio/wav;base64,"+valueBase64;
//                    MediaPlayer playerTest = new MediaPlayer();
//                    try {
//                        playerTest.setDataSource(url);
//                        playerTest.prepare();
//                        playerTest.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                                FileForUpload fileForUpload = new FileForUpload();
                                fileForUpload.setFilename( outfile.getName());
                                fileForUpload.setUsername("Hillavas_HamrahDars");
                                fileForUpload.setPassword("1qaz!QAZ");
                                fileForUpload.setStringBase64(valueBase64);
                                if(editSubject.getText().length() > 0) {
                                    subject = editSubject.getText().toString();
                                    new TaskUploadFile().execute(fileForUpload);
                                }
                            }
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
                }else {
                    Toast.makeText(getActivity(), R.string.errorInsertSubject, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class TaskUploadFile extends AsyncTask<FileForUpload , Void , JSONObject>{

       @Override
       protected JSONObject doInBackground(FileForUpload... params) {
           try {
               return FileManagerHelper.fileUpload(params[0]);
           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }
            return null;
       }

       @Override
       protected void onPostExecute(JSONObject jsonObject) {
           super.onPostExecute(jsonObject);
           if(jsonObject != null){
               Gson gson = new Gson();
               ResultUploadFileResponse resultUploadResponse = gson.fromJson(jsonObject.toString() ,ResultUploadFileResponse.class);
               List<AttachedFile> lstAttacheFiles = new ArrayList<>();
               AttachedFile attachedFile= new AttachedFile(resultUploadResponse.getFileID(),resultUploadResponse.getFileType());
               lstAttacheFiles.add(attachedFile);
               Question question = new Question();
               question.setSubject(subject);
               question.setBody("media");
               question.setAttachedFiles(lstAttacheFiles);
               new TaskSendQuestion().execute(question);
           }
       }
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

