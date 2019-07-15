package jboss.kisconsult.com.championsassembly.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import jboss.kisconsult.com.championsassembly.App.Config;
import jboss.kisconsult.com.championsassembly.Model.User;
import jboss.kisconsult.com.championsassembly.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewsAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<User> data;

    public NewsAdapter(Context context, ArrayList<User> data) {
        super(context, Config.NEWS_RESOURCES);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(Config.NEWS_RESOURCES, parent, false);
        TextView headline, daty;
        CircleImageView imageView;

        headline = (TextView) row.findViewById(R.id.headline);
        daty = (TextView) row.findViewById(R.id.date);
        imageView = (CircleImageView) row.findViewById(R.id.profile_pix);

        headline.setText(data.get(position).getFullname());
        daty.setText(data.get(position).getUsername());
       // mobile.setText(data.get(position).getMobile());

        return row;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}

