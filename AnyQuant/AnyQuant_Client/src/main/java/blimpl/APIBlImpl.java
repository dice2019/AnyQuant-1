package blimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import po.StockPO;
import util.MyTime;
import vo.BenchMarkVO;
import vo.StockVO;
import blservice.APIBlservice;
import dataservice.APIInterface;
import dataservice.APIInterfaceImpl;
import enumeration.MyDate;
import enumeration.Stock_Attribute;

/**
 * API逻辑层实现
 * @author czq
 * @date 2016年3月4日
 */
public class APIBlImpl implements APIBlservice {
	/**
	 * 单例模式
	 */
	private static APIBlservice APIBlservice;
	private APIInterface APIDataSer; 
	/**
	 * 两份股票的数据，Collection版便于排序，Map版便于查找
	 */
	private List<StockVO> stocks;
	private Map<String , StockVO> stockMap;
	/**
	 * 大盘数据
	 */
	private List<BenchMarkVO> benchMarkVOs;
	
	
	private APIBlImpl() {
		APIDataSer = new APIInterfaceImpl();
		List<String> stocksCode = APIDataSer.getAllStocks();
		stocks = new ArrayList<StockVO>(stocksCode.size());
//		benchMarkVOs = APIDataSer.
		stockMap = new HashMap<String, StockVO>(3000);
		System.out.println(stocksCode.size());
		int count = 0;
		for (String string : stocksCode) {
//			System.out.println(string);
//			System.out.println(APIDataSer.getStockMes(string));
//			System.out.println(APIDataSer.getStockMes(string).getHigh());
			StockVO tmp =  (StockVO) VOPOchange.POtoVO(APIDataSer.getStockMes(string));
			stockMap.put(string, tmp);
			count ++;
			if(count > 100){
				break;
			}
		}
		
		stocks = new ArrayList<StockVO>(stockMap.values());
		
	}
	
	public static APIBlservice getAPIBLService(){
		if(APIBlservice == null){
			APIBlservice = new APIBlImpl();
		}
		return APIBlservice;
	}
	
	
	@Override
	public Iterator<StockVO> getAllStocks() {
		
		return stocks.iterator();
	}

	@Override
	public Iterator<StockVO> getSortStocks(boolean isUp, Stock_Attribute attr) {
		return StockSortHelper.sortStocks(stocks, attr , isUp);
	}

	@Override
	public Iterator<StockVO> getSortStocksInScope(boolean isUp,
			Stock_Attribute attr, List<String> stocksCode) {
		List<StockVO> stocks = new ArrayList<StockVO>(stocksCode.size());
		for (String string : stocksCode) {
			stocks.add(stockMap.get(string));
		}
		return StockSortHelper.sortStocks(stocks, attr, isUp);
	}

	@Override
	public Iterator<BenchMarkVO> getAllBenchMarks() {
		// TODO Auto-generated methsod stub
		return null;
	}

	@Override
	public Iterator<StockVO> getRecentStocks(String stockCode) {
		return getStocksByTime(stockCode, MyTime.getAnotherDay(-30) , MyTime.getToDay());
	}

	@Override
	public Iterator<StockVO> getStocksByTime(String stockCode , MyDate start, MyDate end) {
		List<StockPO> pos = APIDataSer.getStockMes(stockCode, start, end);
		if(pos != null){
			List<StockVO> result = new ArrayList<StockVO>(pos.size());
			for (StockPO stockPO : pos) {
				result.add((StockVO) VOPOchange.POtoVO(stockPO));
			}
			return result.iterator();
		}else{
			return null;
		}
	}

	@Override
	public Iterator<StockVO> getStocksByStockCode(String code) {
		List<StockVO> result = new ArrayList<StockVO>();
		
		for (Iterator<String> iterator = stockMap.keySet().iterator(); iterator.hasNext();) {
			String temp = iterator.next();
			if(temp.contains(code)){
				result.add(stockMap.get(temp));
			}
			if(result.size() > 10){
				break;
			}
		}
		
		return result.iterator();
	}

	public static void main(String[] args) {
		APIBlImpl imp = new APIBlImpl();
		imp.getAllStocks();
	}
}
