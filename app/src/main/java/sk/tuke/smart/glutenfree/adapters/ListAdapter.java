package sk.tuke.smart.glutenfree.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import sk.tuke.smart.glutenfree.DetailActivity;
import sk.tuke.smart.glutenfree.R;
import sk.tuke.smart.glutenfree.pojo.Podniky;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Podniky> podnikyList;
    private List<Podniky> podnikyArrayList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fragmentName;
        TextView fragmentType;
        ImageView fragmentStar;
        ImageView fragmentStar2;
        ImageView fragmentStar3;
        ImageView fragmentStar4;
        ImageView fragmentStar5;
        CardView cardViewFragment;


        ViewHolder(final View itemView) {
            super(itemView);
            cardViewFragment = (CardView)itemView.findViewById(R.id.card);
            fragmentName = (TextView) itemView.findViewById(R.id.fragment_name);
            fragmentType = (TextView) itemView.findViewById(R.id.fragment_type);
            fragmentStar = (ImageView) itemView.findViewById(R.id.fragment_imageView);
            fragmentStar2 = (ImageView) itemView.findViewById(R.id.fragment_imageView2);
            fragmentStar3 = (ImageView) itemView.findViewById(R.id.fragment_imageView3);
            fragmentStar4 = (ImageView) itemView.findViewById(R.id.fragment_imageView4);
            fragmentStar5 = (ImageView) itemView.findViewById(R.id.fragment_imageView5);

        }
    }

    public List<Podniky> getPodnikyList() {
        return podnikyList;
    }

    public ListAdapter(List<Podniky> podnikyList) {
        this.podnikyList = podnikyList;
        podnikyArrayList.addAll(this.podnikyList);
    }

//    //Filter used in searView in MainActivity
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        timeZones = new ArrayList<>();
//        if (charText.length() == 0) {
//            timeZones.addAll(timeZoneArrayList);
//        } else {
//            for (TimeZone tz: timeZoneArrayList) {
//                if (tz.getName().toLowerCase(Locale.getDefault()).contains(charText)) timeZones.add(tz);
//            }
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewfragment, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fragmentName.setText(podnikyList.get(position).getName());
        holder.fragmentType.setText(podnikyList.get(position).getText());
        if (podnikyList.get(position).getStars()>=1){
            holder.fragmentStar.setImageResource(android.R.drawable.star_big_on);
            holder.cardViewFragment.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
        if (podnikyList.get(position).getStars()>1){
            holder.fragmentStar2.setImageResource(android.R.drawable.star_big_on);
            holder.cardViewFragment.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
        if (podnikyList.get(position).getStars()>2) {
            holder.fragmentStar3.setImageResource(android.R.drawable.star_big_on);
            holder.cardViewFragment.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
        if (podnikyList.get(position).getStars()>3) {
            holder.fragmentStar4.setImageResource(android.R.drawable.star_big_on);
            holder.cardViewFragment.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }
        if (podnikyList.get(position).getStars()==5) {
            holder.fragmentStar5.setImageResource(android.R.drawable.star_big_on);
            holder.cardViewFragment.setBackgroundColor(Color.parseColor("#FFEBEE"));
        }

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("podnik", (Serializable) podnikyList.get(holder.getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        };

        holder.cardViewFragment.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return podnikyList.size();
    }

    public void addAll(List<Podniky> podnikyList){
        this.podnikyList.clear();
        this.podnikyArrayList.clear();
        this.podnikyList.addAll(podnikyList);
        this.podnikyArrayList.addAll(podnikyList);
        if (this.podnikyList==null) Log.d("RVAdapter", "ArrayList is null!");
        notifyDataSetChanged();
    }


}
