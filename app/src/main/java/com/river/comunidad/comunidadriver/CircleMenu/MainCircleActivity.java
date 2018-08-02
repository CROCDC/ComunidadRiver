package com.river.comunidad.comunidadriver.CircleMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.river.comunidad.comunidadriver.R;
import com.river.comunidad.comunidadriver.View.Activitys.NoticiasActivity;


public class MainCircleActivity extends AppCompatActivity {

	private CircleMenuLayout mCircleMenuLayout;

	private int[] mItemImgs = new int[] {
			R.drawable.eventosjpg, R.drawable.actividadesjpg,
			R.drawable.sabiasquejpg, R.drawable.historiasdevidajpg};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maincircle);

		mCircleMenuLayout = findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs);

		mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
		{

			@Override
			public void itemClick(View view, int pos)
			{
				Toast.makeText(MainCircleActivity.this, mItemImgs[pos],Toast.LENGTH_SHORT).show();
			}

			@Override
			public void itemCenterClick(View view){
				startActivity(new Intent(MainCircleActivity.this, NoticiasActivity.class));
			}
		});
	}
}