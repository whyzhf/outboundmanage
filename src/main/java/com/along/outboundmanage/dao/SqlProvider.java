package com.along.outboundmanage.dao;


import com.along.outboundmanage.model.*;
import com.along.outboundmanage.utill.GeneralUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cglib.beans.BeanMap;


import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.along.outboundmanage.utill.BeanHump.camelToUnderline;
import static com.along.outboundmanage.utill.DataUtil.*;
import static com.along.outboundmanage.utill.GeneralUtils.toLowerCaseFirstOne;

/**
 * @Auther: why
 * @Data:2019/5/24
 * @Deacription:
 */
public class SqlProvider extends SQL {

   public  String updataUser(@Param("OutboundUser") OutboundUser OutboundUser){
       return  updataSql(OutboundUser);
   }

    public  String upPolice(@Param("OutboundPolice") OutboundPolice OutboundPolice){
        StringBuffer strBf=new StringBuffer();
        strBf.append("UPDATE outbound_police set  ");
        BeanMap.create(OutboundPolice).forEach((K,V)->{
            if (!"userId".equals(K+"")){
                strBf.append(camelToUnderline(K+"")+"=#{OutboundPolice."+K+"},");
            }
        });
        String sql=strBf.toString();
        sql=sql.substring(0,sql.length()-1);
        sql=sql+" where id="+OutboundPolice.getId();

        return sql;
    }

    public  String upEquipment(@Param("OutboundEquipment") OutboundEquipment outboundEquipment){
        return updataSql(outboundEquipment);
    }
    public  String upPrisoner(@Param("OutboundPrisoner") OutboundPrisoner outboundPrisoner){
        return updataSql2(outboundPrisoner);
    }
    public  String upCar(@Param("OutboundCar") OutboundCar outboundCar){
        return updataSql(outboundCar);
    }

    public  String updataTask(@Param("OutboundTask") OutboundTask outboundTask){
        return updataSql(outboundTask);
    }

    public  String updataRoute(@Param("OutboundRoute") OutboundRoute outboundRoute){
        return updataSql3(outboundRoute);
    }

    public  String insertSql(Object clazz){
       StringBuffer sqlBf=new StringBuffer();
       String beanName=clazz.getClass().getName();
       beanName=beanName.replace("com.along.outboundmanage.model.","");
       StringBuffer beanBf=new StringBuffer(beanName);
       String tableName=camelToUnderline(toLowerCaseFirstOne(beanName));
       sqlBf.append("Insert into ").append(tableName).append(" ");
       StringBuffer keySb=new StringBuffer();
       StringBuffer valueSb=new StringBuffer();
       BeanMap.create(clazz).forEach((K,V)->{
            if (!GeneralUtils.isNull(V)){
                keySb.append(camelToUnderline(K+"")).append(",");
                valueSb.append(" #{"+beanBf+"."+K+"}").append(",");
            }
        });
        String key=keySb.toString();
        key=key.substring(0,key.length()-1);
        String value=valueSb.toString();
        value=value.substring(0,value.length()-1);
        sqlBf.append(key).append(" values (").append(value).append(")");
        return sqlBf.toString();
    }

    public  String updataSql(Object clazz){
        StringBuffer sqlBf=new StringBuffer();
        String beanName=clazz.getClass().getName();
        beanName=beanName.replace("com.along.outboundmanage.model.","");
        String tableName=camelToUnderline(toLowerCaseFirstOne(beanName));
        StringBuffer beanBf=new StringBuffer(beanName);
        sqlBf.append("UPDATE ").append(tableName).append(" set ");
        BeanMap.create(clazz).forEach((K,V)->{
            if (!GeneralUtils.isNull(V)){
                sqlBf.append(camelToUnderline(K+"")+"=#{"+beanBf+"."+K+"},");
            }
        });
        String sql=sqlBf.toString();
        sql=sql.substring(0,sql.length()-1);
        sql=sql+" where id=#{"+beanName+".id}";
        return sql;
    }

