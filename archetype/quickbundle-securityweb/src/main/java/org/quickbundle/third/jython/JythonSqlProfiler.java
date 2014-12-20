package org.quickbundle.third.jython;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JythonSqlProfiler {
	public static String getReplaceSql(String sql, String p_id, String p_ids) {
		sql = sql.replaceAll(p_id, "@id");
		return sql.replaceAll(p_ids, "$1");
	}
	
	public static void main(String[] args) {
		try {
			long startTime = System.currentTimeMillis();
			Map mData = new HashMap();
			InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("/tmp/cg_home/sql.log.1"))); 
			BufferedReader br = new BufferedReader(isr);//接收输入存至缓冲区，参数是System.inint c;
			List<String> lines = new ArrayList<String>();
			String line = null;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			
			for(String s : lines) {
				s = s.replaceAll("\\d{19}|'\\d{1,10}'", "@id");
				s = s.replaceAll("(@id\\s*,?){2,}", "$1");
				mData.put(s, null);
			}
			
			System.out.println("count:" + mData.size() + " cost " + (System.currentTimeMillis() - startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
