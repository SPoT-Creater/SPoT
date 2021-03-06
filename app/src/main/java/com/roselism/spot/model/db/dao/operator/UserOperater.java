package com.roselism.spot.model.db.dao.operator;

import android.content.Context;

import com.roselism.spot.model.db.dao.listener.OnBuildListener;
import com.roselism.spot.model.db.dao.listener.OnDeleteListener;
import com.roselism.spot.model.db.dao.listener.OnLoadListener;
import com.roselism.spot.model.domain.bmob.User;
import com.roselism.spot.model.domain.local.Image;
import com.roselism.spot.model.engine.bmob.listener.OnFindListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.SaveListener;


/**
 * 操作User model的方法类
 * Created by simon on 2016/4/13.
 * 增
 * 删
 * 改
 * 查
 *
 * @deprecated
 */
public class UserOperater extends Operater {

    public static AddOperater adder = getAdder();
    public static DeleteOperater deleter = getDeleter();
    public static UpdateOperater updater = getUpdater();
    public static QueryOperater query = getQuery();

    public static Context mContext; // 上下文对象


    /*
     * 获取添加器
     *
     * @return
     */
    private static AddOperater getAdder() {
        ; // 初始化上下文对象
        return new AddOperater();
    }

    /**
     * 获取删除器
     * 静态工厂方法
     *
     * @return
     */
    private static DeleteOperater getDeleter() {
//        initContext(); // 初始化上下文对象
        return new DeleteOperater();
    }

    /**
     * 获取升级器
     *
     * @return
     */
    private static UpdateOperater getUpdater() {
//        initContext(); // 初始化上下文对象
        return new UpdateOperater();
    }

    /**
     * 获取查询器
     *
     * @return
     */
    private static QueryOperater getQuery() {
//        initContext(); // 初始化上下文对象
        return new QueryOperater();
    }

    /**
     * 增加器
     * 用于创建新的对象
     */
    public static class AddOperater extends UserOperater {
        volatile User user;

        /**
         * 设置用户的昵称
         *
         * @param nickName
         * @return
         */
        public AddOperater setNickName(String nickName) {
            user.setNickName(nickName);
            return this;
        }

        /**
         * 设置用户的头像
         *
         * @param profile
         * @return
         */
        public AddOperater setProfile(Image profile) {
            user.setProfile(profile);
            return this;
        }

        /**
         * 建造对象
         */
        public void build(OnBuildListener<User> listener) {
            if (user == null)
                throw new IllegalArgumentException("User不能为null，在调用build方法之前请先调用newUser()方法");
            if (mContext == null || user.getProfile() == null)
                throw new IllegalArgumentException("用户头像不能为null");

            user.save(mContext, new SaveListener() { // 储存user对象
                @Override
                public void onSuccess() {
                    listener.onBuildFinished(user);
                }

                @Override
                public void onFailure(int i, String s) {
                    listener.onBuildFinished(null);
                }
            });
        }
    }

    public static class DeleteOperater extends UserOperater {

        /**
         * 删除某用户
         *
         * @param user 要被删除的用户（希望不会用到这个功能）
         */
        public static void delete(User user, OnDeleteListener<User> onDeleteListener) {
            user.delete(mContext, new cn.bmob.v3.listener.DeleteListener() {
                @Override
                public void onSuccess() {
                    onDeleteListener.onDeleteFinished(user);
                }

                @Override
                public void onFailure(int i, String s) {
                    onDeleteListener.onDeleteFinished(null);
                }
            });
        }
    }

    public static class UpdateOperater extends UserOperater {

    }

    public static class QueryOperater extends UserOperater {

        public void getUserByEmail(String email, OnLoadListener<User> listener) {
            if (mContext == null)
                throw new IllegalArgumentException("上下文对象不能为null,需要在此方法之前调用setContext(Context context) 方法");
            BmobQuery<User> query = new BmobQuery<>(); // 查询
            query.addWhereEqualTo("email", email);
            query.findObjects(mContext, new OnFindListener(listener));
        }
    }
}