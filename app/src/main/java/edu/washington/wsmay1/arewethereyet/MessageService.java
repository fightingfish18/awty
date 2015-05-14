package edu.washington.wsmay1.arewethereyet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.Runnable;
import android.widget.Toast;
public class MessageService extends Service {
    private Timer messageTime = null;
    public static final int INTERVAL = MainActivity.getInterval();
    private String message = MainActivity.getMessageText();
    private String phone = MainActivity.getPhoneNumber();
    private Handler messageHandler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (messageTime != null) {
            messageTime.cancel();
        } else {
            messageTime = new Timer();
            messageTime.scheduleAtFixedRate(new MessageDisplayTask(), 0, INTERVAL * 60000);
        }
    }

    @Override
    public void onDestroy(){
        Toast.makeText(this, "Sending Cancelled", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        messageTime.cancel();
    }

    class MessageDisplayTask extends TimerTask {
        @Override
        public void run() {
            messageHandler.post(new Runnable() {
                @Override
                public void run() {
                    String formattedPhone = "(" + phone.substring(0,3) + ")" + " " + phone.substring(3,6) + "-" + phone.substring(6);
                    message = formattedPhone + " : Are we there yet?";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
