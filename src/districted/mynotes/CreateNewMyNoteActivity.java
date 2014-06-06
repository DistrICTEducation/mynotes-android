package districted.mynotes;

import districted.mynotes.model.IPersistency;
import districted.mynotes.model.MyNote;
import districted.mynotes.model.MyNotePersistency;
import districted.mynotes.model.PersistencyException;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CreateNewMyNoteActivity extends Activity {
	
	private static String TAG = "mynotes";
	
	private IPersistency persistency = new MyNotePersistency(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_my_note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.create_new_my_note, menu);
		return true;
	}

	public void createMyNote(View view) {
		
		EditText titleField = (EditText) findViewById(R.id.create_mynote_title);
		EditText textField = (EditText) findViewById(R.id.create_mynote_text);
		String title = titleField.getText().toString().trim();
		String text = textField.getText().toString().trim();
		
		if (title.isEmpty() || text.isEmpty()) {
			new AlertDialog.Builder(this).setTitle("Wrong input:")
				.setMessage("Please make sure both the title and text fields are filled in.")
				.setNeutralButton("Close", null).show();
		} else {
			try {
				persistency.insert(new MyNote(title, text));
			} catch (PersistencyException pe) {
				Log.e(TAG, pe.getLocalizedMessage());
			}
			finish();
	        startActivity(new Intent(this, MainActivity.class));
		}
	}
	
	public void cancelOperation(View view) {
		finish();
        startActivity(new Intent(this, MainActivity.class));
	}
}
