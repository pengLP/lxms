package lxms.lucene;

import java.io.IOException;
import java.nio.file.Paths;
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
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import lxms.entity.User;
import lxms.entity.Wanted;
/**
 * 悬赏索引
 * @author Administrator
 *
 */
public class WantedIndex {
	private Directory directory;

	/**
	 * 创建IndexWriter
	 * 
	 * @return
	 * @throws IOException
	 */
	public IndexWriter getWriter() throws IOException {
		directory = FSDirectory.open(Paths.get("/opt/lucene/Wanted"));   ///opt/lucene/Wanted
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		return indexWriter;
	}

	/**
	 * 添加悬赏索引
	 * 
	 * @param route
	 * @throws IOException
	 */
	public void addIndex(Wanted wanted) throws IOException {
		IndexWriter indexWriter = getWriter();
		Document document = new Document();
		document.add(new StringField("wid", String.valueOf(wanted.getWid()), Field.Store.YES));
		document.add(new StringField("productName", wanted.getProductName(), Field.Store.YES));
		document.add(new StringField("total", String.valueOf(wanted.getTotal()), Field.Store.YES));
		document.add(new StringField("num", wanted.getNum(), Field.Store.YES));
		document.add(new StringField("uid", String.valueOf(wanted.getUser().getUid()), Field.Store.YES));
		document.add(new StringField("picture",wanted.getPicture(), Field.Store.YES));
		document.add(new StringField("status", wanted.getStatus(), Field.Store.YES));
		document.add(new StringField("deliveryArea",wanted.getDeliveryArea(), Field.Store.YES));
		document.add(new StringField("deliveryTime", String.valueOf(wanted.getDeliveryTime()), Field.Store.YES));
		indexWriter.addDocument(document);
		indexWriter.close();
	}

	/**
	 * 删除悬赏索引
	 * 
	 * @param rid
	 * @throws IOException
	 */
	public void deleteIndex(Long wid) throws IOException {
		IndexWriter indexWriter = getWriter();
		indexWriter.deleteDocuments(new Term("wid", String.valueOf(wid))); //
		indexWriter.forceMergeDeletes();
		indexWriter.commit();
		indexWriter.close();
	}

	/**
	 * 更新悬赏索引
	 * 
	 * @param route
	 * @throws IOException
	 */
	public void updateIndex(Wanted wanted) throws IOException {
		IndexWriter indexWriter = getWriter();
		Document document = new Document();
		document.add(new StringField("wid", String.valueOf(wanted.getWid()), Field.Store.YES));
		document.add(new StringField("productName", wanted.getProductName(), Field.Store.YES));
		document.add(new StringField("total", String.valueOf(wanted.getTotal()), Field.Store.YES));
		document.add(new StringField("num", wanted.getNum(), Field.Store.YES));
		document.add(new StringField("uid", String.valueOf(wanted.getUser().getUid()), Field.Store.YES));
		document.add(new StringField("picture",wanted.getPicture(), Field.Store.YES));
		document.add(new StringField("status", wanted.getStatus(), Field.Store.YES));
		document.add(new StringField("deliveryArea",wanted.getDeliveryArea(), Field.Store.YES));
		document.add(new StringField("deliveryTime", String.valueOf(wanted.getDeliveryTime()), Field.Store.YES));
		indexWriter.updateDocument(new Term("wid", String.valueOf(wanted.getWid())), document);

		indexWriter.close();
	}

	/**
	 * 根据商品名搜索
	 * 
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public List<Wanted> searchWanted(String queryString) throws Exception {
		directory = FSDirectory.open(Paths.get("/opt/lucene/Wanted"));
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		QueryParser parser = new QueryParser("productName", analyzer);
		Query query = parser.parse(queryString);
		booleanQuery.add(query, BooleanClause.Occur.SHOULD);
		TopDocs hits = indexSearcher.search(booleanQuery.build(), 100);
		List<Wanted> wantedList = new LinkedList<Wanted>();
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = indexSearcher.doc(scoreDoc.doc);
			//wid,productName,total,num,uid,picture,status,deliveryArea,deliveryTime		
			Wanted wanted = new Wanted();
			wanted.setWid(Long.parseLong(doc.get("wid")));
			User user = new User();
			user.setUid(Long.parseLong(doc.get("uid")));
			wanted.setUser(user);
			wanted.setProductName(doc.get("productName"));
			wanted.setTotal(Double.parseDouble(doc.get("total")));
			wanted.setNum(doc.get("num"));
			wanted.setPicture(doc.get("picture"));
			wanted.setStatus(doc.get("status"));
			wanted.setDeliveryArea(doc.get("deliveryArea"));
			wanted.setDeliveryTime(Integer.parseInt(doc.get("deliveryArea")));
			wantedList.add(wanted);
		}
		return wantedList;
	}
}
