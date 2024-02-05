package com.cameraview_lib.filter;

import androidx.annotation.NonNull;

import com.cameraview_lib.filters.AutoFixFilter;
import com.cameraview_lib.filters.BlackAndWhiteFilter;
import com.cameraview_lib.filters.BrightnessFilter;
import com.cameraview_lib.filters.ContrastFilter;
import com.cameraview_lib.filters.CrossProcessFilter;
import com.cameraview_lib.filters.DocumentaryFilter;
import com.cameraview_lib.filters.DuotoneFilter;
import com.cameraview_lib.filters.FillLightFilter;
import com.cameraview_lib.filters.GammaFilter;
import com.cameraview_lib.filters.GrainFilter;
import com.cameraview_lib.filters.GrayscaleFilter;
import com.cameraview_lib.filters.HueFilter;
import com.cameraview_lib.filters.InvertColorsFilter;
import com.cameraview_lib.filters.LomoishFilter;
import com.cameraview_lib.filters.PosterizeFilter;
import com.cameraview_lib.filters.SaturationFilter;
import com.cameraview_lib.filters.SepiaFilter;
import com.cameraview_lib.filters.SharpnessFilter;
import com.cameraview_lib.filters.TemperatureFilter;
import com.cameraview_lib.filters.TintFilter;
import com.cameraview_lib.filters.VignetteFilter;

/**
 * Contains commonly used {@link Filter}s.
 *
 * You can use {@link #newInstance()} to create a new instance and
 * pass it to {@link com.cameraview_lib.CameraView#setFilter(Filter)}.
 */
public enum Filters {

    /** @see NoFilter */
    NONE(NoFilter.class),

    /** @see AutoFixFilter */
    AUTO_FIX(AutoFixFilter.class),

    /** @see BlackAndWhiteFilter */
    BLACK_AND_WHITE(BlackAndWhiteFilter.class),

    /** @see BrightnessFilter */
    BRIGHTNESS(BrightnessFilter.class),

    /** @see ContrastFilter */
    CONTRAST(ContrastFilter.class),

    /** @see CrossProcessFilter */
    CROSS_PROCESS(CrossProcessFilter.class),

    /** @see DocumentaryFilter */
    DOCUMENTARY(DocumentaryFilter.class),

    /** @see DuotoneFilter */
    DUOTONE(DuotoneFilter.class),

    /** @see FillLightFilter */
    FILL_LIGHT(FillLightFilter.class),

    /** @see GammaFilter */
    GAMMA(GammaFilter.class),

    /** @see GrainFilter */
    GRAIN(GrainFilter.class),

    /** @see GrayscaleFilter */
    GRAYSCALE(GrayscaleFilter.class),

    /** @see HueFilter */
    HUE(HueFilter.class),

    /** @see InvertColorsFilter */
    INVERT_COLORS(InvertColorsFilter.class),

    /** @see LomoishFilter */
    LOMOISH(LomoishFilter.class),

    /** @see PosterizeFilter */
    POSTERIZE(PosterizeFilter.class),

    /** @see SaturationFilter */
    SATURATION(SaturationFilter.class),

    /** @see SepiaFilter */
    SEPIA(SepiaFilter.class),

    /** @see SharpnessFilter */
    SHARPNESS(SharpnessFilter.class),

    /** @see TemperatureFilter */
    TEMPERATURE(TemperatureFilter.class),

    /** @see TintFilter */
    TINT(TintFilter.class),

    /** @see VignetteFilter */
    VIGNETTE(VignetteFilter.class);

    private Class<? extends Filter> filterClass;

    Filters(@NonNull Class<? extends Filter> filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * Returns a new instance of the given filter.
     * @return a new instance
     */
    @NonNull
    public Filter newInstance() {
        try {
            return filterClass.newInstance();
        } catch (IllegalAccessException e) {
            return new NoFilter();
        } catch (InstantiationException e) {
            return new NoFilter();
        }
    }
}
