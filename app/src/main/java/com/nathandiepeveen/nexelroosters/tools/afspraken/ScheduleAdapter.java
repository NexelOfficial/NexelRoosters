package com.nathandiepeveen.nexelroosters.tools.afspraken;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nathandiepeveen.nexelroosters.R;

import java.util.ArrayList;

public class ScheduleAdapter extends ArrayAdapter<Schedule> {

    private Context context;
    private int resourceId;

    public ScheduleAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Schedule> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        convertView = layoutInflater.inflate(this.resourceId, parent, false);

        TextView hourNumber = convertView.findViewById(R.id.hourNumber);
        TextView mainInformation = convertView.findViewById(R.id.mainInformation);
        TextView change = convertView.findViewById(R.id.change);

        hourNumber.setText(getItem(position).getClassHour());
        mainInformation.setText(getItem(position).getClassInformation());
        change.setText(getItem(position).getClassChange());

        return convertView;
    }
}
