package com.ruscassie.video.ffprobe;

public enum EnumFFProbe {
        INDEX("index"),
        CODEC_NAME("codec_name"),
        CODEC_LONG_NAME("codec_long_name"),
        PROFILE("profile"),
        CODEC_TYPE("codec_type"),
        CODEC_TIME_BASE("codec_time_base"),
        CODEC_TAG_STRING("codec_tag_string"),
        CODEC_TAG("codec_tag"),
        WIDTH("width"),
        HEIGHT("height"),
        CODED_WIDTH("coded_width"),
        CODED_HEIGHT("coded_height"),
        CLOSED_CAPTIONS("closed_captions"),
        HAS_B_FRAMES("has_b_frames"),
        SAMPLE_ASPECT_RATIO("sample_aspect_ratio"),
        DISPLAY_ASPECT_RATIO("display_aspect_ratio"),
        PIX_FMT("pix_fmt"),
        LEVEL("level"),
        COLOR_RANGE("color_range"),
        COLOR_SPACE("color_space"),
        COLOR_TRANSFER("color_transfer"),
        COLOR_PRIMARIES("color_primaries"),
        CHROMA_LOCATION("chroma_location"),
        FIELD_ORDER("field_order"),
        TIMECODE("timecode"),
        REFS("refs"),
        IS_AVC("is_avc"),
        NAL_LENGTH_SIZE("nal_length_size"),
        ID("id"),
        R_FRAME_RATE("r_frame_rate"),
        AVG_FRAME_RATE("avg_frame_rate"),
        TIME_BASE("time_base"),
        START_PTS("start_pts"),
        START_TIME("start_time"),
        DURATION_TS("duration_ts"),
        DURATION("duration"),
        BIT_RATE("bit_rate"),
        MAX_BIT_RATE("max_bit_rate"),
        BITS_PER_RAW_SAMPLE("bits_per_raw_sample"),
        NB_FRAMES("nb_frames"),
        NB_READ_FRAMES("nb_read_frames"),
        NB_READ_PACKETS("nb_read_packets"),
        DISPOSITION_DEFAULT("DISPOSITION:default"),
        DISPOSITION_DUB("DISPOSITION:dub"),
        DISPOSITION_ORIGINAL("DISPOSITION:original"),
        DISPOSITION_COMMENT("DISPOSITION:comment"),
        DISPOSITION_LYRICS("DISPOSITION:lyrics"),
        DISPOSITION_KARAOKE("DISPOSITION:karaoke"),
        DISPOSITION_FORCED("DISPOSITION:forced"),
        DISPOSITION_HEARING_IMPAIRED("DISPOSITION:hearing_impaired"),
        DISPOSITION_VISUAL_IMPAIRED("DISPOSITION:visual_impaired"),
        DISPOSITION_CLEAN_EFFECTS("DISPOSITION:clean_effects"),
        DISPOSITION_ATTACHED_PIC("DISPOSITION:attached_pic"),
        DISPOSITION_TIMED_THUMBNAILS("DISPOSITION:timed_thumbnails");
    private String value = "";


    EnumFFProbe(String value){
        this.value = value;
    }

    public String toString(){
        return value;
    }
}
