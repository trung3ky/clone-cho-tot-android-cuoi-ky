package hqtrung.hqt.cuoi_ky.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Window;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class Picture implements Parcelable {
    private String path;
    private int selectcount;
    private int position;

    public Picture(){

    }

    protected Picture(Parcel in) {
        path = in.readString();
        selectcount = in.readInt();
        position = in.readInt();
    }

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSelectcount() {
        return selectcount;
    }

    public void setSelectcount(int selectcount) {
        this.selectcount = selectcount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getSelectcount() == ((Picture)obj).getSelectcount();
    }
    public static ArrayList<Picture> getGalleryPhotos(Context context){
        ArrayList<Picture> pictures = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] colums = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;

        Cursor cursorPhotos = context.getContentResolver().query(uri, colums, null, null, orderBy);
        if (cursorPhotos !=  null && cursorPhotos.getCount() > 0 ){
            while (cursorPhotos.moveToNext()){
                Picture picture = new Picture();

                int indexPath = cursorPhotos.getColumnIndex(MediaStore.MediaColumns.DATA);
                picture.setPath(cursorPhotos.getString(indexPath));

                pictures.add(picture);
            }
        }

        Collections.reverse(pictures);

        return pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(selectcount);
        dest.writeInt(position);
    }
}
