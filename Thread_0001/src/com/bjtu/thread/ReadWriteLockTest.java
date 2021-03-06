package com.bjtu.thread;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



/**
 * 读写锁
 * 还有写锁之前挂读锁=更新锁
 * @author cuijianglin
 *
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final Queue3 q3 = new Queue3();
		for(int i = 0; i < 3; i++)
		{
			//三个线程来读数据
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true)
					{
						q3.get();
					}
				}
			}).start();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true)
					{
						q3.put(new Random().nextInt(10000));
					}
				}
			}).start();
		}
	}

}

class Queue3 
{
	private Object data = null;//共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据
	
	//增加一个读写锁
	ReadWriteLock rw1 = new ReentrantReadWriteLock();
	
	public void get()
	{
		//加读锁
		rw1.readLock().lock();

		try
		{
			System.out.println(Thread.currentThread().getName()+"be ready to read data;");
			Thread.sleep((long) (Math.random()* 1000));
			System.out.println(Thread.currentThread().getName()+"have read data:"+data);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rw1.readLock().unlock();
		}

	}
	
	public void put(Object data)
	{
		rw1.writeLock().lock();
		try
		{
			System.out.println(Thread.currentThread().getName()+"be ready to write data;");
			Thread.sleep((long) (Math.random()* 1000));
			this.data = data;
			System.out.println(Thread.currentThread().getName()+"have write data:"+data);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rw1.writeLock().unlock();
		}
		
	}
	
}