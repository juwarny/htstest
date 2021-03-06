package GUI;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import stock.StockCode;

/**
 * @author Christopher Deckers
 */
//종목명을 선택해서 관련 뉴스를 띄워주는 네이티브 브라우저 패널 클라스
public class FinanceNews2 extends JPanel {

	private String address_front;
	private String address_back;
	private String code;
	private StockCode stc;
	private ArrayList<String> stclist_name;
	private AutoSuggest itemCode_comboBoxs;
	private JWebBrowser webBrowser;
	private String address;
	
  public FinanceNews2(String itemcode, ArrayList<String> list) {
    super(new BorderLayout());    
    this.code = itemcode.substring(1);
    setLink();
    stc = new StockCode();
    stclist_name = list;
    	
	itemCode_comboBoxs = new AutoSuggest(stclist_name.toArray());
	itemCode_comboBoxs.setEditable(true);
	itemCode_comboBoxs.addItemListener(new ItemCodeListener());
	
    JPanel webBrowserPanel = new JPanel(new BorderLayout());
    webBrowser = new JWebBrowser();    
    webBrowser.navigate(address);
    webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
    webBrowserPanel.add(itemCode_comboBoxs, BorderLayout.NORTH);
    add(webBrowserPanel, BorderLayout.CENTER);       
  }
  
  public void setLink(){//선택된 종목 뉴스로 주소로 세팅
	  address_front = "http://news.moneta.co.kr/Service/stock/ShellList.asp?LinkID=263&NewsSetID=1696&stockcode=";
	  address_back = "&ModuleID=282";
	  address = address_front+code+address_back;
  }  
 
  public class ItemCodeListener implements ItemListener{//해당 종목 뉴스로 주소 변경후 화면 갱신
		public void itemStateChanged(ItemEvent e) {
			 if (e.getStateChange() == ItemEvent.SELECTED) {
				 try{					 	
					 	code = stc.NameToCode(e.getItem().toString());
					 	code = code.substring(1);
					 	setLink();
					 	webBrowser.navigate(address);
					 	
					}catch(Exception ex){
						ex.printStackTrace();
					}			        
			    }			
		}
  }
}