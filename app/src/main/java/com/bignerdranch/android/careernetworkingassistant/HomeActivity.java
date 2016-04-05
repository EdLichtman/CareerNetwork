package com.bignerdranch.android.careernetworkingassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnAddJob;
    private Button btnAddContact;
    private Button btnFindContact;
    private Button btnViewEdit;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAddJob = (Button) findViewById(R.id.AddJob);
        btnAddJob.setOnClickListener(new ExternalOnClickListener());
        btnAddContact = (Button) findViewById(R.id.AddContact);
        btnAddContact.setOnClickListener(new ExternalOnClickListener());
        btnFindContact = (Button) findViewById(R.id.FindContact);
        btnFindContact.setOnClickListener(new ExternalOnClickListener());
        btnViewEdit = (Button) findViewById(R.id.ViewEdit);
        btnViewEdit.setOnClickListener(new ExternalOnClickListener());
        btnProfile = (Button) findViewById(R.id.UserProfile);
        btnProfile.setOnClickListener(new ExternalOnClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class ExternalOnClickListener implements View.OnClickListener {

        public ExternalOnClickListener() {
            // keep references for your onClick logic
        }

        @Override public void onClick(View v) {
            Class changeClass = this.getClass();
            if (v == btnAddJob) {
                changeClass = JobApplicationPagerActivity.class;
            }
            if (v == btnViewEdit) {
                changeClass = JobApplicationsListActivity.class;
            }
            Intent intent = new Intent(HomeActivity.this, changeClass);
            startActivity(intent);
        }

    }
}
