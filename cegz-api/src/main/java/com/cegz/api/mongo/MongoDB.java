package com.cegz.api.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Component(value = "mongodb")
public class MongoDB {

	@Autowired
	private MongoDbFactory mongodbFactory;
	/**
	 * 获得第一条数据
	 * @param database 数据库
	 * @param document 表
	 * @param queryMap 条件
	 * @return
	 */
	public Document getFirstData(String document, Map<String, Object> queryMap) {
		
		MongoDatabase db = mongodbFactory.getDb();
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
	public Document getCurrentData(String document, String timeComlum , Map<String, Object> queryMap) {
		MongoDatabase db =mongodbFactory.getDb();
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
	public List<Document> getDataById(String collection,BasicDBList idList){ 
				
			
			Document mainconditionDoc = new Document();  //主条件
			
			MongoDatabase db = mongodbFactory.getDb();
			
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
	public boolean isDataExistBetween(String document, Map<String, String> queryMap) {

		MongoDatabase db = mongodbFactory.getDb();
		
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
	public boolean insertListDocuments(String document, 
			List<Document> documentList){
		try {
			MongoDatabase db = mongodbFactory.getDb();
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
	public boolean dataInsert(String document, Map<String, Object> dataMap) {
		try {
			MongoDatabase db = mongodbFactory.getDb();
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
	public boolean isExistBiggerData(String document,Map<String, Object> queryMap, String columName, int value) {
		MongoDatabase db = mongodbFactory.getDb();
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
	public List<Document> getByMutilCondition( String document, Map<String, Object> conditionParams) {
		
		MongoDatabase db  = mongodbFactory.getDb();
		
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
	public boolean updateDataByMutilCondition(String document, 
		Map<String, Object> condition, Map<String, Object> dest) {
		
		MongoDatabase db =  mongodbFactory.getDb();
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
	public boolean deleteDocuments(String document, List<String> _idLists) {
		MongoDatabase db =mongodbFactory.getDb();
		
		List<Bson> bsonList = new ArrayList<Bson>();
		for (int i = 0; i < _idLists.size(); i++) {
			Bson bson = Filters.eq("_id", new ObjectId(_idLists.get(i)));
			bsonList.add(bson);
		}
		Bson filter = Filters.or(bsonList);
		db.getCollection(document).deleteMany(filter);
		return true;
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
	public String getOneColumn( String document, String columnname, String columnValue,
			final String targetColumn) {
		//使用读源
		MongoDatabase db = mongodbFactory.getDb();
		
		
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
	 * 根据时间段查询并且排序
	 * 
	 * @param dbName
	 * @param collection
	 * @param timeColum
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Document> getInfoByTime( String collection, String timeColum,
			Map<String, String> paramMap, String startTime, String endTime) {
		
		Document mainconditionDoc = new Document();  //主条件
		if(paramMap!=null){
			Set<String> keys = paramMap.keySet();
			for(String key:keys){
				mainconditionDoc.append(key, paramMap.get(key));
			}
		}
		MongoDatabase db = mongodbFactory.getDb();
		
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
	public List<Document> listDocumentByTime(String document,String columnName,String columnValue,String timeName, String startTime,String endTime){
		
		MongoDatabase db=mongodbFactory.getDb();
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
	
}
