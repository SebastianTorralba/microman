package ar.com.twoboot.microman;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import ar.com.twoboot.microman.util.CoderLog;

public class CoderLogReader extends Activity {
	private EditText etLogReader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coder_log_reader);
		etLogReader = (EditText) findViewById(R.id.etLogReader);
		etLogReader.setText(CoderLog.leerLog());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coder_log_reader, menu);
		return true;
	}

}
