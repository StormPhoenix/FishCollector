package com.stormphoenix.fishcollector.shared.constants;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.fragments.BenthosFragment;
import com.stormphoenix.fishcollector.mvp.ui.fragments.MonitoringSiteFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public class ModelConstantMap {

    public static final ModelHolder BENTHOS_HOLDER = new ModelHolder(ModelNames.BENTHOS_NAME, R.string.ic_folder, BenthosFragment.class.getName(), Arrays.asList(ModelConstant.DOMINANT_BENTHOS_SPECIES));
    public static final ModelHolder CATCHES_HOLDER = new ModelHolder(ModelNames.CATCHES_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.FISHES, ModelConstant.FISH_EGGS));
    public static final ModelHolder CATCH_TOOLS_HOLDER = new ModelHolder(ModelNames.CATCH_TOOLS_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder DOMINANT_BENTHOS_SPECIES_HOLDER = new ModelHolder(ModelNames.DOMINANT_BENTHOS_SPECIES_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder DOMINANT_PHYTOPLANKTON_SPECIES_HOLDER = new ModelHolder(ModelNames.DOMINANT_PHYTOPLANKTON_SPECIES_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder DOMINANT_ZOOPLANKTON_SPECIES_HOLDER = new ModelHolder(ModelNames.DOMINANT_ZOOPLANKTON_SPECIES_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder FISH_EGGS_HOLDER = new ModelHolder(ModelNames.FISH_EGGS_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder FISHES_HOLDER = new ModelHolder(ModelNames.FISHES_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder FRACTURE_SURFACE_HOLDER = new ModelHolder(ModelNames.FRACTURE_SURFACE_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.MEASURING_LINE, ModelConstant.SEDIMENT, ModelConstant.PHYTOPLANKTON, ModelConstant.ZOOPLANKTON, ModelConstant.BENTHOS));
    //    public static final ModelHolder FRACTURE_SURFACE_SEDIMENT_HOLDER = new ModelHolder(ModelNames.FRACTURE_SURFACE_SEDIMENT_NAME, R.string.ic_folder, "hello", null);
    public static final ModelHolder MEASURING_LINE_HOLDER = new ModelHolder(ModelNames.MEASURING_LINE_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.MEASURING_POINT));
    public static final ModelHolder MEASURING_POINT_HOLDER = new ModelHolder(ModelNames.MEASURING_POINT_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.WATER_LAYER));
    public static final ModelHolder MONITORING_SITE_HOLDER = new ModelHolder(ModelNames.MONITORING_SITE_NAME, R.string.ic_folder, MonitoringSiteFragment.class.getName(), Arrays.asList(ModelConstant.FRACTURE_SURFACE));
    public static final ModelHolder PHYTOPLANKTON_HOLDER = new ModelHolder(ModelNames.PHYTOPLANKTON_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.DOMINANT_PHYTOPLANKTON_SPECIES));
    public static final ModelHolder SEDIMENT_HOLDER = new ModelHolder(ModelNames.SEDIMENT_NAME, R.string.ic_folder, "hello", new ArrayList<String>());
    public static final ModelHolder WATER_LAYER_HOLDER = new ModelHolder(ModelNames.WATER_LAYER_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.CATCH_TOOLS, ModelConstant.CATCHES));
    //    public static final ModelHolder WATER_LAYER_CATCH_TOOLS_HOLDER = new ModelHolder(ModelNames.WATER_LAYER_CATCH_TOOLS_NAME, R.string.ic_folder, "hello", null);
    public static final ModelHolder ZOOPLANKTON_HOLDER = new ModelHolder(ModelNames.ZOOPLANKTON_NAME, R.string.ic_folder, "hello", Arrays.asList(ModelConstant.DOMINANT_ZOOPLANKTON_SPECIES));

    private static Map<String, ModelHolder> modelMap = new LinkedHashMap<>();

    public static ModelHolder getHolder(String className) {
        return modelMap.get(className);
    }

    static {
        modelMap.put(ModelConstant.BENTHOS, BENTHOS_HOLDER);
        modelMap.put(ModelConstant.CATCHES, CATCHES_HOLDER);
        modelMap.put(ModelConstant.CATCH_TOOLS, CATCH_TOOLS_HOLDER);
        modelMap.put(ModelConstant.DOMINANT_BENTHOS_SPECIES, DOMINANT_BENTHOS_SPECIES_HOLDER);
        modelMap.put(ModelConstant.DOMINANT_PHYTOPLANKTON_SPECIES, DOMINANT_PHYTOPLANKTON_SPECIES_HOLDER);
        modelMap.put(ModelConstant.DOMINANT_ZOOPLANKTON_SPECIES, DOMINANT_ZOOPLANKTON_SPECIES_HOLDER);
        modelMap.put(ModelConstant.FISH_EGGS, FISH_EGGS_HOLDER);
        modelMap.put(ModelConstant.FISHES, FISHES_HOLDER);
        modelMap.put(ModelConstant.FRACTURE_SURFACE, FRACTURE_SURFACE_HOLDER);
        modelMap.put(ModelConstant.MEASURING_LINE, MEASURING_LINE_HOLDER);
        modelMap.put(ModelConstant.MEASURING_POINT, MEASURING_POINT_HOLDER);
        modelMap.put(ModelConstant.MONITORING_SITE, MONITORING_SITE_HOLDER);
        modelMap.put(ModelConstant.PHYTOPLANKTON, PHYTOPLANKTON_HOLDER);
        modelMap.put(ModelConstant.SEDIMENT, SEDIMENT_HOLDER);
        modelMap.put(ModelConstant.WATER_LAYER, WATER_LAYER_HOLDER);
        modelMap.put(ModelConstant.ZOOPLANKTON, ZOOPLANKTON_HOLDER);
    }

    /**
     * 如果 ModelHolder 不声明为 static， 则会出错
     */
    public static class ModelHolder {
        public String MODEL_NAME;
        public int iconResId;
        public String fragmentClassName;
        // 对应的子节点的唯一标示
        public List<String> subModels;

        public ModelHolder(String model_name, int iconResId, String fragmentClassName, List<String> subModels) {
            MODEL_NAME = model_name;
            this.iconResId = iconResId;
            this.fragmentClassName = fragmentClassName;
            this.subModels = subModels;
        }
    }
}
