package com.example.my_diary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
//import com.example.chapter08.bean.BillInfo;
//import com.example.chapter08.database.BillDBHelper;
//import com.example.chapter08.util.ViewUtil;

import java.util.Date;
import java.util.List;
public class SQLiteWriteActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {
    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private EditText title;
    private EditText text;
    private TextView date;
    private int xuhao;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_sqlite);
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        date = findViewById(R.id.date);
        findViewById(R.id.save_diary).setOnClickListener(this);
        findViewById(R.id.date).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        xuhao = getIntent().getIntExtra("xuhao", -1);
        mHelper = UserDBHelper.getInstance(this); // 获取账单数据库的帮助器对象
        if (xuhao != -1) { // 序号有值，就展示数据库里的账单详情
            List<UserInfo> bill_list = (List<UserInfo>) mHelper.queryById(xuhao);
            if (bill_list.size() > 0) { // 已存在该账单
                UserInfo bill = bill_list.get(0); // 获取账单信息
                Date date = DateUtil.formatString(bill.date);
                calendar.set(Calendar.YEAR, date.getYear()+1900);
                calendar.set(Calendar.MONTH, date.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, date.getDate());
                title.setText(bill.title); // 设置账单的描述文本
                text.setText(bill.text); // 设置账单的交易金额
            }
        }
        date.setText(DateUtil.getDate(calendar)); // 设置账单的发生时间
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_diary) {
            String title_string = title.getText().toString();
            String text_string = text.getText().toString();
            Log.d("SQLiteWriteActivity","onClick: "+title_string);
            if (TextUtils.isEmpty(title_string)) {
                Toast toast=Toast.makeText(this, "请先填写题目",Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else if (TextUtils.isEmpty(text_string)) {
                Toast toast_string=Toast.makeText(this, "请先输入内容",Toast.LENGTH_SHORT);
                toast_string.show();
                return;
            }
            // 以下声明一个用户信息对象，并填写它的各字段值
            UserInfo info =new UserInfo();
            //Toast.makeText(this, "保存",Toast.LENGTH_SHORT).show();
            info.title= title_string;
            info.text = text_string;
            info.xuhao = xuhao;
            info.date = date.getText().toString();
            info.month = 100*calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH)+1);
            mHelper.save(info); // 把账单信息保存到数据库
            //Toast.makeText(this, "已添加账单", Toast.LENGTH_SHORT).show();
            //resetPage(); // 重置页面
            //info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");
            //mHelper.insert(info); // 执行数据库帮助器的插入操作
            Toast.makeText(this, "已保存",Toast.LENGTH_SHORT).show();
            finish();
        }
        if(v.getId()==R.id.date){
            DatePickerDialog dialog=new DatePickerDialog(this,this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        if (v.getId() == R.id.iv_back) {
            finish();
        }


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date.setText(DateUtil.getDate(calendar));
    }



}



