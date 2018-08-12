package com.shlomisasportas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import nz.co.iswe.android.mediagallery.MediaGalleryView;
import nz.co.iswe.android.mediagallery.MediaInfo;

public class YoutubeVideoActivity extends AppCompatActivity {

    ListView list;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ProgressDialog pd ;

    ImageView profile, memorialEvent, picture;

    public static ArrayList<String> deleteKeys = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        pd = new ProgressDialog(this,R.style.ProgressDiaglog);
        pd.setMessage("loading");
        pd.show();

        profile = (ImageView) findViewById(R.id.profile);
        memorialEvent = (ImageView) findViewById(R.id.memorialevent);
        picture = (ImageView) findViewById(R.id.music);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(YoutubeVideoActivity.this, PhotoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });


        memorialEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(YoutubeVideoActivity.this, DeleteEventsActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position", 0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url", getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });
        /*memorialEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(YoutubeVideoActivity.this);
                builder.setCancelable(true)
                        .setPositiveButton("Events", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(YoutubeVideoActivity.this, DeleteEventsActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position", 0));
                                i.putExtra("data", getIntent().getStringExtra("data"));
                                i.putExtra("url", getIntent().getStringExtra("url"));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Add Events", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(YoutubeVideoActivity.this, AddMemorialEventActivity.class);
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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(YoutubeVideoActivity.this, IndiviualsInfoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position",0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url",getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =    mFirebaseDatabase.getReference("YoutubeVideos");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("Database",""+ childDataSnapshot.getKey()); //displays the key for the node

                    String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));
                    if (key.equals(""+childDataSnapshot.child("key").getValue())){
                        deleteKeys.add(""+childDataSnapshot.getKey());
                        String n = ""+childDataSnapshot.child("name").getValue();
                        String u = ""+childDataSnapshot.child("url").getValue();
                        String i=""+childDataSnapshot.child("id").getValue();


                        names.add(n);
                        url.add(u);
                        id.add(i);

                        Log.i("ssss", n);

                        Log.i("ssss", u);

                        Log.i("ssss", i);
                    }
                }

                new Thread(new Runnable() {
                    public void run() {
                        final Bitmap bm[] = new Bitmap[url.size()] ;//= getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/shlomisasportas-344fc.appspot.com/o/-L1rNct1TFhYFxZaHYq-%2Foria.png?alt=media&token=775d5d43-8cde-466d-b69c-fbf32f2c4617");
                        for (int i=0; i<url.size(); i++){
                            bm[i] = getBitmapFromURL(url.get(i));
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CustomList adapter = new
                                        CustomList(YoutubeVideoActivity.this, names, bm);
                                list=(ListView)findViewById(R.id.list);
                                list.setAdapter(adapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long ii) {
                                       // Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(YoutubeVideoActivity.this, WebviewYoutube.class);
                                        i.putExtra("url", id.get(position));
                                        startActivity(i);
                                    }
                                });

                                pd.dismiss();
                            }
                        });
                    }
                }).start();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addvid, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.add:

                LinearLayout layout = new LinearLayout(YoutubeVideoActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(YoutubeVideoActivity.this);
                edittext.setHint("Enter Thumbnail URL");
                final EditText edittext1 = new EditText(YoutubeVideoActivity.this);
                edittext1.setHint("Enter Video name");
                final EditText edittext2 = new EditText(YoutubeVideoActivity.this);
                edittext2.setHint("Enter Video ID");

                layout.addView(edittext);
                layout.addView(edittext1);
                layout.addView(edittext2);

                alert.setView(layout);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String YouEditTextValue = edittext.getText().toString();

                        if (!YouEditTextValue.equals("") & !edittext1.getText().toString().equals("")){
                            addData(YouEditTextValue, edittext1.getText().toString(), edittext2.getText().toString());

                            Intent i = new Intent(YoutubeVideoActivity.this, YoutubeVideoActivity.class);
                            i.putExtra("position", getIntent().getIntExtra("position",0));
                            i.putExtra("data", getIntent().getStringExtra("data"));
                            i.putExtra("url",getIntent().getStringExtra("url"));
                            startActivity(i);
                            finish();
                        }
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addData(String url, String name,String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Log.i("reference",""+SearchActivity.keys.get(MainActivity.allkeys.get( getIntent().getIntExtra("position",0))));

        DatabaseReference posts = database.getReference("YoutubeVideos");//.child(""+MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0))));
        posts.keepSynced(true);

        String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));

        YoutubePost eventPost = new YoutubePost();
        eventPost.setKey(key);
        eventPost.setUrl(url);
        eventPost.setName(name);
        eventPost.setId(id);
        posts.push().setValue(eventPost);


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

            myBitmap = getResizedBitmap(myBitmap, 200,200);
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

}
