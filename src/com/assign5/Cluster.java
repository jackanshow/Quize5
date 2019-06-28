package com.assign5;

import java.util.ArrayList;
import java.util.List;
 
public class Cluster {
    private int id;// ��ʶ
    private Point center;// ����
    private List<Point> members = new ArrayList<Point>();// ��Ա
 
    public Cluster(int id, Point center) {
        this.id = id;
        this.center = center;
    }
 
    public Cluster(int id, Point center, List<Point> members) {
        this.id = id;
        this.center = center;
        this.members = members;
    }
    int count=0;
    public void addPoint(Point newPoint) {
       /* //�ظ��ľͲ����
    	if (!members.contains(newPoint)){
            members.add(newPoint);
        }else{
        	count++;
            System.out.println("�������ݵ� {"+newPoint.toString()+"} �Ѿ����ڣ�"+count);
        }*/
    	
    	//�޸�Ϊ�ظ�Ҳ���
    	members.add(newPoint);
    }
 
    public int getId() {
        return id;
    }
 
    public Point getCenter() {
        return center;
    }
 
    public void setCenter(Point center) {
        this.center = center;
    }
 
    public List<Point> getMembers() {
        return members;
    }
 
    @Override
    public String toString() {
        String toString = "Cluster \n" + "Cluster_id=" + this.id + ", center:{" + this.center.toString()+"}";
        for (Point point : members) {
            toString+="\n"+point.toString();
        }
        return toString+"\n";
    }
}
