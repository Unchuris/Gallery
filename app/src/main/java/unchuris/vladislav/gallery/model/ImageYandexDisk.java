package unchuris.vladislav.gallery.model;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

/**
 * The image model from the Yandex disk.
 */
public class ImageYandexDisk implements Parcelable {
    /**
     * Image Name.
     */
    private String name;

    /**
     * Preview link.
     */
    private String preview;

    /**
     * Image size in bytes
     */
    private Integer size;

    /**
     * Time of creation.
     */
    private DateTime created;

    /**
     * Time of modified.
     */
    private DateTime modified;

    /**
     * Constructor
     * @param in parcel.
     */
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
        /**
         * Create from parcel.
         * @param in parcel.
         * @return new ImageYandexDisk(in).
         */
        @Override
        public ImageYandexDisk createFromParcel(final Parcel in) {
            return new ImageYandexDisk(in);
        }

        /**
         * New array.
         * @param size array size.
         * @return newArray.
         */
        @Override
        public ImageYandexDisk[] newArray(final int size) {
            return new ImageYandexDisk[size];
        }
    };

    /**
     * Set image name.
     * @param name image name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get image name.
     * @return image name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get image size.
     * @return image size in bytes.
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sei image size.
     * @param size image size in bytes.
     */
    public void setSize(final Integer size) {
        this.size = size;
    }

    /**
     * Get preview link.
     * @return preview link.
     */
    public String getPreview() {
        return preview;
    }

    /**
     * Set preview link.
     * @param preview preview link.
     */
    public void setPreview(final String preview) {
        this.preview = preview;
    }

    /**
     * Get time of creation.
     * @return time of creation.
     */
    public DateTime getDateTimeCreated() {
        return created;
    }

    /**
     * Set time of creation.
     * @param created time of creation.
     */
    public void setDateTimeCreated(final DateTime created) {
        this.created = created;
    }

    /**
     * Get time of modified.
     * @return time of modified.
     */
    public DateTime getDateTimeModified() {
        return modified;
    }

    /**
     * Set time of modified.
     * @param modified time of modified.
     */
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
}
