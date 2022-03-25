package com.example.templatetest.core.helper.map;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.util.Log;

import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class SVGBuilder {

    private InputStream data;
    private Integer searchColor = null;
    private Integer replaceColor = null;
    private ColorFilter strokeColorFilter = null, fillColorFilter = null;
    private boolean whiteMode = false;
    private boolean overideOpacity = false;
    private boolean closeInputStream = true;

    public SVGBuilder readFromInputStream(InputStream svgData) {
        this.data = svgData;
        return this;
    }

    public SVGBuilder readFromString(String svgData) {
        this.data = new ByteArrayInputStream(svgData.getBytes());
        return this;
    }

    public SVGBuilder readFromResource(Resources resources, int resId) {
        this.data = resources.openRawResource(resId);
        return this;
    }


    public SVGBuilder readFromAsset(AssetManager assetMngr, String svgPath) throws IOException {
        this.data = assetMngr.open(svgPath);
        return this;
    }

    public SVGBuilder clearColorSwap() {
        searchColor = replaceColor = null;
        return this;
    }

    public SVGBuilder setColorSwap(int searchColor, int replaceColor) {
        return setColorSwap(searchColor, replaceColor, false);
    }


    public SVGBuilder setColorSwap(int searchColor, int replaceColor, boolean overideOpacity) {
        this.searchColor = searchColor;
        this.replaceColor = replaceColor;
        this.overideOpacity = overideOpacity;
        return this;
    }


    public SVGBuilder setWhiteMode(boolean whiteMode) {
        this.whiteMode = whiteMode;
        return this;
    }


    public SVGBuilder setColorFilter(ColorFilter colorFilter) {
        this.strokeColorFilter = this.fillColorFilter = colorFilter;
        return this;
    }


    public SVGBuilder setStrokeColorFilter(ColorFilter colorFilter) {
        this.strokeColorFilter = colorFilter;
        return this;
    }


    public SVGBuilder setFillColorFilter(ColorFilter colorFilter) {
        this.fillColorFilter = colorFilter;
        return this;
    }


    public SVGBuilder setCloseInputStreamWhenDone(boolean closeInputStream) {
        this.closeInputStream = closeInputStream;
        return this;
    }

    public SVG build() throws SVGParseException {
        if (data == null) {
            throw new IllegalStateException("SVG input not specified. Call one of the readFrom...() methods first.");
        }

        try {
            final SVGParser.SVGHandler handler = new SVGParser.SVGHandler();
            handler.setColorSwap(searchColor, replaceColor, overideOpacity);
            handler.setWhiteMode(whiteMode);
            if (strokeColorFilter != null) {
                handler.strokePaint.setColorFilter(strokeColorFilter);
            }
            if (fillColorFilter != null) {
                handler.fillPaint.setColorFilter(fillColorFilter);
            }

            // SVGZ support (based on
            // https://github.com/josefpavlik/svg-android/commit/fc0522b2e1):
            if (!data.markSupported())
                data = new BufferedInputStream(data); // decorate stream so we
            // can use mark/reset
            try {
                data.mark(4);
                byte[] magic = new byte[2];
                int r = data.read(magic, 0, 2);
                int magicInt = (magic[0] + ((magic[1]) << 8)) & 0xffff;
                data.reset();
                if (r == 2 && magicInt == GZIPInputStream.GZIP_MAGIC) {
                    // Log.d(SVGParser.TAG, "SVG is gzipped");
                    GZIPInputStream gin = new GZIPInputStream(data);
                    data = gin;
                }
            } catch (IOException ioe) {
                throw new SVGParseException(ioe);
            }

            final SVG svg = SVGParser.parse(new InputSource(data), handler);
            return svg;

        } finally {
            if (closeInputStream) {
                try {
                    data.close();
                } catch (IOException e) {
                    Log.e(SVGParser.TAG, "Error closing SVG input stream.", e);
                }
            }
        }
    }
}
