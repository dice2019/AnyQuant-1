package dataservice;

import java.util.Iterator;
import java.util.List;

import po.BenchMarkPO;
import po.StockPO;
import enumeration.Exchange;
import enumeration.MyDate;

/**
 * API接口定义
 *
 * @author ss,czq
 *
 */
public interface APIInterface {

	/**
	 * 下列重载方法用于获取股票代码 Exchange 为交易所枚举类型
	 *
	 * @return
	 */

	/**
	 * 返回2015年上海和深圳的全部股票代码
	 *
	 * @return
	 */
	public List<String> getAllStocks();

	public List<String> getAllStocks(int year);

	public List<String> getAllStocks(Exchange exchange);

	public List<String> getAllStocks(int year, Exchange exchange);

	/**
	 * 下列方法用于获取某只股票的具体信息，参数的个数为任意多的Stock_Attribute枚举类型
	 *
	 * @param stockCode
	 *            股票代码
	 * @param fields
	 *            属性
	 * @return
	 */

	/**
	 * 只需要传入股票的代码例如"sh600126"，返回最新信息
	 *
	 * @param stockCode
	 * @return
	 */
	public StockPO getStockMes(String stockCode);
	/**
	 *增加了时间限制，可以设置只查看某一天的数据
	 * @param stockCode
	 * @param date
	 * @return
	 */
	public StockPO getStockMes(String stockCode,MyDate date);


	/**
	 * 增加了时间限制，可以查看某段时间内的数据
	 *
	 * @param stockCode
	 * @param fields
	 * @return
	 */
	public List<StockPO> getStockMes(String stockCode, MyDate start, MyDate end);

	/**
	 * 返回当天的全部股票具体信息
	 * @return
	 */
	public List<StockPO> getAllStockMes();



	/**
	 * 返回所有可用的大盘的代号
	 * @return
	 */
	public List<String> getAllBenchMarks();

	/**
	 * 只需要传入大盘的代码例如"hs300"，返回当天的全部信息
	 *
	 * @param benchCode
	 * @return
	 */
	public BenchMarkPO getBenchMes(String benchCode);
	/**
	 * 增加了时间限制,可以查看某一天的大盘的数据
	 * @param benchCode
	 * @param date
	 * @return
	 */
	public BenchMarkPO getBenchMes(String benchCode,MyDate date);

	/**
	 * 增加了时间限制，可以查看某段时间内的大盘数据
	 *
	 * @param benchCode
	 * @param start
	 * @param end
	 * @return
	 */
	public List<BenchMarkPO> getBenchMes(String benchCode, MyDate start,
			MyDate end);

    /**
     * 返回全部大盘的当日数据
     * @return
     */
	public List<BenchMarkPO>  getAllBenchMes();


	/**
	 * Get the customize stocks' last transaction data
	 * @return the iteration of the collection that contains the optional stocks with the latest data
	 */
	public Iterator<StockPO> getOptionalStocks() ;

	/**
	 * delete stockCode of the customize stocks
	 * @return whether the operation is successfully done<br>
	 * 		            when the stockCode doesn't exist,return false
	 */
	public boolean deleteOptionalStock(String stockCode);
	/**
	 * add stockCode of the customize stocks
	 * @param stockCode
	 * @return whether the operation is successfully done<br>
	 * 		            when the stockCode doesn't exist,or it has exist in the collection ,return false
	 */
	public boolean addOptionalStock(String stockCode);

	public List<String> getSelectedStockCodes();
	/**
	 * clear all the optional stocks
	 * @return success or not
	 */
	public boolean clearOptionalStocks();
}
