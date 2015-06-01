package tw.edu.ncu.ce.networkprogramming.xmlexample;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AQXXmlParser {

    private static final String AQX_TAG = "AQX";
    private static final String DATA_TAG = "Data";
    private static final String SITENAME_TAG ="SiteName";
    private static final String STATUS_TAG = "Status";
    private static final String PM25_TAG ="PM2.5";


    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readAQX(parser);
        } finally {
            in.close();
        }
    }



    private List readAQX(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, AQX_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the Data tag
            if (name.equals(DATA_TAG)) {
                entries.add(readData(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private AQXData readData(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, DATA_TAG);
        String SiteName = null;
        String Status = null;
        String pm25 = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(SITENAME_TAG)) {
                SiteName = readTags(parser, SITENAME_TAG);
            } else if (name.equals(STATUS_TAG)) {
                Status = readTags(parser, STATUS_TAG);
            } else if (name.equals(PM25_TAG)) {
                pm25 = readTags(parser, PM25_TAG);
            } else {
                skip(parser);
            }
        }
        return new AQXData(SiteName, Status, pm25);
    }

    private String readTags(XmlPullParser parser,String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,tag);
        String tagData = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return tagData;
    }

    // extracts the text values of tags.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }




}
