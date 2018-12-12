//package munchhunt.munchhuntproject.Feed;
//
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.List;
//
//import munchhunt.munchhuntproject.R;
//
//public class FeedAdapter extends BaseAdapter{
//
//    private List<Status> listData;
//    private LayoutInflater layoutInflater;
//    private Context context;
//
//    public FeedAdapter(Context aContext, List<Status> listData){
//        this.context = aContext;
//        this.listData = listData;
//        layoutInflater = LayoutInflater.from(aContext);
//    }
//
//    static class ViewHolder{
//        ImageView image1View;
//        ImageView image2View;
//        TextView dateView;
//        TextView messageView;
//        TextView nameView;
//    }
//
//    @Override
//    public int getCount() {
//        return listData.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent){
//        ViewHolder holder;
//
//        Status status = this.listData.get(position);
//
//        int imageId1 = this.getMipmapResIdByName(status.getUserPic());
////        int imageId2 = this.getMipmapResIdByName(status.getUserPic2());
//
////        holder.image2View.setImageResource(imageId2);
//
//        return convertView;
//
//    }
//
//    public int getMipmapResIdByName(String resName){
//        String pkgName = context.getPackageName();
//        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
//        Log.i("FeedAdapter", "Res Name: " + resName+ "==> Res ID = " + resID);
//        return resID;
//    }
//}
