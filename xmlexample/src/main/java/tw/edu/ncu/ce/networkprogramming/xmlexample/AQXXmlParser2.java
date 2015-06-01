package tw.edu.ncu.ce.networkprogramming.xmlexample;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AQXXmlParser2 {


    private static final String DATA_TAG = "Data";
    private static final String SITENAME_TAG = "SiteName";
    private static final String STATUS_TAG = "Status";
    private static final String PM25_TAG = "PM2.5";
    private String mSiteName;
    private String mStatus;
    private String mPM25;
    private boolean mIsParsingSiteName;
    private boolean mIsParsingStatus;
    private boolean mIsParsingPM25;

    private List<String> mResults = new ArrayList<String>();


    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            // Get the first Parser event and start iterating over the XML document
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    startTag(parser.getName());
                } else if (eventType == XmlPullParser.END_TAG) {
                    endTag(parser.getName());
                } else if (eventType == XmlPullParser.TEXT) {
                    text(parser.getText());
                }
                eventType = parser.next();
            }
            return mResults;
        } finally {
            in.close();
        }
    }

    public void startTag(String localName) {
        if (localName.equals(PM25_TAG)) {
            mIsParsingPM25 = true;
        } else if (localName.equals(STATUS_TAG)) {
            mIsParsingStatus = true;
        } else if (localName.equals(SITENAME_TAG)) {
            mIsParsingSiteName = true;
        }
    }

    public void text(String text) {
        if (mIsParsingPM25) {
            mPM25 = text.trim();
        } else if (mIsParsingStatus) {
            mStatus = text.trim();
        } else if (mIsParsingSiteName) {
            mSiteName = text.trim();
        }
    }

    public void endTag(String localName) {
        if (localName.equals(PM25_TAG)) {
            mIsParsingPM25 = false;
        } else if (localName.equals(STATUS_TAG)) {
            mIsParsingStatus = false;
        } else if (localName.equals(SITENAME_TAG)) {
            mIsParsingSiteName = false;
        } else if (localName.equals(DATA_TAG)) {
            mResults.add("地點" + ":"
                    + mSiteName + ","
                    + "空氣品質" + ":"
                    + mStatus + ","
                    + "PM2.5" + ":"
                    + mPM25);
            mPM25 = null;
            mStatus = null;
            mSiteName = null;
        }
    }
}
