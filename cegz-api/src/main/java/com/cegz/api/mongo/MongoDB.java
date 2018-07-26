package com.cegz.api.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Component(value="MongoDB")
public class MongoDB {

	/**
	 * 获得第一条数据
	 * @param database 数据库
	 * @param document 表
	 * @param queryMap 条件
	 * @return
	 */
	public Document getFirstData(String database, String document, Map<String, Object> queryMap) {
		MongoClient client = this.openReadClient();
		MongoDatabase db =client.getDatabase(database);
		// 获取所有查询记录
		Document retDocument = db.getCollection(document).find(new Document(queryMap)).first();
		return retDocument;
	}
	
	
	/**
	 * 获取最新上传时间的数据
	 * @param database 数据库
	 * @param document 表
	 * @param queryMap 条件
	 * @return
	 */
	public Document getCurrentData(String database, String document, String timeComlum , Map<String, Object> queryMap) {
		MongoClient client = this.openReadClient();
		MongoDatabase db =client.getDatabase(database);
		// 获取所有查询记录
		FindIterable<Document> retDocument = db.getCollection(document).find(new Document(queryMap)).sort(new BasicDBObject(timeComlum,-1));
		return retDocument.first();
	}
	
	
	/**
	 * 通过ID集合查询数据
	 * @param dbName
	 * @param collection
	 * @param idList
	 * @return
	 */
	public List<Document> getDataById(String dbName, String collection,BasicDBList idList){ 
				
			
			Document mainconditionDoc = new Document();  //主条件
			
			MongoClient client = this.openReadClient();
			MongoDatabase db = client.getDatabase(dbName);
			
			MongoCollection<Document> col = db.getCollection(collection);
			
			Document NinConditionDoc=new Document();
			if(idList.size()!=0){
				NinConditionDoc.append("$in", idList);
				mainconditionDoc.append("_id", NinConditionDoc);
			}else{
				return null;
			}
			FindIterable<Document> findRefResult;
			findRefResult = col.find(mainconditionDoc);
			List<Document> resultList = new ArrayList<>();
			for (Document item : findRefResult) {
				resultList.add(item);
				
			}
			return resultList;
			//client.close();
			//client = null;//一定要写这句话，不然系统不会回收，只是关闭了，连接存在。
			
		}	
	/**
	 * 包含时间的数据判断是否存在
	 * @param collection
	 * @param document
	 * @param queryMap
	 * @return
	 */
	public boolean isDataExistBetween(String collection, String document, Map<String, String> queryMap) {

		MongoClient client = this.openReadClient();
		MongoDatabase db =client.getDatabase(collection);
		
		if(!queryMap.containsKey("deviceColumn") || 
				!queryMap.containsKey("fromTime") || 
				!queryMap.containsKey("toTime") || 
				!queryMap.containsKey("deviceId") ||
				!queryMap.containsKey("timeColumn")){
			return false;
		}
		//条件
		Document mainconditionDoc = new Document();
		Document timeConditionDoc = new Document();
		timeConditionDoc.append("$gte", queryMap.get("fromTime"));
		timeConditionDoc.append("$lt", queryMap.get("toTime"));
		mainconditionDoc.append(queryMap.get("deviceColumn"), queryMap.get("deviceId"));
		mainconditionDoc.append(queryMap.get("timeColumn"), timeConditionDoc);

		// 获取所有查询记录
		Document dcu = db.getCollection(document).find(mainconditionDoc).first();
		
		boolean isExist = dcu != null ? true : false;
		return isExist;
		
	}
	
