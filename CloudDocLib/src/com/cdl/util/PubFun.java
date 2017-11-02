package com.cdl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * ���ú�������̬����
 * 
 */
public class PubFun {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static final String SESSION_NAME = "session_object";
	
	public static final String PORTAL_SESSION_NAME = "portal_session_object";
	
	public static final String PORTAL_SESSION_LATEST_URI = "lates_uri";
	
	public static final String YXLIB_PORTAL_LOGIN_COOKIE = "yxlib_portal_login_cookie";
	
	public static final String VIEW_FILE_LIST = "view_file_list";

	public static final String VIEW_ALBUM_LIST = "view_album_list";
	
	public static final String SESSION_AUTH_NAME = "session_auth";

	public static String BASEPATH; // Ӧ��ϵͳ�ĸ�Ŀ¼

	/**
	 * ��ָ���ַ�����MD5�����㷨�����ؼ��ܺ��ַ���
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Сдת��д
	 */
	public static String getNumberToRMB(String m) {
		String num = "��Ҽ��������½��ƾ�";
		String dw = "Բʰ��Ǫ����";
		String mm[] = null;
		mm = m.split(".");
		String money = mm[0];

		String result = num.charAt(Integer.parseInt("" + mm[1].charAt(0)))
				+ "��" + num.charAt(Integer.parseInt("" + mm[1].charAt(1)))
				+ "��";

		for (int i = 0; i < money.length(); i++) {
			String str = "";
			int n = Integer.parseInt(money.substring(money.length() - i - 1,
					money.length() - i));
			str = str + num.charAt(n);
			if (i == 0) {
				str = str + dw.charAt(i);
			} else if ((i + 4) % 8 == 0) {
				str = str + dw.charAt(4);
			} else if (i % 8 == 0) {
				str = str + dw.charAt(5);
			} else {
				str = str + dw.charAt(i % 4);
			}
			result = str + result;
		}
		result = result.replaceAll("��([^����Բ�Ƿ�])", "��");
		result = result.replaceAll("����+��", "����");
		result = result.replaceAll("��+", "��");
		result = result.replaceAll("��([����Բ])", "");
		result = result.replaceAll("Ҽʰ", "ʰ");

		return result;
	}

	/**
	 * ��ָ���ַ���תΪUTF-8����
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ȡ��ǰ���ڣ����ع̶���ʽ�ַ���
	 */
	public static String getNow() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * ȡ��ǰ���ڣ�����Date��ʽ
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * ȡHashmap��valueֵ��û�ж�Ӧ�ķ��ؿ��ַ�
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public String getValue(HashMap map, String key) {
		String value = Constants.SPACE;

		if (map != null) {
			value = (String) map.get(key);
			if (value == null) {
				value = Constants.SPACE;
			}
		}
		return value;

	}

	/**
	 * ����ָ�����ţ�����ַ��� �������Ϊ�գ����ؿն��󣬽����쳣�췵�ؿն���
	 * 
	 * @param orgString
	 *            Դ�ַ���
	 * @param ָ������
	 * @return ��ֺ������
	 */
	public static String[] getStringArray(String orgString, String flag) {
		String[] returnValue = null;
		try {
			if (orgString != null && flag != null && !"".equals(orgString)) {
				returnValue = orgString.split(flag);
			}
		} catch (Exception e) {
		}
		return returnValue;
	}
	
	/**
	 * ����ָ�����ţ�����ַ��� �������Ϊ�գ����ؿն��󣬽����쳣�췵�ؿն���
	 * @author ���꺣 Dec 21, 2008
	 * @param orgString
	 *            Դ�ַ���
	 * @param ָ������
	 * @return ��ֺ������
	 */
	public static Long[] getLongArray(String orgString, String flag) {
		Long[] returnValue = null;
		try {
			if (orgString != null && flag != null && !"".equals(orgString)) {
				String values[] = orgString.split(flag);
				returnValue = new Long[values.length];
				for(int i = 0; i < values.length; i++){
					returnValue[i] = Long.valueOf(values[i]);
				}
			}
		} catch (Exception e) {
		}
		return returnValue;
	}

