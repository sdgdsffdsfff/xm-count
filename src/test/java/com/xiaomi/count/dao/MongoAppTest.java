package com.xiaomi.count.dao;

import com.xiaomi.count.util.SqlFormatUtil;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MongoAppTest {



    private static final Logger logger = Logger.getLogger(MongoAppTest.class);

    @Test
    public void testMain() throws Exception {
//        String json = "[{aa:'fuck122',bb:'12231'},{aa:'fuck231',bb:'657665765'}]";
//        BasicDBList testObj = (BasicDBList) JSON.parse(json); //字符串转对象
//
//        List<DBObject> basicDBObjectList = new ArrayList<DBObject>();
//        for (Object basicDBObject : testObj) {
//            basicDBObjectList.add((DBObject) basicDBObject);
//        }
//
//        DBCollection dbCount = mongoTemplate.getCollection("test"); //得到集合
////        mongoTemplate.remove(new Query(), "test");
//
//        String jsonSql = "{distinct:'test', key:'aa'}";
//        CommandResult commandResult = mongoTemplate.executeCommand(jsonSql);
//
//        BasicDBList list = (BasicDBList) commandResult.get("values");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        System.out.println();
//
//        BasicDBObject basicDBObject = mongoTemplate.findOne(new Query(), BasicDBObject.class, "test");

//        boolean exist = mongoTemplate.collectionExists("test");//判断集合是否存在
//        System.out.println(exist);
//        //        mongoTemplate.dropCollection("test");  //删除集合
//
//        dbCount.insert(basicDBObjectList); //结合中插入数据
//
//        Query query = new Query();
//        Criteria criteria = new Criteria();
////        query.addCriteria(Criteria.where("测试44").is("fuck7").and("测试24").is("12231"));//查询条件
//        query.addCriteria(criteria.orOperator(Criteria.where("aa").regex("武汉市"), Criteria.where("aa").regex("武汉")));//测试模糊查询
//        query.addCriteria(Criteria.where("bb").is("1"));
////        query.addCriteria(Criteria.where("测试44").is("fuck7").and("测试24").is("12231"));//查询条件
////        query.limit(3);
////        query.skip(3);
////        Query query = TextQuery.queryText (new TextCriteria().matchingAny("fuck7"));
//
//        List<BasicDBObject> list = mongoTemplate.find(query, BasicDBObject.class, "test");//查询
//
//        logger.error(mongoTemplate.find(query, BasicDBObject.class, "test"));
//        for (BasicDBObject object : list) {
//            System.out.print(object.get("aa") + "==");
//        }
//
////        query.addCriteria(Criteria.where("测试").is("fuck7")); //删除
//        mongoTemplate.remove(query,"test");
//
//        GroupBy groupBy = GroupBy.key("aa").initialDocument("{count:0}").reduceFunction("function(key, values){values.count+=1;}");
//        GroupByResults groupByResults = mongoTemplate.group("test", groupBy, BasicDBObject.class);//查询
//        long count = mongoTemplate.count(query, "test");//查询总数
//        System.out.println(count);
//
//
//        IndexOperations io = mongoTemplate.indexOps("test");//索引
//        io.dropIndex("aa_1");
//        Index index =new Index();
//        index.on("aa", Order.ASCENDING); //为name属性加上 索引
//        index.unique();//唯一索引
//        io.ensureIndex(index);


    }

    @Test
    public void testRegex() {
//        Pattern p = Pattern.compile("测试");
//        Matcher m = p.matcher("2312df测2试fuck7321fdd");
//        System.out.println(m.find());
//        Map<String, String> sqlFieldAsMap = new HashMap<String, String>();
//        String sql = "SELECT COUNT(T1.localid) AS '总量' FROM (SELECT DISTINCT localid FROM pcboot WHERE time < 1437321599 ) T1 LIMIT 0, 100000";
//
//        String originalSql = sql.replaceAll("as", "AS").replaceAll("As", "AS").replaceAll("from", "FROM");
//        System.out.println("originalSql:  "+originalSql);
//
//        try {
//            String fields = originalSql.substring(6, originalSql.indexOf("FROM"));
//            String[] fieldsArray = fields.split(",");
//            for (String field : fieldsArray) {
//                if (field.contains("AS")) {
//                    sqlFieldAsMap.put(field.split("AS")[0].trim(), field.split("AS")[1].trim());
//                }
//            }
//        } catch (Exception e) {
//
//        }
//
//        System.out.println(sqlFieldAsMap.size());

//        MySqlStatementParser mySqlStatementParser = new MySqlStatementParser(sql);
//        SQLStatement sqlStatement = mySqlStatementParser.parseStatement();
//        SQLSelectStatement sqlSelectStatement = (SQLSelectStatement)sqlStatement;


        /**
         *  TIMESTAMP(y, m, d, h, min, s)
         *  y: 年
         *  m: 月
         *  d: 日
         * h: 小时
         * min: 分钟
         * s: 秒
         */

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        String sql = "select d from dual where a<$timestamp(y) and b>$timestamp(y,m) and c<$timestamp(y,m,d) and d<$timestamp(y,m,d,h) or e<$timestamp(y,m,d,h,min) and f>$timestamp(y,m,d,h,min,s) $alias(a,b,c)";

        System.out.println(SqlFormatUtil.formatTimestamp(sql));
        System.out.println(SqlFormatUtil.formatAlias(sql)[0]);
//        //年 时间戳
//        String regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*\\)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(sql);
//        boolean has = matcher.find();
//        System.out.println("$timestamp(y)  " + has);
//        if (has) {
//            calendar.setTime(date);
//            calendar.set(Calendar.MONTH, 0);
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");
//
//        //年月 时间戳
//        regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*\\)";
//        pattern=Pattern.compile(regex);
//        matcher = pattern.matcher(sql);
//        has = matcher.find();
//        System.out.println("$timestamp(y,m)  " + has);
//        if(has) {
//            calendar.setTime(date);
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");
//
//        //年月日 时间戳
//        regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*\\)";
//        pattern=Pattern.compile(regex);
//        matcher = pattern.matcher(sql);
//        has = matcher.find();
//        System.out.println("$timestamp(y,m,d)  " + has);
//        if(has) {
//            calendar.setTime(date);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");
//
//        //年月日 小时 时间戳
//        regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*\\)";
//        pattern=Pattern.compile(regex);
//        matcher = pattern.matcher(sql);
//        has = matcher.find();
//        System.out.println("$timestamp(y,m,d,h)  " + has);
//        if(has) {
//            calendar.setTime(date);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");
//
//        //年月日 小时 分钟 时间戳
//        regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*,[\\s]*min[\\s]*\\)";
//        pattern=Pattern.compile(regex);
//        matcher = pattern.matcher(sql);
//        has = matcher.find();
//        System.out.println("$timestamp(y,m,d,h,min)  " + has);
//        if(has) {
//            calendar.setTime(date);
//            calendar.set(Calendar.SECOND, 0);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");
//
//        //年月日 小时 分钟 秒 时间戳
//        regex="\\$timestamp[\\s]*\\([\\s]*[y|Y][\\s]*,[\\s]*[m|M][\\s]*,[\\s]*[d|D][\\s]*,[\\s]*[h|H][\\s]*,[\\s]*min[\\s]*,[\\s]*[s|S][\\s]*\\)";
//        pattern=Pattern.compile(regex);
//        matcher = pattern.matcher(sql);
//        has = matcher.find();
//        System.out.println("$timestamp(y,m,d,h,min,s)  " + has);
//        if(has) {
//            calendar.setTime(date);
//            System.out.println("ymdhms:  " + simpleDateFormat.format(calendar.getTime()) + " mis:  " + calendar.getTimeInMillis());
//            sql = sql.replaceAll(regex, calendar.getTimeInMillis()/1000 + "");
//        }
//        System.out.println(sql);
//        System.out.println("------------------------------------------------");


    }

    @Test
    public void formatTest() {

        String sql = "select a,b,c from aacc where a<fsd LimiT 12312,132322 $alias( aa,bb,cc) ;";
//
//        String regex = "\\$alias[\\s]*\\(([\\s\\S]+)\\)[\\s]*[;]?";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(sql);
//        boolean has = matcher.find();
//
//        if (has) {
//
//            sql = sql.replaceAll(regex, "");
//
//            if (matcher.groupCount() > 0) {
//                System.out.println(matcher.group(1));
//            }
//
//        }

//        String regex = "limit[\\s]+[\\d]+[\\s]*,[\\s]*([\\d]+)";
//        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(sql);
//        boolean has = matcher.find();
//        System.out.println(has);
//        if (has) {
//
//            if (matcher.groupCount() > 0) {
//                System.out.println(matcher.group(0));
//                System.out.println(matcher.group(1));
//            }
//
//        }

        System.out.println(SqlFormatUtil.formatLimit(sql));
        System.out.println(SqlFormatUtil.formatAlias(sql)[0]);
        System.out.println(SqlFormatUtil.formatAlias(sql)[1]);

    }

}