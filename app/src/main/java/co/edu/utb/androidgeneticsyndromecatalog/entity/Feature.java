package co.edu.utb.androidgeneticsyndromecatalog.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by william on 21/06/16.
 */
public class Feature implements Parcelable{

    private int id;
    private String name;

    public Feature(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Feature(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        Feature that = (Feature)o;
        return this.id == that.id;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);

    }

    public static final Parcelable.Creator<Feature> CREATOR = new Parcelable.Creator<Feature>() {
        public Feature createFromParcel(Parcel in) {
            return new Feature(in);
        }

        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
}
