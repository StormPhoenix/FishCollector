package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

/**
 * 维护 监测点 界面
 */
@SuppressLint("ValidFragment")
public class MonitoringSiteFragment extends BaseFragment {

//    private ArrayAdapter<String> provinceAdapter = null;
//    private ArrayAdapter<String> cityAdapter = null;

    //城市在省级常量表中的下标值
    private int cityPosition = 0;

    //    private View addSurfaceView = null;
    private View addPictureView = null;

    //用于计算GridLayout一个View的大小
    private int size;
    private RelativeLayout.LayoutParams params = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitoring_site;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    private void setTimeText(final TextView text) {
//        Calendar cal = Calendar.getInstance();
//        final DatePickerDialog dpd = DatePickerDialog.newInstance(
//                dpl,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)
//        );
//        dpd.show(getFragmentManager(), "Datepickerdialog");
//        dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                MonthAdapter.CalendarDay day = dpd.getSelectedDay();
//                text.setText(TimeUtils.mergeTime(day.getYear(), day.getMonth() + 1, day.getDay()));
//            }
//        });
//    }

//    @Override
//    public void onClick(final View v) {
//        final Activity mActivity = MonitoringSiteFragment.this.getActivity();
//        switch (v.getId()) {
//            case R.id.monitoring_date:
//                setTimeText(monitoringDate);
//                break;
//            case R.id.end_time:
//                setTimeText(endTime);
//                break;
//            case R.id.start_time:
//                setTimeText(startTime);
//                break;
//            case R.id.img_start_location:
//                pb_start_locate.setVisibility(View.VISIBLE);
//                startLocate.setVisibility(View.INVISIBLE);
//                LocationUtils.locate(mActivity.getApplicationContext(), new Callback() {
//                    @Override
//                    public void onLBSResultBack(final double latitude, final double longitude) {
//                        mActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                pb_start_locate.setVisibility(View.INVISIBLE);
//                                startLocate.setVisibility(View.VISIBLE);
//                                startLongitude.setText(String.valueOf(longitude));
//                                startLatitude.setText(String.valueOf(latitude));
//                            }
//                        });
//                    }
//                });
//                break;
//            case R.id.img_end_location:
//                pb_end_locate.setVisibility(View.VISIBLE);
//                endLocate.setVisibility(View.INVISIBLE);
//                LocationUtils.locate(mActivity.getApplicationContext(), new Callback() {
//                    @Override
//                    public void onLBSResultBack(final double latitude, final double longitude) {
//                        mActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                pb_end_locate.setVisibility(View.INVISIBLE);
//                                endLocate.setVisibility(View.VISIBLE);
//                                endLongitude.setText(String.valueOf(longitude));
//                                endLatitude.setText(String.valueOf(latitude));
//                            }
//                        });
//                    }
//                });
//                break;
//            case R.id.add_pic:
//                File imageFile = PictureUtils.takePicture(this.getActivity());
//                ImageAware ia = new ImageAware(this.getActivity(), params);
//                ia.setImagePath(imageFile.getPath());
//                imagesAware.add(ia);
//                break;
//        }
//    }

//    @Override
//    public void updateUI() {
//        pictureGallery.removeAllViews();
//        for (final ImageAware ia : imagesAware) {
////            if (ia.checkIsFileExists()) {
////            } else {
////                imagesAware.remove(ia);
////                continue;
////            }
//            ia.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent it = new Intent(MonitoringSiteFragment.this.getActivity(), PhotoActivity.class);
//                    it.putExtra("filePath", ia.getImagePath());
//                    startActivity(it);
//                }
//            });
//            pictureGallery.addView(ia.getViewHolder());
//            ia.showImage();
//        }
//
//        addPictureView = LayoutInflater.from(getActivity())
//                .inflate(R.layout.grid_view_add_pic, null);
//        addPictureView.setLayoutParams(params);
//        addPictureView.setOnClickListener(this);
//        pictureGallery.addView(addPictureView);
//        System.gc();
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        switch (parent.getId()) {
//            case R.id.province:
//                provinceStr = ConstantData.PROVINCE[position];
//                cityPosition = position;
//                cityAdapter = new ArrayAdapter<>(getActivity(),
//                        android.R.layout.simple_spinner_item, ConstantData.CITY[position]);
//                city.setAdapter(cityAdapter);
//                city.setSelection(0);
//                break;
//            case R.id.city:
//                Log.e("OnItemSelected", ConstantData.CITY[cityPosition][position]);
//                cityIndex = position;
//                cityStr = ConstantData.CITY[cityPosition][position];
//                break;
//        }
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

//    @Override
//    public BaseNode save() {
//        MonitoringSite monitorSiteNode = null;
//        if (this.baseNode != null) {
//            monitorSiteNode = (MonitoringSite) this.baseNode;
//        } else {
//            return null;
//        }
//        String detectionUnitStr = detectionUnit.getText().toString(); //检测单位
//        String monitorsStr = monitors.getText().toString(); //检测人员
//        String monitoringDateStr = monitoringDate.getText().toString(); //日期
//        String addressDetails = village.getText().toString(); //详细地址
//        String waterAreaStr = waterArea.getText().toString(); //水域
//        String startTimeStr = startTime.getText().toString();
//        String endTimeStr = endTime.getText().toString();
//        String weatherStr = weather.getText().toString();
//        String temperatureStr = temperature.getText().toString();
//        String startLatitudeStr = startLatitude.getText().toString();
//        String startLongitudeStr = startLongitude.getText().toString();
//        String endLatitudeStr = endLatitude.getText().toString();
//        String endLongitudeStr = endLongitude.getText().toString();
//
//        try {
//            monitorSiteNode.setInstitution(detectionUnitStr);
//            monitorSiteNode.setInvestigator(monitorsStr);
//            monitorSiteNode.setInvestigationDate(monitoringDateStr);
//            monitorSiteNode.setSite(String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + addressDetails);
//            monitorSiteNode.setRiver(waterAreaStr);
//            monitorSiteNode.setStartTime(startTimeStr);
//            monitorSiteNode.setEndTime(endTimeStr);
//            monitorSiteNode.setWeather(weatherStr);
//            if (!StringUtils.isStringEmpty(temperatureStr)) {
//                monitorSiteNode.setTemperature(Float.valueOf(temperatureStr));
//            }
//            if (!StringUtils.isStringEmpty(startLatitudeStr)) {
//                monitorSiteNode.setStartLatitude(Float.valueOf(startLatitudeStr));
//            }
//            if (!StringUtils.isStringEmpty(startLongitudeStr)) {
//                monitorSiteNode.setStartLongitude(Float.valueOf(startLongitudeStr));
//            }
//            if (!StringUtils.isStringEmpty(endLatitudeStr)) {
//                monitorSiteNode.setEndLatitude(Float.valueOf(endLatitudeStr));
//            }
//            if (!StringUtils.isStringEmpty(endLongitudeStr)) {
//                monitorSiteNode.setEndLongitude(Float.valueOf(endLongitudeStr));
//            }
//            monitorSiteNode.setPhoto(StringUtils.mergeBySemicolon(imagesAware));
//        } catch (NumberFormatException nfe) {
//            Toast.makeText(getActivity(), "输入数据格式有误", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//
//        return monitorSiteNode;
//    }
}
