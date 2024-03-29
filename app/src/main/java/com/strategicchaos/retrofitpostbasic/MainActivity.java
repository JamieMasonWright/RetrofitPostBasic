package com.strategicchaos.retrofitpostbasic;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.strategicchaos.retrofitpostbasic.data.Post;
import com.strategicchaos.retrofitpostbasic.data.remote.APIService;
import com.strategicchaos.retrofitpostbasic.data.remote.ApiUtils;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
	private TextView mResponseTv;
	private APIService mAPIService;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText titleEt = (EditText) findViewById(R.id.et_title);
		final EditText bodyEt = (EditText) findViewById(R.id.et_body);
		Button submitButton = (Button) findViewById(R.id.btn_submit);
		mResponseTv = (TextView) findViewById(R.id.tv_response);

		mAPIService = ApiUtils.getAPIService();
		submitButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String title = titleEt.getText().toString().trim();
				String body = bodyEt.getText().toString().trim();
				if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body))
				{
					sendPost(title, body);
				}
			}

		});
	}

	public void sendPost(String title, String body)
	{
		mAPIService.savePost(title, body, 1).enqueue(new Callback<Post>()
		{
			@Override
			public void onResponse(Call<Post> call, Response<Post> response)
			{
				if (response.isSuccessful())
				{
					showResponse(response.body().toString());
					Log.i(TAG, "has been sent");
				}
			}

			@Override
			public void onFailure(Call<Post> call, Throwable t)
			{
				Log.e(TAG, "Unable to submit post to API.");
			}

		});
	}

	public void showResponse(String response)
	{
		if (mResponseTv.getVisibility() == View.GONE)
		{
			mResponseTv.setVisibility(View.VISIBLE);
		}
		mResponseTv.setText(response);
	}
}
