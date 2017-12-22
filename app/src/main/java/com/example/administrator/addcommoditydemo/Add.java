package com.example.administrator.addcommoditydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.kevin.wraprecyclerview.WrapRecyclerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${李小磊} on 2017/12/22/022.
 */

public class Add extends Activity {

    @InjectView(R.id.rv_certification_hospital1)
    WrapRecyclerView rvCertificationHospital1;
    private View footerView;
    private View headerView;
    private ImagePickerAdapter adapter;
    private int maxImgCount = 8;               //允许选择图片最大数
    private ArrayList<ImageItem> hospitalImageList = new ArrayList<>(); //医院执照图片
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT_HOSPITAL = 102;//医院执照
    public static final int REQUEST_CODE_PREVIEW_HOSPITAL = 103; //预览医院执照
    private ArrayList<ImageItem> deleteImageList;
    private String newPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.inject(this);

        initHeaderView();
        initFooterView();
        initHospitalRecyclerView();
    }

    private void initFooterView() {
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_certification_footerview, null);
    }

    private void initHeaderView() {
        headerView = LayoutInflater.from(this).inflate(R.layout.layout_certification_headerview, null);
    }

    /**
     * 初始化医院执照图片选择器列表
     */
    private void initHospitalRecyclerView() {
        adapter = new ImagePickerAdapter(this, hospitalImageList, maxImgCount);
        adapter.setOnItemClickListener(new ImagePickerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImagePicker.getInstance().setMultiMode(true);
                switch (position) {
                    case IMAGE_ITEM_ADD:
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - hospitalImageList.size());
                        Intent intent = new Intent(Add.this, ImageGridActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_SELECT_HOSPITAL);
                        break;
                    default:
                        //打开预览
                        Intent intentPreview = new Intent(Add.this, ImagePreviewDelActivity.class);
                        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW_HOSPITAL);
                        break;
                }
            }
        });
        rvCertificationHospital1.setLayoutManager(new GridLayoutManager(this, 4));
        rvCertificationHospital1.addHeaderView(headerView);
        rvCertificationHospital1.addFooterView(footerView);
        rvCertificationHospital1.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null && requestCode == REQUEST_CODE_SELECT_HOSPITAL) {
            try {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < images.size(); i++) {
                    ImageItem temp = images.get(i);
                    temp.isLocal = true;
                    hospitalImageList.add(temp);
                }
                adapter.setImages(hospitalImageList);
                //                rvCertificationHospital1.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK && data != null && requestCode == REQUEST_CODE_PREVIEW_HOSPITAL) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
            deleteImageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.DELETE_IMAGE_LIST);
            Log.i("deleteImageList", deleteImageList.size() + "");
            //                    for (int a = 0; a < deleteImageList.size(); a++) {
            ////                        if (!images1.equals("{,}")) {
            ////                            images1 = "{";
            //                            if (deleteImageList.get(a).path.startsWith("http")) {
            //
            //                                Log.i("fkalsj", "");
            //                                image_id1 = imageList.get(a).verify_image_id;
            //                               String images111 = "\"" + image_id1 + "\"" + ",";
            //                                Log.i("复活甲啊",images111);
            //                                //                            String url = deleteImageList.get(a).path;
            //                                //                            deleteImage(url);
            //                            } else {
            //                                Toast.makeText(mContext, "删除" + a, Toast.LENGTH_SHORT).show();
            //                            }
            ////                            images1 = images1.substring(0, images.lastIndexOf(","));
            ////                            images1 += "}";
            ////                            Log.i("复活甲啊",images1);
            ////                        }
            //                    }

            hospitalImageList.clear();
            int size = images.size();
            StringBuilder sbPath = new StringBuilder();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    String path = images.get(i).path;

                    if (path.startsWith("http")) {
                        sbPath.append(images.get(i).imgId).append(",");
                    }
                    ImageItem temp = images.get(i);
                    temp.isLocal = true;
                    hospitalImageList.add(temp);
                }

                String strPath = sbPath.toString();
                if ("".equals(strPath)) {
                    newPath = "IMAGESDELETE";
                } else {
                    newPath = strPath.substring(0, strPath.length() - 1);
                }
            } else {
                newPath = "IMAGESDELETE";

            }
            System.out.println("newPath: " + newPath);
            adapter.setImages(hospitalImageList);
        }
    }
}
