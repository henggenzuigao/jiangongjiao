package com.whpe.qrcode.jiangxi_jian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.PassenerInfoBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yang on 2019/4/24.
 */

public class DBCustombusHelper extends SQLiteOpenHelper {
    /**
     * 数据库名
     */
    private static final String DB_NAME = "custombus.db";
    /**
     * 数据库版本号
     */
    private static final int DB_VERSION = 1;
    /**
     * 表名
     */
    private static final String PASSENER_TABLE_NAME = "passener";
    /**
     * 上下文
     */
    private Context context;
    private ParentActivity activity;

    /**
     * 构造方法
     *
     * @param context
     */
    public DBCustombusHelper(ParentActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        activity=context;
    }

    /**
     * 创建方法：当数据库不存在时才调用
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        createTable(db, PASSENER_TABLE_NAME);
        // 插入记录
        //insertRecords(db);
    }



    /**
     * 创建表
     *
     * @param tableName
     */
    private void createTable(SQLiteDatabase db, String tableName) {
        // 定义SQL字符串
        String strSQL = "CREATE TABLE " + tableName + "(_idcard text primary key, name text, sex text, phone text)";
        try {
            // 执行SQL，创建表
            db.execSQL(strSQL);
        } catch (SQLException e) {
            activity.showExceptionAlertDialog();
        }
    }

    /**
     * 插入表记录
     *
     */
    public void insertPasseners( ArrayList<PassenerInfoBean> passenerInfoBeans) {
        // 创建内容值对象
        // 获取一个数据库
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(PassenerInfoBean passenerInfoBean :passenerInfoBeans){
            // 通过键值对方式存放记录信息
            values.put("_idcard", passenerInfoBean.getIdcard());
            values.put("name", passenerInfoBean.getName());
            values.put("sex", passenerInfoBean.getSex());
            //values.put("idcard", passenerInfoBean.getIdcard());
            values.put("phone", passenerInfoBean.getPhone());
            // 调用db的插入方法，插入第10条记录
            db.insert(PASSENER_TABLE_NAME, null, values);
        }
        db.close();
    }

    /**
     * 查询方法(获取全部)
     *
     */
    public ArrayList<PassenerInfoBean> queryPasseners() {
        ArrayList<PassenerInfoBean> passenerInfoBeans=new ArrayList<PassenerInfoBean>();
        // 获取一个只读数据库
        SQLiteDatabase db = getReadableDatabase();
        // 执行查询方法，返回游标
        String sql="select * from "+PASSENER_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        //int i=0;
        while (cursor.moveToNext()) {
            //Log.e("YC",cursor.getString(cursor.getColumnIndex("name"))+"????"+i++);
            PassenerInfoBean passenerInfoBean = new PassenerInfoBean();
            passenerInfoBean.setIdcard(cursor.getString(cursor.getColumnIndex("_idcard")));
            passenerInfoBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            passenerInfoBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            passenerInfoBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            passenerInfoBeans.add(passenerInfoBean);
        }
        db.close();
        return passenerInfoBeans;
    }

    /**
     * 查询方法(获取一部分)
     *
     */
    public ArrayList<PassenerInfoBean> queryPasseners(String idcard) {
        ArrayList<PassenerInfoBean> passenerInfoBeans=new ArrayList<PassenerInfoBean>();
        // 获取一个只读数据库
        SQLiteDatabase db = getReadableDatabase();
        // 执行查询方法，返回游标
        String sql="select * from "+PASSENER_TABLE_NAME+" WHERE _idcard = '"+idcard+"'";
        Cursor cursor = db.rawQuery(sql, null);
        //int i=0;
        while (cursor.moveToNext()) {
            //Log.e("YC",cursor.getString(cursor.getColumnIndex("name"))+"????"+i++);
            PassenerInfoBean passenerInfoBean = new PassenerInfoBean();
            passenerInfoBean.setIdcard(cursor.getString(cursor.getColumnIndex("_idcard")));
            passenerInfoBean.setName(cursor.getString(cursor.getColumnIndex("name")));
            passenerInfoBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            passenerInfoBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            passenerInfoBeans.add(passenerInfoBean);
        }
        db.close();
        return passenerInfoBeans;
    }

    /**
     * 删除信息(一条)
     * @param idcard
     */
    public void deletePasseners( String idcard) {
        String[] whereArgs={idcard};
        // 获取一个可写数据库
        SQLiteDatabase db = getWritableDatabase();
        // 执行删除方法，返回删除记录数
        db.delete(PASSENER_TABLE_NAME, "_idcard=?", whereArgs);
        db.close();
        return;
    }

    /**
     * 删除信息(全部)
     */
    public void deletePasseners() {
        SQLiteDatabase db = getWritableDatabase();
        // 执行删除方法，返回删除记录数
        db.delete(PASSENER_TABLE_NAME, null,null);
        db.close();
        return;
    }

    /**
     * 更新方法（课堂练习）
     *
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return 0;
    }

    /**
     * 插入方法（课堂练习）
     *
     * @param values
     * @return
     */
    public int insert(ContentValues values) {
        return 0;
    }

    /**
     * 升级方法：当数据库升级时被调用
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 暂时不升级数据库
    }

}
