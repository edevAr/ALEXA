package alexa.eden.gogenesis.alexa;

import alexa.eden.gogenesis.alexa.pubnub.PubNubHandler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PubNubHandler.PubNubNotifier{

    private Button senderNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    @Override
    public void onMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "ESTA LLEGANDO "+message, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initialize() {
        senderNotification = (Button)findViewById(R.id.senderNotification);
        senderNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PubNubHandler.getInstance(MainActivity.this);
                PubNubHandler.getInstance().init();
                PubNubHandler.getInstance().suscribe("my_channel");
                PubNubHandler.getInstance().publish("my_channel", "hello world");
            }
        });
    }
}
