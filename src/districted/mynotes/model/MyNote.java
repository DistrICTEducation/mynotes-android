package districted.mynotes.model;

import java.sql.Timestamp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>
 * MyNote is a wrapper class for a note data type containing a title,
 * a text and some meta-data.
 * </p>
 * @author Joris Schelfaut, DistrICT Education
 */
public class MyNote implements Parcelable {

	private String title;
	private String text;
	private long id;
	private Timestamp timeCreated;
	
	public static final Parcelable.Creator<MyNote> CREATOR = new Parcelable.Creator<MyNote>() {

		@Override
		public MyNote createFromParcel(Parcel source) {
			return new MyNote(source);
		}

		@Override
		public MyNote[] newArray(int size) {
			return new MyNote[size];
		}
	};
	
	public MyNote() {
		super();
	}
	
	public MyNote(String title, String text) throws NullPointerException, IllegalArgumentException {
		this();
		this.setTitle(title);
		this.setText(text);
	}
	
	public MyNote(String title, String text, long id, Timestamp timeCreated) throws NullPointerException, IllegalArgumentException {
		this(title, text);
		this.setID(id);
		this.setTimeCreated(timeCreated);
	}
	
	public MyNote(String title, String text, long id, String timeCreated) throws NullPointerException, IllegalArgumentException {
		this(title, text);
		this.setID(id);
		this.setTimeCreated(timeCreated);
	}
	
	public MyNote(Parcel parcel) {
		this(parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readString());
	}
	
	public final void setTitle(String title) throws NullPointerException, IllegalArgumentException {
		if (title == null) throw new NullPointerException("MyNote.title resolved as NULL.");
		if (title.length() == 0) throw new IllegalArgumentException("No empty MyNote.title allowed.");
		this.title = title;
	}
	
	public final void setText(String text) throws NullPointerException, IllegalArgumentException {
		if (text == null) throw new NullPointerException("MyNote.text resolved as NULL.");
		if (text.length() == 0) throw new IllegalArgumentException("No empty MyNote.text allowed.");
		this.text = text;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getText() {
		return text;
	}
		
	final void setID(long id) {
		this.id = id;
	}
	
	final void setTimeCreated(Timestamp timeCreated) {
		if (timeCreated == null) throw new NullPointerException("MyNote.timeCreated resolved as NULL.");
		this.timeCreated = timeCreated;
	}
	
	final void setTimeCreated(String timeCreated) {
		setTimeCreated(Timestamp.valueOf(timeCreated));
	}
	
	public long getID() {
		return id;
	}
	
	public Timestamp getTimeCreated() {
		return timeCreated;
	}
	
	@Override
	public String toString() {
		return this.getTitle();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (! (o instanceof MyNote)) return false;
		MyNote m = (MyNote) o;
		if (m.getID() != this.getID()) return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getTitle());
		dest.writeString(this.getText());
		dest.writeLong(this.getID());
		dest.writeString(this.getTimeCreated().toString());
	}
}
