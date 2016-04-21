package com.roselism.spot.model.dao.bmob.query;

import com.roselism.spot.model.OnOperateListener;
import com.roselism.spot.model.Strategy;
import com.roselism.spot.model.dao.operator.FolderOperater;
import com.roselism.spot.model.domain.bmob.Folder;
import com.roselism.spot.model.domain.bmob.User;

import java.util.List;

/**
 * Created by simon on 16-4-19.
 */
public class QueryFolderByCreater implements Strategy<OnOperateListener<List<Folder>>> {
    User user;

    public QueryFolderByCreater(User user) {
        this.user = user;
    }

    @Override
    public void Do(OnOperateListener<List<Folder>> onOperateListener) {
        FolderOperater operater = new FolderOperater(null);
        operater.findFolderCreateBy(user, (data) -> {
            onOperateListener.onOperated(data);
        });
    }
}