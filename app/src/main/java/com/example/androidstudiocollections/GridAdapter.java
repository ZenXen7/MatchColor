package com.example.androidstudiocollections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private int[][] grid;

    public GridAdapter(Context context, int[][] grid) {
        this.context = context;
        this.grid = grid;
    }

    @Override
    public int getCount() {
        return grid.length * grid[0].length;
    }

    @Override
    public Object getItem(int position) {
        int row = position / grid.length;
        int col = position % grid[0].length;
        return grid[row][col];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300)); // Adjust according to your need
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        int row = position / grid.length;
        int col = position % grid[0].length;
        int tile = grid[row][col];


        if (tile == 1) {
            imageView.setImageResource(R.drawable.bluecandy);
        } else if(tile == 2) {
            imageView.setImageResource(R.drawable.orangecandy);
        }
        else if(tile == 3) {
            imageView.setImageResource(R.drawable.redcandy);
        }
        else if(tile == 4) {
            imageView.setImageResource(R.drawable.purplecandy);
        }

        return imageView;
    }
}
