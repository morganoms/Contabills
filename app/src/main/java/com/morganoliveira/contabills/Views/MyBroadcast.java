package com.morganoliveira.contabills.Views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.morganoliveira.contabills.R;

/**
 * Created by Morgan Oliveira on 14/04/2018.
 */

public class MyBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nf = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 100, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Toast.makeText(context, "Ação com broadcast...", Toast.LENGTH_LONG).show();

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel canal1 = new NotificationChannel("default", "teste", NotificationManager.IMPORTANCE_DEFAULT);
            canal1.setDescription("descricao");
            nf.createNotificationChannel(canal1);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX)
                .setCategory(Notification.CATEGORY_CALL)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Você Possui Contas Atrasadas")
                .setContentText("Clique aqui para voltar ao Contabills");

        nf.notify(100, builder.build());
    }
}
