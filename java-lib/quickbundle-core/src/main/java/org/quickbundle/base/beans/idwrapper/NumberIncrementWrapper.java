package org.quickbundle.base.beans.idwrapper;

import java.util.Random;

import org.quickbundle.base.beans.NumberIncrementService;
import org.quickbundle.base.beans.TableIdRuleVo;
import org.quickbundle.itf.base.IRmIdWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * 实现集群+高并发下从中心节点获取ID段，并在内存中自增，最小值从1开始
 * 
 * @author 白小勇
 */
public class NumberIncrementWrapper implements IRmIdWrapper {
	private static Logger log = LoggerFactory.getLogger(NumberIncrementWrapper.class.getName());
	
	TableIdRuleVo ruleVo = null;
	NumberIncrementService nfoService = null;

	static Random random = new Random();
	
	long blocksize = 10000;
	int maxAttempts = 3;

	// runtime state
	long lastId = -2;
	long nextId = -1;


	public NumberIncrementWrapper(TableIdRuleVo ruleVo) {
		this.ruleVo = ruleVo;
		nfoService = new NumberIncrementService(); 
	}

	public void init() {
		//取到本表的最大值，并记录到RM_ID_POOL
		nextId = nfoService.initIdPool(ruleVo, blocksize);
		lastId = nextId + blocksize -1;
	}

	public String[] nextValue(int length) {
		String[] result = new String[length];
		for (int i = 0; i < length; i++) {
			result[i] = nextValue();
		}
		return result;
	}

	public synchronized String nextValue() {
		// if no more ids available
		if (lastId < nextId) {
			// acquire a next block of ids
			log.debug("last id " + lastId + " was consumed.  acquiring new block...");

			// reset the id block
			lastId = -2;
			nextId = -1;

			// try couple of times
			try {
				acquireDbidBlock();
			} catch (Exception e) {
				throw new RuntimeException("couldn't acquire block of ids", e);
			}
		}

		return String.valueOf(nextId ++);
	}

	protected void acquireDbidBlock() {
		for (int attempts = maxAttempts; (attempts > 0) && (nextId == -1); attempts--) {
			try {
				// acquire block
				nextId = nfoService.acquireId(ruleVo, blocksize);
				lastId = nextId + blocksize - 1;

				log.debug("acquired new id block [" + nextId + "-" + lastId + "]");

			} catch (DataAccessException e) {
				// optimistic locking exception indicating another thread tried to acquire a block of ids concurrently
				attempts--;

				// if no attempts left
				if (attempts == 0) {
					// fail the surrounding transaction
					throw new RuntimeException("couldn't acquire block of ids, tried " + maxAttempts + " times");
				}

				// if there are still attempts left, first wait a bit
				int millis = 20 + random.nextInt(200);
				log.debug("optimistic locking failure while trying to acquire id block.  retrying in " + millis + " millis");
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e1) {
					log.debug("waiting after id block locking failure got interrupted");
				}
			}
		}
	}
}
