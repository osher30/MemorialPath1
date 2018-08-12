package com.shlomisasportas;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class IndiviualsInfoActivity extends AppCompatActivity {

    ImageView profilePic, edit, memorialEvent, picture, video, maps;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_indiviuals_info);


        pd = new ProgressDialog(IndiviualsInfoActivity.this, R.style.ProgressDiaglog);
        pd.setMessage("loading");
        pd.show();

        maps = (ImageView) findViewById(R.id.map);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("www.google.com").appendPath("maps").appendPath("dir").appendPath("").appendQueryParameter("api", "1")
                        .appendQueryParameter("destination", Double.parseDouble(ShowSearchDataActivity.latitude) + "," + Double.parseDouble(ShowSearchDataActivity.longitude));
                String url = builder.build().toString();
                Log.d("Directions", url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        profilePic = (ImageView) findViewById(R.id.profilepic);
        memorialEvent = (ImageView) findViewById(R.id.memorialevent);
        picture = (ImageView) findViewById(R.id.music);
        video = (ImageView) findViewById(R.id.video);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndiviualsInfoActivity.this, PhotoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndiviualsInfoActivity.this, YoutubeVideoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        /*video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(IndiviualsInfoActivity.this);
                builder.setCancelable(true)
                        .setPositiveButton("Youtube Videos?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(IndiviualsInfoActivity.this, YoutubeVideoActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position", 0));
                                i.putExtra("data", getIntent().getStringExtra("data"));
                                i.putExtra("url", getIntent().getStringExtra("url"));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Videos?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(IndiviualsInfoActivity.this, VideoActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position", 0));
                                i.putExtra("data", getIntent().getStringExtra("data"));
                                i.putExtra("url", getIntent().getStringExtra("url"));
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });*/

        new Thread(new Runnable() {
            public void run() {

                final Bitmap bm = getBitmapFromURL(getIntent().getStringExtra("url"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        profilePic.setImageBitmap(bm);
                        pd.dismiss();
                    }
                });
            }
        }).start();


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        TextView v = (TextView) findViewById(R.id.info);
        v.setText(getIntent().getStringExtra("data"));
        v.setMovementMethod(new ScrollingMovementMethod());



        memorialEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(IndiviualsInfoActivity.this, DeleteEventsActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });


        /*memorialEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(IndiviualsInfoActivity.this);
                builder.setCancelable(true)
                        .setPositiveButton("Events", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(IndiviualsInfoActivity.this, DeleteEventsActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position", 0));
                                i.putExtra("data", getIntent().getStringExtra("data"));
                                i.putExtra("url", getIntent().getStringExtra("url"));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Add Events", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(IndiviualsInfoActivity.this, AddMemorialEventActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position", 0));
                                i.putExtra("data", getIntent().getStringExtra("data"));
                                i.putExtra("url", getIntent().getStringExtra("url"));
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                Intent i = new Intent(IndiviualsInfoActivity.this, EditProfileActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                startActivity(i);
                return true;

        }
        return true;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            myBitmap = getResizedBitmap(myBitmap, 200, 200);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editprofile, menu);
        return true;
    }
}




