package com.example.labmob2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class ContactAdapter extends ArrayAdapter {

    private List contactsInfoList;
    private Context context;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.contactsInfoList = objects;
        this.context = context;
    }


    private class ViewHolder{
        TextView displayName;
        TextView phoneNumber;
        ImageView thumbnailImage;

        public TextDrawable getDrawable(String text){
            String letter = "";

            if (text != null && !text.toString().isEmpty()){
                letter = text.toString().substring(0,1);
            }

            ColorGenerator colorGenerator = ColorGenerator.DEFAULT;
            TextDrawable drawable;
            int color = colorGenerator.getRandomColor();

            drawable = TextDrawable.builder().buildRound(letter, color);

            return drawable;
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_view_row, null);


            holder = new ViewHolder();
            holder.displayName = (TextView) convertView.findViewById(R.id.list_display_name);
            holder.phoneNumber = (TextView) convertView.findViewById(R.id.list_phone_number);
            holder.thumbnailImage = (ImageView) convertView.findViewById(R.id.thumbnail_image);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        ContactsInfo contactsInfo = (ContactsInfo) contactsInfoList.get(position);
        holder.displayName.setText(contactsInfo.getDisplayName());
        holder.phoneNumber.setText(contactsInfo.getPhoneNumber());

        TextDrawable drawable;
        drawable = holder.getDrawable(contactsInfo.getDisplayName());

        holder.thumbnailImage.setImageDrawable(drawable);

        return convertView;
    }

}