	public  String updataSql3(Object clazz){
		StringBuffer sqlBf=new StringBuffer();
		String beanName=clazz.getClass().getName();
		beanName=beanName.replace("com.along.outboundmanage.model.","");
		String tableName=camelToUnderline(toLowerCaseFirstOne(beanName));
		StringBuffer beanBf=new StringBuffer(beanName);
		sqlBf.append("UPDATE ").append(tableName).append(" set ");
		BeanMap.create(clazz).forEach((K,V)->{
			if (!GeneralUtils.isNull(V)){
				if("originLngLat".equals(K)||"destinationLngLat".equals(K)){
					sqlBf.append(K  + "=#{" + beanBf + "." + K + "},");
				}else {
					sqlBf.append(camelToUnderline(K + "") + "=#{" + beanBf + "." + K + "},");
				}
			}
		});
		String sql=sqlBf.toString();
		sql=sql.substring(0,sql.length()-1);
		sql=sql+" where id=#{"+beanName+".id}";
		return sql;
	}

    public  String updataSql2(Object clazz){
        StringBuffer sqlBf=new StringBuffer();
        String beanName=clazz.getClass().getName();
        beanName=beanName.replace("com.along.outboundmanage.model.","");
        String tableName=camelToUnderline(toLowerCaseFirstOne(beanName));
        StringBuffer beanBf=new StringBuffer(beanName);
        sqlBf.append("UPDATE ").append(tableName).append(" set ");
        BeanMap.create(clazz).forEach((K,V)->{
            if (!GeneralUtils.isNull(V)|| (K+"").contentEquals("equipmentId")){
                sqlBf.append(camelToUnderline(K+"")+"=#{"+beanBf+"."+K+"},");
            }
        });
        String sql=sqlBf.toString();
        sql=sql.substring(0,sql.length()-1);
        sql=sql+" where id=#{"+beanName+".id}";
        return sql;
    }

