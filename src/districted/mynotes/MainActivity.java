package districted.mynotes;

import java.util.ArrayList;
import java.util.List;

import districted.mynotes.model.IPersistency;
import districted.mynotes.model.MyNote;
import districted.mynotes.model.MyNotePersistency;
import districted.mynotes.model.PersistencyException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private static String TAG = "mynotes";

	private IPersistency persistency;
	
	// List view
    private ListView listview;
    
    // List view adapter
    private ArrayAdapter<MyNote> adapter;
    
    // Search edit text
    private EditText inputSearch;
    
    // List for list view
    private List<MyNote> mynotes = new ArrayList<MyNote>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.persistency = new MyNotePersistency(this);
		
		// fetch the data from the database :
		try {
			mynotes = persistency.selectAll();
		} catch (PersistencyException pe) {
			Log.e(TAG, pe.getLocalizedMessage());
		} finally {
			persistency.closeTransaction();
		}
		
		// initialize and create screen elements :
		listview = (ListView) findViewById(R.id.listView1);
		adapter = new ArrayAdapter<MyNote>(this, android.R.layout.simple_list_item_1, mynotes);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListenerImplementation());
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new EditTextWatcher());
	}
	
	private class OnItemClickListenerImplementation implements OnItemClickListener {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			Bundle bundle = new Bundle();
			bundle.putParcelable("mynote", mynotes.get(position));
			Intent intent = new Intent(MainActivity.this, MyNoteActivity.class);
			intent.putExtras(bundle);

			finish();
            startActivity(intent);
		}
	}
	
	private class EditTextWatcher implements TextWatcher {
		@Override
	    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
	        MainActivity.this.adapter.getFilter().filter(cs);   
	    }
	    
	    @Override
	    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	    
	    @Override
	    public void afterTextChanged(Editable arg0) {}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createNewMyNote(View view) {
		finish();
        startActivity(new Intent(MainActivity.this, CreateNewMyNoteActivity.class));
	}
}
