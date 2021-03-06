package com.roselism.spot.model.domain.bmob;

import android.content.Context;

import com.roselism.spot.SPoTApplication;
import com.roselism.spot.model.db.dao.listener.OnDeleteListener;
import com.roselism.spot.model.db.dao.listener.OnUpdateListener;
import com.roselism.spot.model.db.dao.operator.FolderOperater;
import com.roselism.spot.model.db.dao.operator.PhotoOperater;
import com.roselism.spot.model.domain.local.File;
import com.roselism.spot.util.LogUtil;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;

/**
 * 持久层 Folder 对象
 * 照片等文件的文件夹
 * <p>
 * Created by hero2 on 2016/1/26.
 * <p>
 * 继承BmobObject，是为了能让其他类能够指向它
 * 请不要做任何修改！
 */
public class Folder extends BmobObject {
    private String name;// 文件夹的名称 (不可变对象)
    private User creater; // 文件夹的创建者
    private BmobRelation workers;// 被邀请的用户

    public Folder(String name, User creater) {
        this.name = name;
        this.creater = creater;
    }

    public Folder(File file) {
        setObjectId(file.getId());
        setName(file.getTitle());
        setCreater(file.getUser());
    }

    /**
     * 查询时使用，请不要使用该构造函数构造出来的对象的save等方法
     *
     * @param objectId
     */
    public Folder(String objectId) {
        setObjectId(objectId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getWorkers() {
        return workers;
    }

    public void setWorkers(BmobRelation workers) {
        this.workers = workers;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    @Override
    public void delete(final Context context, DeleteListener listener) {
        super.delete(context, listener); // 删除当前的Folder对象

        // 删除当前文件夹下所有的该用户的照片
        PhotoOperater.query.allPhotosFrom(this, (list -> { // 查询当前文件夹下的所有的照片
            for (Photo p : list) { // 遍历当前文件夹下的图片
                if (p.getUploader().getObjectId().equals(SPoTApplication.getUser().getObjectId())) // 只删除自己的照片

                    PhotoOperater.deleter.delete(p, new OnDeleteListener<Photo>() {
                        @Override
                        public void onDeleteFinished(Photo photo) {
                            LogUtil.i("照片" + photo.getName() + "删除成功");
                        }

                        @Override
                        public void onOperated(Photo photo) {
                            LogUtil.i("照片" + photo.getName() + "删除成功");
                        }
                    });
            }
        }));

        // 移除当前用户权限
        FolderOperater.updater.removeWorkder(this, (User) SPoTApplication.getUser(), new OnUpdateListener<User>() {
            @Override
            public void onUpdateFinished(User user) {
                if (user != null)
                    LogUtil.i("用户权限移除成功");
            }
        });
    }
}