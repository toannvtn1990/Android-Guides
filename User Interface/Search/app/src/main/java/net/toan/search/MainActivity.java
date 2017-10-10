package net.toan.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.toan.search.dictionary.DictionaryActivity;

public class MainActivity extends AppCompatActivity {

    private String[] mMenuItems = new String[] {"Dictionary"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mMenuItems);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Class<?> clazz = null;
                switch (position) {
                    case 0:
                        clazz = DictionaryActivity.class;
                        break;
                    default:
                        break;
                }

                if (clazz == null) {
                    return;
                }

                startActivity(new Intent(getApplicationContext(), clazz));
            }
        });
    }
}