    public String addPolicEquip(Map map){
        List<KandV> students = (List<KandV>) map.get("tpIds");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO outbound_task_police_rel (task_id, police_id) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'tpIds[{0}].id}, #'{'tpIds[{0}].id2})"
        );
        for (int i = 0; i < students.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < students.size() - 1)
                sb.append(",");
        }
        return sb.toString();

    }

    public String addTaskPolic(Map map){
        List<KandV> students = (List<KandV>) map.get("tpIds");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO outbound_task_police_rel (task_id, police_id) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'tpIds[{0}].id}, #'{'tpIds[{0}].id2})"
        );
        for (int i = 0; i < students.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < students.size() - 1)
                sb.append(",");
        }
        return sb.toString();

    }

    public String addTaskPrisoner(Map map){
        List<KandV> students = (List<KandV>) map.get("tpIds");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO outbound_task_prisoner_rel (task_id, prisoner_id) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'tpIds[{0}].id}, #'{'tpIds[{0}].id2})"
        );
        for (int i = 0; i < students.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < students.size() - 1)
                sb.append(",");
        }
        return sb.toString();

    }

    public String addTaskCar(Map map){
        List<KandV> students = (List<KandV>) map.get("tpIds");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO outbound_task_car_rel (task_id, car_id) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'tpIds[{0}].id}, #'{'tpIds[{0}].id2})"
        );
        for (int i = 0; i < students.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < students.size() - 1)
                sb.append(",");
        }
        return sb.toString();

    }

    public String addTaskHis(Map map){
        List<TaskHistory> dataList = (List<TaskHistory>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO outbound_task_history (task_id, user_id, firjson, secjson, area_id ) VALUES ");
        MessageFormat mf = new MessageFormat(
                "(#'{'list[{0}].taskId}, #'{'list[{0}].userId}, #'{'list[{0}].firjson}, #'{'list[{0}].secjson}, #'{'list[{0}].areaId} )"
        );
        for (int i = 0; i < dataList.size(); i++) {
            sb.append(mf.format(new Object[] {i}));
            if (i < dataList.size() - 1)
                sb.append(",");
        }
        return sb.toString();
    }

   /*作废*/
    public String getDate(@Param("area")String area){
            String data = getLastMonth("yyyy-MM");
        int year=Integer.valueOf(data.split("-")[0]);
        int mon=Integer.valueOf(data.split("-")[1]);
        int firData=Integer.valueOf(getFirstDayOfMonth(year,mon,"dd"));
        int lasData=Integer.valueOf(getLastDayOfMonth(year,mon,"dd"))+1;
            StringBuffer sb=new StringBuffer();
            for (int i=firData;i<lasData;i++){
                sb.append(" SELECT count(id) as coun ,convert('").append(data).append("-").append(i).append("',date) as data FROM outbound_prisoner WHERE uptime <= '")
                  .append(data).append("-").append(i) .append(" 23:59:59'").append(area).append(" and ifdel=0 union ");
            }
            String sql=sb.toString();
            sql=sql.substring(0,sql.length()-6);
            return sql;
    }

    public String getAddDate(@Param("area")String area){
        String data = getLastMonth("yyyy-MM");
        int year=Integer.valueOf(data.split("-")[0]);
        int mon=Integer.valueOf(data.split("-")[1]);
        //int firData=Integer.valueOf(getFirstDayOfMonth(year,mon,"dd"));
        int lasData=Integer.valueOf(getLastDayOfMonth(year,mon,"dd"));
        StringBuffer sb=new StringBuffer();
        sb.append("SELECT sum(coun) as coun , DATE_FORMAT(b.data,'%e') as data from (\n" +
                "   SELECT 0-count(id) as coun , data from (\n" +
                "       SELECT id  ,convert(outtime,date) as data \n" +
                "       FROM outbound_prisoner  WHERE  outtime <= '"+data+"-"+lasData+" 23:59:59' and outtime >= '"+data+"-02 00:00:00' " +area+
                "   ) t \n" +
                "   group by data\n" +
                "   union all\n" +
                "   SELECT count(id) as coun , data from (\n" +
                "        SELECT id  ,convert(intime,date) as data \n" +
                "       FROM outbound_prisoner  WHERE  intime <= '"+data+"-"+lasData+" 23:59:59' and intime >= '"+data+"-02 00:00:00'" +area+
                "   ) t \n" +
                "   group by data\n" +
                " ) b group by data");
        String sql=sb.toString();

        return sql;
    }

    public  String updataPoliceEquip(Map map){
        StringBuffer sb=new StringBuffer();
        StringBuffer sb2=new StringBuffer();
        StringBuffer ids=new StringBuffer();
        List<OutboundPoliceForSel> dataList= (List<OutboundPoliceForSel>) map.get("list");
        sb.append("UPDATE outbound_police SET equipment_id = (CASE ");
        for (int i = 0; i < dataList.size(); i++) {
            sb.append( "  WHEN id=").append(dataList.get(i).getId()).append(" THEN ").append(dataList.get(i).getEquipmentId());
            sb2.append( "  WHEN id=").append(dataList.get(i).getId()).append(" THEN ").append(dataList.get(i).getEquipmentId2());
            ids.append(dataList.get(i).getId()).append(",");
        }
        String id=ids.toString();
        id=id.substring(0,id.length()-1);
        sb.append(" END) ,equipment_id2 = (CASE ").append(sb2).append(" END) WHERE id IN (").append(id).append(")");
        return sb.toString();
    }

    public  String updataPrisonerEquip(Map map){
        StringBuffer sb=new StringBuffer();

        StringBuffer ids=new StringBuffer();
        List<OutboundPrisoner> dataList= (List<OutboundPrisoner>) map.get("list");
        sb.append("UPDATE outbound_prisoner SET equipment_id = (CASE ");
        for (int i = 0; i < dataList.size(); i++) {
            sb.append( "  WHEN id=").append(dataList.get(i).getId()).append(" THEN ").append(dataList.get(i).getEquipmentId());
            ids.append(dataList.get(i).getId()).append(",");
        }
        String id=ids.toString();
        id=id.substring(0,id.length()-1);
        sb.append(" END) WHERE id IN (").append(id).append(")");
        return sb.toString();
    }
}
