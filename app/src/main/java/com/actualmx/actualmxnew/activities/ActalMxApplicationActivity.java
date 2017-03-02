package com.actualmx.actualmxnew.activities;

import android.app.Application;
import android.os.Handler;

import com.actualmx.actualmxnew.listner.BaseUIListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/*@ReportsCrashes(// will not be used
	mailTo = "rbhawan@craterzone.com")*/
public class ActalMxApplicationActivity extends Application {
	
	public static final String TAG = Application.class.getName();
	private static ActalMxApplicationActivity _instance;
	private final Handler _handler;
	private boolean closed;
	private Map<Class<? extends BaseUIListener>, Collection<? extends BaseUIListener>> uiListeners;

	public ActalMxApplicationActivity() {
		_instance = this;
		 closed = false;
		_handler = new Handler();
		uiListeners = new HashMap<>();
	}

	public static ActalMxApplicationActivity getInstance() {
		if(_instance == null) {
			_instance = new ActalMxApplicationActivity();
		}
		return _instance;
	}
/**
 * Register new listener.
 * 
 * Should be called from {@link Activity#onResume()}.
 * 
 * @param cls
 * @param listener
 */
public <T extends BaseUIListener> void addUIListener(Class<T> cls,
		T listener) {
	getOrCreateUIListeners(cls).add(listener);
}

	public String getHttpServer(){
		return "188.166.90.36";
	}
/**
 * Unregister listener.
 * 
 * Should be called from {@link Activity#onPause()}.
 * 
 * @param cls
 * @param listener
 */
public <T extends BaseUIListener> void removeUIListener(Class<T> cls,
		T listener) {
	getOrCreateUIListeners(cls).remove(listener);
}


/**
 * @param cls
 *            Requested class of listeners.
 * @return List of registered UI listeners.
 */
public <T extends BaseUIListener> Collection<T> getUIListeners(Class<T> cls) {
	if (closed)
		return Collections.emptyList();
	return Collections.unmodifiableCollection(getOrCreateUIListeners(cls));
}


@SuppressWarnings("unchecked")
private <T extends BaseUIListener> Collection<T> getOrCreateUIListeners(
		Class<T> cls) {
	Collection<T> collection = (Collection<T>) uiListeners.get(cls);
	if (collection == null) {
		collection = new ArrayList<T>();
		uiListeners.put(cls, (Collection<? extends BaseUIListener>) collection);
	}
	return collection;
}

/**
 * Submits request to be executed in UI thread.
 * 
 * @param runnable
 */
public void runOnUiThread(final Runnable runnable) {
	_handler.post(runnable);
}

@Override
public void onCreate() {
	super.onCreate();
	_instance = this;
// Initialize the SDK before executing any other operations,

}

}
