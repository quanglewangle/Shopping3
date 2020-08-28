package com.quanglewangle.peter.shopping3;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class InboxActivity extends ListActivity implements WebFilteredDownload.download_complete {

    public ListView list;
//	public ArrayList<Products> products = new ArrayList<Products>();

    private CupboardListAdapter adapter;
    ArrayList<HashMap<String, String>> inboxList;
    ArrayList<HashMap<String, String>> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PETER", "in main acc");
        //	setContentView(R.layout.activity_main);
        setContentView(R.layout.cupboard_list);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("PETER", "in onResume");
        products = new ArrayList<HashMap<String, String>>();

       /* adapter = new SimpleAdapter(
                this, products,
                R.layout.cupboard_list_item, new String[]{"description", "barcode", "date"},
                new int[]{R.id.description, R.id.barcode, R.id.date}); */
        adapter = new CupboardListAdapter(this, products);

        // updating listview
        setListAdapter(adapter);
        ExecuteWebTask task = new ExecuteWebTask();
        task.execute(new String[]{"http://fimblefowl.co.uk/json?cmd=dumpAll"});
        //    final WebFilteredDownload webRequest = new WebFilteredDownload((WebFilteredDownload.download_complete) this);

        //   webRequest.doRequest("1");
        adapter.update(products);
        ((BaseAdapter) adapter).notifyDataSetChanged();
    }

    protected void onListItemClick(ListView list, View v, int position, long id) {
        super.onListItemClick(list, v, position, id);

        Map o = (HashMap<String, String>) this.getListAdapter().getItem(position);
        String pen = o.toString();
        Toast.makeText(this, "You have chosen the pen: " + " " + pen + o.getClass(), Toast.LENGTH_LONG).show();
        //     final WebChangeBasketStatus webrequest = new WebChangeBasketStatus((WebChangeBasketStatus.download_complete)this);
        //    webrequest.doRequest("http://fimblefowl.co.uk/json?cmd=ubT&basket=2&product_id=" + o.get("id"));
        ExecuteWebTask task = new ExecuteWebTask();
        task.execute(new String[]{"http://fimblefowl.co.uk/json?cmd=ubT&basket=2&product_id=" + o.get("id")});
        adapter.update(products);
    }

    public interface AsyncTaskCompleteListener<T> {
        public void onTaskComplete(T result, int number);
    }

    public void get_data(String data) {
        try {
            JSONArray data_array = new JSONArray(data);

            for (int i = 0; i < data_array.length(); i++) {
                JSONObject obj = new JSONObject(data_array.get(i).toString());
                Log.d("in get data", data_array.get(i).toString());
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put("description", obj.getString("description"));

                map.put("barcode", obj.optString("barcode"));
                map.put("id", obj.getString("id"));

                // adding HashList to ArrayList
                products.add(map);
            }
//			adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class ExecuteWebTask extends AsyncTask<String, Void, String> {
        private String response;

        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    this.response = response.body().string();
                    //   return response.body().string();
                    return (this.response);
                }
            } catch (Exception e) {
                Log.e("ASYNC", e.toString());
                return "Fail";
            }

            return "reached end";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("ASYNC", "PosPostExecute was passed: " + result);
        }
    }

    private class GetJsonWebTask extends AsyncTask<String, Void, String> {
        private String response;

        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    this.response = response.body().string();
                    //   return response.body().string();
                    return (this.response);
                }
            } catch (Exception e) {
                Log.e("ASYNC", e.toString());
                return "Fail";
            }

            return "reached end";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("ASYNC", "PosPostExecute was passed: " + result);
        }

        public void get_data(String data) {
            try {
                JSONArray data_array = new JSONArray(data);

                for (int i = 0; i < data_array.length(); i++) {
                    JSONObject obj = new JSONObject(data_array.get(i).toString());
                    Log.d("in get data", data_array.get(i).toString());
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("description", obj.getString("description"));

                    map.put("barcode", obj.optString("barcode"));
                    map.put("id", obj.getString("id"));

                    // adding HashList to ArrayList
                    products.add(map);
                }
//			adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}





