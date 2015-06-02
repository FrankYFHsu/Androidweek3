package tw.edu.ncu.ce.networkprogramming.simpleairquality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class AQXDataAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<AQXData> mAQXData;


    public AQXDataAdapter(Context c, List<AQXData> aqxData) {
        mInflater = LayoutInflater.from(c);
        mAQXData = aqxData;
    }

    public void updateAQXData(List<AQXData> aqxData) {
        mAQXData = aqxData;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mAQXData.size();
    }

    @Override
    public AQXData getItem(int i) {
        return mAQXData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder ;
        if (view == null) {
            view = mInflater
                    .inflate(R.layout.item_aqxdata, viewGroup, false);

             holder = new ViewHolder();

            holder.sitetextView = (TextView) view.findViewById(R.id.sitetextView);
            holder.statustextView = (TextView) view.findViewById(R.id.statustextView);
            holder.pm25textView = (TextView) view.findViewById(R.id.pm25textView);
            view.setTag(holder);
        } else {
             holder = (ViewHolder) view.getTag();
        }


        AQXData aqxData = getItem(i);

        holder.sitetextView.setText(aqxData.getSiteName());
        holder.statustextView.setText("空氣品質 : "+aqxData.getStatus());
        holder.pm25textView.setText("PM2.5 : "+aqxData.getPM2_5());
        return view;
    }


    static class ViewHolder {
        TextView sitetextView;
        TextView statustextView;
        TextView pm25textView;
        int position;
    }
}
