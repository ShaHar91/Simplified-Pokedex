package be.appwise.simplifiedPokedex.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import be.appwise.simplifiedPokedex.R
import be.appwise.simplifiedPokedex.ui.main.MainActivity
import java.util.*

class NotificationIntentService : JobIntentService() {
    companion object {
        private const val CHANNEL_ID = "CubigoChannel"
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, setComponent: Intent) {
            enqueueWork(context, NotificationIntentService::class.java, JOB_ID, setComponent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                initializeNotification(this@NotificationIntentService)
            }
        }, 10000)
    }

    private fun getNotificationManager(context: Context, CHANNEL_ID: String): NotificationManager? {
        val mNotificationMgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "simplePokedexChannel"
            val description = "This is a simplePokedex notification channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = description
            mChannel.setShowBadge(false)
            mNotificationMgr.createNotificationChannel(mChannel)
        }

        return mNotificationMgr
    }

    private fun initializeNotification(context: Context) {
        val notificationManager = getNotificationManager(context, CHANNEL_ID)

        // The intent we get when clicking on the notification.
        val resultIntent = Intent(context, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        resultIntent.action = java.lang.Long.toString(System.currentTimeMillis())
        resultIntent.putExtra("rating", true)

        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentTitle("What is your opinion?")
            .setColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("How is your experience with this app, tap here to rate it!")
            )
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_VIBRATE)
            .setContentIntent(resultPendingIntent)

        notificationManager?.notify(1, mBuilder.build())
    }
}