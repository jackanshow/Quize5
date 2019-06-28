package com.assign5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
 
public class KMeansRun {  
    private int kNum;                             //�صĸ���
    private int iterNum = 10;                     //��������
 
    private int iterMaxTimes = 100000;            //���ε���������д���
    private int iterRunTimes = 0;                 //���ε���ʵ�����д���
    private double disDiff = (double) 0.0001;         //���ε�����ֹ���������������������ĵľ����
 
    private List<double[]> original_data =null;    //���ڴ�ţ�ԭʼ���ݼ�  
    private static List<Point> pointList = null;  //���ڴ�ţ�ԭʼ���ݼ��������ĵ㼯
    private DistanceCompute disC = new DistanceCompute();
    private int len = 0;                          //���ڼ�¼ÿ�����ݵ��ά��
 
    public KMeansRun(int k, List<double[]> original_data) {
        this.kNum = k;
        this.original_data = original_data;
        this.len = original_data.get(0).length; 
        //���淶
        check();
        //��ʼ���㼯��
        init();
    }
 
    /**
     * ���淶
     */
    private void check() {
        if (kNum == 0){
            throw new IllegalArgumentException("k must be the number > 0");  
        }
        if (original_data == null){
            throw new IllegalArgumentException("program can't get real data");
        }
    } 
 
    /** 
     * ��ʼ�����ݼ���������ת��ΪPoint���͡�
     */
    private void init() {
        pointList = new ArrayList<Point>();
        for (int i = 0, j = original_data.size(); i < j; i++){
            pointList.add(new Point(i, original_data.get(i)));
        }
    }
 
    /** 
     * ���ѡȡ���ĵ㣬�����������ࡣ
     */  
    private Set<Cluster> chooseCenterCluster() {
        Set<Cluster> clusterSet = new HashSet<Cluster>();
        Random random = new Random();
        for (int id = 0; id < kNum; ) {
            Point point = pointList.get(random.nextInt(pointList.size()));
            // ���ڱ���Ƿ��Ѿ�ѡ��������ݡ�
            boolean flag =true;
            for (Cluster cluster : clusterSet) {
                if (cluster.getCenter().equals(point)) {
                    flag = false;
                }
            }
            // ������ѡȡ�ĵ�û�б�ѡ�й���������һ��cluster
            if (flag) {
                Cluster cluster =new Cluster(id, point);
                clusterSet.add(cluster);
                id++;
            }
        }
        return clusterSet;  
    }
 
    /**
     * Ϊÿ�������һ���࣡
     */
    public void cluster(Set<Cluster> clusterSet){
        // ����ÿ���㵽K�����ĵľ��룬����Ϊÿ����������
        for (Point point : pointList) {
            double min_dis = Integer.MAX_VALUE;
            for (Cluster cluster : clusterSet) {
                double tmp_dis = (double) Math.min(disC.getEuclideanDis(point, cluster.getCenter()), min_dis);
                if (tmp_dis != min_dis) {
                    min_dis = tmp_dis;
                    point.setClusterId(cluster.getId());
                    point.setDist(min_dis);
                }
            }
        }
        // �����ԭ�����е����г�Ա�������еĵ㣬�ֱ����ÿ�����
        for (Cluster cluster : clusterSet) {
            cluster.getMembers().clear();
            for (Point point : pointList) {
                if (point.getClusterid()==cluster.getId()) {
                    cluster.addPoint(point);
                }
            }
        }
    }
 
    /**
     * ����ÿ���������λ�ã�
     */
    public boolean calculateCenter(Set<Cluster> clusterSet) {
        boolean ifNeedIter = false; 
        for (Cluster cluster : clusterSet) {
            List<Point> point_list = cluster.getMembers();
            double[] sumAll =new double[len];
            // ���е㣬��Ӧ����ά�Ƚ������
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < point_list.size(); j++) {
                    sumAll[i] += point_list.get(j).getlocalArray()[i];
                }
            }
            // ����ƽ��ֵ
            for (int i = 0; i < sumAll.length; i++) {
                sumAll[i] = (double) sumAll[i]/point_list.size();
            }
            // ���������¡������ĵľ��룬�������һ���������ƶ��ľ������dis_diff�����������
            if(disC.getEuclideanDis(cluster.getCenter(), new Point(sumAll)) > disDiff){
                ifNeedIter = true;
            }
            // �����µ�������λ��
            cluster.setCenter(new Point(sumAll));
        }
        return ifNeedIter;
    }
 
    /**
     * ���� k-means
     */
    public Set<Cluster> run() {
        Set<Cluster> clusterSet= chooseCenterCluster();
        boolean ifNeedIter = true; 
        while (ifNeedIter) {
            cluster(clusterSet);
            ifNeedIter = calculateCenter(clusterSet);
            iterRunTimes ++ ;
        }
        return clusterSet;
    }
 
    /**
     * ����ʵ�����д���
     */
    public int getIterTimes() {
        return iterRunTimes;
    }
}
