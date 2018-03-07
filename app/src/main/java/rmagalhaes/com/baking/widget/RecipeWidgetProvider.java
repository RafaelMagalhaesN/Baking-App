package rmagalhaes.com.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import rmagalhaes.com.baking.R;
import rmagalhaes.com.baking.widget.service.RecipeWidgetService;

/**
 * Created by Rafael Magalhães on 05/03/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredient);
            updateWidget(appWidgetManager, context, appWidgetId);

        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName BakingWidgetComp = new ComponentName(context.getApplicationContext(),
                RecipeWidgetProvider.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(BakingWidgetComp);

        if(intent != null && intent.getAction() != null && intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            onUpdate(context, appWidgetManager, widgetIds);
        }
    }


    private static void updateWidget(AppWidgetManager appWidgetManager, Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(R.id.widget_ingredient, intent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
       // super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        //super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
       // super.onEnabled(context);


    }

    @Override
    public void onDisabled(Context context) {
     //   super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
      //  super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
