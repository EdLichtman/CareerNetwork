package database;

/**
 * Created by EdwardLichtman on 4/10/16.
 */
public class JobApplicationSchema {

    public static final class JobApplicationTable {
        // Table Names
        public static final String NAME = "TBJobApplications";

        public static final class Cols {
            public static final String strUUID = "uuid";
            public static final String strCOMPANY_NAME = "companyName";
            public static final String strPOSITION_TITLE = "positionTitle";
            public static final String blnINTERVIEW = "interview";
            public static final String strCITY = "city";
            public static final String strSTATE = "state";
            public static final String dtmLISTED = "listedDate";
            public static final String dtmAPPLIED = "appliedDate";
        }
    }

}
