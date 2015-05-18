package science.com.smartbrowse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class IntroActivity extends Activity {

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent = new Intent(this, BubbleService.class);
        serviceIntent.putExtra("From", "1");
        startService(serviceIntent);
        final Intent intent = getIntent();
        final String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            serviceIntent.putExtra("From", "2");
            startService(serviceIntent);
        }
        finish();
    }

}
