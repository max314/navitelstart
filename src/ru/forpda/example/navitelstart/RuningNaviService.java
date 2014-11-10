package ru.forpda.example.navitelstart;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.ultimate.root.LinuxShell;
import org.ultimate.root.RootUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by max on 10.11.2014.
 */
public class RuningNaviService extends IntentService {
    static String TAG = RuningNaviService.class.getName();
    static String packageName = "com.navitel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public RuningNaviService() {
        super("navitelrunner");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Run " + packageName);
        try {
            CorrectIniFile();
        } catch (Exception e) {
            Log.e(TAG, "Error run " + packageName, e);
            e.printStackTrace();
        }
    }

    public void runAndroidPackage(String packageName) {
        Intent LaunchIntent = this.getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(LaunchIntent);
    }

    private void CorrectIniFile() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
                BufferedReader buff = LinuxShell.execute("busybox sed -i 's/SafeExit = 0/SafeExit = 1/g' /data/data/com.navitel/settings.001.ini");
                if (buff == null) {
                    Log.i(TAG, "Error update ini file");
                    Toast.makeText(getApplicationContext(), String.format("Error update ini file ....", packageName), Toast.LENGTH_LONG).show();
                    Thread.sleep(600);
                    return;
                }
                while ((line = buff.readLine()) != null) stringBuilder.append(line);
                String res = stringBuilder.toString();
                Log.d(TAG, "Ok -" + res);
                Toast.makeText(getApplicationContext(), String.format("Запуск = %s", packageName), Toast.LENGTH_LONG).show();
                Thread.sleep(600);
                Log.d(TAG, "Run ");
                runAndroidPackage(packageName);
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
    }

}
