package com.ruscassie.video.ffmpeg;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutPutMetadata {

    private static String MAJOR_BRAND = "    major_brand     ";
    private static String MINOR_VERSION = "    minor_version   ";
    private static String COMPATIBLE_BRANDS = "    compatible_brands";
    private static String ENCODER = "    encoder         ";
    private static String STREAM = "    Stream #";
    private static String METADATA = "    Metadata";
    private static String SIDE_DATA = "    Side data";

    private String majorBrand ;
    private String minorVersion ;
    private String compatibleBrands;
    private String encoder;
    private String stream;
    private Metadata metadata;
    private SideData sideData;
}
