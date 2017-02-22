package adiel.myaspectj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import myaspectj.annotations.Ignore;
import myaspectj.annotations.MyDebugTrace;
import myaspectj.filelogger.LocalLogManager;
import stam.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {
    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    @Ignore
    @MyDebugTrace
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person = new Person();
        LocalLogManager.initLogger();
        logger.debug("Activity loading....");

    }

    @Override
    protected void onResume() {
        super.onResume();
//        LocalLogManager.superLog("LocalLog onResume");
        grandfather();
    }

    private void grandfather(){
        father();
        String str = null;
       // str.isEmpty();
    }
     private void father(){
        int x =5;
         son(x);
    }

     private String son(int x){
         Log.d("adiel", "son: ");
         ignoreMe();
         return "son result";
    }
    @Ignore
   private void ignoreMe(){
         Log.d("adiel", "ignoreMe");
    }


    public void makeEx(View view) {
        String str = null;
        str.isEmpty();
    }

    public void catchEx(View view) {
        try {
            String str = null;
            str.isEmpty();
        }catch (RuntimeException e){
            e.printStackTrace();
            Log.e("notAspect",e.getMessage());
        }
    }

    public void bombLogger(View view) {

          //  startService(new Intent(MainActivity.this,BombLoggerIntentService.class));
        for (int i =0 ; i<10000 ; i++){
            //LocalLogManager.superLog("msg num "+i);
            logger.debug(" num:"+i);
        }
    }

    @Ignore
    public void checkUi(View view) {
        //Toast.makeText(this, "check ui", Toast.LENGTH_SHORT).show();
        Log.d("adiel","check ui");

    }
}
