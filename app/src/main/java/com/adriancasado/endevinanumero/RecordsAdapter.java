package com.adriancasado.endevinanumero;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RecordsAdapter extends ArrayAdapter<Record> {

    public RecordsAdapter(Context context, ArrayList<Record> records) {
        super(context, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_record, parent, false);
        }

        TextView userTextView = convertView.findViewById(R.id.user_text_view);
        TextView triesTextView = convertView.findViewById(R.id.tries_text_view);

        userTextView.setText(record.getUser());
        triesTextView.setText(String.valueOf(record.getTries()));

        return convertView;
    }
}