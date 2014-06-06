package districted.mynotes.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.util.Log;

public class TestData implements IPersistency {

	private static final String TAG = "TestData";
	private static long ID = 0;
	
	// Database contents :
	private final Map<Long, MyNote> myNotes = new HashMap<Long, MyNote>();
	
	private static IPersistency persistency;
	
	public static IPersistency getInstance() {
		if (TestData.persistency == null) TestData.persistency = new TestData();
		return TestData.persistency;
	}
	
	private TestData() {
		super();
		this.insert(new MyNote("Example MyNote 1", "This is an example note."));
		this.insert(new MyNote("Example MyNote 2", "This is an example note."));
		this.insert(new MyNote("Example MyNote 3", "This is an example note."));
		this.insert(new MyNote("Example MyNote 4", "This is an example note."));
	}

	@Override
	public long insert(MyNote myNote) {
		// yyyy-MM-dd HH:mm:ss.nnnnnnnnn
		myNote.setID(++ID);
		myNote.setTimeCreated(Timestamp.valueOf("2014-06-03 14:23:00.000000000"));
		this.myNotes.put(myNote.getID(), myNote);
		Log.v(TAG, "MyNote(" + myNote.getID() + "," + myNote.getTimeCreated() + "," + myNote.getTitle() + "," + myNote.getText());
		return 0;
	}

	@Override
	public void update(MyNote myNote) {
		this.myNotes.put(myNote.getID(), myNote);
	}

	@Override
	public void delete(MyNote myNote) {
		this.myNotes.remove(myNote.getID());
	}

	@Override
	public MyNote select(long id) {
		return this.myNotes.get(id);
	}

	@Override
	public List<MyNote> selectAll() {
		return new ArrayList<MyNote>(myNotes.values());
	}

	@Override
	public int recordCount() {
		return this.myNotes.values().size();
	}

	@Override
	public void closeTransaction() {
		Log.i("TestData", "Transaction close.");
	}
}
