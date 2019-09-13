package tech.appzilla.com.setmydate.notification

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import tech.appzilla.com.setmydate.R
import tech.appzilla.com.setmydate.Utils.AppConstants
import tech.appzilla.com.setmydate.Utils.PrefManager
import tech.appzilla.com.setmydate.activity.MainBottomNavigationActivity
import tech.appzilla.com.setmydate.activity.MessageActivity


object NotificationHandler {


    var prefManager : PrefManager ? = null

    //This method would display the notification
    fun showNotificationMessage(
        context: Context,
        title: String,
        message: String,
        subtitle: String,
        noitification_id: Int
    ) {
        val builder = NotificationCompat.Builder(context)
        builder.setSmallIcon(R.color.notification_icon_bg_color)
        builder.setStyle(NotificationCompat.BigTextStyle())
        //TODO:intent based on UID
        // Intent intent = new Intent(mContext, ChatActivity.class);
        // PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        // builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title)
        builder.setContentText("$subtitle : $message")
        builder.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
        builder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
        builder.setAutoCancel(true)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(noitification_id, builder.build())
    }


    //for oreo and all
    fun showNotification(
        context: Context,
        title: String,
        notifyId: Int,
        userId: Int
    ) {

        prefManager = PrefManager.getInstance(context)

        Handler(Looper.getMainLooper()).post {

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channelId = "setMydate-100"
            val channelName = "SetMyDate"


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val mChannel = NotificationChannel(
                    channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(mChannel)
            }
            val mBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.notificationicon)
                .setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.applogo))
                .setChannelId(channelId)
                .setColor(Color.RED)

/*
            Glide.with(context)
                .asBitmap()
                .load(newsImage)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Log.d("RESOURCE", resource.toString())


                        val bigPicStyle = NotificationCompat.BigPictureStyle()
                        bigPicStyle.setBigContentTitle(title)
                        bigPicStyle.bigPicture(resource)
                        bigPicStyle.setBigContentTitle(title)
                        mBuilder.setStyle(bigPicStyle)


                    }
                })
*/

            //pending intent is only for other ids not for 111
            //111 just for display notification
            if(notifyId != 111 && notifyId != 2 && prefManager!!.getPreference(AppConstants.USER_LOGIN_STATUS,false)){
            val intent = Intent(context, MainBottomNavigationActivity::class.java)
            intent.putExtra("notifyID", notifyId)
            intent.putExtra("userid",userId)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder.setContentIntent(pendingIntent)
        }

            mBuilder.setAutoCancel(true)
            mBuilder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_LIGHTS or Notification.DEFAULT_VIBRATE)
            mBuilder.priority = Notification.PRIORITY_HIGH
            notificationManager.notify(1, mBuilder.build())


        }


    }

    //This method will check whether the app is in background or not
    fun isAppIsInBackground(context: Context): Boolean {
        var isInBackground = true
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            var runningProcesses: List<ActivityManager.RunningAppProcessInfo>? = null
            runningProcesses = am.runningAppProcesses
            if (runningProcesses != null) {
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            }
        } else {
            var taskInfo: List<ActivityManager.RunningTaskInfo>? = null
            taskInfo = am.getRunningTasks(1)
            var componentInfo: ComponentName? = null
            if (taskInfo != null) {
                componentInfo = taskInfo[0].topActivity
            }
            if (componentInfo != null && componentInfo.packageName == context.packageName) {
                isInBackground = false
            }
        }

        return isInBackground
    }


    /**show notification for message*/

    fun showNotificationForMessage(
        context: Context,
        message: String,
        receiver_id: String,
        receiver_name: String,
        sender_id: String,
        receiver_image: String,
        sender_name: String,
        sender_image: String?
    ) {
        Handler(Looper.getMainLooper()).post {
            Glide.with(context)
                .asBitmap()
                .load(sender_image)
                .apply(RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Log.d("RESOURCE", resource.toString())

                        val notificationManager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        val channelId = "setMydate-100"
                        val channelName = "SetMyDate"
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            val mChannel = NotificationChannel(
                                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
                            )
                            mChannel.description= "description"
                            mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC;
                            mChannel.enableVibration(true)
                            notificationManager.createNotificationChannel(mChannel)
                        }
                        val mBuilder = NotificationCompat.Builder(context, channelId)
                            .setSmallIcon(R.drawable.notificationicon)
                            .setContentTitle("New message from $sender_name")
                            .setContentText(message)
                            .setStyle(NotificationCompat.BigTextStyle())
                            .setChannelId(channelId)
                            .setColor(Color.RED)
                            .setDefaults(NotificationCompat.DEFAULT_ALL or NotificationCompat.DEFAULT_VIBRATE)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                            .setLargeIcon(resource)

                        val intent = Intent(context, MessageActivity::class.java)
                        intent.putExtra("chat_key", 1002)
                        intent.putExtra("user_id", sender_id)
                        intent.putExtra("user_name", sender_name)
                        intent.putExtra("user_image", sender_image);
                        val pendingIntent =
                            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                        mBuilder.setContentIntent(pendingIntent)
                        notificationManager.notify(AppConstants.FIREBASE_MESSAGE_NOTIFICATION_ID.toInt(), mBuilder.build())

                    }

                })


        }


    }

    @JvmStatic
    fun showSetMydate(context: Context, set_my_date: String, i: Int, s: String, currentTimeMillis: Int) {

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        if (!powerManager.isInteractive) { // if screen is not already on, turn it on (get wake_lock for 10 seconds)
            val wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.ON_AFTER_RELEASE, ":MyWakelockTag")
            wl.acquire(10000)
            val wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ":MyWakelockTag")
            wl_cpu.acquire(10000)
        }


        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "setMydate-100"
        val channelName = "SetMyDate"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            mChannel.description= "description"
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC;
            mChannel.enableVibration(true)
            notificationManager.createNotificationChannel(mChannel)
        }
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle(set_my_date)
            .setContentText(s)
            .setStyle(NotificationCompat.BigTextStyle())
            .setChannelId(channelId)
            .setDefaults(NotificationCompat.DEFAULT_ALL or NotificationCompat.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val intent = Intent(context, MessageActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(1, mBuilder.build())

    }


}