	/**
	 * 多条数据插入mongo数据库
	 * @param collection	数据库
	 * @param document		数据表
	 * @param documentList	插入数据库的数据
	 */
	public boolean insertListDocuments(String database, String document, 
			List<Document> documentList){
		try {
			MongoClient client = this.openWriteClient();
			MongoDatabase db = client.getDatabase(database);
			db.getCollection(document).insertMany(documentList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * mongo数据插入
	 * @param collection	数据库
	 * @param documents		数据表
	 * @param dataMap		数据HashMap
	 * @return
	 */
	public boolean dataInsert(String database, String document, Map<String, Object> dataMap) {
		try {
			MongoClient client = this.openWriteClient();
			MongoDatabase db = client.getDatabase(database);
			Document documentParams = new Document(dataMap);
			db.getCollection(document).insertOne(documentParams);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取是否存在某列大于某个值的数据
	 * @author lijiaxin
	 * @createDate 2018年4月23日下午4:37:47
	 * @param collection
	 * @param document
	 * @param queryMap
	 * @param columName
	 * @param value
	 * @return
	 * @modifiedAuthor 
	 * @modifiedDate 
	 * @modifiedContent 
	 * @check
	 */
	public boolean isExistBiggerData(String collection, String document,Map<String, Object> queryMap, String columName, int value) {
		MongoClient client = this.openReadClient();
		MongoDatabase db =client.getDatabase(collection);
		Document mainDoc = new Document(queryMap);
		mainDoc.append(columName, new Document("$gt", value));
		Document retDocument = db.getCollection(document).find(new Document(mainDoc)).first();
		return retDocument != null ? true : false;
	}
	/**
	 * 根据多条件查询数据
	 * @param dbName			数据库名
	 * @param collection		数据库表
	 * @param conditionParams 	多条件map
	 * @return
	 */
	public List<Document> getByMutilCondition(String database, String document, Map<String, Object> conditionParams) {
		
		MongoClient client = this.openReadClient();
		MongoDatabase db  = client.getDatabase(database);
		
		List<Document> resultList = new ArrayList<>();
		try {
			MongoCollection<Document> col = db.getCollection(document);
			BasicDBList condList = new BasicDBList();
			for (String key : conditionParams.keySet()) {
				BasicDBObject nameCond = new BasicDBObject();
				nameCond.put(key, conditionParams.get(key));
				condList.add(nameCond);
			}
			BasicDBObject searchCond = new BasicDBObject();
			searchCond.put("$and", condList);
			FindIterable<Document> findRefResult = col.find(searchCond);

			for (Document doc : findRefResult) {
				resultList.add(doc);
			}
			return resultList;
		} catch (Exception e) {
			return resultList;
		}
	}
	/**
	 * 多条件值修改
	 * @param database		数据库名
	 * @param document		document
	 * @param conditionMap	条件map
	 * @param params		每一个要修改的字段与值
	 * @return
	 */
	public boolean updateDataByMutilCondition(String database, String document, 
		Map<String, Object> condition, Map<String, Object> dest) {
		
		MongoClient writeClient = this.openWriteClient();
		MongoDatabase db =  writeClient.getDatabase(database);
		try {
			MongoCollection<Document> col = db.getCollection(document);
			Document conDocument = new Document(condition);
			Document destDocument = new Document(dest); 
			
			System.out.println(col.replaceOne(conDocument, destDocument));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * mongo删除数据
	 * @param database		数据库
	 * @param collection	数据表
	 * @param _idLists		mongo表id列表
	 * @return
	 */
	public boolean deleteDocuments(String database, String document, List<String> _idLists) {
		MongoClient client = this.openWriteClient();
		MongoDatabase db = client.getDatabase(database);
		
		List<Bson> bsonList = new ArrayList<Bson>();
		for (int i = 0; i < _idLists.size(); i++) {
			Bson bson = Filters.eq("_id", new ObjectId(_idLists.get(i)));
			bsonList.add(bson);
		}
		Bson filter = Filters.or(bsonList);
		db.getCollection(document).deleteMany(filter);
		return true;
	}
	
	//读源
	private MongoClient openReadClient() {
		return MongoUtil.getMongoReadClient();
	}
	//写源
	private MongoClient openWriteClient() {
		return MongoUtil.getMongoReadClient();
	}
	
	/**
	 * 判断是否存在
	 * 
	 * @param collection
	 * @param document
	 * @param columnname
	 * @param columnValue
	 * @param columnname2
	 * @param columnValue2
	 * @return
	 */
	public boolean whetherexist(String collection, String document, String columnname, String columnValue,
			String columnname2, String columnValue2) {
		//打开读源
		MongoClient client = this.openReadClient();
		DB db = client.getDB(collection);
		
		BasicDBObject query = new BasicDBObject();
		query.put(columnname2, columnValue2);
		query.put(columnname, columnValue);
		
		int iterable = db.getCollection(document).find(query).count();
		
		//client.close();
		//client = null;//一定要写这句话，不然系统不会回收，只是关闭了，连接存在。

		if (iterable != 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 *  指定字段查询
	 * @param collection
	 * @param document
	 * @param columnname
	 * @param columnValue
	 * @param targetColumn
	 * @return
	 */
	public String getOneColumn(String collection, String document, String columnname, String columnValue,
			final String targetColumn) {
		//使用读源
		MongoClient client = this.openReadClient();
		MongoDatabase db = client.getDatabase(collection);
		
		
		final ArrayList<String> a = new ArrayList<String>();
		FindIterable<Document> iterable = db.getCollection(document).find(new Document(columnname, columnValue));
		iterable.forEach(new Block<Document>() {

			public void apply(Document document) {

				for (String set : document.keySet()) {
					if (set.equals(targetColumn) == true) {
						a.add(document.get(set).toString());
					}
				}
			}
		});
		if (a.size() == 0) {
			return "null";
		}
		
		//client.close();
		//client = null;//一定要写这句话，不然系统不会回收，只是关闭了，连接存在。
		
		String b = a.get(0);
		return b;
	}
	
	/**
	 * 获取数据
	 * 
	 * @param collection
	 * @param document
	 * @param mainColumn
	 * @param mainValue
	 * @return
	 */
	public int getRows(String collection, String document, String mainColumn, String mainValue) {

		MongoClient client = this.openReadClient();
		DB db = client.getDB(collection);
		
		BasicDBObject query = new BasicDBObject();
		if (mainColumn == null) {
			query = null;
		} else {
			query.put(mainColumn, mainValue);
		}
		int iterable = db.getCollection(document).find(query).count();
		
		//client.close();
		//client = null;//一定要写这句话，不然系统不会回收，只是关闭了，连接存在。
		return iterable;

	}
	
	
	/**
	 * 根据时间段查询并且排序
	 * 
	 * @param dbName
	 * @param collection
	 * @param timeColum
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Document> getInfoByTime(String dbName, String collection, String timeColum,
			Map<String, String> paramMap, String startTime, String endTime) {
		
		Document mainconditionDoc = new Document();  //主条件
		if(paramMap!=null){
			Set<String> keys = paramMap.keySet();
			for(String key:keys){
				mainconditionDoc.append(key, paramMap.get(key));
			}
		}
		MongoClient client = this.openReadClient();
		MongoDatabase db = client.getDatabase(dbName);
		
		MongoCollection<Document> col = db.getCollection(collection);
		Document timeConditionDoc = new Document();
		timeConditionDoc.append("$gte", startTime);
		timeConditionDoc.append("$lt", endTime);
		
		mainconditionDoc.append(timeColum, timeConditionDoc);
		FindIterable<Document> findRefResult = col.find(mainconditionDoc).sort(new BasicDBObject(timeColum,1));
		List<Document> resultList = new ArrayList<>();
		for (Document item : findRefResult) {
			resultList.add(item);
		}
		
		//client.close();
		//client = null;//一定要写这句话，不然系统不会回收，只是关闭了，连接存在。
		return resultList;
	}
	
	/**6
	 *根据一个条件和时间段查询
	 * @param database数据库
	 * @param document表
	 * @param columnName列名
	 * @param columnValue值
	 * @param startTime开始时间
	 * @param endTime结束时间
	 * @return
	 */
	public List<Document> listDocumentByTime(String database,String document,String columnName,String columnValue,String timeName, String startTime,String endTime){
		
		MongoClient client=this.openReadClient();
		MongoDatabase db=client.getDatabase(database);
		List<Document> list=new ArrayList<>();
		MongoCollection<Document> cli=db.getCollection(document);
		Document mainDocument=new Document();
		mainDocument.put(columnName, columnValue);
		Document timeDocument=new Document();
		timeDocument.put("$gte", startTime);
		timeDocument.put("$lte", endTime);
		mainDocument.put(timeName, timeDocument);
		//时间升序排序
		BasicDBObject sortObject=new BasicDBObject();
		sortObject.put(timeName, 1);
		FindIterable<Document> findIterable=cli.find(mainDocument).sort(sortObject);
		for(Document doc:findIterable){
			list.add(doc);
		}
		return list;
	}
	
	public void test() {
		MongoClient client=this.openReadClient();
		MongoDatabase db=client.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("IDSWtest");
		FindIterable<Document> findIterable=collection.find();
		if(findIterable.iterator().hasNext()) {
			System.out.println(findIterable.first());
		}
	}
	//测试
	public static void main(String[] args) {
		
		//更新
//		Map<String,Object> desMap = new HashMap<String,Object>(); 
//		desMap.put("IDSWID", "IDSW-J1121");
		//desMap.put("AvpTime", "20160918134852");
//		desMap.put("version", "dd");
		//System.out.println(mongo.updateDataByMutilCondition("IDSW", "AVP", paramMap, desMap));
//		MongoDB db = new MongoDB();
//		Map<String,String> queryMap = new HashMap<String,String>(); 
//		queryMap.put("IMEI", "459432812639566");
//		List<Document> gg = db.getInfoByTime("IDSW", "GtDrivePeriodData","time", queryMap,"20170101000000","20170901000000");
//		for(Document document : gg) {
//			System.out.println(document);
//		}
		MongoDB db1 = new MongoDB();
		db1.test();
//		System.out.println(db.getInfoByTime("IDSW", "GtDrivePeriodData","time", queryMap,"20170101000000","20170901000000"));
		//删除
//		List<String> idList = new ArrayList<String>();
//		idList.add("58b425e8cc66fb592481bfcf");
		//System.out.println(mongo.deleteDocuments("IDSW", "AVP", idList));
	}
}
