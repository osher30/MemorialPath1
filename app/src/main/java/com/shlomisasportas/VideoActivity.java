package com.shlomisasportas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class VideoActivity extends AppCompatActivity {
    private MediaGalleryView mMediaGalleryView;
    ArrayList<String> allURls=new ArrayList<>();
    public static ArrayList<String> thumbnailURLs = new ArrayList<>();
    ArrayList<String> deleteKeys = new ArrayList<>();

    ProgressDialog pd ;

    ImageView profile, memorialEvent, picture, video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        pd = new ProgressDialog(this,R.style.ProgressDiaglog);
        pd.setMessage("loading");
        pd.show();



        picture = (ImageView) findViewById(R.id.music);
        memorialEvent = (ImageView) findViewById(R.id.memorialevent);
        profile = (ImageView) findViewById(R.id.profile);
        video = (ImageView) findViewById(R.id.video);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(VideoActivity.this, PhotoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position",0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url",getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VideoActivity.this, IndiviualsInfoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position",0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url",getIntent().getStringExtra("url"));
                startActivity(i);
            }
        });

        memorialEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
                builder.setCancelable(false)
                        .setPositiveButton("מחק אירוע", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(VideoActivity.this, DeleteEventsActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position",0));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("הוסף אירוע", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(VideoActivity.this, AddMemorialEventActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position",0));
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
                builder.setCancelable(false)
                        .setPositiveButton("סרטון יוטיוב?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i= new Intent(VideoActivity.this, YoutubeVideoActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position",0));
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("סרטון?", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i= new Intent(VideoActivity.this, VideoActivity.class);
                                i.putExtra("position", getIntent().getIntExtra("position",0));
                                startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference =    mFirebaseDatabase.getReference("Videos");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v("Database",""+ childDataSnapshot.getKey()); //displays the key for the node

                    String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));
                    if (key.equals(""+childDataSnapshot.child("key").getValue())){
                        deleteKeys.add(""+childDataSnapshot.getKey());
                        String url = ""+childDataSnapshot.child("url").getValue();
                        String thumbnailurl = ""+childDataSnapshot.child("thumbnailurl").getValue();
                        allURls.add(url);
                        thumbnailURLs.add(thumbnailurl);
                        Log.i("urlssss", url);
                        Log.i("urlssss", thumbnailurl);
                    }
                }


                mMediaGalleryView = (MediaGalleryView) findViewById(R.id.scroll_gallery_view);
                new Thread(new Runnable() {
                    public void run() {
                        final Bitmap bm[] = new Bitmap[allURls.size()] ;//= getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/shlomisasportas-344fc.appspot.com/o/-L1rNct1TFhYFxZaHYq-%2Foria.png?alt=media&token=775d5d43-8cde-466d-b69c-fbf32f2c4617");
                        for (int i=0; i<allURls.size(); i++){
                            bm[i] = getBitmapFromURL(thumbnailURLs.get(i));
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                for(int i=0; i<allURls.size();i++){
                                    mMediaGalleryView
                                            .setThumbnailSize(100)
                                            .setZoom(true)
                                            .setFragmentManager(getSupportFragmentManager())
                                            .addMedia(MediaInfo
                                                    .url(allURls.get(i), bm[i]));


                                }


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

    private Bitmap convertDrawableToBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addpic, menu);
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

                LinearLayout layout = new LinearLayout(VideoActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                final EditText edittext = new EditText(VideoActivity.this);
                edittext.setHint("הכנס קישור לסרטון");
                final EditText edittext1 = new EditText(VideoActivity.this);
                edittext1.setHint("הכנס תמונת קישור");

                layout.addView(edittext);
                layout.addView(edittext1);

                alert.setView(layout);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String YouEditTextValue = edittext.getText().toString();

                        if (!YouEditTextValue.equals("") & !edittext1.getText().toString().equals("")){
                            addData(YouEditTextValue, edittext1.getText().toString());
                            Intent i = new Intent(VideoActivity.this, VideoActivity.class);
                            i.putExtra("position", getIntent().getIntExtra("position",0));
                            i.putExtra("data", getIntent().getStringExtra("data"));
                            i.putExtra("url",getIntent().getStringExtra("url"));
                            startActivity(i);
                            finish();
                        }
                    }
                });

                alert.setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();


                return true;

            case R.id.delete:

                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("Videos").child(deleteKeys.get(mMediaGalleryView.current_Selected()));
                dbNode.removeValue();


                Toast.makeText(VideoActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                Intent i = new Intent(VideoActivity.this, VideoActivity.class);
                i.putExtra("position", getIntent().getIntExtra("position",0));
                i.putExtra("data", getIntent().getStringExtra("data"));
                i.putExtra("url",getIntent().getStringExtra("url"));
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void addData(String url, String thumbnail){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Log.i("reference",""+SearchActivity.keys.get(MainActivity.allkeys.get( getIntent().getIntExtra("position",0))));

        DatabaseReference posts = database.getReference("Videos");//.child(""+MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0))));
        posts.keepSynced(true);

        String key = MainActivity.allkeys.get(SearchActivity.keys.get(getIntent().getIntExtra("position",0)));

        VideoPost eventPost = new VideoPost();

        eventPost.setKey(key);
        eventPost.setUrl(url);
        eventPost.setThumbnailurl(thumbnail);
        posts.push().setValue(eventPost);


    }
}
