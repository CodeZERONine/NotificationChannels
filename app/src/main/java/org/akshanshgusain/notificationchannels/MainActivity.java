package org.akshanshgusain.notificationchannels;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mTitle, mDesc;
    private Button mSendOne, mSendTwo;

    private NotificationManagerCompat mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = findViewById(R.id.notifi_Title);
        mDesc = findViewById(R.id.notifi_Desc);
        mSendOne = findViewById(R.id.notifi_channel1Btn);

        mSendTwo = findViewById(R.id.notifi_channel2Btn);

        mNotificationManager = NotificationManagerCompat.from(this);
    }
    public void sendToChannel1(View v)
    {
       /* Now, We will create an action for the notification,*/
        /*Then we will create a pending intent*/

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "You have clicked Notification 1");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);



          Notification notification = new NotificationCompat.Builder(this, "channel1")
                                                     .setSmallIcon(R.drawable.ic_goal)
                .setContentTitle(mTitle.getText().toString().trim())
                .setContentText(mDesc.getText().toString().trim())
                  .setContentIntent(contentIntent)
                  .setAutoCancel(true)
                  // setOnlyAlertOnce(boolean) - to show/not show notification repeatedly...
                   .setColor(Color.CYAN)
                  .addAction(R.mipmap.ic_launcher_round,"Toast",actionIntent)
                  .build();
        //Now Display the notification
        mNotificationManager.notify(1,notification);

    }
    public void sendToChannel2(View v)
    {
        Notification notification = new NotificationCompat.Builder(this, "channel2")
                .setSmallIcon(R.drawable.ic_glasses)
                .setContentTitle(mTitle.getText().toString().trim())
                .setContentText(mDesc.getText().toString().trim()).build();
        //Now Display the notification
        mNotificationManager.notify(2,notification);
    }
}
