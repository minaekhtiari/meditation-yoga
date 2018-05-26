package com.hillavas.filemanaging.helpers;



import com.google.gson.Gson;
import com.hillavas.filemanaging.classes.FileByteArrayResult;
import com.hillavas.filemanaging.classes.FileForUpload;
import com.hillavas.filemanaging.classes.FileURLResult;
import com.hillavas.filemanaging.classes.ResultJsonBase;
import com.hillavas.filemanaging.classes.ResultJsonGetFileByteArray;
import com.hillavas.filemanaging.classes.ResultJsonGetFileURL;
import com.hillavas.filemanaging.factories.RetrofitFileManagerFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by A.Mohammadi on 8/9/2017.
 */

public class FileManagerHelper {
    private static IRetrofitFileManaging fileManagerClient = RetrofitFileManagerFactory.getRetrofitClient();

    public static JSONObject fileUpload(FileForUpload fileForUpload) throws IOException, JSONException {
        JSONObject jo = null;
            ResultJsonBase js = fileManagerClient.uploadFile(fileForUpload).execute().body();
            if (js.isIsSuccessfull()) {
                Gson gson = new Gson();
                Object o = js.getResult();
                String json = gson.toJson(o, LinkedHashMap.class);
                jo = new JSONObject(json);
                return jo;
            }
        return null;
    }

    public static FileURLResult getFileURL(String fileId , String fileType){
        FileURLResult fileURLResult = null;
        try {
            ResultJsonGetFileURL jsURL = fileManagerClient.getFileUrl(fileId , fileType).execute().body();
            if(jsURL.isIsSuccessfull())
                fileURLResult = jsURL.getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileURLResult;
    }

    public static List<String> getFileAddress(List<String> fileIds , String fileType){
        List<String> fileUrls = new ArrayList<>();
        FileURLResult fileURLResult = null;
        try {
            for(String s : fileIds) {
                ResultJsonGetFileURL jsURL = fileManagerClient.getFileUrl(s, fileType).execute().body();
                if (jsURL.isIsSuccessfull())
                    fileUrls.add(jsURL.getResult().getFileAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileUrls;
    }

    public static String getOneFileAddress(String fileId , String fileType){
        FileURLResult fileURLResult = null;
        try {
            ResultJsonGetFileURL jsURL = fileManagerClient.getFileUrl(fileId, fileType).execute().body();
            if (jsURL.isIsSuccessfull())
               return jsURL.getResult().getFileAddress();
        }catch (Exception e){}
        return null;
    }

    public static FileByteArrayResult getFileByteArray(String fileId , String fileType){
        FileByteArrayResult fileByteArrayResult = null;
        try {
            ResultJsonGetFileByteArray jsByteArray = fileManagerClient.getFileByteArray(fileId , fileType).execute().body();
            if(jsByteArray.isIsSuccessfull())
                fileByteArrayResult = jsByteArray.getResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileByteArrayResult;
    }
}
