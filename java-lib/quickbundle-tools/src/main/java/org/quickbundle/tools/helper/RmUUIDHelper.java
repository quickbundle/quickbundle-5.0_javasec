package org.quickbundle.tools.helper;

import java.util.UUID;

import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.Logger;
import org.safehaus.uuid.UUIDGenerator;


public class RmUUIDHelper {
	private static EthernetAddress defaultEa = null;
	static {
		//defaultEa = NativeInterfaces.getPrimaryInterface();
	}
	/**
	 * 全局获得uuid的唯一入口方法
	 * @return
	 */
	public static String generateUUID() {
		return UUIDGenerator.getInstance().generateTimeBasedUUID().toString();
	}
	
	/**
	 * 从基于时间戳的uuid字符串，获得UUID专用时间戳
	 * @param uuid
	 * @return
	 */
	public static long getTimestamp(String uuid) {
		UUID uu = UUID.fromString(uuid);
		return uu.timestamp();
	}
	
    /**
     * Since System.longTimeMillis() returns time from january 1st 1970,
     * and UUIDs need time from the beginning of gregorian calendar
     * (15-oct-1582), need to apply the offset:
     */
    private static final long kClockOffset = 0x01b21dd213814000L;
    /**
     * Also, instead of getting time in units of 100nsecs, we get something
     * with max resolution of 1 msec... and need the multiplier as well
     */
    private static final long kClockMultiplier = 10000;
    private static final long kClockMultiplierL = 10000L;

    /**
     * Let's allow "virtual" system time to advance at most 100 milliseconds
     * beyond actual physical system time, before adding delays.
     */
    private static final long kMaxClockAdvance = 100L;
    
    // // // Clock state:

    /**
     * Additional state information used to protect against anomalous
     * cases (clock time going backwards, node id getting mixed up).
     * Third byte is actually used for seeding counter on counter
     * overflow.
     */
    private static final byte[] mClockSequence = new byte[3];

    /**
     * Last physical timestamp value <code>System.currentTimeMillis()</code>
     * returned: used to catch (and report) cases where system clock
     * goes backwards. Is also used to limit "drifting", that is, amount
     * timestamps used can differ from the system time value. This value
     * is not guaranteed to be monotonically increasing.
     */
    private static long mLastSystemTimestamp = 0L;
    
    /**
     * Timestamp value last used for generating a UUID (along with
     * {@link #mClockCounter}. Usually the same as
     * {@link #mLastSystemTimestamp}, but not always (system clock
     * moved backwards). Note that this value is guaranteed to be
     * monotonically increasing; that is, at given absolute time points
     * t1 and t2 (where t2 is after t1), t1 <= t2 will always hold true.
     */
    private static long mLastUsedTimestamp = 0L;

    /**
     * First timestamp that can NOT be used without synchronizing
     * using synchronization object ({@link #mSync}). Only used when
     * external timestamp synchronization (and persistence) is used,
     * ie. when {@link #mSync} is not null.
     */
    private static long mFirstUnsafeTimestamp = Long.MAX_VALUE;

    /**
     * Counter used to compensate inadequate resolution of JDK system
     * timer.
     */
    private static int mClockCounter = 0;
	
	public static long getSysTimestamp() {
        long systime = System.currentTimeMillis();

        /* Let's first verify that the system time is not going backwards;
         * independent of whether we can use it:
         */
        if (systime < mLastSystemTimestamp) {
            Logger.logWarning("System time going backwards! (got value "+systime+", last "+mLastSystemTimestamp);
            // Let's write it down, still
            mLastSystemTimestamp = systime;
        }

        /* But even without it going backwards, it may be less than the
         * last one used (when generating UUIDs fast with coarse clock
         * resolution; or if clock has gone backwards over reboot etc).
         */
        if (systime <= mLastUsedTimestamp) {
            /* Can we just use the last time stamp (ok if the counter
             * hasn't hit max yet)
             */
            if (mClockCounter < kClockMultiplier) { // yup, still have room
                systime = mLastUsedTimestamp;
            } else { // nope, have to roll over to next value and maybe wait
                long actDiff = mLastUsedTimestamp - systime;
                long origTime = systime;
                systime = mLastUsedTimestamp + 1L;

                Logger.logWarning("Timestamp over-run: need to reinitialize random sequence");
            }
        } else {
            /* Clock has advanced normally; just need to make sure counter is
             * reset to a low value (need not be 0; good to leave a small
             * residual to further decrease collisions)
             */
            mClockCounter &= 0xFF;
        }

        mLastUsedTimestamp = systime;

        /* Now, let's translate the timestamp to one UUID needs, 100ns
         * unit offset from the beginning of Gregorian calendar...
         */
        systime *= kClockMultiplierL;
        systime += kClockOffset;

        // Plus add the clock counter:
        systime += mClockCounter;
        return systime;
	}
	public static void main(String[] args) {
		System.out.println(getSysTimestamp());
	}
}
