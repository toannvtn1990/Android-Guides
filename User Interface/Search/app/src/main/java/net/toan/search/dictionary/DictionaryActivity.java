package net.toan.search.dictionary;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import net.toan.search.R;

public class DictionaryActivity extends AppCompatActivity {

    private final String TAG = "DictionaryActivity";
    private SearchManager mSearchManager;

    // bundle key for saving previously selected search result item
    private static final String STATE_PREVIOUSLY_SELECTED_KEY = "net.toan.search.dictionary.activity.selected_item";

    private String mSearchTern;
    private ListView mDictionaryListView;

    /**
     * Performing a search:
     * Your searchable activity involves three steps:
     *  -  Receiving the query
     *  -  Searching your data
     *  -  Presenting the results
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        mSearchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        mDictionaryListView = (ListView) findViewById(R.id.dictionaryList);

        // Receiving the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // Searching your data
            Log.d(TAG, "Receiving the query: " + query);
        }

        if (savedInstanceState != null) {
            mSearchTern = savedInstanceState.getString(SearchManager.QUERY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_action_menu, menu);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            final MenuItem menuItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "onQueryTextSubmit: " + query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    String filterText = TextUtils.isEmpty(newText) ? null : newText;
                    Log.d(TAG, "filterText: " + filterText);
                    return true;
                }

                MenuItem.OnActionExpandListener expandListener = new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when action item collapses
                        return true;  // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true;  // Return true to expand action view
                    }
                };
            });




            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mSearchTern)) {
            // saves the current search string
            outState.putString(SearchManager.QUERY, mSearchTern);

            // saves the currently selected dictionary
            outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, mDictionaryListView.getCheckedItemPosition());
        }
    }
}
