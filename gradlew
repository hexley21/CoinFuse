PRG="$0"
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link";
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`


DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use th<vector xmlns:android="http://schemas.android.com/apk/res/android"
android:width="108dp"
android:height="108dp"
android:viewportWidth="108"
android:viewportHeight="108">
                       <group android:scaleX="0.103125"
android:scaleY="0.103125"
android:translateX="27.6"
android:translateY="27.6">
                   <path
android:pathData="M263.36,413.93v34.19c19,10.49 25.52,22.13 21,37.52 -4,13.58 -16.2,22.08 -30.63,21.31 -13,-0.69 -24.7,-11.2 -26.82,-24.13 -2.54,-15.44 4.89,-27.36 21.55,-34.25L248.46,412.32a158.63,158.63 0,0 1,-83.82 -29.25c-7.72,12.37 -15.65,24.08 -22.5,36.4 -4.18,7.5 -9.31,10.25 -17.83,9.84 -14.3,-0.7 -28.66,-0.19 -43.1,-0.19 -0.58,1.43 -1.08,2.69 -1.61,3.94a29.67,29.67 0,0 1,-33.27 17.86c-13.06,-2.4 -23.31,-14.43 -23.66,-27.8 -0.38,-14.47 9.08,-26.93 22.82,-30.05s27.63,3.41 33.37,16.5c1.85,4.21 3.91,5.39 8.31,5.27 12.26,-0.34 24.53,0 36.8,-0.21 1.77,0 4.32,-0.78 5.16,-2.08 7.44,-11.43 14.58,-23.05 21.79,-34.63 0.72,-1.16 1.35,-2.37 2.2,-3.86q-49,-44.3 -53.55,-110.52L63.78,263.54c-10.42,18.67 -21.83,25.19 -37,21a29.63,29.63 0,0 1,0.85 -57.37c15.3,-3.57 27.1,3.41 36,21.33L99.59,248.5q4.43,-66.18 53.64,-110.69c-8.13,-13 -16.11,-25.93 -24.23,-38.74a4.57,4.57 0,0 0,-3.31 -1.74c-14.85,-0.12 -29.7,-0.08 -44.4,-0.08C73.4,114.88 61,122.31 45.62,119a29.64,29.64 0,0 1,0.06 -57.77c15.53,-3.32 27.78,4.06 35.63,21.68 15.57,0 31.28,0.19 47,-0.11 5.55,-0.1 8.91,1.94 11.72,6.62 7.89,13.16 16.16,26.08 24.55,39.54a159.16,159.16 0,0 1,84.19 -29.28c0,-11.61 0.13,-23 -0.19,-34.3 0,-1.22 -2.49,-2.74 -4.12,-3.48 -11.62,-5.27 -19,-17 -18,-28.94C227.56,19.7 235.81,9.41 248,6.09a29.55,29.55 0,0 1,19.59 55.64c-1.26,0.55 -2.56,1 -4.1,1.62L263.49,99.57a159.28,159.28 0,0 1,83.69 29.28c1,-1.49 2,-2.8 2.86,-4.19C357.43,112.8 365,101 372.13,89c2.73,-4.56 6.14,-6.31 11.42,-6.22 15.7,0.26 31.42,0.1 47,0.1 8.47,-17.95 20.69,-25.21 36.1,-21.64a29.7,29.7 0,0 1,22.63 29.7C489,104.36 479,116.44 465.94,119c-12.51,2.48 -28.4,-2.35 -35.21,-21.73 -14.86,0 -29.86,0 -44.86,0.07A4,4 0,0 0,383 99c-8.1,12.82 -16.1,25.72 -24.26,38.82 32.78,29.72 50.67,66.49 53.6,110.94 11.59,0 22.95,0.12 34.3,-0.19 1.2,0 2.72,-2.52 3.44,-4.15 5,-11.45 16.77,-18.8 29,-18A29.58,29.58 0,1 1,450.42 268c-0.63,-1.41 -1.18,-2.86 -1.86,-4.54h-36.2c-2.89,43.77 -20.63,80.59 -53.63,110.52 4.2,6.78 8.43,13.63 12.68,20.46 3.65,5.87 7.23,11.78 11,17.53 0.81,1.22 2.53,2.59 3.85,2.6 14.84,0.19 29.69,0.12 44.39,0.12 7.47,-17.32 19.61,-24.76 35.12,-21.78 13.23,2.53 23.36,14.83 23.48,28.49 0.13,14.12 -9.59,26.51 -23,29.38 -15.43,3.3 -27.69,-4.09 -35.64,-21.68 -14.36,0 -28.89,-0.52 -43.36,0.2 -8.44,0.42 -13.28,-2.56 -17.34,-9.78 -6.92,-12.3 -14.81,-24.06 -21.75,-35.18 -13.9,6.58 -27,14.12 -41,19S278.53,410.4 263.36,413.93ZM255.71,398.05c78.16,0 142.17,-63.27 142.3,-140.74C398.15,177.67 335,114 255.69,113.94c-77.82,-0.06 -141.7,63.61 -141.75,141.28C113.9,334.3 177.14,398 255.71,398.05ZM256,49.66a15.14,15.14 0,1 0,-15.33 -15A15.27,15.27 0,0 0,256 49.66ZM37.06,90.06a15.12,15.12 0,1 0,15 -15.3A15.17,15.17 0,0 0,37.06 90.06ZM474.9,90.17a15.12,15.12 0,1 0,-15.1 15.2A15.29,15.29 0,0 0,474.9 90.17ZM492.55,256.23a15.14,15.14 0,1 0,-15.23 15A15.26,15.26 0,0 0,492.55 256.23ZM19.4,256a15.14,15.14 0,1 0,15 -15.33A15.16,15.16 0,0 0,19.4 256ZM37.06,421.7a15.13,15.13 0,1 0,15.22 -15.08A15.2,15.2 0,0 0,37.06 421.7ZM474.9,422a15.12,15.12 0,1 0,-15 15.26A15.26,15.26 0,0 0,474.9 422ZM256,492.58a15.13,15.13 0,1 0,-15.31 -14.95A15.17,15.17 0,0 0,256 492.58Z"
android:strokeWidth="10"
android:fillColor="#00c555"
android:strokeColor="#00c555"/>
                    <path
android:pathData="M263.25,192.5c3.34,0.73 6.55,1.29 9.68,2.12a62.45,62.45 0,0 1,28.23 16.29c2,2 2,3.77 0.18,5.79 -1.27,1.39 -2.55,2.76 -3.84,4.13 -2.28,2.42 -4,2.43 -6.48,0A50.09,50.09 0,0 0,263.69 207c-24.55,-4.1 -49,11.87 -55.39,36.13 -6.9,26.17 8,53 33.92,60.34 18,5.08 34.06,1 48,-11.48 2.78,-2.49 4,-2.55 6.8,0 1.28,1.14 2.58,2.25 3.84,3.41 2.08,1.9 2.15,3.92 0.16,5.87a63.18,63.18 0,0 1,-33.92 17.46l-3.83,0.65c0,3.36 0,6.62 0,9.88 0,3.48 -1.17,4.62 -4.71,4.63 -1.82,0 -3.65,0 -5.47,0 -2.56,-0.07 -3.87,-1.38 -3.91,-3.93 0,-3 -0.06,-6.08 0,-9.12 0,-1.19 -0.34,-1.62 -1.53,-1.78A63.63,63.63 0,0 1,243 193.73c1.61,-0.35 3.26,-0.58 4.9,-0.79 0.94,-0.11 1.32,-0.46 1.3,-1.46 0,-3.1 0,-6.19 0,-9.29 0,-2.66 1.32,-4 4,-4 2,-0.05 4.09,0 6.13,0 2.66,0 4,1.37 4,4C263.27,185.62 263.25,189.09 263.25,192.5Z"
android:strokeWidth="10"
android:fillColor="#83ff8f"
android:strokeColor="#83ff8f"/>
                    </group>
</vector>
e maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptor limit: $MAX_FD"
        fi
    else
        warn "Could not query maximum file descriptor limit: $MAX_FD_LIMIT"
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if $darwin; then
    GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ "$cygwin" = "true" -o "$msys" = "true" ] ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`

    JAVACMD=`cygpath --unix "$JAVACMD"`

    # We build the pattern for arguments to be converted via cygpath
    ROOTDIRSRAW=`find -L / -maxdepth 1 -mindepth 1 -type d 2>/dev/null`
    SEP=""
    for dir in $ROOTDIRSRAW ; do
        ROOTDIRS="$ROOTDIRS$SEP$dir"
        SEP="|"
    done
    OURCYGPATTERN="(^($ROOTDIRS))"
    # Add a user-defined pattern to the cygpath arguments
    if [ "$GRADLE_CYGPATTERN" != "" ] ; then
        OURCYGPATTERN="$OURCYGPATTERN|($GRADLE_CYGPATTERN)"
    fi
    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    i=0
    for arg in "$@" ; do
        CHECK=`echo "$arg"|egrep -c "$OURCYGPATTERN" -`
        CHECK2=`echo "$arg"|egrep -c "^-"`                                 ### Determine if an option

        if [ $CHECK -ne 0 ] && [ $CHECK2 -eq 0 ] ; then                    ### Added a condition
            eval `echo args$i`=`cygpath --path --ignore --mixed "$arg"`
        else
            eval `echo args$i`="\"$arg\""
        fi
        i=`expr $i + 1`
    done
    case $i in
        0) set -- ;;
        1) set -- "$args0" ;;
        2) set -- "$args0" "$args1" ;;
        3) set -- "$args0" "$args1" "$args2" ;
        4) set -- "$args0" "$args1" "$args2" "$args3" ;
        5) set -- "$args0" "$args1" "$args2" "$args3" "$args4" ;
        6) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" ;;
        7) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" ;;
        8) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" ;;
        9) set -- "$args0" "$args1" "$args2" "$args3" "$args4" "$args5" "$args6" "$args7" "$args8" ;;
    esac
fi

# Escape application args
save () {
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}
APP_ARGS=`save "$@"`

# Collect all arguments for the java command, following the shell quoting and substitution rules
eval set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "\"-Dorg.gradle.appname=$APP_BASE_NAME\"" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS"

exec "$JAVACMD" "$@"
