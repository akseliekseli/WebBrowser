package com.example.webbrowser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebView web;
    ArrayList<String> urlList = new ArrayList<String>();
    ListIterator litr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.webView);

        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        urlList.add("http://www.google.fi");
        litr = urlList.listIterator();

        String url = (String) litr.next();
        web.loadUrl(url);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.super_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                onSearch(query);
                return true;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.refresh:
                onRefresh();
                break;

            case R.id.back:
                onBack();
                break;

            case R.id.next:
                onNext();
                break;

            case R.id.jsbtn:
                javascriptShout();
                break;

            case R.id.jsbtn2:
                javasriptInit();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void onRefresh(){
        web.loadUrl((String) litr.previous());
    }

    public void onSearch(String page){
        if (page.equalsIgnoreCase("index.html")){
            web.loadUrl("file:///android_asset/index.html");
        }
        else {
            String url = "http://" + page;
            litr.add(url);
            web.loadUrl(url);
        }
    }

    public void onBack(){
        if (litr.hasPrevious()) {
            web.loadUrl((String) litr.previous());
        }
    }

    public void onNext(){
        if (litr.hasNext()){
            web.loadUrl((String) litr.next());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void javascriptShout(){
        web.evaluateJavascript("javascript:shoutOut()",null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void javasriptInit(){
        web.evaluateJavascript("javascript:initialize()",null);
    }

}