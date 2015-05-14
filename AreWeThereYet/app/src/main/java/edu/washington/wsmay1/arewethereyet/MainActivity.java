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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submitButton = (Button) findViewById(R.id.submit);
        final EditText message = (EditText) findViewById(R.id.messagePrompt);
        final EditText phoneNumber = (EditText) findViewById(R.id.numberInput);
        final EditText frequency = (EditText) findViewById(R.id.frequencyInput);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                if (interval == 0) {
                    handleButtonStart(message, phoneNumber, frequency, b);
                } else {
                    handleButtonStop(b);
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

    public void handleButtonStart(EditText message, EditText phoneNum, EditText frequency, Button v) {
        if (message.getText().toString().length() > 0 && phoneNum.getText().toString().length() == 10 && frequency.getText().toString().length() > 0) {
            v.setText("Stop");
            interval = Integer.parseInt(frequency.getText().toString());
            messageText = message.getText().toString();
            phoneNumber = phoneNum.getText().toString();
            Intent beginService = new Intent(this, MessageService.class);
            startService(beginService);
        } else {
            Toast.makeText(getApplicationContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), phoneNum.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), frequency.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void handleButtonStop(Button b) {
        b.setText("Start");
        Intent endService = new Intent(this, MessageService.class);
        startService(endService);
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
