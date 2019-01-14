package apps.hillavas.com.yoga.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohsen.mohammadi on 6/24/2017.
 */

public class Home_Pager_Page implements Parcelable{

    private int id;
    private int imageBackId;
    private int imageMovieIconId;
    private String txtMovie;
    private String txtText;
    private int viewCount;
    private int likeCount;
    private int dateDayNumber;
    private String dateMounthName;
    private String dateYearName;
    private String imageFileIdString;
    private String image2FileIdString;
    private String videoFileIdString;
    private String description;
    private int price;
    private boolean isFree;

    protected Home_Pager_Page(Parcel in) {
        id = in.readInt();
        imageBackId = in.readInt();
        imageMovieIconId = in.readInt();
        txtMovie = in.readString();
        txtText = in.readString();
        viewCount = in.readInt();
        likeCount = in.readInt();
        dateDayNumber = in.readInt();
        dateMounthName = in.readString();
        dateYearName = in.readString();
        imageFileIdString = in.readString();
        videoFileIdString = in.readString();
        description = in.readString();
        image2FileIdString = in.readString();
    }
    public Home_Pager_Page(){

    }
    public static final Creator<Home_Pager_Page> CREATOR = new Creator<Home_Pager_Page>() {
        @Override
        public Home_Pager_Page createFromParcel(Parcel in) {
            return new Home_Pager_Page(in);
        }

        @Override
        public Home_Pager_Page[] newArray(int size) {
            return new Home_Pager_Page[size];
        }
    };

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoFileIdString() {
        return videoFileIdString;
    }

    public void setVideoFileIdString(String videoFileIdString) {
        this.videoFileIdString = videoFileIdString;
    }

    public String getImageFileIdString() {
        return imageFileIdString;
    }

    public void setImageFileIdString(String imageFileIdString) {
        this.imageFileIdString = imageFileIdString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageBackId() {
        return imageBackId;
    }

    public void setImageBackId(int imageBackId) {
        this.imageBackId = imageBackId;
    }

    public int getImageMovieIconId() {
        return imageMovieIconId;
    }

    public void setImageMovieIconId(int imageMovieIconId) {
        this.imageMovieIconId = imageMovieIconId;
    }

    public String getTxtMovie() {
        return txtMovie;
    }

    public void setTxtMovie(String txtMovie) {
        this.txtMovie = txtMovie;
    }

    public String getTxtText() {
        return txtText;
    }

    public void setTxtText(String txtText) {
        this.txtText = txtText;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDateDayNumber() {
        return dateDayNumber;
    }

    public void setDateDayNumber(int dateDayNumber) {
        this.dateDayNumber = dateDayNumber;
    }

    public String getDateMounthName() {
        return dateMounthName;
    }

    public void setDateMounthName(String dateMounthName) {
        this.dateMounthName = dateMounthName;
    }

    public String getDateYearName() {
        return dateYearName;
    }

    public void setDateYearName(String dateYearName) {
        this.dateYearName = dateYearName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImage2FileIdString() {
        return image2FileIdString;
    }

    public void setImage2FileIdString(String image2FileIdString) {
        this.image2FileIdString = image2FileIdString;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(imageBackId);
        dest.writeInt(imageMovieIconId);
        dest.writeString(txtMovie);
        dest.writeString(txtText);
        dest.writeInt(viewCount);
        dest.writeInt(likeCount);
        dest.writeInt(dateDayNumber);
        dest.writeString(dateMounthName);
        dest.writeString(dateYearName);
        dest.writeString(imageFileIdString);
        dest.writeString(videoFileIdString);
        dest.writeString(description);
        dest.writeString(image2FileIdString);
    }
}
