package edu.washington.wsmay1.arewethereyet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class MainActivity extends ActionBarActivity {
    private static int interval = 0;
    private static String messageText;
    private static String phoneNumber;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitButton = (Button) findViewById(R.id.submit);
        final EditText message = (EditText) findViewById(R.id.messagePrompt);
        final EditText phoneNumber = (EditText) findViewById(R.id.numberInput);
        final EditText frequency = (EditText) findViewById(R.id.frequencyInput);
        final Intent messageServiceIntent = new Intent(this, MessageService.class);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (!started) {
                    handleButtonStart(message, phoneNumber, frequency, b, messageServiceIntent);
                    started = true;
                } else {
                    handleButtonStop(b, messageServiceIntent);
                    started = false;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleButtonStart(EditText message, EditText phoneNum, EditText frequency, Button v, Intent i) {
        if (message.getText().toString().length() > 0 && phoneNum.getText().toString().length() == 10 && frequency.getText().toString().length() > 0) {
            if (Integer.parseInt(frequency.getText().toString()) > 0 && phoneNum.getText().toString().matches("[0-9]+")) {
                v.setText("Stop");
                interval = Integer.parseInt(frequency.getText().toString());
                messageText = message.getText().toString();
                phoneNumber = phoneNum.getText().toString();
                startService(i);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a number greater than 0 for your frequency, and make sure the phone number contains only numbers", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please check your input, all fields required, and the phone number should be 10 digits.", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleButtonStop(Button b, Intent i) {
        b.setText("Start");
        stopService(i);
        interval = 0;
    }

    public static int getInterval() {
        return interval;
    }

    public static String getMessageText() {
        return messageText;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }
}