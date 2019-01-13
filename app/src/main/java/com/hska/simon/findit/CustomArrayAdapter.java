package com.hska.simon.findit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hska.simon.findit.database.DataAccessHelper;
import com.hska.simon.findit.model.Job;

import java.util.List;

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
        protected ImageButton imageButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.list_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = view.findViewById(R.id.listTextView);
            viewHolder.imageButton = view.findViewById(R.id.myImageButton);
            viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    Job job = (Job) viewHolder.imageButton.getTag();
                    if (job.getIsfavorite() == 1) {
                        job.setIsfavorite(0);
                        dataAccessHelper = new DataAccessHelper(context);
                        dataAccessHelper.changeIsfavorite(job, 0);
                        viewHolder.imageButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    } else {
                        job.setIsfavorite(1);
                        dataAccessHelper = new DataAccessHelper(context);
                        dataAccessHelper.changeIsfavorite(job, 1);
                        viewHolder.imageButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                    }

                    //if(isChecked)
                    //    Toast.makeText(getContext(), "checked"+ job.getPosition(), Toast.LENGTH_LONG).show();
                    //else if(!isChecked)
                    //    Toast.makeText(getContext(), "unchecked"+ job.getPosition(), Toast.LENGTH_LONG).show();
                }
            });
            view.setTag(viewHolder);
            viewHolder.imageButton.setTag(jobs.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).imageButton.setTag(jobs.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(Html.fromHtml(jobs.get(position).toString()));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        holder.text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog diaBox = AskOption(jobs.get(position));
                diaBox.show();
                return true;
            }
        });
        if (jobs.get(position).getIsfavorite() == 0)
            holder.imageButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        else if (jobs.get(position).getIsfavorite() == 1)
            holder.imageButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        return view;
    }


    private AlertDialog AskOption(final Job job) {
        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(context)
                //set message, title, and icon
                .setTitle(R.string.delete)
                .setMessage(R.string.ask_delete)
                .setIcon(R.drawable.ic_delete_black_24dp)

                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dataAccessHelper = new DataAccessHelper(context);
                        dataAccessHelper.deleteJob(job);
                        dialog.dismiss();
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myDeleteDialogBox;
    }
}