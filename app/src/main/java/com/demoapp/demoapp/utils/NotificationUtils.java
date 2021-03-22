package com.demoapp.demoapp.utils;

import android.content.Intent;


import org.json.JSONObject;

public class NotificationUtils {

//    public NotificationUtils(RemoteMessage remoteMessage) {
//        if (remoteMessage != null && remoteMessage.getData().size() > 0) {
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
////                handleDataMessages(json);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        if (remoteMessage != null && remoteMessage.getNotification() != null) {
//            try {
////                handleNotificationMessages(remoteMessage.getNotification());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }

//    private void handleDataMessages(JSONObject json) {
//        try {
//            JSONObject data = json.getJSONObject("data");
//            String notificationTitle = data.getString("title");
//            String notificationMessage = data.getString("message");
//            sendSimpleNotification(notificationTitle, notificationMessage, new Intent(AppController.getAppContext(), ViewAllNewsOrNotificationStudentActivity.class));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void handleNotificationMessages(RemoteMessage.Notification notification) {
//
//        String notificationTitle = "";
//        String notificationMessage = "";
//
//        if (!CommonUtil.checkIsEmptyOrNullCommon(notification.getTitle())) {
//            notificationTitle = notification.getTitle();
//        }
//
//        if (!CommonUtil.checkIsEmptyOrNullCommon(notification.getBody())) {
//            notificationMessage = notification.getBody();
//        }
//
//        if (!CommonUtil.isAppIsInBackground(AppController.getAppContext())) {
//
//            String clickAction = "";
//
//            if (!CommonUtil.checkIsEmptyOrNullCommon(notification.getClickAction())) {
//                clickAction = notification.getClickAction();
//            }
//
//            Intent intent = null;
//
//            if (clickAction != null && !clickAction.isEmpty()) {
//                if (clickAction.equalsIgnoreCase(DynamicActivityName.FEES_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), PayFeeActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.ATTENDANCE_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentAttendanceActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.LESSON_PLAN_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentLessonPlanActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.NEWS_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), ViewAllNewsOrNotificationStudentActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.HOMEWORK_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentHomeWorkActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.SYLLABUS_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentSyllabusActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.RESULT_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentResultActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.ASSIGNMENT_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), AssignmentActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.EXAM_TIMETABLE_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), StudentTimeTableActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.FEE_CIRCULAR_ACTIVITY_FOR_STUDENt)) {
//                    intent = new Intent(AppController.getAppContext(), FeeDetailsActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.LEAVE_APPLICATION_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), LeaveApplicationActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.E_LEARNING_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), ELearningActivity.class);
//                } else if (clickAction.equalsIgnoreCase(DynamicActivityName.HOLIDAY_LIST_ACTIVITY_FOR_STUDENT)) {
//                    intent = new Intent(AppController.getAppContext(), HolidayActivity.class);
//                } else {
//                    intent = new Intent(AppController.getAppContext(), ViewAllNewsOrNotificationStudentActivity.class);
//                }
//            } else {
//                intent = new Intent(AppController.getAppContext(), ViewAllNewsOrNotificationStudentActivity.class);
//            }
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            PendingIntent pendingIntent = PendingIntent.getActivity(AppController.getAppContext(), 1410,
//                    intent, PendingIntent.FLAG_ONE_SHOT);
//
//            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
//                    NotificationCompat.Builder(AppController.getAppContext(), AppController.NOTIFICATION_CHANNEL_ID)
//                    .setSmallIcon(R.drawable.logo)
//                    .setContentTitle(notificationTitle)
//                    .setContentText(notificationMessage)
//                    .setLargeIcon(BitmapFactory.decodeResource(AppController.getAppContext().getResources(), R.drawable.logo))
//                    .setAutoCancel(true)
//                    .setPriority(NotificationCompat.PRIORITY_HIGH)
//                    .setContentIntent(pendingIntent)
//                    .setChannelId(AppController.NOTIFICATION_CHANNEL_ID);
//
//            NotificationManager notificationManager = (NotificationManager) AppController.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
//        }
////        else {
////            sendSimpleNotification(notificationTitle, notificationMessage, new Intent(AppController.getAppContext(), NewsOrNotificationActivity.class));
////        }
//    }
//
//    private void sendSimpleNotification(String notificationTitle, String notificationMessage, Intent intent) {
////        intent.putExtra("type", "notification");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        final PendingIntent resultPendingIntent =
//                PendingIntent.getActivity(AppController.getAppContext(), 2450, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new
//                NotificationCompat.Builder(AppController.getAppContext(), AppController.NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle(notificationTitle)
//                .setContentText(notificationMessage)
//                .setContentIntent(resultPendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setLargeIcon(BitmapFactory.decodeResource(AppController.getAppContext().getResources(), R.drawable.logo))
//                .setAutoCancel(true)
//                .setChannelId(AppController.NOTIFICATION_CHANNEL_ID);
//
//        NotificationManager notificationManager = (NotificationManager) AppController.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
//    }

//}
