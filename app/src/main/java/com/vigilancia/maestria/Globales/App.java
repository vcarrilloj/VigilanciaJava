package com.vigilancia.maestria.Globales;

import android.app.PendingIntent;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static Map<Long, PendingIntent> pendingIntentMap = new HashMap<>();
    public static long IdIntentUsado = 0;
}
