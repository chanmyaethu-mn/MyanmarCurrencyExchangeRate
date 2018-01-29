package com.example.chan.myanmarcurrencyexchangerate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.dto.CurrencyListItemInfoDto;

import java.util.List;

/**
 * Created by CHAN MYAE THU on 1/29/2018.
 */

public class CurrencyInfoListAdapter extends ArrayAdapter<CurrencyListItemInfoDto> {

    private Context mContext;

    private List<CurrencyListItemInfoDto> mCurrencyListItemInfoDtoList;

    public CurrencyInfoListAdapter(@NonNull Context context, @NonNull List<CurrencyListItemInfoDto> mCurrencyListItemInfoDtoList) {
        super(context, R.layout.currencytype_list_item, mCurrencyListItemInfoDtoList);
        this.mContext = context;
        this.mCurrencyListItemInfoDtoList = mCurrencyListItemInfoDtoList;
    }

    private static class ViewHolder{
        ImageView flagImgView;
        TextView curTypeTxtView;
        TextView countryTxtView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = convertView;

        if (null == rowView) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.currencytype_list_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.flagImgView = (ImageView) rowView.findViewById(R.id.ct_flag_imgview);
            viewHolder.curTypeTxtView = (TextView) rowView.findViewById(R.id.ct_cur_type_textview);
            viewHolder.countryTxtView = (TextView) rowView.findViewById(R.id.ct_country_textview);

            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        viewHolder.curTypeTxtView.setText(mCurrencyListItemInfoDtoList.get(position).getCurrencyType());
        viewHolder.countryTxtView.setText(mCurrencyListItemInfoDtoList.get(position).getCountry());

        Glide.with(mContext)
                .load("http://forex.cbm.gov.mm//template/img/flag/"+ mCurrencyListItemInfoDtoList.get(position).getCurrencyType() + "_24.png")
                .apply(RequestOptions.placeholderOf(R.drawable.cbm_logo_24).error(R.drawable.cbm_logo_24))
                .into(viewHolder.flagImgView);

        return rowView;

    }
}
