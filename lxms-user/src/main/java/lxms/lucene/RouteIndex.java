package lxms.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.RangeFilterBuilder;
import org.apache.lucene.queryparser.xml.builders.RangeQueryBuilder;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FilteredQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import lxms.entity.Route;
import lxms.entity.User;
import lxms.utils.DateUtil;

/**
 * 
 * @author Yanlihui 行程索引
 */

public class RouteIndex {
	private Directory directory;

	/**
	 * 创建IndexWriter
	 * 
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getWriter() throws IOException {
		directory = FSDirectory.open(Paths.get("/opt/lucene/Route"));
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		return indexWriter;
	}

	/**
	 * 添加行程索引
	 * 
	 * @param route
	 * @throws IOException
	 */
	public void addIndex(Route route) throws IOException {
		IndexWriter indexWriter = getWriter();
		Document document = new Document();
		document.add(new StringField("rid", String.valueOf(route.getRid()), Field.Store.YES));
		document.add(new StringField("uid", String.valueOf(route.getUser().getUid()), Field.Store.YES));
		document.add(new StringField("name", route.getUser().getName(), Field.Store.YES));
		document.add(new StringField("head", route.getUser().getHead(), Field.Store.YES));
		document.add(new StringField("startPlace", route.getStartPlace(), Field.Store.YES));
		document.add(new StringField("travelPlace", route.getTravelPlace(), Field.Store.YES));
		document.add(new StringField("mainCity", route.getMainCity(), Field.Store.YES));
		document.add(
				new StringField("returnDate", DateUtil.formatDate(route.getReturnDate(), "yyyyMMdd"), Field.Store.YES));
		document.add(
				new StringField("travelDate", DateUtil.formatDate(route.getTravelDate(), "yyyyMMdd"), Field.Store.YES));
		indexWriter.addDocument(document);
		indexWriter.close();
	}

	/**
	 * 删除行程索引
	 * 
	 * @param rid
	 * @throws IOException
	 */
	public void deleteIndex(Long rid) throws IOException {
		IndexWriter indexWriter = getWriter();
		indexWriter.deleteDocuments(new Term("rid", String.valueOf(rid))); //
		indexWriter.forceMergeDeletes();
		indexWriter.commit();
		indexWriter.close();
	}

	/**
	 * 更新行程索引
	 * 
	 * @param route
	 * @throws IOException
	 */
	public void updateIndex(Route route) throws IOException {
		IndexWriter indexWriter = getWriter();
		Document document = new Document();
		document.add(new StringField("rid", String.valueOf(route.getRid()), Field.Store.YES));
		document.add(new StringField("uid", String.valueOf(route.getUser().getUid()), Field.Store.YES));
		document.add(new StringField("name", route.getUser().getName(), Field.Store.YES));
		document.add(new StringField("head", route.getUser().getHead(), Field.Store.YES));
		document.add(new StringField("startPlace", route.getStartPlace(), Field.Store.YES));
		document.add(new StringField("travelPlace", route.getTravelPlace(), Field.Store.YES));
		document.add(new StringField("mainCity", route.getMainCity(), Field.Store.YES));
		document.add(
				new StringField("returnDate", DateUtil.formatDate(route.getReturnDate(), "yyyyMMdd"), Field.Store.YES));
		document.add(
				new StringField("travelDate", DateUtil.formatDate(route.getTravelDate(), "yyyyMMdd"), Field.Store.YES));
		indexWriter.updateDocument(new Term("rid", String.valueOf(route.getRid())), document);

		indexWriter.close();
	}

	/**
	 * 根据目的地国家和城市搜索
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public List<Route> searchRoute(String queryString) throws Exception{
		directory = FSDirectory.open(Paths.get("/opt/lucene/Route"));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		QueryParser parser = new QueryParser("travelPlace",analyzer);
		Query query = parser.parse(queryString);	
		QueryParser parser2 = new QueryParser("mainCity",analyzer);
		Query query2 = parser2.parse(queryString);
		
		//-------查找时间范围内的数据
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		
		
		calendar.set(2000, 0, 0);  
		String future = df.format(calendar.getTime());
		Term startTime = new Term("returnDate",today);
		Term endTime = new Term("returnDate",future);
		//2000-now 的数据范围not
		TermRangeQuery termRangeQuery = new TermRangeQuery("returnDate", endTime.bytes() ,startTime.bytes(), true, false);
		
		//-------new TermRangeQuery("returnDate",startTime, endTime, true, true);
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);   // or
		booleanQuery.add(query2, BooleanClause.Occur.SHOULD);  // or
		booleanQuery.add(termRangeQuery, BooleanClause.Occur.MUST_NOT);  // not
		TopDocs hits = indexSearcher.search(booleanQuery.build(),100);
		List<Route> routeList = new LinkedList<Route>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc = indexSearcher.doc(scoreDoc.doc);
			Route route = new Route();
			route.setRid(Long.parseLong(doc.get("rid")));
			User user = new User();
			user.setUid(Long.parseLong(doc.get("uid")));
			user.setHead(doc.get("head"));
			user.setName(doc.get("name"));
			route.setUser(user);
			route.setMainCity(doc.get("mainCity"));
			route.setTravelPlace(doc.get("travelPlace"));
			route.setStartPlace(doc.get("startPlace"));
			route.setTravelDate(DateUtil.formatString(doc.get("travelDate"),"yyyyMMdd"));
			route.setReturnDate(DateUtil.formatString(doc.get("returnDate"),"yyyyMMdd"));
			routeList.add(route);
		}
		return routeList;
	}
}
