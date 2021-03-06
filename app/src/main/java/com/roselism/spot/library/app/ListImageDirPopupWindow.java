package com.roselism.spot.library.app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.roselism.spot.R;
import com.roselism.spot.adapter.CommonAdapter;
import com.roselism.spot.adapter.viewholder.CommonViewHolder;
import com.roselism.spot.library.widget.BasePopupWindowForListView;
import com.roselism.spot.model.domain.local.ImageFolder;

import java.util.List;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFolder> {
    private ListView mListDir;
    private OnImageDirSelected mImageDirSelected;

    public ListImageDirPopupWindow(int width, int height, List<ImageFolder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFolder>(context, mDatas, R.layout.list_item_dir) {
            @Override
            public void convert(CommonViewHolder helper, ImageFolder item) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setImageByUrl(R.id.id_dir_item_image, item.getFirstImagePath());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
            }
        });
    }

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

    public interface OnImageDirSelected {
        void selected(ImageFolder floder);
    }

}
