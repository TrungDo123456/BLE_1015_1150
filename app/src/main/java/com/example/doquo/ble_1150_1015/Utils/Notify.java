package com.example.doquo.ble_1150_1015.Utils;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;

import com.example.doquo.ble_1150_1015.R;

/**
 * Created by doquo on 04/07/2018.
 */

public class Notify  {
    public static NotificationCompat.Builder showNotify(Context context, String title, String content, String numberPhone){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_card_giftcard)
                        .setContentTitle(title)
                        .setOngoing(false)//hien mac dinh tren man hinh khoa.
                        .setPriority(2)//xet muc do uu tien
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher_background))
                        .setContentText(content);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numberPhone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        PendingIntent pendingIntent = PendingIntent.getActivities(context,0, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.addAction(R.drawable.ic_contact_phone,"Call Shop",pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
    return mBuilder;
    }
}
