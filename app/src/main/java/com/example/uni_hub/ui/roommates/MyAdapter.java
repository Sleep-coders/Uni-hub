package com.example.uni_hub.ui.roommates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni_hub.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[];
    int images[];
    Context context;

    public MyAdapter(Context ct, String s1[], int img[]){
        context=ct;
        data1=s1;
        images=img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);

    }

//
//            public void onStart(){
//              super.onStart();
//
//                Button btn= (Button) context.findViewById(R.id.reqRoomBtnId);
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent= new Intent(context, Room_Owner.class);
//                        startActivity(intent);
//                    }
//                });
//
//            }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        holder.myText1.setText(data1[position]);
//        holder.myText2.setText(data1[position]);
//        holder.myText3.setText(data1[position]);
//        holder.myText4.setText(data1[position]);
//
//        holder.myImage.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView myText1, myText2, myText3, myText4;
        ImageView myImage;
        Button btn;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            myText1= itemView.findViewById(R.id.postTimeId);
            myText2= itemView.findViewById(R.id.userNameId);
            myText3= itemView.findViewById(R.id.locationId);
            myText4= itemView.findViewById(R.id.costId);

            myImage= itemView.findViewById(R.id.roomImageId);

            btn= itemView.findViewById(R.id.reqRoomBtnId);
        }
    }


}