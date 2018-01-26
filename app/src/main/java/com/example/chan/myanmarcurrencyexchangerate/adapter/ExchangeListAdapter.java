package com.example.chan.myanmarcurrencyexchangerate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chan.myanmarcurrencyexchangerate.R;
import com.example.chan.myanmarcurrencyexchangerate.dto.ExchangeListItemInfoDto;

import java.util.List;

/**
 * Created by techfun on 1/26/2018.
 */

public class ExchangeListAdapter extends ArrayAdapter<ExchangeListItemInfoDto> {

    private Context mContext;

    private List<ExchangeListItemInfoDto> mExchangeListItemInfoDtoList;

    public ExchangeListAdapter(@NonNull Context context, @NonNull List<ExchangeListItemInfoDto> exchangeListItemInfoDtoList) {
        super(context, R.layout.exchange_list_item, exchangeListItemInfoDtoList);
        this.mContext = context;
        this.mExchangeListItemInfoDtoList = exchangeListItemInfoDtoList;
    }

    private static class ViewHolder{
        ImageView flagImgView;
        TextView curTypeTxtView;
        TextView exRateTxtView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.exchange_list_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.flagImgView = (ImageView) rowView.findViewById(R.id.ex_flag_imgview);
            viewHolder.curTypeTxtView = (TextView) rowView.findViewById(R.id.ex_cur_type_textview);
            viewHolder.exRateTxtView = (TextView) rowView.findViewById(R.id.ex_rate_textview);

            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        viewHolder.curTypeTxtView.setText(mExchangeListItemInfoDtoList.get(position).getCurrencyType());
        viewHolder.exRateTxtView.setText(mExchangeListItemInfoDtoList.get(position).getExchangeRate());

        Glide.with(mContext)
                .load("http://forex.cbm.gov.mm//template/img/flag/"+ mExchangeListItemInfoDtoList.get(position).getCurrencyType() + "_24.png")
                .apply(RequestOptions.placeholderOf(R.drawable.cbm_logo_24).error(R.drawable.cbm_logo_24))
                .into(viewHolder.flagImgView);

        return rowView;
    }
}
