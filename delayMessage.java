package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class delayMessage {
	
	//标识正在检测的slot
	private int currentIndex = 0;
	
	//环形队列长度maxIndex秒
	private int maxIndex = 10;
	
	private Set<SlotBean>[] hss;
	
	private Timer timer;
	
	private delayTask dt;
	
	public delayMessage(){
		timer = new Timer();
	}
	
	@SuppressWarnings("unchecked")
	public void start(){
		hss = new HashSet[maxIndex];
		
		Set<SlotBean> slot = new HashSet<>();
		//从第0圈到第8圈，每圈打印一个数字
		for(int i=0; i<9; i++){
			SlotBean so = new SlotBean();
			so.setCycleNum(i);
			so.setTaskFun("第"+i+"圈");
			slot.add(so);
		}
		
		Set<SlotBean> slot2 = new HashSet<>();
		//第2圈第6秒
		SlotBean so2 = new SlotBean();
		so2.setCycleNum(2);
		so2.setTaskFun("我是第2圈第6秒");
		slot2.add(so2);
		
		hss[3] = slot;
		hss[5] = slot2;
		
		dt = new delayTask();
		timer.schedule(dt, 0, 1000);
	}
	
	private class SlotBean {
		private int cycleNum;
		private String taskFun;
		
		public int getCycleNum() {
			return cycleNum;
		}
		public void setCycleNum(int cycleNum) {
			this.cycleNum = cycleNum;
		}
		public String getTaskFun() {
			return taskFun;
		}
		public void setTaskFun(String taskFun) {
			this.taskFun = taskFun;
		}
		
		@Override
		public boolean equals(Object o){
			System.out.println("i am equals");
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			SlotBean slotBean = (SlotBean) o;
			
			if(slotBean.cycleNum == this.cycleNum && slotBean.taskFun.equals(this.taskFun))
			{
				return true;
			}
			return false;
		}
	}
	
	private class delayTask extends TimerTask{
		public void run(){
			System.out.print("*");
			if(hss[currentIndex] != null){
				Iterator<SlotBean> it = hss[currentIndex].iterator();
				while(it.hasNext()) {
					SlotBean o = it.next();
					if(o.getCycleNum()== 0){
						System.out.print(o.getTaskFun());
						it.remove();
					} else {
						o.setCycleNum(o.getCycleNum()-1);
					}
				}
			}
            currentIndex++;
            //是否到队列尾
            if(currentIndex==maxIndex){
            	currentIndex = 0;
            	System.out.println("end");
            }
        }
    }
	
	public static void main(String[] args) {
		delayMessage dm = new delayMessage();
		dm.start();
	}

}

