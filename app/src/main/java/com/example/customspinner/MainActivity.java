package com.example.customspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpEditTextClickListner();
        setUpTextWatcher();
    }

    private void setUpEditTextClickListner()
    {

        EditText editText = findViewById(R.id.city);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });

        editText = findViewById(R.id.state);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setUpCustomSpinner(v);
            }
        });
    }

    private void setUpCustomSpinner(final View view)
    {
        ListView listView = findViewById(R.id.listView);
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideCustomSpinnerLayout();
            }
        });

        if (view.getId()==R.id.city)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search City");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.city));
            listView.setAdapter(arrayAdapter);
        }
        else if (view.getId() == R.id.state)
        {
            ((EditText)findViewById(R.id.editText)).setHint("Search State");
            arrayAdapter = new ArrayAdapter<>(this,R.layout.text_view,R.id.textView,getResources().getStringArray(R.array.state));
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView textView = v.findViewById(R.id.textView);
                if (textView!=null)
                {
                    Log.e("selectedIs",textView.getText().toString());
                    ((EditText)view).setText(textView.getText().toString());

                }
                hideCustomSpinnerLayout();
            }
        });

        findViewById(R.id.includeContainer).animate().translationX(0).setDuration(1000);
    }

    private void hideCustomSpinnerLayout() {
        int i = findViewById(R.id.includeContainer).getWidth();
        findViewById(R.id.includeContainer).animate().translationX(i).setDuration(1000);
        findViewById(R.id.view).setVisibility(View.GONE);
    }


    private void setUpTextWatcher()
    {
        ((EditText)findViewById(R.id.editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
