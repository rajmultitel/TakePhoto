package be.multitel.raj.takephoto;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;






public class MainActivity extends AppCompatActivity {

    NotificationCompat.Builder notification ;
    private static final int uniqueID = 15468;


    static final int REQUEST_IMAGE_CAPTURE= 1 ;
    ImageView rajImageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rajButton = (Button) findViewById(R.id.buttonPhoto);
        rajImageViewer = (ImageView) findViewById(R.id.imageView);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);


        //Disable the button if user has no camera

        if(!hasCamera())
        {
            rajButton.setEnabled(false);

        }

        //check if user has camera



    }

    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //Launching the camera

    public void launchCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //take pic and pass results along to onActivity Resul

        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);


    }


    //IF YOU WANT TO RETURN THE IMAGE
    //  for github add comments


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageBitmap(photo);

        }
    }

    public void buttonSendNotification(View view)
    {//Build notification

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("Hey new Ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Here is the TITLE");
        notification.setContentText("I am the BODY text of your notification");

        Intent i = new Intent(this,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, i,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and issues it

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());





    }
}
