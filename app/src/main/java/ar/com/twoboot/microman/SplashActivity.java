package ar.com.twoboot.microman;

import android.app.Activity;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import ar.com.twoboot.microman.util.Util;
import ar.com.twoboot.microman.util.Permiso;
import android.widget.Toast;
import ar.com.twoboot.microman.R;


public class SplashActivity extends AppCompatActivity {

	private static final int TIME = 4 * 1000;// 4 seconds
	private TextView tvVersion;
	private boolean solicitando;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//tvVersion=(TextView) findViewById(R.id.tvVersion);
		//tvVersion.setText("Version: "+Util.VERSION_MICROMAN.toString());


	}
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case Permiso.REQUEST: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED
						&& grantResults[1] == PackageManager.PERMISSION_GRANTED
						&& grantResults[2] == PackageManager.PERMISSION_GRANTED
						&& grantResults[3] == PackageManager.PERMISSION_GRANTED
						&& grantResults[4] == PackageManager.PERMISSION_GRANTED) {
					lanzarapp();
					finish();
				} else {
					Toast.makeText(this, "Es necesario que concedas los permisos",
							Toast.LENGTH_LONG).show();
					solicitando = false;
					finish();
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}
	private void lanzarapp() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent(SplashActivity.this, MicroMan.class);
				startActivity(intent);

				SplashActivity.this.finish();

				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

			}
		}, TIME);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, TIME);


	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!solicitando) {
			if (Permiso.check(this)) {
				lanzarapp();
			} else {
				solicitando = true;
				Permiso.solicitar(this);
			}
		}
	}

}
