package nu.placebo.whatsup.activity;

import nu.placebo.whatsup.R;
import nu.placebo.whatsup.constants.Constants;
import nu.placebo.whatsup.ctrl.SessionHandler;
import nu.placebo.whatsup.model.SessionInfo;
import nu.placebo.whatsup.network.Login;
import nu.placebo.whatsup.network.NetworkOperationListener;
import nu.placebo.whatsup.network.NetworkTask;
import nu.placebo.whatsup.network.OperationResult;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * Activity with the log in form.
 * 
 */

public class LogInActivity extends Activity implements OnClickListener,
		NetworkOperationListener<SessionInfo> {

	private String userName;
	private String password;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);

		Button logIn = (Button) this.findViewById(R.id.log_in);
		logIn.setOnClickListener(this);
		this.result = (TextView) this.findViewById(R.id.result);
		
	}

	public void onClick(View view) {
		if(view.getId() == R.id.log_in){
			this.userName = ((TextView) this.findViewById(R.id.user_name))
					.getText().toString();
			this.password = ((TextView) this.findViewById(R.id.password)).getText()
					.toString();
			this.result.setText("Logging in...");
			((Button) this.findViewById(R.id.log_in)).setEnabled(false);
			Login login = new Login(this.userName, this.password);
			login.addOperationListener(this);
			new NetworkTask<SessionInfo>().execute(login);
			this.result.setText("Logging in...");
		}
	}

	public void operationExcecuted(final OperationResult<SessionInfo> r) {
		if (!r.hasErrors()) {
			SessionHandler sh = SessionHandler.getInstance(LogInActivity.this);
			sh.saveSession(r.getResult());
			sh.saveCredentials(userName, password);
			this.setResult(Constants.ACTIVITY_FINISHED_OK);
			this.finish();
			Toast.makeText(this, "Logged in", Toast.LENGTH_SHORT).show();
		} else {
			result.setText("Failed to login: " + r.getStatusMessage());
			((Button) this.findViewById(R.id.log_in)).setEnabled(true);
		}
	}
}
