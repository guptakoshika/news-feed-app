package com.example.android.newfeedapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Adapter>> {
    private item mAdapter;
    private static final int LOADER_ID = 1;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?q=debate&tag=environment/recycling&from-date=2016-01-01&api-key=test&show-fields=byline";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);


        mAdapter = new item(this, new ArrayList<Adapter>());

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Adapter currentnews = mAdapter.getItem(position);
                String url = currentnews.getURL();

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(LOADER_ID, null, MainActivity.this);
        } else
            Toast.makeText(MainActivity.this, "Check your internet connection and try again later", Toast.LENGTH_LONG).show();
    }

    @Override
    public newsLoader onCreateLoader(int i, Bundle bundle) {

        return new newsLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Adapter>> loader, List<Adapter> adapters) {
        if (adapters != null) {
            mAdapter.addAll(adapters);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Adapter>> loader) {
        mAdapter.clear();
    }
}