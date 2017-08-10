package hu.renesans.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import hu.renesans.testlib.EventSender;
import hu.renesans.testlib.TestEvent;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends Activity {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText numberEditText = (EditText) findViewById(R.id.et_number);
        final EditText textEditText = (EditText) findViewById(R.id.et_text);
        final Button sendButton = (Button) findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestObject object = new TestObject();

                try {
                    object.number = Integer.parseInt(numberEditText.getText().toString());
                } catch (NumberFormatException e) {
                    object.number = 0;
                }

                object.text = textEditText.getText().toString();

                EventSender.sendEvent(object);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void handleTestEvent(TestEvent event) {
        TestObject object = GSON.fromJson(event.getJson(), TestObject.class);
        String toastText = String.format(Locale.getDefault(), "number: %s, text: %s", object.number, object.text);
        Toast.makeText(this, toastText, LENGTH_SHORT).show();
    }
}
