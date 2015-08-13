package com.baozi.utils;

/**
 * Created by User on 2015/7/29 029.
 */
public class Constants {
    private static String IP = "http://192.168.1.250:8002/Services/";
    /**
     * �û���¼/ע��
     */
    public static String USER_URL = IP + "NPCUserService.asmx/GeneralService";
    /**
     * NPC�̼�
     */
    public static String NPC_BUSINESS = IP + "NPCManagerService.asmx/GeneralService";
    /**
     * NPC����
     */
    public static String NPC_PERSON = IP + "NPCPersonalManager.asmx/GeneralService";
    /**
     * ��ƷΨһ����
     */
    public static String NPC_PRODUCT_URL = IP + "NPCProductClassApply.asmx/GeneralService";
    /**
     * ��ȡ������������ݳɹ���״̬��ݣ�
     */
    public static final int REQUEST_OK = 0x00;
    /**
     * ��ȡ�������������ʧ�ܣ�״̬��ݣ�
     */
    public static final int REQUEST_SORRY = 0x99;
}
