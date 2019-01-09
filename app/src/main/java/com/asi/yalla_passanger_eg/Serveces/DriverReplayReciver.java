package com.asi.yalla_passanger_eg.Serveces;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.asi.yalla_passanger_eg.R;
import com.asi.yalla_passanger_eg.TripCompletedActivity;

/**
 * Created by m.khalid on 7/17/2017.
 */

public class DriverReplayReciver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getStringExtra("type").equals("1"))
        {

            generateNotification(context,context.getResources().getString(R.string.YourTripConfirmed));

        }else if (intent.getStringExtra("type").equals("2")){
            generateNotification(context,context.getResources().getString(R.string.yourcarHasbeenarrived));
        }else if (intent.getStringExtra("type").equals("3")){

            // trip started
            generateNotification(context,context.getResources().getString(R.string.yourTripHasbeenStarted));
        }else if (intent.getStringExtra("type").equals("4")){

            // trip completed
            generateNotification(context,context.getResources().getString(R.string.yourTripHasbeenCompleted));
        }else if (intent.getStringExtra("type").equals("5")){

            // trip fare updated
            generateNotification(context,context.getResources().getString(R.string.tripfareupdated));
            Intent intent1=new Intent(context, TripCompletedActivity.class);
            intent1.putExtra("totalcost",intent.getStringExtra("totalcost"));
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent1);
        }
    }


    public void generateNotification(Context context, String message)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(message);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(001, mBuilder.build());
    }

}
