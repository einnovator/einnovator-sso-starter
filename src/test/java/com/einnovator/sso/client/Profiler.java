package com.einnovator.sso.client;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;


public class Profiler {

	public static class Stats {
		public long n;
		public long sum;
		public long mean;
		public long max;
		public long min;
		public long dev;
		
		@Override
		public String toString() {
			return "n=" + n + " mean=" + mean + " max=" + max + " min=" + min + " dev=" + dev;
		}
	}
	
	public static final Map<String, Stats> stats = new ConcurrentHashMap<>();
	
	public static Stats add(String s, long dt) {
		Stats stat = stats.get(s);
		if (stat==null) {
			stat = new Stats();
			stats.put(s, stat);
			stat.n = 1;
			stat.max = dt;
			stat.sum = dt;
			stat.min = dt;
			stat.mean = dt;
			stat.dev = 0;
		} else {
			stat.n++;
			if (dt>stat.max) {
				stat.max = dt;
			}
			if (dt<stat.min) {
				stat.min = dt;
			}
			stat.sum += dt;
			stat.dev = (stat.n-2)/(stat.n-1)*stat.dev + (dt - stat.mean)^2/stat.n;
			stat.mean = stat.sum/stat.n;
		}
		return stat;
	}
	
	public static final <T> T run(String s, Callable<T> f) throws RuntimeException {
		long t0 = System.currentTimeMillis();
		T result;
		try {
			result = f.call();
			long t1 = System.currentTimeMillis();
			long dt = t1-t0;
			Stats stat = add(s, dt);
			System.out.println(s + ":" + dt + " " + stat);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static final void run(int n, String s, Runnable f) throws RuntimeException {
		for (int i=0; i<n; i++) {
			long t0 = System.currentTimeMillis();
			try {
				f.run();
				long t1 = System.currentTimeMillis();
				long dt = t1-t0;
				Stats stat = add(s, dt);
				System.out.println(s + ":" + dt + " " + stat);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void dump() {
		if (stats==null) {
			return;
		}
		for (Map.Entry<String, Stats> e: stats.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}
	}

}
