package ru.forpda.example.navitelstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.ultimate.root.LinuxShell;
import org.ultimate.root.RootUtils;

import java.io.BufferedReader;

/**
 * Created by max on 10.11.2014.
 */
public class FooActivity extends Activity {
    static String TAG = FooActivity.class.getName();
    static String packageName = "com.navitel";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        if (LinuxShell.isRoot()) {
            startService(new Intent(this, RuningNaviService.class));
        }
        else {
            Toast.makeText(getApplicationContext(), String.format("Не хататет root прав для запуска"), Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Log.e(TAG,"Error sleep,",e);
                e.printStackTrace();
            }
        }
        Log.d(TAG, "by by by...");
        finish();
    }


}