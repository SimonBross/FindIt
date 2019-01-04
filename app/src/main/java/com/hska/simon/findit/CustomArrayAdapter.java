package com.hska.simon.findit;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.hska.simon.findit.database.DataAccessHelper;
import com.hska.simon.findit.model.Job;

public class CustomArrayAdapter extends ArrayAdapter<Job> {

    private final List<Job> jobs;
    private final Activity context;
    private DataAccessHelper dataAccessHelper;

    public CustomArrayAdapter(Activity context, List<Job> jobs) {
        super(context, R.layout.list_layout, jobs);
        this.context = context;
        this.jobs = jobs;
    }

    static class ViewHolder {
        protected TextView text;
        protected ToggleButton toggleButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = view.findViewById(R.id.listTextView);
            viewHolder.toggleButton = view.findViewById(R.id.myToggleButton);
            viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Job job = (Job) viewHolder.toggleButton.getTag();
                    int isFavorite = buttonView.isChecked()?1:0;
                    job.setIsfavorite(isFavorite);
                    dataAccessHelper = new DataAccessHelper(context);
                    dataAccessHelper.changeIsfavorite(job, isFavorite);
                    //if(isChecked)
                    //    Toast.makeText(getContext(), "checked"+ job.getPosition(), Toast.LENGTH_LONG).show();
                    //else if(!isChecked)
                    //    Toast.makeText(getContext(), "unchecked"+ job.getPosition(), Toast.LENGTH_LONG).show();
                    }
            });
            view.setTag(viewHolder);
            viewHolder.toggleButton.setTag(jobs.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).toggleButton.setTag(jobs.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(jobs.get(position).toString());
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent mapsIntent = new Intent(context, MapsActivity.class);
                mapsIntent.putExtra("company", jobs.get(position).getCompany());
                context.startActivity(mapsIntent);
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, jobs.get(position).toString());
//                shareIntent.setType("text/plain");
//                context.startActivity(shareIntent);
//                return true;
            }
        });
        if(jobs.get(position).getIsfavorite() == 0)
            holder.toggleButton.setChecked(false);
        else if(jobs.get(position).getIsfavorite() == 1)
            holder.toggleButton.setChecked(true);
        else
            Toast.makeText(getContext(), "Custom Array Adapter IsFavorite problem", Toast.LENGTH_LONG).show();
        return view;
    }
}