package org.akshanshgusain.notificationchannels;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mTitle, mDesc;
    private Button mSendOne, mSendTwo;

    private NotificationManagerCompat mNotificationManager;
    private MediaSessionCompat mMediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = findViewById(R.id.notifi_Title);
        mDesc = findViewById(R.id.notifi_Desc);
        mSendOne = findViewById(R.id.notifi_channel1Btn);

        mSendTwo = findViewById(R.id.notifi_channel2Btn);

        mNotificationManager = NotificationManagerCompat.from(this);

        mMediaSessionCompat =  new MediaSessionCompat(this,"tag");
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

         //Now lets add a image to our notification
        //Now this done in the form of a bitmap

//        Bitmap largeImage = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);


          Notification notification = new NotificationCompat.Builder(this, "channel1")
                  .setSmallIcon(R.drawable.ic_goal)
                  .setContentTitle(mTitle.getText().toString().trim())
                  .setContentText(mDesc.getText().toString().trim())
                  .setLargeIcon(drawableToBitmap(getDrawable(R.drawable.ic_goal)))// Set the large Icon for the Notification (Bitmap)
/*                  .setStyle(new NotificationCompat.BigTextStyle()
                          .bigText(getString(R.string.long_dummy_text))<----------- BigTextStyle
                          .setBigContentTitle("Summary Title")
                          .setSummaryText("Summary Text"))*/
                  .setStyle( new NotificationCompat.BigPictureStyle()
                                  .bigPicture(drawableToBitmap(getDrawable(R.drawable.ic_goal)))
                                   )
                  .setContentIntent(contentIntent)
                  .setAutoCancel(true)
                  // setOnlyAlertOnce(boolean) - to show/not show notification repeatedly...
              //    .setColor(Color.CYAN)
               //   .addAction(R.mipmap.ic_launcher_round, "Toast", actionIntent)
                  .build();
        //Now Display the notification
        mNotificationManager.notify(1,notification);

    }
    public void sendToChannel2(View v)
    {
        Notification notification = new NotificationCompat.Builder(this, "channel2")
                .setSmallIcon(R.drawable.ic_glasses)
                .setContentTitle(mTitle.getText().toString().trim())
                .setContentText(mDesc.getText().toString().trim())
                .setLargeIcon(drawableToBitmap(getDrawable(R.drawable.ic_goal)))
          /*      .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("This will be the line 1")
                        .addLine("This will be the line 2")
                        .setBigContentTitle("Summary Title")
                        .setSummaryText("Summary Text")
                )*/
                .addAction(R.drawable.ic_airplanemode_active_black_24dp, "Airplane MOde", null)
                .addAction(R.drawable.ic_airplanemode_active_black_24dp, "Airplane MOde", null)
                .addAction(R.drawable.ic_airplanemode_active_black_24dp, "Airplane MOde", null)
                .addAction(R.drawable.ic_airplanemode_active_black_24dp, "Airplane MOde", null)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 3, 2)
                .setMediaSession(mMediaSessionCompat.getSessionToken()))
                .build();
        //Now Display the notification
        mNotificationManager.notify(2,notification);
    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
