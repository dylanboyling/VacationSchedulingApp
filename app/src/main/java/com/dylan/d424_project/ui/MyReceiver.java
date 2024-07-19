package com.dylan.d424_project.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.dylan.d424_capstone.R;

public class MyReceiver extends BroadcastReceiver {

    private static String CHANNEL_ID = "test";

    static int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("reminderMessage"))
                .build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, notification);
    }

    private void createNotificationChannel(Context context) {
        final CharSequence name = "my channel name";
        final String description = "my channel desc";
        final int importance = NotificationManager.IMPORTANCE_DEFAULT;

        final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        final NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}