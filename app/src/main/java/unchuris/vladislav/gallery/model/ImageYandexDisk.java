package unchuris.vladislav.gallery.model;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * The image model from the Yandex disk.
 */
public final class ImageYandexDisk implements Parcelable {

    private String name;

    private String preview;

    /**
     * Image size in bytes
     */
    private Integer size;

    private DateTime created;

    private DateTime modified;

    public ImageYandexDisk() {

    }

    private ImageYandexDisk(final Parcel in) {
        name = in.readString();
        preview = in.readString();
        created = (DateTime) in.readSerializable();
        modified = (DateTime) in.readSerializable();
        size = (in.readByte() == 0) ? null : in.readInt();
    }

    /**
     * Create an instance of the ImageYandexDisk and fill it with data from Parcel.
     */
    public static final Creator<ImageYandexDisk> CREATOR = new Creator<ImageYandexDisk>() {
        @Override
        public ImageYandexDisk createFromParcel(final Parcel in) {
            return new ImageYandexDisk(in);
        }
        @Override
        public ImageYandexDisk[] newArray(final int size) {
            return new ImageYandexDisk[size];
        }
    };

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

    public String getPreview() {
        return preview;
    }

    /**
     * Get preview link.
     * @param stringSize size (M, L, XL, XXL, XXXL)
     * @return preview link.
     */
    public String getPreview(final String stringSize) {
        return preview.replace("?", "?size=" + stringSize + "&");
    }

    public void setPreview(final String preview) {
        this.preview = preview;
    }

    public DateTime getDateTimeCreated() {
        return created;
    }

    public void setDateTimeCreated(final DateTime created) {
        this.created = created;
    }

    public DateTime getDateTimeModified() {
        return modified;
    }

    public void setDateTimeModified(final DateTime modified) {
        this.modified = modified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(name);
        dest.writeString(preview);
        dest.writeInt(size);
        dest.writeSerializable(created);
        dest.writeSerializable(modified);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageYandexDisk that = (ImageYandexDisk) o;
        return size.equals(that.size) && (name != null ? name.equals(that.name)
                : that.name == null) && (created != null ? created.equals(that.created)
                : that.created == null) && (modified != null ? modified.equals(that.modified)
                : that.modified == null) && (preview != null ? preview.equals(that.preview)
                : that.preview == null);
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (preview != null ? preview.hashCode() : 0);
        result = 31 * result + size;
        return result;
    }
}
