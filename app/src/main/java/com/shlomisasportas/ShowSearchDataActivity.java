package com.shlomisasportas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowSearchDataActivity extends AppCompatActivity {
    ListView listView ;
    ArrayList listData;
    public static String latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_show_search_data);

        listView = (ListView) findViewById(R.id.list);

        listData = new ArrayList();

        for (int i =0; i< SearchActivity.keys.size(); i++){
            listData.add(MainActivity.allUsers.get(SearchActivity.keys.get(i)).getFirstName() + " " + MainActivity.allUsers.get(SearchActivity.keys.get(i)).getLastName());

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listData);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition     = position;
                String firstName = "שם פרטי: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getFirstName()+"\n"
                + "שם משפחה: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getLastName()+"\n"
                +"תאריך לידה: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDateOfBirth()+"\n"
                + "שם האם: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getMomName()+"\n"
                +  "שם האב: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDadName()+"\n"
                + "מקום קבורה: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDeathLocation()+"\n"
                + "תאריך פטירה: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getDateOfDeath()+"\n"
                + "גיל: "+MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getAge()+"\n"
                        + "עיר קבורה:" +MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getCity()+"\n"
                        + "שורה:" +MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getRow()+"\n"
                        + "גוש:" +MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getParcel()+"\n"
                        + "חלקה:" +MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getPart()+"\n"
                        + "מספר קבר:" +MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getGravenumber()+"\n";


                latitude = MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getLatitude();
                longitude = MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getLongitude();

                Intent i = new Intent(ShowSearchDataActivity.this, IndiviualsInfoActivity.class);
                i.putExtra("data", firstName);
                i.putExtra("url", MainActivity.allUsers.get(SearchActivity.keys.get(itemPosition)).getProfilePicture());
                i.putExtra("position", position);

                startActivity(i);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
