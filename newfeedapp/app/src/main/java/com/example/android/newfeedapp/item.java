package com.example.android.newfeedapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class item extends ArrayAdapter<Adapter> {

    public item(Context context, List<Adapter> news) {
        super(context, -1, news);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
        }

        Adapter currentWord = getItem(position);

        TextView titleTextView =  listItemView.findViewById(R.id.heading);
        titleTextView.setText(currentWord.getHeading());

        TextView auhtorTextView =  listItemView.findViewById(R.id.relevantinfo);
        auhtorTextView.setText(currentWord.getRelevantinfo());

        TextView DateTextView =  listItemView.findViewById(R.id.date);
        DateTextView.setText(currentWord.getDetails());

        TextView AuhtortextView = listItemView.findViewById(R.id.auhtors);
        AuhtortextView.setText((CharSequence) currentWord.getAuthor());
        return listItemView;
    }

    //method for date
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    //method for time
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}