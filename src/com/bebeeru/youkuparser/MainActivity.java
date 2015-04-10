package com.bebeeru.youkuparser;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mUrlView;
	private TextView mInfoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
	}

	private void initViews() {
		mUrlView = (EditText) findViewById(R.id.et_url);
		mInfoView = (TextView) findViewById(R.id.tv_info);
		findViewById(R.id.btn_parse).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						new ParserTask().execute();
					}
				});
	}

	private class ParserTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected String doInBackground(String... params) {
			return Parser.parse();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				mInfoView.setText(result);
			}
		}
	}

}
