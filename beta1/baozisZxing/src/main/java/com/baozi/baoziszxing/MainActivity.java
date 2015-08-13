package com.baozi.baoziszxing;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baozi.Zxing.CaptureActivity;
import com.baozi.activity.LoginActivity;
import com.example.baoziszxing.R;
import com.heinrichreimersoftware.materialdrawer.DrawerView;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;

/**
 * 
 * @author Baozi
 * @联系方式: 2221673069@qq.com
 * 
 * @描述: 1 project能扫描二维码和普通一维码 ;
 * 
 *      2 能从相册拿到二维码照片然后进行解析 --->注意 照片中的二维码 在拍摄的时候需要正对齐 否则会解析不出
 * 
 *      3 针对大部分中文解决乱码问题 , 但依旧会有部分编码格式会出现中文乱码 如解决请联系我 QQ:2221673069
 * 
 *      4 Zxing在使用过程中发现了新问题 :如果扫描的时候手机与二维码的角度超过30度左右的时候就解析不了 如解决请联系我
 *      QQ:2221673069
 * 
 *      谢谢大家 希望对大家有用
 */
public class MainActivity extends ActionBarActivity {
	private DrawerView drawer;

	private ActionBarDrawerToggle drawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MainActivity.this,
//						CaptureActivity.class);
//
//				startActivityForResult(intent, 100);
//
//			}
//		});

		final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawer = (DrawerView) findViewById(R.id.drawer);
		//顶端菜单栏
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);


		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};

		drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.color_primary_dark));
		drawerLayout.setDrawerListener(drawerToggle);
		drawerLayout.closeDrawer(drawer);//关闭侧滑菜单；

		/**
		 * 菜单栏开始 
		 */

//		// 无图片
//		drawer.addItem(new DrawerItem()
//						.setTextPrimary("11")
//						.setTextSecondary("12")
//		);
		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("11"));

//		有图片
//		drawer.addItem(new DrawerItem()
//						.setImage(getResources().getDrawable(R.drawable.ic_mail))
//						.setTextPrimary("21")
//						.setTextSecondary("22")
//		);
		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("21"));

		//        drawer.addDivider();
		//圆形图片
//		drawer.addItem(new DrawerItem()
//						.setRoundedImage((BitmapDrawable) getResources().getDrawable(R.drawable.cat_1))
//						.setTextPrimary("31")
//						.setTextSecondary("32")
//		);
		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("31"));

//		drawer.addItem(new DrawerHeaderItem().setTitle("41"));
		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("41"));

		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("51"));

		drawer.addItem(new DrawerItem().setImage(getResources().getDrawable(R.drawable.ic_mail)).setTextPrimary("61"));


//		drawer.addItem(new DrawerItem()
//						.setRoundedImage((BitmapDrawable) getResources().getDrawable(R.drawable.cat_2), DrawerItem.SMALL_AVATAR)
//						.setTextPrimary("61")
//						.setTextSecondary("62", DrawerItem.THREE_LINE)
//		);
		//默认选择1
		drawer.selectItem(1);
		//菜单中间监听
		drawer.setOnItemClickListener(new DrawerItem.OnItemClickListener() {
			@Override
			public void onClick(DrawerItem item, long id, int position) {
				drawer.selectItem(position);
				switch (position){
					case 0:
						Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(intent1);
						drawerLayout.closeDrawer(drawer);
						break;
				}
//				Toast.makeText(MainActivity.this, "Clicked item #11111" + position, Toast.LENGTH_SHORT).show();
			}
		});


		drawer.addFixedItem(new DrawerItem()
						.setRoundedImage((BitmapDrawable) getResources().getDrawable(R.drawable.cat_2), DrawerItem.SMALL_AVATAR)
						.setTextPrimary("71")
		);

		drawer.addFixedItem(new DrawerItem()
						.setImage(getResources().getDrawable(R.drawable.ic_flag))
						.setTextPrimary("81")
		);
		//菜单下方按钮监听
		drawer.setOnFixedItemClickListener(new DrawerItem.OnItemClickListener() {
			@Override
			public void onClick(DrawerItem item, long id, int position) {
				drawer.selectFixedItem(position);
				Toast.makeText(MainActivity.this, "Clicked fixed item22222 #" + position, Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);

				startActivityForResult(intent, 100);
				
			}
		});


		drawer.addProfile(new DrawerProfile()
						.setId(1)
						.setRoundedAvatar((BitmapDrawable) getResources().getDrawable(R.drawable.cat_1))
						.setBackground(getResources().getDrawable(R.drawable.cat_wide_1))
						.setName("91")
						.setDescription("92")
		);

		drawer.addProfile(new DrawerProfile()
						.setId(2)
						.setRoundedAvatar((BitmapDrawable) getResources().getDrawable(R.drawable.cat_2))
						.setBackground(getResources().getDrawable(R.drawable.cat_wide_1))
						.setName("101")
		);

		drawer.addProfile(new DrawerProfile()
						.setId(3)
						.setRoundedAvatar((BitmapDrawable) getResources().getDrawable(R.drawable.cat_1))
						.setBackground(getResources().getDrawable(R.drawable.cat_wide_2))
						.setName("111")
						.setDescription("112")
		);

		//头像监听
		drawer.setOnProfileClickListener(new DrawerProfile.OnProfileClickListener() {
			@Override
			public void onClick(DrawerProfile profile, long id) {
				Toast.makeText(MainActivity.this, "Clicked profile *" + id, Toast.LENGTH_SHORT).show();
			}
		});
		//下拉菜单监听
		drawer.setOnProfileSwitchListener(new DrawerProfile.OnProfileSwitchListener() {
			@Override
			public void onSwitch(DrawerProfile oldProfile, long oldId, DrawerProfile newProfile, long newId) {
				Toast.makeText(MainActivity.this, "Switched from profile *" + oldId + " to profile *" + newId, Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		super.onActivityResult(arg0, arg1, data);

		/**
		 * 拿到解析完成的字符串
		 */
		if (data != null) {
//			TextView text = (TextView) findViewById(R.id.textView1);
//			text.setText(data.getStringExtra("result"));
			Toast.makeText(getApplication(),data.getStringExtra("result"),Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
//	//主界面按钮
//	public void openDrawerFrameLayout(View view) {
//		Intent intent = new Intent(this, MainActivity2.class);
//		startActivity(intent);
//	}
//
//	public void openDrawerActivity(View view) {
//		Intent intent = new Intent(this, MainActivity3.class);
//		startActivity(intent);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
			case R.id.action_github:
				String url = "https://github.com/HeinrichReimer/material-drawer";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}
}