	/**
	 * ������ˮ���
	 * 
	 * @author ³��
	 * @param prefix
	 *            ��ˮ��ǰ׺
	 * @param int
	 *            length ��ˮ�ų��� ������ǰ׺
	 * @return String ���ɺ����ˮ��
	 */
	public static String generateSequence(String prefix, int length) {
		if (prefix == null || length < 0) {
			return Constants.SPACE;
		}
		int preLen = prefix.length();

		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		Random r = new Random();
		if (length > 4) {
			Calendar c = Calendar.getInstance();
			sb.append(c.get(Calendar.MONTH));
			sb.append(c.get(Calendar.DAY_OF_MONTH));
		}
		while (sb.length() < length + preLen) {
			sb.append(r.nextInt(10000));
		}
		// sb.delete(length, sb.length() - length);

		return sb.toString().substring(0, length + preLen);
	}

	/**
	 * ����8λ���к�,����ǰ׺�ܹ�10λ,���㲹0
	 * 
	 * @author ������
	 * @param prefix
	 * @param seqNumber
	 *            ���к�
	 * @return
	 */
	public static String generateCode(String prefix, String seqNumber) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		int len = seqNumber.length();
		int addZeorLen = 8 - len;
		if (addZeorLen < 0) {
			addZeorLen = 0;
		}

