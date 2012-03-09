package de.yogularm.boxcontacts.view;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import de.yogularm.boxcontacts.Config;
import de.yogularm.boxcontacts.R;
import de.yogularm.boxcontacts.model.Call;
import de.yogularm.boxcontacts.network.Session;
import de.yogularm.boxcontacts.utils.UnexpectedResponseException;

public class ContactsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Config config = new Config();
        config.setHost("fritz.box");
        config.setPassword("melcher");
        config.setIsRemote(false);
        Session session = new Session(config);
        try {
					if (session.login()) {
						List<Call> calls = session.getCalls();
						Log.d("ContactsActivity", calls.size() + " calls");
					} else
						Log.d("ContactsActivity", "Login failed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnexpectedResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
}