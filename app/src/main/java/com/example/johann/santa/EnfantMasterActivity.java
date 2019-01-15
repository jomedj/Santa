package com.example.johann.santa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class EnfantMasterActivity extends ListActivity implements OnItemClickListener {

    private final class DownloadPeople extends AsyncTask<String, Void, ArrayList<Enfant>> {

        private static final String BASE_URL = "https://data.nantesmetropole.fr/api/records/1.0/search/?dataset=244400404_prenoms-enfants-nes-nantes&facet=prenom&facet=sexe&facet=annee_naissance\n";
        private HttpURLConnection urlConnection;
        private ProgressDialog progress;

        public DownloadPeople() {
            this.progress = new ProgressDialog(EnfantMasterActivity.this);
        }

        @Override
        protected void onPreExecute() {
            progress.setTitle("Please wait");
            progress.setMessage("Fetching remote data...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
        }

        @Override
        protected ArrayList<Enfant> doInBackground(String... params) {

            ArrayList<Enfant> fetchedData = new ArrayList<Enfant>();
            String stream = null;

            try {
                URL url = new URL(BASE_URL);
                this.urlConnection = (HttpURLConnection) url.openConnection();
                this.urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(this.urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                stream = sb.toString();

                JSONArray concepts = new JSONObject(stream).getJSONArray("records");
                for (int i = 0; i < concepts.length(); ++i) {
                    JSONObject record = concepts.getJSONObject(i).getJSONObject("fields");
                    fetchedData.add(new Enfant(
                            record.getString("sexe"),
                            record.getString("prenom"),
                            record.getString("annee_naissance")
                    ));
                }
            } catch(Exception e) {
                Log.e("genDROID","An error occured while fetching", e);
            } finally {
                this.urlConnection.disconnect();
            }
            return fetchedData;
        }

        @Override
        protected void onPostExecute(ArrayList<Enfant> result) {

            if(progress.isShowing()) progress.dismiss();
            EnfantMasterActivity.this.populate(result);
        }
    }

    private EnfantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new EnfantAdapter(this, R.layout.enfant_item);
        this.setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new DownloadPeople().execute("maj");
    }

    public void populate(ArrayList<Enfant> data) {
        this.adapter.clear();
        this.adapter.addAll(data);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {

        Enfant currentPeople = this.adapter.getItem(position);
        Intent goToNextScreen = new Intent(this, EnfantDetailActivity.class);
        goToNextScreen.putExtra("selected", currentPeople);
        this.startActivity(goToNextScreen);
    }
}