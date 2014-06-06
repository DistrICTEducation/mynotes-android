package districted.mynotes;

import districted.mynotes.model.IPersistency;
import districted.mynotes.model.MyNote;
import districted.mynotes.model.MyNotePersistency;
import districted.mynotes.model.PersistencyException;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MyNoteActivity extends Activity {
	
	private static String TAG = "mynotes";

	private IPersistency persistency;
	private MyNote myNote;
	
	private EditText titleField;
	private EditText textField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_note);
		Bundle bundle = this.getIntent().getExtras();
		myNote = bundle.getParcelable("mynote");
		
		titleField = (EditText) findViewById(R.id.edit_mynote_title);
		textField = (EditText) findViewById(R.id.edit_mynote_text);
		titleField.setText(myNote.getTitle());
		textField.setText(myNote.getText());
		
		persistency = new MyNotePersistency(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.my_note, menu);
		return true;
	}

	public void updateMyNote(View view) {
		
        EditText titleField = (EditText) findViewById(R.id.edit_mynote_title);
		EditText textField = (EditText) findViewById(R.id.edit_mynote_text);
		String title = titleField.getText().toString().trim();
		String text = textField.getText().toString().trim();
		
		if (title.isEmpty() || text.isEmpty()) {
			new AlertDialog.Builder(this).setTitle("Wrong input:")
				.setMessage("Please make sure both the title and text fields are filled in.")
				.setNeutralButton("Close", null).show();
		} else {
			myNote.setTitle(title);
			myNote.setText(text);
			persistency.update(myNote);
			
			finish();
	        startActivity(new Intent(this, MainActivity.class));
		}
	}
	
	public void deleteMyNote(View view) {
		new AlertDialog.Builder(this).setTitle("Warning")
			.setMessage("Are you sure you want to delete this note?")
			.setNegativeButton("Cancel", null)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	try {
	        			persistency.delete(myNote);
	        		} catch (PersistencyException pe) {
	        			Log.e(TAG, pe.getLocalizedMessage());
	        		}
	        		MyNoteActivity.this.finish();
	        		MyNoteActivity.this.startActivity(new Intent(MyNoteActivity.this, MainActivity.class));
	            }
	        })
			.show();
	}

	public void cancelOperation(View view) {
		finish();
        startActivity(new Intent(this, MainActivity.class));
	}
}
