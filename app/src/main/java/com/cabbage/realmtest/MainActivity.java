package com.cabbage.realmtest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    PersonAdapter mAdapter;
    DataManager dataManager;

    @BindView(R.id.et_person_name) EditText etPersonName;
    @BindView(R.id.rv_persons) RecyclerView rvPersons;

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_add)
    void addPerson() {
        String input = etPersonName.getText().toString();
        if (!TextUtils.isEmpty(input)) {
            Person newPerson = new Person();
            Random rnd = new Random();
            newPerson.setFirstName(input + rnd.nextInt(100));

            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.insert(newPerson);
            realm.commitTransaction();

            mAdapter.addPerson(newPerson);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataManager = DataManager.getInstance();

        rvPersons.setHasFixedSize(false);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));
        rvPersons.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 0, false, true));
        mAdapter = new PersonAdapter();
        rvPersons.setAdapter(mAdapter);

        rvPersons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    View view = MainActivity.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        RealmQuery<Person> query = Realm.getDefaultInstance().where(Person.class);
        RealmResults<Person> results = query.findAll();
        for (Person result : results) {
            mAdapter.addPerson(result);
        }
    }

    public static class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

        private List<Person> mData = new ArrayList<>();

        public void addPerson(Person person) {
            if (!mData.contains(person)) {
                mData.add(0, person);
                notifyDataSetChanged();
            }
        }

        public void updatePersons(List<Person> personList) {
            mData.clear();
            mData.addAll(personList);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_person, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Person person = mData.get(position);
            String displayName = person.getFirstName();
            holder.tvPersonName.setText(displayName);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_person_name) TextView tvPersonName;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
