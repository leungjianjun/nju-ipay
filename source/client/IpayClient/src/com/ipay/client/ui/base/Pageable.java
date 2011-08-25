/**
 * 
 */
package com.ipay.client.ui.base;

/**
 * @author tangym
 *
 */
public interface Pageable {
	
	/**
	 * 获取下一页内容
	 */
	public  void nextPage();
	
	/**
	 * 
	 * @return int 当前的页码
	 */
	public int getCurrentPage();

}
