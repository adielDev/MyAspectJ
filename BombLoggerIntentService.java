package adiel.myaspectj;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import myaspectj.filelogger.LocalLogManager;

/**
 * Created by recntrek7 on 22/02/17.
 */

public class BombLoggerIntentService extends IntentService {


    public BombLoggerIntentService() {
        super("bomber");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i =0 ; i<100 ; i++){
            //LocalLogManager.superLog("msg num "+i);
            Log.d("adiel"," num:"+i);
        }
    }
}
