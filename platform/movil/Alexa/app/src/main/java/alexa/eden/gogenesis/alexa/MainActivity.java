package alexa.eden.gogenesis.alexa;

import alexa.eden.gogenesis.alexa.pubnub.PubNubHandler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button senderNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
    private void initialize() {
        senderNotification = (Button)findViewById(R.id.senderNotification);
        senderNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PubNubHandler.getInstance().init();
                PubNubHandler.getInstance().suscribe("");
            }
        });
    }
}
