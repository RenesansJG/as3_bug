package hu.renesans.testlib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

public class EventSender {
    private static final Gson GSON = new GsonBuilder().create();

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(new TestEvent(GSON.toJson(object)));
    }
}
