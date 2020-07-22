package com.ruscassie.video.ffmpeg;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
class InputMetadata {
        public static String MAJOR_BRAND = "major_brand";
        public static String MINOR_VERSION = "minor_version";
        public static String COMPATIBLE_BRANDS  ="compatible_brands";
        public static String CREATION_TIME = "creation_time";

        private String majorBrand;
        private String minorVersion;
        private String compatibleBrands;
        private String creationTime;
    }