		for (int i = 0; i < addZeorLen; i++) {
			sb.append("0");
		}
		sb.append(seqNumber);
		return sb.toString();
	}
	/**
     * ��������ˮ��
     * 
     * @param prefix
     *            ǰ׺
     * @param seqNumber
     *            ��ǰ��ˮ��
     * @param length
     *            ����ˮ�ų���
     * @return
     * @author ���
     */
    public static String generateSeqCodeByDay(String prefix, String seqNumber,
            int length)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        int len = seqNumber.length();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(date);
        sb.append(str);
        int addZeorLen = length - len;
        if (addZeorLen < 0)
        {
            seqNumber = seqNumber.substring(seqNumber.length()-length);
            addZeorLen = 0;
        }
        for (int i = 0; i < addZeorLen; i++)
        {
            sb.append("0");
        }
        sb.append(seqNumber);
        return sb.toString();
    }

	/**
	 * ��ָ����ʽת���ַ���Ϊʱ���ʽ�����ת���쳣���򷵻ؿն���
	 * 
	 * @param sDate
	 *            ʱ���ַ�����ʽ
	 * @param format
	 *            ��ʽformat
	 * @return
	 */
	public static Date stringToDate(String sDate, String format) {
		Date date = null;
		if (sDate != null && format != null && !"".equals(sDate)) {
			SimpleDateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(sDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;

	}

	/**
	 * �������ƣ�dateToString ����ժҪ����һ������ת�����ַ������磺2008-08-18
	 * 
	 * @param Date
	 *            date ��Ҫת��������
	 * @return String ����һ���ַ�������
	 */
	public static String dateToString(Date date) {
		if (date != null) {
			return dateFormat.format(date);
		} else {
			return "";
		}
	}

	/**
	 * ����:�Ѷ���������ֵΪ���Ӵ���תΪnull
	 * 
	 * @param obj
	 *            Ҫת���Ķ���
	 * @return
	 */
	public static Object ConvertEmptyValueToNull(Object obj) {
		Class c = obj.getClass();
		try {
			Field[] fs = c.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].getType().toString().equals(String.class.toString())) {
					String name = fs[i].getName();// �õ���ǰ���е��ֶ�����,��Ϊֻ�й��еĲ��ܷ���
					String getname = "get"
							+ String.valueOf(name.charAt(0)).toUpperCase()
							+ name.substring(1);// ��ϳ�get����
					String setname = "set"
							+ String.valueOf(name.charAt(0)).toUpperCase()
							+ name.substring(1);// ��ϳ�set����
					Method getMethod = c.getDeclaredMethod(getname);// �õ����������ж���ķ���
					String str = (String) getMethod.invoke(obj);// ȡ�������еķ���ֵ��Ҳ����STRUTS��ǰ̨����η���BEAN�е�ֵ
					if (str != null && str.length() == 0) {
						Method setMethod = c.getDeclaredMethod(setname,
								String.class);// �õ�set������ʵ��
						setMethod.invoke(obj, new Object[] { null });// ����set������ֵΪnull
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return obj;
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * @param scale
	 *            С���������λ
	 * @return ���������Ľ��
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static List convertJSONList(Object jsonArray, Class c) {
		Iterator iterator = JSONArray.fromObject(jsonArray).iterator();
		List list = new ArrayList();
		while (iterator.hasNext()) {
			list.add(JSONObject.toBean((JSONObject) iterator.next(), c));
		}
		return list;
	}

	public static Object convertJSONObj(Object jsonObject, Class c) {
		JSONObject obj = JSONObject.fromObject(jsonObject);
		return JSONObject.toBean((JSONObject) obj, c);
	}

	public static Map convertJSONMap(Object jsonObject) {
		JSONObject obj = JSONObject.fromObject(jsonObject);
		Map map = (HashMap) JSONObject.toBean(obj, Map.class);
		return map;
	}

	/**
	 * ���㲹������
	 * 
	 * @param passPoint
	 *            �趨�Ĳ�������
	 * @param paperPoint
	 *            �Ծ��ܷ�
	 * @return
	 */
	public static BigDecimal getPassPoint(BigDecimal passPoint,
			BigDecimal paperPoint) {
		BigDecimal passReuslt = BigDecimal.ZERO;// �趨Ĭ�Ϸ���Ϊ��
		if (passPoint != null) {
			passReuslt = passPoint;// ����趨�˲������� �򷵻�ԭֵ
		} else if (paperPoint != null) {
			// DecimalFormat df = new DecimalFormat("0.00");
			// ���û���趨�����������Ծ������0.6���� ��Ϊ��ʱ ����0
			passReuslt = paperPoint.multiply(BigDecimal.valueOf(0.60));
		}
		return passReuslt;
	}

	public static String nullToString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * �����ļ��� ƥ������
	 * 
	 * @param map(��ѯ�����ֵ��)
	 * @param org
	 * @return
	 */
	public static String marchFileInfo(HashMap map, String org) {
		String result = "unMarchType";
		if (org != null && map != null) {
			int index = org.indexOf(Constants.DOT);
			if (index != -1) {
				org = org.substring(index + 1, org.length());
				String temp = (String) map.get(org.toLowerCase());
				if (temp != null) {
					result = temp;
				}
			}
		}
		return result;
	}

	/**
	 * ����HIBERNATE�Ĳ�ѯ����,��������ѯ,������ͼ ��:select a.userName,b.sid,c.dd from InfoUser
	 * a,InfoDept b,sh c ���Ϊ:[userName,sid,dd]
	 * 
	 * @param sql
	 * @return
	 */
	public static String[] getMetaData(String sql) {
		String[] source = sql.split("(?i)from")[0].split("select")[1]
				.split(",");
		String[] dest = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			String[] tmp = source[i].trim().split("\\.")[source[i].trim()
					.split("\\.").length - 1].split(" ");
			dest[i] = tmp[tmp.length - 1];
		}
		return dest;
	}

	public static String encodeStrToUtf8(String orgStr) {
		String result = null;
		try {
			result = new String(orgStr.getBytes("GB2312"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orgStr;
	}

	public static String parseList(List<String> list, String reg) {
		String result = null;
		if (list != null) {
			String temp;
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < list.size(); i++) {
				temp=list.get(i);
				if (sb.length()>0){
					sb.append(reg);
					sb.append(temp);
				}
				else {
					sb.append(temp);
				}
			}
			result=sb.toString();
		}
		return result;
	}
	
	/**
     * �������ȡ���
     * 
     * @param seqStr
     * @return
     */
    public static String getCodeInfoStr(String headStr, int totalLen,
            String existCode)
    {
        String reItemCode = ""; // ���ص���Ŀ���
        String tempItemStr = "";
        int initNum = 1;
        System.out.println("totalLen : " + totalLen);
        // ��Ų�����
        if (null == existCode || "".equals(existCode))
        {
            for (int j = 0; j < totalLen - 1; j++)
            {
                tempItemStr = tempItemStr + "0";
            }
            reItemCode = headStr + tempItemStr + initNum;
        }
        // ��Ŵ���
        else
        {
            String tempStr = "";
            Integer tempInt;
            int headStrLen;
            headStrLen = headStr.length();
            System.out.println("headStrLen : " + headStrLen);
            System.out.println("existCode.length() : " + existCode.length());
            tempStr = existCode.substring(headStrLen, existCode.length());
            System.out.println("tempStr : " + tempStr);
            tempInt = Integer.valueOf(tempStr);
            ++tempInt;
            int tempIntLen = (tempInt + "").length();
            int addLen = totalLen - tempIntLen;
            // �����ǰ���"0"������ܳ���
            if (addLen > 0)
            {
                for (int j = 0; j < addLen; j++)
                {
                    tempItemStr = tempItemStr + "0";
                }
                reItemCode = headStr + tempItemStr + tempInt;
            } else
            {
                reItemCode = headStr + tempInt;
            }
        }
        System.out.println("reItemCode : " + reItemCode);

        return reItemCode;
    }
    
    
    /**
     * У��ֵ�Ƿ�Ϊ����
     * 
     * @param string
     * @return
     */
    public static String checkValueIsNum(String string, String msg)
    {
        String message = "";
        if (null != string && !string.equals("") && (!string.matches("[0-9]*")))
        {
            message = "\"" + msg + "\"ֻ����������";
        } else
        {
            System.out.println("string.matches: " + string.matches("[0-9]*"));
        }
        return message;
    }
    
    
    /**
     * У��ֵ�Ƿ�Ϊ����
     * 
     * @param string
     * @return
     */
    public static String checkValueIsDate(String string, String msg)
    {
        String message = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (null != string && !"".equals(string))
        {
            try
            {
                Date date = format.parse(string);
                // ���У�飺�������ݱ���Ϊ����,�ָ���'-'�������
                String[] checkStrings = string.split("-");
                String subCheckString = null; // ���ڽ�ȡ�ַ���
                String subCheckMsg = "";
                if (checkStrings.length == 3)
                {
                    for (int i = 0; i < checkStrings.length; i++)
                    {
                        subCheckString = checkStrings[i];
                        if (null != checkStrings && !"".equals(checkStrings))
                        {
                            // �������У���Ƿ�Ϊ����
                            subCheckMsg = PubFun.checkValueIsNum(
                                    subCheckString, "");
                            if ("".equals(subCheckMsg))
                            {
                                // �������ַ���У��ͨ��
                            } else
                            {
                                message = "\"" + string
                                        + "\"���������ڸ�ʽ\"yyyy-MM-dd\",����������";
                            }
                        }
                        if (i == 2)
                        {
                            // ����У��ͨ�������ؿ��ַ���
                        }
                    }
                } else
                {
                    message = "\"" + string + "\"���������ڸ�ʽ\"yyyy-MM-dd\",����������";
                }

                System.out.println("checkValueIsDate-date : " + date);
            } catch (ParseException e)
            {
                message = "\"" + string + "\"���������ڸ�ʽ\"yyyy-MM-dd\",����������";
                e.printStackTrace();
            }
        }
        return message;
    }
    /**
     * ��Сд����ת���ɴ�д����
     * @param n
     * @return
     */
    public static String changeNumberToBigNum(Long n){
    	
    	String str[] =new String[]{"��","һ","��","��","��","��","��","��","��","��"};
    	
    	if(n!=null)
    	{
    		String numStr = n.toString();
    		char[] numArr = numStr.toCharArray();
    		StringBuffer sourceStr = new StringBuffer();
    		for(int i=0;i<numArr.length;i++)
    		{
    			char index = numArr[i];
    			sourceStr.append(str[index-48]);
    		}
    		return sourceStr.toString();
    	}
    	else
    	{
    		return "";
    	}
    };
    /**
     * �ֽ�����ת������
     * @param buf
     * @return
     */
    public static final InputStream byte2Input(byte[] buf)
    {
        return new ByteArrayInputStream(buf);
    }
    /**
     * ������ת�ֽ�����
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream)throws IOException 
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        
        try
        {
            while ((rc = inStream.read(buff, 0, 1024)) > 0) 
            {
                swapStream.write(buff, 0, rc);
            }
            
            byte[] in2b = swapStream.toByteArray();
            
            return in2b;
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            swapStream.close();
        }
        
    }

    public static String unescape(String s)
    {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for(int i = 0, more = -1; i < l; i++)
        {
            /* Get next byte b from URL segment s */
            switch(ch = s.charAt(i))
            {
            case '%':
                ch = s.charAt(++i);
                int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                ch = s.charAt(++i);
                int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                b = (hb << 4) | lb;
                break;
            case '+':
                b = ' ';
                break;
            default:
                b = ch;
            }
            /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if((b & 0xc0) == 0x80)
            { // 10xxxxxx (continuation byte)
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
                if(--more == 0)
                    sbuf.append((char) sumb); // Add char to sbuf
            }
            else if((b & 0x80) == 0x00)
            { // 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b); // Store in sbuf
            }
            else if((b & 0xe0) == 0xc0)
            { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte
            }
            else if((b & 0xf0) == 0xe0)
            { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes
            }
            else if((b & 0xf8) == 0xf0)
            { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes
            }
            else if((b & 0xfc) == 0xf8)
            { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes
            }
            else
            /* if ((b & 0xfe) == 0xfc) */{ // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes
            }
            /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }
}
