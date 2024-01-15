package com.example.demo.tcp.util;

import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Detect off-heap memory
 * @author allen
 * January 2, 2019
 */
public class DirectMemoryThead implements Runnable{
	
	Logger logger = LoggerFactory.getLogger(DirectMemoryThead.class);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
		field.setAccessible(true);
		while(true){
			try{
				AtomicLong directMemory = (AtomicLong)field.get(PlatformDependent.class);
				int memoryInkb = (int)(directMemory.get()/1024);
				logger.info("Off-heap memory is used："+memoryInkb);
			}catch(Exception e){
				logger.info("Off-heap memory acquisition exception");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
