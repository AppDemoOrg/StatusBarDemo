package com.pi.basic.utils;

import android.media.ExifInterface;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.pi.basic.log.LogHelper;

import java.io.File;
import java.io.IOException;

/**
 * @描述： @视频meta工具类
 * @作者： @蒋诗朋
 * @创建时间： @2017-04-25
 */
public final class MetadataUtil {
    private static final String TAG = "MetadataUtil";

    /**
     * 读取视频元数据
     * @param filepath
     * @return
     */
    public static final Metadata readMetadata(String filepath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Metadata metadata  = null;
        try {
            retriever.setDataSource(filepath);
            metadata                     = new Metadata();
//            metadata.length              = MemoryConverterUtil.BTrim.convert(new File(filepath).length());
            metadata.length              = new File(filepath).length();
            metadata.firstFrame         = retriever.getFrameAtTime();
            metadata.date                = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
            metadata.width               = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            metadata.height              = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            metadata.duration            = Integer.parseInt(retriever.
                    extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            metadata.source              = "Pilot Era";
            MediaExtractor extractor      = new MediaExtractor();
            extractor.setDataSource(filepath);
            String avc                    = null;
            String aac                    = null;
            String fps                    = null;
            for (int i = 0; i < extractor.getTrackCount(); i++) {
                MediaFormat trackFormat   = extractor.getTrackFormat(i);
                String mime               = trackFormat.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("video/")) {
                    fps                   = trackFormat.getInteger(MediaFormat.KEY_FRAME_RATE) +" fps";
                    avc                   = "H.264";
                } else if (mime.startsWith("audio/")) {
                    aac                   = "AAC";
                }
                if(null != avc && null != aac){
                    break;
                }
            }

            extractor.release();
            metadata.fps                  = fps;
            metadata.codec                = avc +" , " + aac;


            LogHelper.v(TAG,"metadata--->" + metadata.toString());
        } catch (Exception ex) {

        } finally {
            try {
                retriever.release();
            } catch (Exception ex) {
            }
        }
        return metadata;
    }

    public static final ExifInfo readExifInfo(String filepath){
        ExifInfo exifInfo             = null;
        try {
            exifInfo                  = new ExifInfo();
            final ExifInterface exif = new ExifInterface(filepath);
            exifInfo.width           = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            exifInfo.height          = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            exifInfo.length          = new File(filepath).length();
            final String shotTime    = exif.getAttribute(ExifInterface.TAG_DATETIME);
            exifInfo.date            = DateUtil.stringToString(shotTime,
                    DateStyle.YYYY_MM_DD_HH_MM_SS);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return exifInfo;
        }
    }
}
