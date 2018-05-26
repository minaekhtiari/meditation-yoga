package classes.tools.helpers;


import classes.models.FileGiver;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by A.Mohammadi on 7/23/2017.
 */

public interface IRetrofitFileManager {
    @GET("file/getfileurl")
    Call<FileGiver> getFiles(@Query("fileid") String fileId,
                             @Query("filetype") int fileType
    );
}
