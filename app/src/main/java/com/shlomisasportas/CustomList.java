package com.shlomisasportas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> web;
    private final Bitmap[] imageId;

    public CustomList(Activity context,
                      ArrayList<String> web, Bitmap[] imageId) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position));

        imageView.setImageBitmap(imageId[position]);

        ImageView delete = (ImageView) rowView.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("YoutubeVideos").child(YoutubeVideoActivity.deleteKeys.get(position));
                dbNode.removeValue();

                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();

                Intent i = new Intent(context, YoutubeVideoActivity.class);
                i.putExtra("position", context.getIntent().getIntExtra("position",0));
                i.putExtra("data", context.getIntent().getStringExtra("data"));
                i.putExtra("url",context.getIntent().getStringExtra("url"));
                context.startActivity(i);
                context.finish();
            }
        });

        return rowView;
    }
}